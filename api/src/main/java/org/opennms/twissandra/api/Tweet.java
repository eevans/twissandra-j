package org.opennms.twissandra.api;

import java.util.Date;
import java.util.UUID;

public class Tweet implements Comparable<Tweet> {
	
	private final UUID id;
	private final Date postedAt;
	private final String postedBy;
	private final String body;

	public Tweet(String postedBy, String body, UUID id, Date postedAt) {
		this.postedBy = postedBy;
		this.body = body;
		this.id = id;
		this.postedAt = postedAt;
	}
	
	public UUID getId() {
		return id;
	}
	public String getPostedBy() {
		return postedBy;
	}
	public String getBody() {
		return body;
	}
	public Date getPostedAt() {
		return postedAt;
	}
	public int compareTo(Tweet o) {
		return this.postedAt.compareTo(o.postedAt);
	}
}
