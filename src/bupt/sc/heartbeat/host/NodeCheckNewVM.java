package bupt.sc.heartbeat.host;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;

import bupt.sc.heartbeat.pcInfo.PCInfo;
import bupt.sc.heartbeat.setting.HMsetting;
import bupt.sc.heartbeat.vm.VMIdentity;
import bupt.sc.nova.vm.VBoxManager;
import bupt.sc.nova.vm.VMOperationManager;


public class NodeCheckNewVM extends Thread{
	private VMOperationManager vmOperationManager = new VBoxManager();
	
	private String HMIP;
	private int refreshInterval = 1;

	private List<VMIdentity> unauthVM;
	private List<String> unregvmid;
	
	private HM_INFO hm;
	
	public static void main(String[] args) throws Exception{
		//HM_INFO hm = new HM_INFO();
		//System.out.println("hm ip is:"+ PCInfo.getIP());
	
	}

	public NodeCheckNewVM(HM_INFO hm){
		this.hm = hm;
		HMIP = PCInfo.getIP();
		unauthVM = new LinkedList<VMIdentity>();
		unregvmid = new LinkedList<String>();	
	}
	public NodeCheckNewVM(){
		HMIP = PCInfo.getIP();		
		unauthVM = new LinkedList<VMIdentity>();
		unregvmid = new LinkedList<String>();	
	}
	private void printListInfo(){
		//log.info("[INFO] Info of authVM: ");
		System.out.println("authVM INFO:");
		for(VMIdentity r:hm.getAuthedVM()){
			//log.info("[INFO] "+r.getVMID()+ "," + r.getVMIP() +"," + r.getVMMAC() +"," +r.getVMNAME());
			System.out.println(r.getVMID()+ "," + r.getVMIP() +"," + r.getVMMAC() +"," +r.getVMNAME());
		}
		//log.info("[INFO] Info of unauthVM: ");
		System.out.println("unauthVM INFO:");
		for(VMIdentity r:unauthVM){
			//log.info("[INFO] "+r.getVMID()+ "," + r.getVMIP());
			System.out.println(r.getVMID()+ "," + r.getVMIP());
		}
		System.out.println("unregvm  id INFO:");
		//log.info("[INFO] Info of unregvmid: ");
		for(String s:unregvmid){
			//log.info("[INFO] "+s);
			System.out.println(s);
		}
		System.out.println();
	}
	
	public void run(){
		initVMList(hm);
		 while(true){
			try{
				printListInfo();
				//printVMList();
				/**Step 1. Get new VMID*/
				Thread.sleep(HMsetting.CHECKNEWVMINTERVAL + refreshInterval * 1000);
				refreshVMList(hm);
				refreshVMID(unregvmid);
				if(unregvmid.isEmpty())
					refreshInterval = 2*refreshInterval;
			    else
					refreshInterval = 1;
				
				/**Step 2. Get ip from id in VMID, if unauthVM does not have it, insert it*/
				refreshVMIP();
				/**Step 3. Interaction between authVM and unauthVM*/
				authVMID();
			}catch(Exception e){
				e.printStackTrace();
			}
			System.out.println("[INFO] Thread over");	 
		 }	
	}

