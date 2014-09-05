package bupt.sc.nova.service;

import java.util.List;

import bupt.sc.nova.model.VNodeInfo;

public interface VNodeInfoService {
	public void saveVNodeInfo(VNodeInfo vNodeInfo);
	public VNodeInfo getVNode(int vNodeId);
	public List<VNodeInfo> getAllVNodes();
}
