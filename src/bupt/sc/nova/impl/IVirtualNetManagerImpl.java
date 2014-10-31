package bupt.sc.nova.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bupt.sc.nova.api.ICloudManager;
import bupt.sc.nova.api.IVirtualManager;
import bupt.sc.nova.api.IVirtualNetManager;
import bupt.sc.nova.model.VNNodeInfo;
import bupt.sc.nova.model.VNVdiInfo;
import bupt.sc.nova.service.IpInfoService;
import bupt.sc.nova.service.VNNodeInfoService;
import bupt.sc.nova.service.VNVdiInfoService;
import bupt.sc.utils.CloudConfig;
import bupt.sc.utils.ConfigInstance;
import bupt.sc.utils.CoreUtil;
import bupt.sc.utils.SCPath;
import bupt.sc.utils.StreamGobbler;

public class IVirtualNetManagerImpl implements IVirtualNetManager{
	private VNNodeInfoService vnNodeInfoService;
	private VNVdiInfoService vnVdiInfoService;
	private IpInfoService ipInfoService;
	
	private final Logger logger = LogManager.getLogger(IVirtualNetManagerImpl.class.getName());

	public VNNodeInfoService getVnNodeInfoService() {
		return vnNodeInfoService;
	}

	public void setVnNodeInfoService(VNNodeInfoService vnNodeInfoService) {
		this.vnNodeInfoService = vnNodeInfoService;
	}
	
	public VNVdiInfoService getVnVdiInfoService() {
		return vnVdiInfoService;
	}

	public void setVnVdiInfoService(VNVdiInfoService vnVdiInfoService) {
		this.vnVdiInfoService = vnVdiInfoService;
	}
	
	public IpInfoService getIpInfoService() {
		return ipInfoService;
	}

	public void setIpInfoService(IpInfoService ipInfoService) {
		this.ipInfoService = ipInfoService;
	}

