package bupt.sc.nova.vm;

import java.util.List;

import bupt.sc.nova.statistic.VMInfo;


public interface VMOperationManager {
	/**
	 * 
	 * @return all vdi uuid
	 */
	public List<String> listVDIID();
	
	/**
	 * return all vm name Fragile
	 * 
	 * @return
	 */
	public List<String> listVMNames();
	
	/**
	 * return all vmid
	 * 
	 * @return
	 */
	public List<String> listVMIDs();
	
	public String getVMMacbyVMID(String VM_UUID);
	
	/**
	 * Get VMIP using GuestAddition
	 * 
	 * @param VM_UUID
	 * @return
	 */
	public String getVMIPbyVMID(String VM_UUID);
	
	public String getVMNamebyVMID(String VM_UUID);
	
	public int getCPUsbyVMID(String VM_UUID);
	
	public int getMEMbyVMID(String VM_UUID);
	
	public int getVRDEportbyVMID(String VM_UUID);
	
	public String getVMStatebyVMID(String VM_UUID);
	
	public VMInfo getVMStatbyVMID(String id);
	
	public String getVMIDbyVMMac(String vmmac);
	
	public String getVMIDbyVMIP(String vmip);
	
	/**
	 * This method is usually invoked to check if a Normal or WriteThrough VDI
	 * is attached to a single VM instance.
	 * 
	 * For other VDI types, it might only return null Use listVMSbyImg instead
	 * 
	 * @param VDI_UUID
	 * @return VM_UUID
	 */
	public String getVMIDbyVDIID(String VDI_UUID);
	
	/**
	 * @param VM_UUID
	 * @return VDI_UUID
	 */
	public String getVDIIDbyVMID(String VM_UUID);
	
	/**
	 * @param VDI_UUID
	 * @return VDI_Type, Normal,Immutable,WriteThrough,Sharable,MultiAttach
	 */
	public String getVDIType(String VDI_UUID);
	
	public String getLocationbyVMID(String VM_UUID);
	
	/**
	 * To see if VDI_UUID is registered
	 * 
	 * @param VDI_UUID
	 * @return
	 */
	public boolean isVDIExist(String VDI_UUID);
	
	/**
	 * @param VDI_UUID
	 * @return
	 */
	public boolean isVDIBase(String VDI_UUID);
	
	/**
	 * @param VDI_UUID
	 * @return
	 */
	public boolean isVDIImmutable(String VDI_UUID);
	
	public boolean isVDINormal(String VDI_UUID);
	
	public boolean isVDIWritethrough(String VDI_UUID);
	
	/**
	 * Invoke this method before start a VM instance
	 * 
	 * @param VDI_UUID
	 * @return
	 */
	// This need to be modified, cause new vdi type
	public boolean isVDIUsable(String VDI_UUID);
	
	/**
	 * @param VM_UUID
	 * @return
	 */
	public boolean isVMHealthy(String VM_UUID);
	
	/**
	 * Type change into Immutable
	 * 
	 * @param UUID
	 * @return
	 */
	public boolean immuVDI(String VDI_UUID);
	
	/**
	 * Type change into Normal
	 * 
	 * @param UUID
	 * @return
	 */
	public boolean normVDI(String VDI_UUID);
	
	/**
	 * Type change into Shareable
	 * 
	 * @param UUID
	 * @return
	 */
	public boolean shareVDI(String VDI_UUID);
	
	/**
	 * Type change into WriteThrough
	 * 
	 * @param UUID
	 * @return
	 */
	public boolean writethroughVDI(String VDI_UUID);
	
	/**
	 * Type change into MultiAttach
	 * 
	 * @param UUID
	 * @return
	 */
	public boolean multiattachVDI(String VDI_UUID);
	
	/**
	 * This method might be abandon in the future Not often invoked by others
	 * 
	 * @param VDI_UUID
	 * @return
	 */
	public boolean unRegisterVDI(String VDI_UUID);
	
	/**
	 * @param UUID
	 * @return
	 */
	public boolean deleteVDI(String VDI_UUID);
	
	/**
	 * @param name
	 * @param MemInM
	 * @param cpu
	 * @param vdiinfo
	 * @return
	 */
	public String startVM(String name, int MemInM, int cpu, String VDI_UUID);
	
	/**
	 * This function is added by XIEZHIWEI,To start a vm using static ip;
	 * 
	 * @param name
	 * @param MemInM
	 * @param cpu
	 * @param VDI_UUID
	 * @param StaticIp
	 * @return
	 */
	public String startVM(String name, int MemInM, int cpu, String VDI_UUID, String StaticIp, String nodeType);

	/**
	 * @param ID
	 */
	public void deleteVM(String UUID_VM);
	
	/**
	 * @param ID
	 */
	public void pauseVM(String UUID_VM);
	
	/**
	 * to poweroff a virtual node in virtual net.
	 */
	public boolean poweroffVM(String UUID_VM);
	
	/**
	 * to start a closed virtual node in virtual net.
	 */
	public String startClosedVM(String UUID_VM);
	
	/**
	 * Start a NodeManager by CloudManager
	 */
	public void HMReportIP();
	
	/**
	 * Not implemented
	 * 
	 * This method returns all VM instance attached to VDI regardless of VDI
	 * type
	 * 
	 * @param VDI_UUID
	 * @return
	 */
	public List<String> listVMSbyImg(String VDI_UUID);

	/**
	 * This method is used to extract true vm_uuid from "vmid#vrdeport"
	 * 
	 * @param UUID_VM
	 * @return
	 */
	public String formatVMID(String UUID_VM);
	
	public int formatVrdeport(String UUID_VM);

}
