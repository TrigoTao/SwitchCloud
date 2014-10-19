package bupt.sc.neutron.service;

import java.util.List;

import bupt.sc.neutron.model.VNodeInfo;

public interface VNodeInfoService {
	public void saveVNodeInfo(VNodeInfo vNodeInfo);
	
	public void deleteInfo(VNodeInfo vNodeInfo);
	
	public VNodeInfo getVNode(int vNodeId);
	public List<VNodeInfo> getVNodesBySubnetId(int subnetId);
	public List<VNodeInfo> getAllVNodes();
}