	@Override
	public VNNodeInfo addVNodeToIaaS(String nodeType, int subnetId) {
		
		VNNodeInfo vnNodeInfo = null;
		
		try {
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();

			String name = nameNode();
			int MemInM = 512;
			int cpu = 1;
			
			CloudConfig cc = ConfigInstance.getCloudConfig();
			String vdi_uuid = getVDIUUIDbynodeType(nodeType);
			String resultVMID = null;
			
			String hm_ip = CoreUtil.getRemoteT("http://"+cc.getCloudIp()+cc.getCloudServiceSuffix(), ICloudManager.class)
							.ChooseHM();
			String vm_static_ip = ipInfoService.leaseIp();
			logger.debug("static ip is:" + vm_static_ip);
	
			if (hm_ip != null && vm_static_ip != null) {
				
				String servicesSuffix = cc.getNodeServiceSuffix();
				String targetEendPoint = "http://" + hm_ip + servicesSuffix;
				logger.info("VirtualNet: addVNode-startVM: " + targetEendPoint);
				
				IVirtualManager iVirtualManager = CoreUtil.getRemoteT(targetEendPoint, IVirtualManager.class);
				resultVMID = iVirtualManager.startVM(name, MemInM, cpu, vdi_uuid, vm_static_ip, nodeType);
				
				if (resultVMID == null)
					logger.error("VM Created failed!");
				else {
					logger.debug("New VM created!, VM_UUID :" + resultVMID);
					// if result1 contains vrdeport info, delete it
					resultVMID = CoreUtil.formatVMID(resultVMID);
					logger.info("New VM created!, VM_UUID :" + resultVMID);
	
					// Get vdi on which it spawns
					String resultVDIID = iVirtualManager.getVDIIDbyVMID(resultVMID);
					logger.debug("VDIID :" + resultVDIID);
					
					vnNodeInfo = new VNNodeInfo();
					// vnInfo.setSubnetId(subnetId);
					if (resultVDIID != null) {
						vnNodeInfo.setNodeId(resultVMID);
						vnNodeInfo.setNodeName(name);
						vnNodeInfo.setNodeType(nodeType);
						vnNodeInfo.setIpAddr(vm_static_ip);
						vnNodeInfo.setHm_ip(hm_ip);
						vnNodeInfo.setCreateTime(date);
						vnNodeInfo.setSubnetId(subnetId);
						vnNodeInfo.setState(VNNodeInfo.STATE_RUNNING);
						
						vnNodeInfoService.add(vnNodeInfo);
						ipInfoService.setUseIp(vm_static_ip);
					}
	
					// Start Agent
					String home = SCPath.getHome();
					Process p = null;
					String vmId = resultVMID.replace("-", "");
					try {
						logger.debug("start "+nodeType+" Agent");
						if (nodeType.equals("MS")) {
							p = Runtime.getRuntime().exec(
									home + File.separatorChar + "scripts/startAgentWin " + name + " " + hm_ip + " " + vmId + " &");
						} else {
							logger.debug("do " + home + "scripts/startAgent");
							p = new ProcessBuilder(home + "scripts/startAgent", name, hm_ip, vmId, "&").start();
							//p = new ProcessBuilder("/home/jiaohuan/Desktop/TestShell/echo", "&").start();
						}
						StreamGobbler infoStreamGobbler = new StreamGobbler(p.getInputStream(), "info", logger);
						StreamGobbler errStreamGobbler = new StreamGobbler(p.getErrorStream(), "err", logger);
						
						infoStreamGobbler.start();
						errStreamGobbler.start();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			else{
				logger.error("No available ip");
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		return vnNodeInfo;
	}

	@Override
	public boolean deleteVNodeInIaaS(String vmid) {
		try {
			CloudConfig cc = ConfigInstance.getCloudConfig();
			VNNodeInfo vnNodeInfo = vnNodeInfoService.getVNNodeInfo(vmid);
			String hm_ip = vnNodeInfo != null ? vnNodeInfo.getHm_ip() : null;
			if (hm_ip != null) {
				String targetEendPoint = "http://" + hm_ip + cc.getNodeServiceSuffix();
				logger.debug(targetEendPoint);
				CoreUtil.getRemoteT(targetEendPoint, IVirtualManager.class).deleteVM(vmid);

				ipInfoService.releaseIp(vnNodeInfo.getIpAddr());
				vnNodeInfoService.remove(vnNodeInfo);
				//dbOperation.delete("delete from node_sysinfo where vmId = '" + in0 + "'");
				return true;
			}else{
				return false;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String poweroffVNodeInIaaS(String vmid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String startVNodeInIaaS(String vmid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VNNodeInfo checkVNodeInIaaS(String nodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] checkAllVNodeInIaaS() {
		// TODO Auto-generated method stub
		return null;
	}

	private String nameNode() {
		String name = null;

	 	List<VNNodeInfo> vnNodeInfos = vnNodeInfoService.getAllVNNodeInfo();

		if (null == vnNodeInfos || vnNodeInfos.isEmpty()) {
			return "node000001";
		} else {
			Integer maxN = new Integer(0);
			Integer n = new Integer(0);
			for(VNNodeInfo vnNodeInfo : vnNodeInfos){
				n = Integer.parseInt( vnNodeInfo.getNodeName().substring(4) );
				if(n > maxN)
					maxN = n;
			}
			maxN++;
			name = "node";
			for (int i = 0; i < 6 - maxN.toString().length(); i++) {
				name = name + "0";
			}
			name = name + maxN.toString();
			logger.debug(name);
		}
		return name;
	}
	
	private String getVDIUUIDbynodeType(String type) {
		VNVdiInfo vnVdiInfo = vnVdiInfoService.getVNVdiInfo(type);

		return ( vnVdiInfo != null ) ? vnVdiInfo.getVdiUuid() : null;
	}

}
