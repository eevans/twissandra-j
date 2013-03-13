package org.opennms.twissandra.persistence.memory.internal;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.opennms.twissandra.api.Tweet;
import org.opennms.twissandra.api.TweetRepository;

public class MemoryTweetRepository implements TweetRepository {

	public String getPassword(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getFriends(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getFollowers(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Tweet> getUserline(String username, Date start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Tweet> getTimeline(String username, Date start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public Tweet getTweet(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveUser(String username, String password) {
		// TODO Auto-generated method stub
		
	}

	public void saveTweet(String username, String body) {
		// TODO Auto-generated method stub
		
	}

	public void addFriend(String username, String friend) {
		// TODO Auto-generated method stub
		
	}

	public void removeFriend(String username, String friend) {
		// TODO Auto-generated method stub
		
	}

}
