package org.sree.rest.service;

import java.util.List;

import org.sree.rest.model.Feed;

public interface FeedService {
	
	boolean saveFeeds(String userId, List<String> feeds);
	
	List<Feed> getFeeds();

}
