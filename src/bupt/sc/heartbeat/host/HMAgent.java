package bupt.sc.heartbeat.host;

import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bupt.sc.heartbeat.setting.CCsetting;
import bupt.sc.heartbeat.setting.HMsetting;
import bupt.sc.utils.CloudConfig;
import bupt.sc.utils.ConfigInstance;

/**
 * This class reports Node Manger information to Cloud Manager
 * @author chenzhuo
 *
 */
public class HMAgent extends Thread {
	private static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public HM_INFO hmInfo;
	private String cloudIP;

	public HMAgent(HM_INFO hm){
		hmInfo = hm;
	}
	public HMAgent(HM_INFO hm, String ip){
		hmInfo = hm;
		cloudIP = ip;
	}

	
	public void init() throws FileNotFoundException{
		CloudConfig cc = ConfigInstance.getCloudConfig();
		cloudIP = cc.getCloudIp();
		hmInfo.setCloudIP(cloudIP);
		/**
		 * Set the port of CloudManager which NodeManager should report to
		 * Set the interval of NodeManger's HeartBeat
		 */
		hmInfo.setHBPort(CCsetting.HM_CC_HB_PORT);
		hmInfo.setInfoPort(CCsetting.HM_CC_INFO_PORT);
		hmInfo.setHBInterval(HMsetting.HM_HB_INTERVAL);
		hmInfo.setCleanerInterval(HMsetting.HM_VM_CLEANER_INTERVAL);
	}

	/**
	 * when a HMAgent is constructed, 
	 * firstly, it reports the MAC and IP information of the NodeManager to the CC
	 * secondly, constructs an instance of HM_Notify_NewVM
	 * thirdly, starts a cleaner Thread
	 * fourthly, constructs a listener listening for the HB Info from the VMs
	 * finally, reports to the CC the VMs' Info termly.
	 */
	public void run(){
		try {
			init();	
			try{
				new HM_CC_HB(hmInfo).start();
				/*Report VMINFO list of HM every 10s*/
				while(true){
					Thread.sleep(HMsetting.HM_VMINFO_INTERVAL);	
					Socket HMINFOSocket = new Socket();
					InetSocketAddress infoSockestAddr = 
							new InetSocketAddress(hmInfo.getCloudIP(),HMsetting.HM_CC_INFO_PORT);
					HMINFOSocket.connect(infoSockestAddr,HMsetting.HM_CC_TIMEOUT);
					new HM_CC_VMINFO(hmInfo,HMINFOSocket).start();
			}
			}catch(Exception e){
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}
	}
}
