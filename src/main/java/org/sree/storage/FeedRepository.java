package org.sree.storage;

import java.util.List;

import org.sree.rest.model.Feed;

public interface FeedRepository {	
	 void put(String key, String value);
	
	 String get(String key);

	 List<Feed> getFeeds();
}
