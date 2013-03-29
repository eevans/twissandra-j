/*
 * Copyright 2013, Matt Brozowski and Eric Evans
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
