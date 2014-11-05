package bupt.sc.heartbeat.host;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bupt.sc.heartbeat.setting.HMsetting;

/**
 * This thread is used by NodeManager
 * to listen to VM HeartBeat
 * 
 * @author Administrator
 *
 */
public class HM_VM_Listener extends Thread{
	private static Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	private HM_INFO hmInfo;
	
	private ServerSocket serverSocket;
	private ExecutorService executorService;  
    private final int POOL_SIZE= HMsetting.MAXVMHBTHREAD;    
	
    public HM_VM_Listener() throws IOException{
		serverSocket = new ServerSocket(HMsetting.VM_HM_HB_PORT);
		executorService = Executors.newFixedThreadPool(POOL_SIZE);
    }
	public HM_VM_Listener(HM_INFO hmInfo) throws IOException{
		this.hmInfo = hmInfo;
		serverSocket = new ServerSocket(HMsetting.VM_HM_HB_PORT);
		executorService = Executors.newFixedThreadPool(POOL_SIZE);
	}
	
	public void run(){
		
		while(true){
			try {
				Socket socket = serverSocket.accept();
				System.out.println("[INFO] New VM HeartBeat Connection established from " + socket.getInetAddress());
				executorService.execute(new OnHMReceiveHB(hmInfo,socket));
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
