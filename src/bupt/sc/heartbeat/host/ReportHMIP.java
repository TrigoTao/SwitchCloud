package bupt.sc.heartbeat.host;


public class ReportHMIP 
{
	public static void main(String[] args) {
		// An new object represents all HM information
		HM_INFO hm = new HM_INFO();
		
		// Report to CloudManager HM's status (HB & VMINFO List)
		new HMAgent(hm).start();
		
		// Listen to All VM's Heart Beat
		//new HM_VM_Listener(hm).start();
		
		// Continue to check New VM
		new NodeCheckNewVM(hm).start();
	}
}