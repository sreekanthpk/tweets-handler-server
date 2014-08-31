package org.sree.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sree.rest.model.Feed;
import org.sree.storage.FeedRepository;

@Service
public class FeedServiceImpl implements FeedService{
	
	@Autowired
	FeedRepository feedRepository;	
	
	@Override
	public boolean saveFeeds(String userId, List<String> feeds) {
		try{
			for (String string : feeds) {
				feedRepository.put(userId, string);
			}
			return true;
		}catch(Exception e){
			return false;
		}
		
	}


	@Override
	public List<Feed> getFeeds() {
		return feedRepository.getFeeds();
	}

}
