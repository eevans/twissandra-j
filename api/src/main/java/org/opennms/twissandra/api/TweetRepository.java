package org.opennms.twissandra.api;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TweetRepository {
	
	String getPassword(String username);
	
	List<String> getFriends(String username);
	
	List<String> getFollowers(String username);
	
	List<Tweet> getUserline(String username, Date start, int limit);
	
	List<Tweet> getTimeline(String username, Date start, int limit);
	
	List<Tweet> getTweets(Date start, int limit);
	
	Tweet getTweet(UUID id);
	
	void saveUser(String username, String password);
	
	Tweet saveTweet(String username, String body);
	
	void addFriend(String username, String friend);
	
	void removeFriend(String username, String friend);

}
