package bupt.sc.heartbeat.host;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bupt.sc.heartbeat.pcInfo.PCInfo;
import bupt.sc.heartbeat.setting.HMsetting;

/**
 * This thread is used by NodeManager to 
 * report HeartBeat to CloudManager regularly
 * @author CHENZHUO
 *
 */
public class HM_CC_HB extends Thread{
	private static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private Socket heartBeat = null;
	private OutputStream socketOut = null;
	private HM_INFO hmInfo = null;

	public HM_CC_HB(HM_INFO hmInfo){
		this.hmInfo = hmInfo;
	}
	public void run(){
		while(true){
			try {
				/* Keep trying to connect to CloudManager */
				heartBeat = new Socket();
				InetSocketAddress hbSockestAddr = 
			        new InetSocketAddress(hmInfo.getCloudIP(),HMsetting.HM_CC_HB_PORT);
				heartBeat.connect(hbSockestAddr,HMsetting.HM_CC_TIMEOUT);
				socketOut = heartBeat.getOutputStream();
				logger.info("Connected! Cloud ip is " + hbSockestAddr);
				/* Connected, keep sending HeartBeat */
				String line = PCInfo.getMAC()+","+PCInfo.getIP()+"\n";
				while(socketOut!=null){
					Thread.sleep(HMsetting.HM_HB_INTERVAL);
					socketOut.write(( PCInfo.getMAC() + "," + PCInfo.getIP() + "\n")
							       .getBytes());
					socketOut.flush();		
					logger.info("[INFO]<HM HeartBeat report> thread :");
					logger.info(line);
					}	
				heartBeat.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch(InterruptedException e){
				e.printStackTrace();
			}finally{
					try {
						if(socketOut!=null)
							socketOut.close();
						if(heartBeat!=null)
							heartBeat.close();
				    } catch (IOException e) {
						e.printStackTrace();
					}
			}
			
		}
	}
}
