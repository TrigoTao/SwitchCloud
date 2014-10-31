package bupt.sc.neutron.impl;

import java.io.FileNotFoundException;
import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import bupt.sc.neutron.api.VNodeManager;
import bupt.sc.neutron.model.SubnetInfo;
import bupt.sc.neutron.model.VNodeInfo;
import bupt.sc.neutron.service.SubnetInfoService;
import bupt.sc.neutron.service.VNodeInfoService;
import bupt.sc.nova.api.IVirtualNetManager;
import bupt.sc.nova.model.VNNodeInfo;
import bupt.sc.utils.CloudConfig;
import bupt.sc.utils.ConfigInstance;
import bupt.sc.utils.CoreUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@WebService
public class VNodeManagerImpl implements VNodeManager {
	private VNodeInfoService vNodeInfoService;
	private SubnetInfoService subnetInfoService;
	
	private final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 
	
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
			logger.info("[Cloud Info] addVNodeToIaaS done.");
			
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
	public boolean deleteVNode(int vnodeId) throws FileNotFoundException {
		logger.debug("In ---- " + System.currentTimeMillis());
		
		VNodeInfo vNodeInfo = vNodeInfoService.getVNode(vnodeId);

		CloudConfig cc = ConfigInstance.getCloudConfig();
		String targetEendPoint = "http://" + cc.getCloudIp() + cc.getVirtualNetServiceSuffix();
		logger.debug("[Cloud Debug] targetEendPoint= " + targetEendPoint);

		IVirtualNetManager ivNetManager = CoreUtil.getRemoteT(targetEendPoint, IVirtualNetManager.class);
		boolean iaasResult = ivNetManager.deleteVNodeInIaaS(vNodeInfo.getVmid());

		if (iaasResult == true) {
			vNodeInfoService.deleteInfo(vNodeInfo);
			logger.info("delete success!");
		}
		
		logger.debug("Out ---- " + System.currentTimeMillis());
		return iaasResult;
	}

	@Override
	public VNodeInfo checkVNode(int vnodeId) {
		return vNodeInfoService.getVNode(vnodeId);
	}
	
	@Override
	public List<VNodeInfo> checkVNodesInSubnet(int subnetId) {
		return vNodeInfoService.getVNodesBySubnetId(subnetId);
	}

	@Override
	public List<VNodeInfo> checkAllVNodes() {
		return vNodeInfoService.getAllVNodes();
	}
	
	public static void main(String args[]) {
	}
}
