package bupt.sc.heartbeat.host;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.net.Socket;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bupt.sc.heartbeat.setting.HMsetting;
import bupt.sc.heartbeat.vm.VMIdentity;


/**
 * @author Administrator
 *
 */
public class OnHMReceiveHB implements Runnable {
	private static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	
	private String vmip;
	private HM_INFO hminfo;
	private Socket socket;

	public OnHMReceiveHB(HM_INFO hminfo,Socket socket){
		this.hminfo = hminfo;
		this.socket = socket;
		this.vmip =   socket.getInetAddress().toString();
	}
	public OnHMReceiveHB(Socket socket){
		this.socket = socket;
	}
	
	public void run(){
		logger.info("[INFO] New VM HeartBeat Connection established from" + vmip);
		try( BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); ) {
			this.socket.setSoTimeout(HMsetting.MAXNOHBTIME);
			String str = null;
			while( (str = in.readLine()) != null || socket!=null){
				System.out.println("[VMHB-INFO] Receiving HB from "+ vmip + " "+ str);
				logger.info("[INFO] New VM HeartBeat Received from " + vmip + " " + str);
			}
        } catch (IOException e) {
			e.printStackTrace();
		}finally{
			/* VM HB Connection is down, cannot receive HB from this connection any more*/
			/* Do clean job */
			logger.info("[INFO] VM HeartBeat Connection closed from" + vmip);
			System.out.println("-----------------------------------------------");
			System.out.println("-----------------------------------------------");
			System.out.println("-----------------------------------------------");
			System.out.println("-----------------------------------------------");
			synchronized(hminfo){
				int index=0;
				List<VMIdentity> tmp = hminfo.getAuthedVM();
				for(VMIdentity toDel:tmp){
					if(toDel.getVMIP().equals(vmip)){
						index = tmp.indexOf(toDel);
						System.out.println("-----------------------------------------------");
						System.out.println(index);
						break;
					}
				}
				tmp.remove(index);
			}
		}
		
	}
}
