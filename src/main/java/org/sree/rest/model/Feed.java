package org.sree.rest.model;

import java.util.List;

public class Feed {
	
	String userId;
	List<String> feeds;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<String> getFeeds() {
		return feeds;
	}
	public void setFeeds(List<String> feeds) {
		this.feeds = feeds;
	}
	

}
