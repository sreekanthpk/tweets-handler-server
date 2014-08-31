package org.sree.storage.cassandra.embedded;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.sree.storage.AdminService;
import org.sree.storage.cassandra.CassandraConnectionFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

@Service(value="embeddedCassandraAdminService")
public class EmbededCassandraAdminServiceImpl implements AdminService{

	Cluster cluster;
	@PostConstruct
	public void init(){
		EmbededCassandraUtil.startEmbeddedCassandra();		
	}
	
	public void destroy(){
		
	}

	@Override
	public boolean createKeyspace(String schemaName) {
		try{		
			if(cluster==null){
				cluster = CassandraConnectionFactory.create();
			}
			
			Session session = cluster.connect();
			session.execute("CREATE KEYSPACE "+ schemaName + " WITH replication " + 
			      "= {'class':'SimpleStrategy', 'replication_factor':1};");
			return true;
		}catch(Exception e){
			System.out.println(e);
			return false;
		}
	}

	@Override
	public boolean createColumnFamily(String keyspace, String tableName, String key, Map<String, String> columns) {
		try{
			if(cluster==null){
				cluster = CassandraConnectionFactory.create();
			}
			
			Session session = cluster.connect(keyspace);
			
			String s="";
			
			for (String column : columns.keySet()) {
				s += column+" "+columns.get(column)+ ", ";
				
			}
			
			s.subSequence(0, s.length()-2);
			
			session.execute("CREATE TABLE " + tableName + " (" +
					key+ " varchar, idvalue uuid, "+ s +
					", PRIMARY KEY("+key+",idvalue)) WITH comment='user information' "+
					"AND read_repair_chance = 1.0");
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
