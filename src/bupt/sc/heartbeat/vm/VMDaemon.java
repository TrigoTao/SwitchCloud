package bupt.sc.heartbeat.vm;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class should be complex and robust
 * @author jiaohuan
 *
 */
public class VMDaemon extends Thread{
	private ServerSocket serversocket;
	private Socket incoming;
	
	public VMDaemon(){	
	}
	
	public VMDaemon(String nodeIP){
		System.out.println("[INFO] VM DAEMON is starting");
		new VMHeartBeat(nodeIP).start();
	}
	
	public void run(){
		// Maximum 10 Daemon Handle Threads
		int POOL_SIZE=10;
		ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);   
		try {
			serversocket = new ServerSocket(bupt.sc.heartbeat.setting.VMsetting.DaemonLISTENPORT);//����8822�˿�
			while(true){
					incoming = serversocket.accept();
					executorService.execute(new VMEndWorker(incoming));
					//OutputStream socketOut = stateSocket.getOutputStream();
					/*socketOut.write(("ipAddress,"+PCInfo.getIP()+
							",diskInG,"+PCInfo.getTotalDisk()+",currentDiskInG,"+PCInfo.getCurrentDisk()+
							",currentMemInM,"+PCInfo.getCurrentMemory()+"\n").getBytes());*/
			}

		}catch (IOException e) {
			e.printStackTrace();
		}

	}
}