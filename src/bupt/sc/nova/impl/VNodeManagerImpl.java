package bupt.sc.nova.impl;

import java.io.FileNotFoundException;
import java.util.Date;

import javax.jws.WebService;

import bupt.sc.neutron.model.SubnetInfo;
import bupt.sc.neutron.service.SubnetInfoService;
import bupt.sc.nova.api.IVirtualNetManager;
import bupt.sc.nova.api.VNodeManager;
import bupt.sc.nova.model.VNNodeInfo;
import bupt.sc.nova.model.VNodeInfo;
import bupt.sc.nova.service.VNodeInfoService;
import bupt.sc.utils.CloudConfig;
import bupt.sc.utils.ConfigInstance;
import bupt.sc.utils.CoreUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebService
public class VNodeManagerImpl implements VNodeManager {
	private VNodeInfoService vNodeInfoService;
	private SubnetInfoService subnetInfoService;
	
	private final Logger logger = LogManager.getLogger(VNodeManagerImpl.class.getName()); 
	
	public VNodeInfoService getvNodeInfoService() {
		return vNodeInfoService;
	}

	public void setvNodeInfoService(VNodeInfoService vNodeInfoService) {
		this.vNodeInfoService = vNodeInfoService;
	}

	public SubnetInfoService getSubnetInfoService() {
		return subnetInfoService;
	}

	public void setSubnetInfoService(SubnetInfoService subnetInfoService) {
		this.subnetInfoService = subnetInfoService;
	}

	@Override
	public int addVNode(String nodeType, int subnetId) throws FileNotFoundException {
		SubnetInfo subnetInfo = subnetInfoService.getSubnet(subnetId);
		if(null != subnetInfo){
			//System.out.println(subnetInfo.getId());
			
			VNodeInfo vNodeInfo = new VNodeInfo(); 
			vNodeInfo.setState(VNodeInfo.STATE_CREATING);
			vNodeInfo.setCreateTime(new Date());
			vNodeInfo.setSubnet(subnetInfo);
			vNodeInfoService.saveVNodeInfo(vNodeInfo);
			
			CloudConfig cc = ConfigInstance.getCloudConfig();
			String targetEendPoint = "http://" + cc.getCloudIp() + cc.getVirtualNetServiceSuffix();
	
			logger.debug("[Cloud Debug] targetEendPoint= " + targetEendPoint);
			IVirtualNetManager ivNetManager = CoreUtil.getRemoteT(targetEendPoint, IVirtualNetManager.class);
			VNNodeInfo vnNodeInfo = ivNetManager.addVNodeToIaaS(nodeType, subnetId);
			
			if(null != vnNodeInfo){
				vNodeInfo.setIpAddr(vnNodeInfo.getIpAddr());
				vNodeInfo.setCreateTime(vnNodeInfo.getCreateTime()); 
				vNodeInfo.setVmid(vnNodeInfo.getNodeId());
				vNodeInfo.setHmIP(vnNodeInfo.getHm_ip());
				vNodeInfo.setState(VNodeInfo.STATE_START);
				vNodeInfoService.saveVNodeInfo(vNodeInfo);
				
				return vNodeInfo.getId();
			}
		}
		return -1;
	}

	@Override
	public boolean deleteVNode(int vnodeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public VNodeInfo checkVNode(int vnodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] checkVNodesInSubnet(int subnet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] checkAllVNodes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String args[]) {
		VNodeManager vNodeManager = new VNodeManagerImpl();
		//vNodeManager.addVNode(nodeType, subnetId);
	}
}
