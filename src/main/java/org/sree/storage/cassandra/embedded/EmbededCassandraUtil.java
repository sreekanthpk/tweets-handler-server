package org.sree.storage.cassandra.embedded;

import static java.util.concurrent.TimeUnit.SECONDS;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.cassandra.config.DatabaseDescriptor;
import org.apache.cassandra.db.commitlog.CommitLog;
import org.apache.cassandra.io.util.FileUtils;
import org.apache.cassandra.service.CassandraDaemon;

public class EmbededCassandraUtil {
	
	public static final String DEFAULT_CASSANDRA_YML_FILE = "C:/work/workspace/restful-server-standalone/src/main/resources/cassandra/cassandra.yaml";
  
    private static CassandraDaemon cassandraDaemon = null;
    static ExecutorService executor;
    private static String launchedYamlFile;
    
    	
	 public static void startEmbeddedCassandra(){
	        if (cassandraDaemon != null) {	            
	            return;
	        }

	        checkConfigNameForRestart(DEFAULT_CASSANDRA_YML_FILE);
 
	        System.setProperty("cassandra.config", "file:" + DEFAULT_CASSANDRA_YML_FILE);
	        System.setProperty("cassandra-foreground", "true");
	        DatabaseDescriptor.createAllDirectories();

	        try {
				cleanupAndLeaveDirs();
			} catch (IOException e1) {
				throw new RuntimeException();
			}
	        
	        final CountDownLatch startupLatch = new CountDownLatch(1);
	        executor = Executors.newSingleThreadExecutor();
	        executor.execute(new Runnable() {
	            @Override
	            public void run() {
	                cassandraDaemon = new CassandraDaemon();
	                cassandraDaemon.activate();
	                startupLatch.countDown();
	            }
	        });
	        try {
	            startupLatch.await(10, SECONDS);
	        } catch (InterruptedException e) {	           
	            throw new AssertionError(e);
	        }
	    }
	 
	 private static void checkConfigNameForRestart(String yamlFile) {
	        boolean wasPreviouslyLaunched = launchedYamlFile != null;
	        if (wasPreviouslyLaunched && !launchedYamlFile.equals(yamlFile)) {
	            throw new UnsupportedOperationException("We can't launch two Cassandra configurations in the same JVM instance");
	        }
	        launchedYamlFile = yamlFile;
	    }

	    private static void cleanupAndLeaveDirs() throws IOException {
	    	DatabaseDescriptor.createAllDirectories();
	        cleanup();
	        DatabaseDescriptor.createAllDirectories();
	        CommitLog.instance.resetUnsafe(); // cleanup screws w/ CommitLog, this
	        // brings it back to safe state
	    }

	    private static void cleanup() throws IOException {
	        // clean up commitlog
	        String[] directoryNames = {DatabaseDescriptor.getCommitLogLocation(),};
	        for (String dirName : directoryNames) {
	            File dir = new File(dirName);
	            if (!dir.exists())
	                throw new RuntimeException("No such directory: " + dir.getAbsolutePath());
	            FileUtils.deleteRecursive(dir);
	        }

	        // clean up data directory which are stored as data directory/table/data
	        // files
	        for (String dirName : DatabaseDescriptor.getAllDataFileLocations()) {
	            File dir = new File(dirName);
	            if (!dir.exists())
	                throw new RuntimeException("No such directory: " + dir.getAbsolutePath());
	            FileUtils.deleteRecursive(dir);
	        }
	    }



}
