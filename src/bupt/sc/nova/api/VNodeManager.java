package bupt.sc.nova.api;

import java.io.FileNotFoundException;

import javax.jws.WebService;

import bupt.sc.nova.model.VNodeInfo;

@WebService
public interface VNodeManager {
	public int addVNode(String nodeType, int subnetId) throws FileNotFoundException;
	public boolean deleteVNode(int vnodeId);
	public VNodeInfo checkVNode(int vnodeId);
	public Object[] checkVNodesInSubnet(int subnet);
	public Object[] checkAllVNodes();
}
