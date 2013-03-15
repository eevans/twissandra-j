package org.opennms.twissandra.api;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TweetRepository {
	
	String getPassword(String username);
	
	List<String> getFriends(String username);
	
	List<String> getFollowers(String username);
	
	/**
	 * Returns a userline.  A userline is a materialized view of the
	 * tweets made by a specific user.
	 * 
	 * @param username the user this view pertains to.
	 * @param start the starting date; the most recent tweet to include.
	 * @param limit maximum number of tweets to return.
	 * @return a list of Tweet instances.
	 */
	List<Tweet> getUserline(String username, Date start, int limit);
	
	/**
	 * Returns a timeline.  A timeline is the materialized view of tweets made
	 * by the friends of a specific user.
	 * 
	 * @param username the user this view pertains to.
	 * @param start the starting date; the most recent tweet to include.
	 * @param limit maximum number of tweets to return.
	 * @return a list of Tweet instances.
	 */
	List<Tweet> getTimeline(String username, Date start, int limit);
	
	List<Tweet> getTweets(Date start, int limit);
	
	Tweet getTweet(UUID id);
	
	void saveUser(String username, String password);
	
	Tweet saveTweet(String username, String body);
	
	void addFriend(String username, String friend);
	
	void removeFriend(String username, String friend);

}
