package bupt.sc.heartbeat.vm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.Executors;

import bupt.sc.heartbeat.pcInfo.PCInfo;
import bupt.sc.heartbeat.setting.HMsetting;
import bupt.sc.heartbeat.setting.VMsetting;


/**
 * works on the VMs to report the health statements of the VMs 
 * and the MAC-IP information;
 * listens on a port for the query of the PCInfo
 * @author Administrator
 *
 */
public class VMClient extends Thread{
	
	/** the ip of HM on which the VM running*/
	private String HMip;
	private String VMtype;
	private String VMIDprefix;
	/** the port VM is listening on*/
	private int listenPort;
	
	/** store the IP address of the Cluster Controller */
	private String CCip;
	
	/** store the interval of heart beat mechanism */
	private int interval=15000;
	
	
	/** supply query function*/
	//final private ServerSocket serversocket;
	
	/** set the CCip when construct a VMClient
	 * @throws IOException */
	public VMClient(String ip,String VMtype,String VMIDprefix) throws IOException{
	    //serversocket = new ServerSocket(VMsetting.LISTEN_PORT);
		setCCip(ip);
		this.setVMtype(VMtype);
		this.setVMIDprefix(VMIDprefix);
		createID();
	}
	public VMClient(){
		
	}
	
	public String getVMIDprefix() {
		return VMIDprefix;
	}


	public void setVMIDprefix(String vMIDprefix) {
		VMIDprefix = vMIDprefix;
	}


	public String getVMtype() {
		return VMtype;
	}


	public void setVMtype(String vMtype) {
		VMtype = vMtype;
	}


	/**
	 * set the IP address of the Cluster Controller
	 */
	public void setCCip(String serverIP){
		CCip = serverIP;
	}

	/**
	 * get the IP address of the HM 
	 * @return
	 */
	public String getHMip() {
		return HMip;
	}

	/**
	 * set the IP address of the HM
	 * @param hMip
	 */
	public void setHMip(String hMip) {
		HMip = hMip;
	}

	/**
	 * get the port VMs are listening on
	 * @return
	 */
	public int getListenPort() {
		return listenPort;
	}

	/**
	 * set the port VMs are listening on
	 * @param listenPort
	 */
	public void setListenPort(int listenPort) {
		this.listenPort = listenPort;
	}

	/**
	 * get the Cluster controller's IP
	 * @return
	 */
	public String getCCip() {
		return CCip;
	}

	/**
	 * get the heart beat interval
	 * @return
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * set 
	 * @param interval
	 */
	public void setInterval() {
		this.interval = VMsetting.HEARTBEAT_INTERVAL;
	}
	
	private String dec2Hex(int dec) { 
        StringBuffer sb = new StringBuffer();
        if(dec<10){
        	return sb.append(dec).toString();
        }
        else{
        	return sb.append((char)('A'+dec-10)).toString();
        }
    }
	
	/**
	 * Create VM ID which identifies which HM it belongs to
	 */
	public String createID() {
		Random rand = new Random();
		int temp = 0;
		StringBuffer newMac = new StringBuffer(this.VMIDprefix);
		for(int i=0;i<12-this.VMIDprefix.length();i++){
			temp = rand.nextInt(16);
			newMac.append(dec2Hex(temp));
		}
		return newMac.toString();
	}
	
	/**
	 * in charge of reporting the IP and MAC of VM
	 * and record the HMip 
	 */
	/**
	 * VM connect to cloud manager and get IP of Node Manger
	 * which spawns this VM
	 * @author zhuochen
	 */
	public void reportIP(){
		Socket socket = null;
		OutputStream socketOut = null;
		InputStream socketInStream = null;
		BufferedReader socketIn = null;
		String VMip = PCInfo.getIP();
		String VMmac = PCInfo.getMAC();
		try {
			//socket = new Socket(CCip,CCsetting.CC_VM_PORT);
			socket = new Socket("59.64.158.232",8888);
			socketInStream = socket.getInputStream();
			socketOut = socket.getOutputStream();
			socketOut.write((VMmac+"\n").getBytes());
			socketOut.write((VMip+"\n").getBytes());
			//socketOut.write((VMtype+"\n").getBytes());
			//socketOut.write((VMID+"\n").getBytes());
			//System.out.println(VMmac+VMip+VMtype+VMID);
			// Get HM IP from Cloud Manager
			socketIn = new BufferedReader(new InputStreamReader(socketInStream));
			String HM_IP = socketIn.readLine();
			setHMip(HM_IP);
			System.out.println(HM_IP);
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if(socketOut!=null)
				try {
					socketOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(socket!=null){
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
		}
	}
	/**
	 * The thread tries to listen to NodeManager
	 * @author jiaohuan
	 *
	 */
	public class vmFindNodeManager extends Thread{
		
		public void run(){
			// Maximum 10 Threads
			int POOL_SIZE=10;
			Executors.newFixedThreadPool(POOL_SIZE);   
			try(ServerSocket serversocket = new ServerSocket(VMsetting.InitialLISTENPORT);) {
				//Socket stateSocket = null;
				while(true){
					try {
						serversocket.accept();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
	}

	/**
	 * first send the IP and MAC to the CC
	 * secondly get the IP of HM 
	 * then send Heart beat information to the HM
	 * thirdly Listen on the LISTEN_PORT for state query 
	 */
	public void run(){
		

		/* VM_HM_StateQuery thread is daemon to notify VM's status */
		//new VM_HM_StateQuery().start();
		// reportIP should be abandoned
		// at this place, VM should waits NodeManager to notify him
		// Potential Bug: Prevent if NodeManager cannot find it
		// 	
		reportIP();
		
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Socket hbsocket = null;
		while(true){
			OutputStream socketOut = null;
			try {
				Thread.sleep(getInterval());
				// Socket connect to VM's HM 
				hbsocket = new Socket(getHMip(),HMsetting.VM_HM_HB_PORT);
				socketOut = hbsocket.getOutputStream();
				socketOut.write((PCInfo.getMAC()+","+PCInfo.getIP()+"\n").getBytes());
			} 
			catch (IOException e) {
				e.printStackTrace();
			} catch(InterruptedException e){
				
				e.printStackTrace();
			}finally{
				if(socketOut!=null)
					try {
						socketOut.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					if(hbsocket!=null){
						try {
							hbsocket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
			} 
 
		}
	}

	
}
