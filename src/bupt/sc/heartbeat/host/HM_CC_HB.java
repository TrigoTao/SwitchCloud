package bupt.sc.heartbeat.host;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import bupt.sc.heartbeat.pcInfo.PCInfo;
import bupt.sc.heartbeat.setting.HMsetting;

/**
 * This thread is used by NodeManager to 
 * report HeartBeat to CloudManager regularly
 * @author CHENZHUO
 *
 */
public class HM_CC_HB extends Thread{

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
				System.out.println("Connected! Cloud ip is " + hbSockestAddr);
				/* Connected, keep sending HeartBeat */
				String line = PCInfo.getMAC()+","+PCInfo.getIP()+"\n";
				while(socketOut!=null){
					Thread.sleep(HMsetting.HM_HB_INTERVAL);
					socketOut.write(( PCInfo.getMAC() + "," + PCInfo.getIP() + "\n")
							       .getBytes());
					socketOut.flush();		
					System.out.println("[INFO]<HM HeartBeat report> thread :");
					System.out.println(line);
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
