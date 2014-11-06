package bupt.sc.heartbeat.cloud;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CCMonitor {
	private static Logger logger = LogManager.getLogger( MethodHandles.lookup().lookupClass() );
	
    public static void main(String[] args){
		logger.info("CCMonitor started!");
		
		ExecutorService exec = Executors.newCachedThreadPool();
		exec.execute(new CC_HMListener());    // receive host connection
		exec.execute(new CC_HMInfoListener()); // get vminfo
		exec.shutdown();
	}
}
