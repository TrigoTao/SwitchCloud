package bupt.sc.heartbeat.cloud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import bupt.sc.heartbeat.model.VMInfo;
import bupt.sc.heartbeat.service.VMInfoService;

/**
 * OnReciveHB, when get HB from HM
 * delete all VM related to HM from DB
 * Add healthyvm to DB
 * 
 * @author jiaohuan
 *
 */
public class OnReceiveHMINFO implements Runnable{
	private static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
	private Socket socket;
	private String line;
	
	@Autowired
	private VMInfoService vmInfoService;

	public OnReceiveHMINFO(Socket socket){
		logger.info("[INFO] New HM VMINFO List Received from" + socket.getInetAddress());
		this.socket = socket;
	}

	/**
	 * Receive VMINFO List from NodeManager
	 * Refresh hm_info 
	 */
	public void run(){
		String hmip = socket.getInetAddress().toString().substring(1);
		try(BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
			String vmmac =null;
			String vmip = null;
			String vmid = null;

			/*Delete all vminfo pertaining to hmip*/
			vmInfoService.deleteByHmIp(hmip);
			while( (line = in.readLine()) != null ){
				logger.info("New HM VMINFO List Received, " + line);
				String [] hminfo = line.split(",");
				vmmac = hminfo[0];
				vmip  = hminfo[1];
				vmid  = hminfo[3];
				/* New information, add to DB*/
				VMInfo vmInfo = new VMInfo();
				vmInfo.setHmIp(hmip);
				vmInfo.setVmId(vmid);
				vmInfo.setVmIp(vmip);
				vmInfo.setVmMac(vmmac);
				vmInfoService.save(vmInfo);
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
