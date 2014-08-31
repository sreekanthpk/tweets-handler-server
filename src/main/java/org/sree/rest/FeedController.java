package org.sree.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sree.rest.model.Feed;
import org.sree.rest.service.FeedService;

@Controller
public class FeedController {
	
	@Autowired
	FeedService feedService;
	
	 @RequestMapping(value="/feed/store/{userId}", method=RequestMethod.POST)
	    public @ResponseBody boolean storeFeeds(	    		
	        @PathVariable(value="userId") String userId, @RequestBody List<String> feeds) {		 
	        return feedService.saveFeeds(userId, feeds);
	    }
	 
	 @RequestMapping(value="/feed/get", method=RequestMethod.GET)
	 	public @ResponseBody List<Feed> getFeeds() {		 
		 	return feedService.getFeeds();		 
	 }
}
