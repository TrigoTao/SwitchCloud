package bupt.sc.neutron.api;

import java.io.FileNotFoundException;
import java.util.List;

import javax.jws.WebService;

import bupt.sc.neutron.model.VNodeInfo;

@WebService
public interface VNodeManager {
	public int addVNode(String nodeType, int subnetId) throws FileNotFoundException;
	public boolean deleteVNode(int vnodeId) throws FileNotFoundException;
	public VNodeInfo checkVNode(int vnodeId);
	public List<VNodeInfo> checkAllVNodes();
	public List<VNodeInfo> checkVNodesInSubnet(int subnetId);
}
