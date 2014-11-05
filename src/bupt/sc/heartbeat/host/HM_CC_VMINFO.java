package bupt.sc.heartbeat.host;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import bupt.sc.heartbeat.setting.HMsetting;
import bupt.sc.heartbeat.vm.VMIdentity;


public class HM_CC_VMINFO extends Thread{
	private Socket socket = null;
	private OutputStream socketOut = null;
	private HM_INFO hmInfo = null;
	
	public HM_CC_VMINFO(HM_INFO hm,Socket s){
		this.hmInfo = hm;
		this.socket = s;
	}
	public void run(){
			try {
				System.out.println("[INFO]<HM VMINFO List> thread :");
				socketOut = socket.getOutputStream();
				PrintWriter out = new PrintWriter(socketOut,true);
				synchronized(hmInfo){
					for(VMIdentity vm:hmInfo.getVMList()){
						String line = vm.getVMMAC() + "," + 
									  vm.getVMIP() + "," +
									  hmInfo.getHMIP() + "," +
									  vm.getVMID();
						System.out.println(line);
						out.println(line);
					}
				}
				System.out.println();
				Thread.sleep(HMsetting.HM_VMINFO_INTERVAL);
			}catch (IOException e) {
				e.printStackTrace();
			}catch(InterruptedException e){
				e.printStackTrace();
			}finally{
					try {
						if(socketOut!=null)
							socketOut.close();
						if(socket!=null)
							socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}
	}

}
