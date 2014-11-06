package bupt.sc.heartbeat.cloud;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import bupt.sc.heartbeat.setting.CCsetting;

public class CC_HMInfoListener implements Runnable{
	private static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
      
	public void run(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("conf/applicationContext.xml");
		logger.info("listen HM Info");
		ExecutorService exec = Executors.newCachedThreadPool();
		try(ServerSocket serverHMINFO = new ServerSocket(CCsetting.HM_CC_INFO_PORT);){
			while(true){
				Socket socket = serverHMINFO.accept();
				OnReceiveHMINFO onre = ctx.getBean("hminfo", OnReceiveHMINFO.class);
				onre.setSocket(socket);
				exec.execute(onre);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
}
