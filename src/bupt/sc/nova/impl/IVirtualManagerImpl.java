package bupt.sc.nova.impl;

import java.util.List;

import javax.jws.WebService;

import bupt.sc.nova.api.IVirtualManager;
import bupt.sc.nova.statistic.VMInfo;
import bupt.sc.nova.vm.VMOManagerFactory;
import bupt.sc.nova.vm.VMOperationManager;

@WebService
public class IVirtualManagerImpl implements IVirtualManager {
	private VMOManagerFactory vmoManagerFactory;
	private VMOperationManager vmOperationManager;
	//private VMOperationManager vmOperationManager = VMOManagerFactory.getVMOperationManager();

	public VMOManagerFactory getVmoManagerFactory() {
		return vmoManagerFactory;
	}

	public void setVmoManagerFactory(VMOManagerFactory vmoManagerFactory) {
		this.vmoManagerFactory = vmoManagerFactory;
		setVmOperationManager(vmoManagerFactory.getVmOperationManager());
	}

	private void setVmOperationManager(VMOperationManager vmOperationManager) {
		this.vmOperationManager = vmOperationManager;
	}
	
	@Override
	public List<String> listVDIID() {
		return vmOperationManager.listVDIID();
	}

	@Override
	public List<String> listVMNames() {
		return vmOperationManager.listVMNames();
	}

	@Override
	public List<String> listVMIDs() {
		return vmOperationManager.listVMIDs();
	}

	@Override
	public String getVMMacbyVMID(String VM_UUID) {
		return vmOperationManager.getVMMacbyVMID(VM_UUID);
	}

	@Override
	public String getVMIPbyVMID(String VM_UUID) {
		return vmOperationManager.getVMIPbyVMID(VM_UUID);
	}

	@Override
	public String getVMNamebyVMID(String VM_UUID) {
		return vmOperationManager.getVMNamebyVMID(VM_UUID);
	}

	@Override
	public int getCPUsbyVMID(String VM_UUID) {
		return vmOperationManager.getCPUsbyVMID(VM_UUID);
	}

	@Override
	public int getMEMbyVMID(String VM_UUID) {
		return vmOperationManager.getMEMbyVMID(VM_UUID);
	}

	@Override
	public int getVRDEportbyVMID(String VM_UUID) {
		return vmOperationManager.getVRDEportbyVMID(VM_UUID);
	}

	@Override
	public String getVMStatebyVMID(String VM_UUID) {
		return vmOperationManager.getVMStatebyVMID(VM_UUID);
	}

	@Override
	public VMInfo getVMStatbyVMID(String id) {
		return vmOperationManager.getVMStatbyVMID(id);
	}

	@Override
	public String getVMIDbyVMMac(String vmmac) {
		return vmOperationManager.getVMIDbyVMMac(vmmac);
	}

	@Override
	public String getVMIDbyVMIP(String vmip) {
		return vmOperationManager.getVMIDbyVMIP(vmip);
	}

	@Override
	public String getVMIDbyVDIID(String VDI_UUID) {
		return vmOperationManager.getVMIDbyVDIID(VDI_UUID);
	}

	@Override
	public String getVDIIDbyVMID(String VM_UUID) {
		return vmOperationManager.getVDIIDbyVMID(VM_UUID);
	}

	@Override
	public String getVDIType(String VDI_UUID) {
		return vmOperationManager.getVDIType(VDI_UUID);
	}

	@Override
	public String getLocationbyVMID(String VM_UUID) {
		return vmOperationManager.getLocationbyVMID(VM_UUID);
	}

	@Override
	public boolean isVDIExist(String VDI_UUID) {
		return vmOperationManager.isVDIExist(VDI_UUID);
	}

	@Override
	public boolean isVDIBase(String VDI_UUID) {
		return vmOperationManager.isVDIBase(VDI_UUID);
	}

	@Override
	public boolean isVDIImmutable(String VDI_UUID) {
		return vmOperationManager.isVDIImmutable(VDI_UUID);
	}

	@Override
	public boolean isVDINormal(String VDI_UUID) {
		return vmOperationManager.isVDINormal(VDI_UUID);
	}

	@Override
	public boolean isVDIWritethrough(String VDI_UUID) {
		return vmOperationManager.isVDIWritethrough(VDI_UUID);
	}

	@Override
	public boolean isVDIUsable(String VDI_UUID) {
		return vmOperationManager.isVDIUsable(VDI_UUID);
	}

	@Override
	public boolean isVMHealthy(String VM_UUID) {
		return vmOperationManager.isVMHealthy(VM_UUID);
	}

	@Override
	public boolean immuVDI(String VDI_UUID) {
		return vmOperationManager.immuVDI(VDI_UUID);
	}

	@Override
	public boolean normVDI(String VDI_UUID) {
		return vmOperationManager.normVDI(VDI_UUID);
	}

	@Override
	public boolean shareVDI(String VDI_UUID) {
		return vmOperationManager.shareVDI(VDI_UUID);
	}

	@Override
	public boolean writethroughVDI(String VDI_UUID) {
		return vmOperationManager.writethroughVDI(VDI_UUID);
	}

	@Override
	public boolean multiattachVDI(String VDI_UUID) {
		return vmOperationManager.multiattachVDI(VDI_UUID);
	}

	@Override
	public boolean unRegisterVDI(String VDI_UUID) {
		return vmOperationManager.unRegisterVDI(VDI_UUID);
	}

	@Override
	public boolean deleteVDI(String VDI_UUID) {
		return vmOperationManager.deleteVDI(VDI_UUID);
	}

	@Override
	public String quickStartVM(String name, int MemInM, int cpu, String VDI_UUID) {
		return vmOperationManager.startVM(name, MemInM, cpu, VDI_UUID);
	}

	@Override
	public String startVM(String name, int MemInM, int cpu, String VDI_UUID,
			String StaticIp, String nodeType) {
		//vmOperationManager = VMOManagerFactory.getVMOperationManager();
		return vmOperationManager.startVM(name, MemInM, cpu, VDI_UUID, StaticIp, nodeType);
	}

	@Override
	public void deleteVM(String UUID_VM) {
		vmOperationManager.deleteVM(UUID_VM);
	}

	@Override
	public void pauseVM(String UUID_VM) {
		vmOperationManager.pauseVM(UUID_VM);
	}

	@Override
	public boolean poweroffVM(String UUID_VM) {
		return vmOperationManager.poweroffVM(UUID_VM);
	}

	@Override
	public String startClosedVM(String UUID_VM) {
		return vmOperationManager.startClosedVM(UUID_VM);
	}

	@Override
	public void HMReportIP() {
		vmOperationManager.HMReportIP();
	}

	@Override
	public List<String> listVMSbyImg(String VDI_UUID) {
		return vmOperationManager.listVMSbyImg(VDI_UUID);
	}

	@Override
	public String formatVMID(String UUID_VM) {
		return vmOperationManager.formatVMID(UUID_VM);
	}

	@Override
	public int formatVrdeport(String UUID_VM) {
		return vmOperationManager.formatVrdeport(UUID_VM);
	}
}
