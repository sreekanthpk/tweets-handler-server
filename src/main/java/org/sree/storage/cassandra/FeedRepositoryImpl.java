package org.sree.storage.cassandra;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.sree.rest.model.Feed;
import org.sree.storage.FeedRepository;
import org.sree.storage.cassandra.embedded.EmbededCassandraUtil;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

@Repository
public class FeedRepositoryImpl implements FeedRepository{

	private Session session;
	@Value("${cassandra.mode}")
	private CassandraMode cassandraMode;
	
	
	@PostConstruct
	public void init(){
		if(cassandraMode==CassandraMode.EMBEDDED){
			EmbededCassandraUtil.startEmbeddedCassandra();
			
		}
		 
	}
	
	@PreDestroy
	public void destroy(){
		session.close();
	}

	@Override
	public void put(String key, String value) {		
		if(session==null){session = CassandraConnectionFactory.create().connect("store");}
		
		session.execute("insert into feed(userid,idvalue,feedvalue) values('"+key+"',"+UUID.randomUUID()+",'"+value.replace("'", "''")+"')");
		
	}

	@Override
	public String get(String key) {		
		return null;
	}

	@Override
	public List<Feed> getFeeds() {
		if(session==null){session = CassandraConnectionFactory.create().connect("store");}
		ResultSet rs = session.execute("select userid from  feed");
		
		List<Feed> feeds = new ArrayList<Feed>();
		Set<String> userIds = new HashSet<String>();
		Iterator<Row> iter = rs.iterator();
		   while (iter.hasNext()) {
		       if (!rs.isFullyFetched())
		           rs.fetchMoreResults();
		       Row row = iter.next();
		       userIds.add(row.getString("userid"));		           
		   }
		   
		   for (String string : userIds) {
			   List<String> feedValues = new ArrayList<String>();
			   ResultSet rs1 = session.execute("select feedvalue from feed where userid='"+string+"'");
			   Iterator<Row> iter1 = rs1.iterator();
			   while (iter1.hasNext()) {
			       if ( !rs1.isFullyFetched())
			           rs1.fetchMoreResults();
			       Row row1 = iter1.next();
			       feedValues.add(row1.getString("feedvalue"));		           
			   }
			   Feed feed = new Feed();
			   feed.setUserId(string);
			   feed.setFeeds(feedValues);
			   feeds.add(feed);
		   }				
		
		return feeds;
	}
	
}
