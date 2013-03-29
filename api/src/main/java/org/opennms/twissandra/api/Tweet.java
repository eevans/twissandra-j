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
	
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append(postedBy).append(": ").append(body).append(" (").append(postedAt).append(")");
		return buf.toString();
	}
}
