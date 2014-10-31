package bupt.sc.nova.service;

import java.util.List;

import bupt.sc.nova.model.VNNodeInfo;

public interface VNNodeInfoService {
	public void add(VNNodeInfo vnNodeInfo);
	public void remove(VNNodeInfo vnNodeInfo);
	
	public List<VNNodeInfo> getAllVNNodeInfo();
	public List<VNNodeInfo> getVNNodeInfosByType(String type);
	public VNNodeInfo getVNNodeInfo(String id);
}
