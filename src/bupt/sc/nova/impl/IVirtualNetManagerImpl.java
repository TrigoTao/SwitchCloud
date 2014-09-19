package bupt.sc.nova.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
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
import bupt.sc.nova.statistic.VNodeInfoIaaS;
import bupt.sc.utils.CloudConfig;
import bupt.sc.utils.ConfigInstance;
import bupt.sc.utils.CoreUtil;
import bupt.sc.utils.SCPath;

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
		
		VNNodeInfo vnNodeInfo = new VNNodeInfo();
		
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
					logger.info("New VM created!, VM_UUID :" + resultVMID);
					// if result1 contains vrdeport info, delete it
					resultVMID = CoreUtil.formatVMID(resultVMID);
					logger.info("New VM created!, VM_UUID :" + resultVMID);
	
					// Get vdi on which it spawns
					String resultVDIID = iVirtualManager.getVDIIDbyVMID(resultVMID);
					
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
					}
	
					// Start Agent
					String home = SCPath.getHome();
					Process p = null;
					String line = null;
					String vmId = resultVMID.replace("-", "");
					try {
						if (nodeType.equals("MS")) {
							p = Runtime.getRuntime().exec(
									home + File.separatorChar + "scripts/startAgentWin " + name + " " + hm_ip + " " + vmId + " &");
						} else {
							p = Runtime.getRuntime().exec(
									home + File.separatorChar + "scripts/startAgent " + name + " " + hm_ip + " " + vmId + " &");
						}
						BufferedInputStream in = new BufferedInputStream(p.getInputStream());
						BufferedInputStream err = new BufferedInputStream(p.getErrorStream());
						BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
						BufferedReader errBr = new BufferedReader(new InputStreamReader(err));
						StringBuffer buff = new StringBuffer();
						while ((line = errBr.readLine()) != null) {
							logger.error("[ERROR][Agent Starting] " + line);
						}
						while ((line = inBr.readLine()) != null) {
							logger.info("[INFO] " + line);
						}
						String errorResult = buff.toString();
	
						if (errorResult.contains("ERROR") || errorResult.contains("error")) {
							logger.error("[ERROR][Agent Starting] " + errorResult);
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						if (p != null)
							p.destroy();
	
					}
				}
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return vnNodeInfo;
	}

	@Override
	public boolean deleteVNodeInIaaS(String vmid) {
		// TODO Auto-generated method stub
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
	public VNodeInfoIaaS checkVNodeInIaaS(String nodeId) {
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
