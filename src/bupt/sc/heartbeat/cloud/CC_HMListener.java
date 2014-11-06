package bupt.sc.heartbeat.cloud;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bupt.sc.heartbeat.setting.CCsetting;

public class CC_HMListener implements Runnable {
	private static Logger logger = LogManager.getLogger( MethodHandles.lookup().lookupClass() );
    
	public void run(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("conf/applicationContext.xml");
		logger.info("listen HM");
		//old : why allocate fixed thread at beginning
		//private final int POOL_SIZE=20;
		//private executorService = Executors.newFixedThreadPool(POOL_SIZE);
		ExecutorService executorService = Executors.newCachedThreadPool();
		try(ServerSocket serverHMHB = new ServerSocket(CCsetting.HM_CC_HB_PORT);){
			while(true){
				Socket hbSocket = serverHMHB.accept();
				hbSocket.setSoTimeout(CCsetting.MAXNOHBTIME);
				OnReceiveHMHB onrec = ctx.getBean("hmhb", OnReceiveHMHB.class);
				onrec.setSocket(hbSocket);
				executorService.execute( onrec );
				//executorService.execute(new OnReceiveHMHB(hbSocket));
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