	private synchronized void initVMList(HM_INFO hm){
		List<VMIdentity> vmlist = hm.getVMList();
		List<String> vmids = vmOperationManager.listVMIDs();	
		String vmip;
		String vmname;
		String vmmac;
		for(String vmid:vmids){
			vmip = vmOperationManager.getVMIPbyVMID(vmid);
			vmname = vmOperationManager.getVMNamebyVMID(vmid);
			vmmac = vmOperationManager.getVMMacbyVMID(vmid);
			VMIdentity toAdd = new VMIdentity(vmid,vmip,vmname,vmmac);
			vmlist.add(toAdd);
		}	
	}
	private synchronized void refreshVMList(HM_INFO hm){
		List<VMIdentity> vmlist = hm.getVMList();
        List<VMIdentity> newVMList = new LinkedList<VMIdentity>();
		List<String> vmids = vmOperationManager.listVMIDs();
        String vmip;
		String vmid;
		String vmname;
		String vmmac;
		for(VMIdentity r:vmlist){
			vmid = r.getVMID();
			if(!vmids.contains(vmid)){
				continue;  /* Old VMID does not exist any more */
			}			
			vmids.remove(vmid);
			vmip = vmOperationManager.getVMIPbyVMID(vmid);
			vmmac = vmOperationManager.getVMMacbyVMID(vmid);
			r.setVMIP(vmip);
			r.setVMMAC(vmmac);
		    newVMList.add(r);
		}
		for(String id:vmids){
			vmip = vmOperationManager.getVMIPbyVMID(id);
			vmname = vmOperationManager.getVMNamebyVMID(id);
			vmmac = vmOperationManager.getVMMacbyVMID(id);
			VMIdentity toAdd = new VMIdentity(id,vmip,vmname,vmmac);
			newVMList.add(toAdd);
		}
		hm.setVMList(newVMList);
		
	}
	/**
	 * Refresh List<String> newVMIP & existVMIP
	 * @param reg
	 * @param unreg
	 */
	private synchronized void refreshVMID(List<String> unreg){
		synchronized(unreg){
			List<String> current = 	vmOperationManager.listVMIDs();	
			List<String> already =  hm.getAuthedVMID();
			unreg.clear();
			for(String s:current){			
				if(already.contains(s))
					continue;
				unreg.add(s);
			}	
		}		
	}
	private synchronized void refreshVMIP(){
		synchronized(unregvmid){
			String vmip = null;
			String vmname = null;
			String vmmac = null;
			boolean exist = false;
			for(String vmid:unregvmid){
				if(vmOperationManager.isVMHealthy(vmid))
					vmip = vmOperationManager.getVMIPbyVMID(vmid);
				vmname = vmOperationManager.getVMNamebyVMID(vmid);
				vmmac = vmOperationManager.getVMMacbyVMID(vmid);
				if(vmip==null)
					continue;
				System.out.println("[INFO] Get VMIP " + vmip);
				exist = false;
				// Check if (vmid,vmip) exits in unauthVM
				synchronized(unauthVM){
					for(VMIdentity toReg:unauthVM){
						if(toReg.getVMIP().equals(vmip)){
							exist = true;
							break;
						}				
					}
					if(!exist){
						VMIdentity toInsert = new VMIdentity(vmid,vmip,vmname,vmmac);	
						unauthVM.add(toInsert);
					}	
				}
			}		
			
		}
	
	}
	private synchronized void authVMID(){
		List<VMIdentity> temp = new LinkedList<VMIdentity>();
		synchronized(unauthVM){
			for(VMIdentity cp:unauthVM){
				temp.add(cp);
			}
		}
		for(VMIdentity toAuth:temp){
			String ip = toAuth.getVMIP();
			String id = toAuth.getVMID();
			if(ip==null)
				continue;
			boolean result = authVM(ip);
			if(result){
				System.out.println("[INFO!!!] VM is authed:" + toAuth);
				synchronized(hm){
					hm.getAuthedVM().add(toAuth);	
				}
				synchronized(unregvmid){
					unregvmid.remove(id);
				}
				synchronized(unauthVM){
					unauthVM.remove(toAuth);
				}
			}
		}			
	}

	private synchronized boolean authVM(String vmip){
		boolean result = false;
		boolean connected = false;
		boolean authed = false;
		Socket auth = new Socket();
		InetSocketAddress sockestAddr = 
				new InetSocketAddress(vmip,HMsetting.HM_VM_INITIAL_PORT);
		try{
			/**
			 * Step 1 , connect to VM
			 */
			while(!connected){
				auth.connect(sockestAddr,HMsetting.HM_VM_INITIALTIMEOUT);
				System.out.println("[INFO]Connection established!!!");
				try(BufferedReader in = new BufferedReader(new InputStreamReader(auth.getInputStream()));
						PrintWriter out = new PrintWriter(auth.getOutputStream(),true)){
					out.println(HMsetting.InitialWORD);	
					String line;
					while(!connected && (line = in.readLine())!=null){
						if(line.equals(HMsetting.RequestIP)){
							out.println(HMsetting.NodeIPINFO + "#" + HMIP);
							connected = true;
						}
					}
					System.out.println("No more lines, Connected");
				}
				// Socket connection timeout!
				catch(SocketTimeoutException e){		
					System.out.println("[ERROR] Connection time out !!!");
					if(auth!=null){
						auth.close();	
					}
					continue;
				}
				finally{
					if(auth!=null){
						auth.close();	
					}
				}	
			}
			
			/**
			 * Step 2, VM authentication
			 */
			while(!authed){
				authed = true;
			}
			result = true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
}
