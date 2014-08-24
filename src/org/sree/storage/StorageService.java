package org.sree.storage;

public interface StorageService {
	
	public void put(String key, String value);
	
	public String get(String key);

}
