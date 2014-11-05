package bupt.sc.heartbeat.vm;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import bupt.sc.heartbeat.pcInfo.PCInfo;


/**
 * When socket (localhost,8822) is connected,
 * VMEndWorker notify its information once
 * And close the socket
 * @author jiaohuan
 *
 */
public class VMEndWorker extends Thread{

	private Socket socket;
	
	public VMEndWorker(Socket socket){
		this.socket = socket;
	} 
	
	public void run(){
		OutputStream socketOut;
		try {
			socketOut = socket.getOutputStream();
			// Notify VM's information
			socketOut.write(( "ipAddress,"+PCInfo.getIP()
							  +",cpu,"+PCInfo.getCPUs()
							  +",memoryInM,"+PCInfo.getTotalMemory()
							  +",diskInG,"+PCInfo.getTotalDisk()
							  +",currentDiskInG,"+PCInfo.getCurrentDisk()
							  +",currentMemInM,"+PCInfo.getCurrentMemory()
							  +",macAddress,"+PCInfo.getMAC()+"\n").getBytes());
			socketOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
