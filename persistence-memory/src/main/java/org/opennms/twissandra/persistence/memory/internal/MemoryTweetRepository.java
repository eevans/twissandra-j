package org.opennms.twissandra.persistence.memory.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.UUID;

import org.opennms.twissandra.api.Tweet;
import org.opennms.twissandra.api.TweetRepository;

public class MemoryTweetRepository implements TweetRepository {
	
	Map<String, String> m_passwords = new HashMap<String, String>();
	
	Map<String, Set<String>> m_friends = new HashMap<String, Set<String>>();
	
	Map<String, Set<String>> m_followers = new HashMap<String, Set<String>>();

	Map<UUID, Tweet> m_tweets = new HashMap<UUID, Tweet>();
	
	SortedSet<Tweet> m_sorted_tweets = new TreeSet<Tweet>();

	public String getPassword(String username) {
		return m_passwords.get(username);
	}

	public List<String> getFriends(String username) {
		Set<String> friends = m_friends.get(username);
		return friends == null ? Collections.<String>emptyList() : new ArrayList<String>(friends);
	}

	public List<String> getFollowers(String username) {
		Set<String> followers = m_followers.get(username);
		return followers == null ? Collections.<String>emptyList() : new ArrayList<String>(followers);
	}

	public List<Tweet> getUserline(String username, Date start, int limit) {
		List<Tweet> results = new ArrayList<Tweet>();

		for (Tweet tweet : m_sorted_tweets) {
			if (tweet.getPostedBy().equals(username) && tweet.getPostedAt().after(start) && results.size() < limit)
				results.add(tweet);
		}

		return results;
	}

	public List<Tweet> getTimeline(String username, Date start, int limit) {
		Set<String> friends = new HashSet<String>(getFriends(username));
		friends.add(username);

		List<Tweet> results = new ArrayList<Tweet>();

		for (Tweet tweet : m_sorted_tweets) {
			if (friends.contains(tweet.getPostedBy()) && tweet.getPostedAt().after(start) && results.size() < limit)
				results.add(tweet);
		}

		return results;
	}

	public Tweet getTweet(UUID id) {
		return m_tweets.get(id);
	}

	public void saveUser(String username, String password) {
		m_passwords.put(username, password);
	}

	public Tweet saveTweet(String username, String body) {
		Tweet tweet = new Tweet(username, body, UUID.randomUUID(), new Date());
		m_tweets.put(tweet.getId(), tweet);
		m_sorted_tweets.add(tweet);
		return tweet;
	}

	public void addFriend(String username, String friend) {
		Set<String> friends = m_friends.get(username);
		if (friends == null) {
			friends = new HashSet<String>();
			m_friends.put(username, friends);
		}
		friends.add(friend);
	}

	public void removeFriend(String username, String friend) {
		Set<String> friends = m_friends.get(username);
		if (friends != null)
			friends.remove(friend);
	}

	public List<Tweet> getTweets(Date start, int limit) {
		List<Tweet> results = new ArrayList<Tweet>();

		for (Tweet tweet : m_sorted_tweets) {
			if (tweet.getPostedAt().after(start) && results.size() < limit)
				results.add(tweet);
		}

		return results;
	}
}
