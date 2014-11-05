package bupt.sc.heartbeat.cloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import bupt.sc.heartbeat.model.HostInfo;
import bupt.sc.heartbeat.service.HostInfoService;

public class OnReceiveHMHB implements Runnable{
	private Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private Socket socket;
	
	@Autowired
	private HostInfoService hostInfoService;
	
	public OnReceiveHMHB(Socket s){
		socket = s;
	}
	
	/**
	 * Receive HB from NodeManager
	 * Refresh hm_info 
	 */
	public void run(){
		String hostIp = socket.getInetAddress().toString().substring(1);				
		logger.info("[INFO] NEW HM HB connection established from " + hostIp);
		try (BufferedReader in = new BufferedReader( new InputStreamReader(socket.getInputStream()) );) {
			String line;
			String [] hminfo = null;
			while( (line = in.readLine()) != null ||socket!=null){
				logger.info("[INFO] New HM HB received from " + hostIp + " : " + line);
				hminfo = line.split(",");
			}
			for( HostInfo host : hostInfoService.getInfoByIp(hostIp) ){
				if(hminfo.length == 2){
					host.setMac(hminfo[0]);
					host.setIp(hminfo[1]);
					hostInfoService.save(host);
				}else{
					logger.error("hminfo length = " + hminfo.length);
				}
			}
			
		} catch (IOException e) {
			logger.error(e.getMessage());
		} 
	}
}