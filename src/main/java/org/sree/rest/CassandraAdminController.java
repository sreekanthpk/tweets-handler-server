package org.sree.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.sree.storage.AdminService;

@Controller
public class CassandraAdminController {
	
	@Autowired	
	private AdminService cassandraAdminService;
	
	 @RequestMapping("/createkeyspace/{keyspace}")
	    public @ResponseBody boolean createSchema(	    		
	    		@PathVariable(value="keyspace") String keyspace) {
		 
	        return cassandraAdminService.createKeyspace(keyspace);
	    }
	 
	 @RequestMapping("/createtable/{keyspace}/{tableName}/{columnKey}/{columns}")
	    public @ResponseBody boolean createColumnFamily(	    		
	    	@PathVariable(value="keyspace") String keyspace,
	    	@PathVariable(value="tableName") String tableName,
	    	@PathVariable(value="columnKey") String columnKey,
	    	@PathVariable(value="columns")String columns) {
		 
		 	
		 	Map<String, String> map = new HashMap<String, String>();
		 	
		 	StringTokenizer tokenizer1 = new  StringTokenizer(columns,",");
		 	
		 	while (tokenizer1.hasMoreElements()) {
		 		StringTokenizer tokenizer = new  StringTokenizer(tokenizer1.nextToken(),"=");
		 		map.put(tokenizer.nextToken(),tokenizer.nextToken());	 
		 		
			}
		 		 
	        return cassandraAdminService.createColumnFamily(keyspace, tableName, columnKey, map);
	    }

}
