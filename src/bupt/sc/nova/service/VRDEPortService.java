package bupt.sc.nova.service;

public interface VRDEPortService {
	public void releasePort(int vrdeport);
	public void releasePortbyVMID(String vmid);
	public int leasePort();
	public void setPortVmid(String vmid,int vrdeport);
}
