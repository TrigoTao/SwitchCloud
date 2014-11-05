package bupt.sc.heartbeat.vm;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import bupt.sc.heartbeat.pcInfo.PCInfo;
import bupt.sc.heartbeat.setting.VMsetting;

public class VMHeartBeat extends Thread{
	private String nodeIP;
	private Socket heartBeat;
	private OutputStream hb;

	public VMHeartBeat(String nodeIP){
		this.nodeIP = nodeIP;
	}
	public void run(){
			try{
				/* Insure VM keep try to connect to HM if HM is down*/
				while(true){
					System.out.println("Trying to connect to cloud");
					heartBeat = new Socket();
					heartBeat.connect(new InetSocketAddress(nodeIP, VMsetting.VMHEARTBEATPORT), 
							bupt.sc.heartbeat.setting.VMsetting.HEARTBEATTIMEOUT);//����NodeManager��8824�˿�
					hb = heartBeat.getOutputStream();
					/* Send HB to HM at a regular interval */
					while(true){
						Thread.sleep(VMsetting.HEARTBEAT_INTERVAL);	
						//System.out.println("HEARTBEATING");
						hb.write(( "#IP:"  + PCInfo.getIP()  +
							       "#MAC:" + PCInfo.getMAC() +
							       "#CPU:" + PCInfo.getCPUs() +
							       "#MEMINM:"  + PCInfo.getTotalMemory() +
							       "#MEMINUSE:"+ PCInfo.getCurrentMemory() + 
							       "#DISKING:" + PCInfo.getTotalDisk() +
							       "#DISKINUSE:"+ PCInfo.getCurrentDisk() +"\n")
							       .getBytes());
						hb.flush();			
					}
				}

			}catch(IOException e){
				e.printStackTrace();
			}catch(InterruptedException e){
				e.printStackTrace();
			}
	}

}
