package org.opennms.twissandra.api;

import java.util.UUID;

public class Tweet {
	
	private UUID id;
	private String postedBy;
	private String body;
	
	public Tweet() {}
	
	public Tweet(String postedBy, String body) {
		this.postedBy = postedBy;
		this.body = body;
	}
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getPostedBy() {
		return postedBy;
	}
	public void setPostedBy(String postedBy) {
		this.postedBy = postedBy;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	

}
