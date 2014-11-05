package bupt.sc.heartbeat.vm;

public class VMIdentity {
	public VMIdentity(){
		VMID = null;
		VMNAME = null;
		VMIP = null;
		VMMAC = null;
	}
	public VMIdentity(String vmid, String vmip){
		VMID = vmid;
		VMIP = vmip;
	}
	public VMIdentity(String vmid, String vmip, String vmname){
		VMID = vmid;
		VMIP = vmip;
		VMNAME = vmname;
	}
	public VMIdentity(String vmid, String vmip, String vmname,String vmmac){
		VMID = vmid;
		VMIP = vmip;
		VMNAME = vmname;
		VMMAC = vmmac;
	}
	public String getVMID(){
		return VMID;
	}
	public String getVMIP(){
		return VMIP;
	}
	public String getVMNAME(){
		return VMNAME;
	}
	public void setVMIP(String vmip){
		this.VMIP = vmip;
	}
	public String getVMMAC(){
		return VMMAC;
	}
	public void setVMMAC(String vmmac){
		this.VMMAC = vmmac;
	}

	private String VMID;
	private String VMNAME;
	private String VMIP;
	private String VMMAC;
}
