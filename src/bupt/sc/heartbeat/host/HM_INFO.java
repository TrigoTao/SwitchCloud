package bupt.sc.heartbeat.host;

import java.util.LinkedList;
import java.util.List;

import bupt.sc.heartbeat.pcInfo.PCInfo;
import bupt.sc.heartbeat.vm.VMIdentity;

/**
 * this class represents the HostMachine and 
 * stores the VMs' information in a list
 * @author Administrator
 *
 */
public class HM_INFO {
	// my use
	private String hmip;
	private String hmmac;
	private List<VMIdentity> authedVM;
	private List<VMIdentity> vmlist;
	private String cloudIP;
	private int hmHeartBeatPort;
	private int hmCloudInfoPort;
	private int HBInterval;
	private int cleanerInterval;
	
	public HM_INFO(){
		hmip = PCInfo.getIP();
		hmmac = PCInfo.getMAC();
		authedVM = new LinkedList<VMIdentity>();
		vmlist = new LinkedList<VMIdentity>();
	}
	
	public List<VMIdentity> getVMList(){
		return vmlist;
	}
	public void setVMList(List<VMIdentity> vmlist){
		this.vmlist = vmlist;
	}
	public List<VMIdentity> getAuthedVM(){
		return authedVM;
	}
	// Return List of VMID of authedVM
	public synchronized List<String> getAuthedVMID(){
		List<String> result = new LinkedList<String>();
		for(VMIdentity r:authedVM){
			result.add(r.getVMID());
		}
		return result;
	}
	
	public String getHMMac(){
		return hmmac;
	}
	public String getHMIP(){
		return hmip;
	}
	
	public int getHBInterval() {
		return HBInterval;
	}
	public int getCleanerInterval() {
		return cleanerInterval;
	}
	public String getCloudIP() {
		return cloudIP;
	}
	public int getHBPort(){
		return hmHeartBeatPort;
	}
	public int getInfoPort(){
		return hmCloudInfoPort;
	}
	
	public void setCloudIP(String ip){
		cloudIP = ip;
	}
	public void setHBPort(int port){
		hmHeartBeatPort = port;
	}
	public void setInfoPort(int port){
		hmCloudInfoPort = port;
	}
	public void setHBInterval(int interval) {
		this.HBInterval = interval;
	}
	public void setCleanerInterval(int cleanerInterval) {
		this.cleanerInterval = cleanerInterval;
	}
	
}
