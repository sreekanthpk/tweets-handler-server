package org.sree.storage;

import java.util.Map;


public interface AdminService {

		boolean createKeyspace(String schemaName);
			
		boolean createColumnFamily(String keyspace, String tableName, String key, Map<String, String> columns);
}
