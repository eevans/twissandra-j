package org.opennms.twissandra.persistence.cassandra.internal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.opennms.twissandra.api.Tweet;
import org.opennms.twissandra.api.TweetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;

public class CassandraTweetRepository implements TweetRepository {
	private static final String PUBLIC_USERLINE_KEY = "!PUBLIC!";
	private static final Logger LOG = LoggerFactory.getLogger(CassandraTweetRepository.class);

	private final String m_cassandraHost;
	private final int m_cassandraPort;
	private final String m_keyspaceName;
	private final Cluster cluster;
	private final Session session;

	public CassandraTweetRepository(String cassandraHost, int cassandraPort, String keyspaceName) {
		m_cassandraHost = cassandraHost;
		m_cassandraPort = cassandraPort;
		m_keyspaceName = keyspaceName;
		
		LOG.info("Connecting to {}:{}", cassandraHost, cassandraPort);

		cluster = Cluster.builder().withPort(m_cassandraPort).addContactPoint(m_cassandraHost).build();
		session = cluster.connect(m_keyspaceName);
	}

	public String getPassword(String username) {
		ResultSet queryResult = execute("SELECT password FROM users WHERE username = '%s'", username);
		return getOneRow(queryResult).getString("password");
	}

	public List<String> getFriends(String username) {
		ResultSet queryResult = execute("SELECT followed FROM following WHERE username = '%s'", username);
		List<String> friends = new ArrayList<String>();
		
		for (Row row : queryResult)
			friends.add(row.getString("followed"));
		
		return friends;
	}

	public List<String> getFollowers(String username) {
		ResultSet queryResult = execute("SELECT following FROM followers WHERE username = '%s'", username);
		List<String> followers = new ArrayList<String>();
		
		for (Row row : queryResult)
			followers.add(row.getString("following"));
		
		return followers;
	}

	public List<Tweet> getUserline(String username, Date start, int limit) {
		ResultSet queryResult = execute(
				"SELECT posted_at, body FROM userline WHERE username = '%s' AND posted_at >= '%s' LIMIT %d",
				username,
				formatDate(start),
				limit);
		List<Tweet> tweets = new ArrayList<Tweet>();
		
		for (Row row : queryResult) {
			UUID id = row.getUUID("posted_at");
			tweets.add(new Tweet(username, row.getString("body"), id, fromUUID1(id)));
		}

		return tweets;
	}

	public List<Tweet> getTimeline(String username, Date start, int limit) {
		ResultSet queryResult = execute(
				"SELECT posted_at, posted_by, body FROM timeline WHERE username = '%s' AND posted_at >= '%s' LIMIT %d",
				username,
				formatDate(start),
				limit);
		List<Tweet> tweets = new ArrayList<Tweet>();
		
		for (Row row : queryResult) {
			UUID id = row.getUUID("posted_at");
			tweets.add(new Tweet(row.getString("posted_by"), row.getString("body"), id, fromUUID1(id)));
		}

		return tweets;
	}

	public List<Tweet> getTweets(Date start, int limit) {
		return getUserline(PUBLIC_USERLINE_KEY, start, limit);
	}

	public Tweet getTweet(UUID id) {
		Row row = getOneRow(execute("SELECT username, body FROM tweets WHERE id = %s", id.toString()));
		return new Tweet(row.getString("username"), row.getString("body"), id, fromUUID1(id));
	}

	public void saveUser(String username, String password) {
		execute("INSERT INTO users (username, password) VALUES ('%s', '%s')", username , password);
	}

	public Tweet saveTweet(String username, String body) {
		UUID id = UUIDs.timeBased();

		// Create the tweet in the tweets cf
		execute("INSERT INTO tweets (id, username, body) VALUES (%s, '%s', '%s')",
				id.toString(),
				username,
				body);
		// Store the tweet in this users userline
		execute("INSERT INTO userline (username, posted_at, body) VALUES (%s, '%s', '%s')",
				username,
				id.toString(),
				body);
		// Store the tweet in the public userline
		execute("INSERT INTO userline (username, posted_at, body) VALUES (%s, '%s', '%s')",
				PUBLIC_USERLINE_KEY,
				id.toString(),
				body);

		// Inser the tweet into follower timelines
		for (String follower : getFollowers(username)) {
			execute("INSERT INTO timeline (username, posted_at, posted_by, body) VALUES ('%s', %s, '%s', '%s')",
					follower,
					id.toString(),
					username,
					body);
		}

		return new Tweet(username, body, id, fromUUID1(id));
	}

	public void addFriend(String username, String friend) {
		execute("INSERT INTO following (username, followed) VALUES ('%s', '%s')", username, friend);
		execute("INSERT INTO followers (username, following) VALUES ('%s', '%s')", friend, username);
	}

	public void removeFriend(String username, String friend) {
		execute("DELETE '%s' FROM following WHERE username = '%s'", friend, username);
		execute("DELETE '%s' FROM followers WHERE username = '%s'", username, friend);
	}

	private Row getOneRow(ResultSet result) {
		Row row = result.one();
		if (!result.isExhausted())
			throw new RuntimeException("ResultSet instance contained more than one row!");
		return row;
	}

	private ResultSet execute(String query, Object...parms) {
		String cql = String.format(query, parms);
		LOG.debug("Executing CQL: {}", cql);
		return session.execute(cql);
	}

	private String formatDate(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	private Date fromUUID1(UUID uuid) {
		assert uuid.version() == 1;
		return new Date((uuid.timestamp() / 10000) + -12219292800000L);    // -12219292800000L is the start epoch
	}
}
