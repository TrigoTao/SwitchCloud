package bupt.sc.neutron.impl;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import bupt.sc.keystone.model.UserInfo;
import bupt.sc.keystone.service.UserInfoService;
import bupt.sc.neutron.api.SubnetManager;
import bupt.sc.neutron.api.VNodeManager;
import bupt.sc.neutron.model.SubnetInfo;
import bupt.sc.neutron.model.UserDemand;
import bupt.sc.neutron.model.VNodeInfo;
import bupt.sc.neutron.service.SubnetInfoService;
import bupt.sc.neutron.service.UserDemandService;
import bupt.sc.neutron.service.VNodeInfoService;
import bupt.sc.nova.model.VNNodeInfo;
import bupt.sc.nova.service.VNNodeInfoService;
import bupt.sc.utils.CloudConfig;
import bupt.sc.utils.ConfigInstance;

@WebService
public class SubnetManagerImpl implements SubnetManager {
	private final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass()); 
	
	private VNodeManager vNodeManager;
	private SubnetInfoService subnetInfoService;
	private UserInfoService userInfoService;
	private VNodeInfoService vNodeInfoService;
	private VNNodeInfoService vnNodeInfoService;
	private UserDemandService userDemandService;
	
	public VNodeManager getvNodeManager() {
		return vNodeManager;
	}

	public void setvNodeManager(VNodeManager vNodeManager) {
		this.vNodeManager = vNodeManager;
	}

	public SubnetInfoService getSubnetInfoService() {
		return subnetInfoService;
	}

	public void setSubnetInfoService(SubnetInfoService subnetInfoService) {
		this.subnetInfoService = subnetInfoService;
	}
	
	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public VNodeInfoService getvNodeInfoService() {
		return vNodeInfoService;
	}

	public void setvNodeInfoService(VNodeInfoService vNodeInfoService) {
		this.vNodeInfoService = vNodeInfoService;
	}

	public VNNodeInfoService getVnNodeInfoService() {
		return vnNodeInfoService;
	}

	public void setVnNodeInfoService(VNNodeInfoService vnNodeInfoService) {
		this.vnNodeInfoService = vnNodeInfoService;
	}

	public UserDemandService getUserDemandService() {
		return userDemandService;
	}

	public void setUserDemandService(UserDemandService userDemandService) {
		this.userDemandService = userDemandService;
	}

	@Override
	public SubnetInfo checkSubnet(int subnetId) {
		return subnetInfoService.getSubnet(subnetId);
	}

	@Override
	public List<SubnetInfo> checkAllSubnets() {
		return subnetInfoService.getAllSubnets();
	}

	@Override
	public int createNet(String userName, UserDemand userDemand) {
		UserInfo user = userInfoService.getInfoByName(userName);
		Date now = new Date();
		try {
			CloudConfig cc = ConfigInstance.getCloudConfig();

			if(null != user){
				SubnetInfo subnetInfo = new SubnetInfo();
				subnetInfo.setUser(user);
				subnetInfo.setUserName(userName);
				subnetInfo.setState(SubnetInfo.STATE_CREATING);
				subnetInfo.setCreateTime(now);
				subnetInfo.setModifyTime(now);
				subnetInfoService.saveSubnetInfo(subnetInfo);
				
				ArrayList<String> vnodeTypeList = new ArrayList<String>();
				vnodeTypeList = divideSubnet(userDemand);
				
				int vnodeId = 0;
				int caps = userDemand.getAudioCap();
				
				logger.debug("subnet "+ subnetInfo.getId() + " nodes num is: " + vnodeTypeList.size());
				for (String vnt : vnodeTypeList) {
					logger.debug("start a " + vnt);
					vnodeId = vNodeManager.addVNode(vnt, subnetInfo.getId());
					if(vnodeId == -1){
						logger.error("addVNode fail when creatNet");
						break;
					}
					
					if (vnt.equals("SCSCF")) {
						VNodeInfo vNodeInfo = vNodeInfoService.getVNode(vnodeId);
						VNNodeInfo vnNodeInfo = vnNodeInfoService.getVNNodeInfo(vNodeInfo.getVmid());
						String nodeName = vnNodeInfo.getNodeName();
						String jetty_home = cc.getScpritsHome();
						Process p = null;
						String line = null;
						BufferedInputStream in = null;
						BufferedInputStream err = null;
						BufferedReader inBr = null;
						BufferedReader errBr = null;
						try {
							p = Runtime.getRuntime().exec(jetty_home + File.separatorChar + "scripts/modifyCSCFMax " + nodeName + " " + caps);
							in = new BufferedInputStream(p.getInputStream());
							err = new BufferedInputStream(p.getErrorStream());
							inBr = new BufferedReader(new InputStreamReader(in));
							errBr = new BufferedReader(new InputStreamReader(err));
							while ((line = errBr.readLine()) != null) {
								System.out.println("[ERROR][CSCFServer Modifying] " + line);
							}
							while ((line = inBr.readLine()) != null) {
								System.out.println("[INFO][CSCFServer Modified] " + nodeName + " " + caps + " " + line);
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (p != null)
								p.destroy();
							try {
								in.close();
								err.close();
								inBr.close();
								errBr.close();
								in = null;
								err = null;
								errBr = null;
								inBr = null;
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						caps = caps - 1500;
					}
				}
				
				subnetInfo.setState(SubnetInfo.STATE_READY);
				subnetInfoService.saveSubnetInfo(subnetInfo);
				
				//userDemandService.saveUserDemand(userDemand);
				
				return subnetInfo.getId();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public boolean deleteNet(String userName, int subnetId) {
		int result = 1;
		
		List<VNodeInfo> vNodes = vNodeInfoService.getVNodesBySubnetId(subnetId);
		try {
			for(VNodeInfo vNode : vNodes){
				if( vNodeManager.deleteVNode(vNode.getId()) == false ){
					logger.error("Release VNode id = "+ vNode.getId() +" failure!");
					result = 0;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		if(result != 0){
			//subnetInfoService
			
//			sql = "delete from subnet where subnetId = "+ subnetId;
			return true;
		}	
		else {
			return false;
		}
	}

	@Override
	public boolean modifySubnet(String userName, int subnetId,
			UserDemand userDemand) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public SubnetInfo checkSubnet(String userName, int subnetId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	private ArrayList<String> divideSubnet(UserDemand userDemand) {
		ArrayList<String> vnodeTypeList = new ArrayList<String>();
		
		int audioCap = userDemand.getAudioCap();
		int mediaCap = userDemand.getMediaCap();
		int strategy = userDemand.getStrategy();
		
		while (audioCap > 0){
			vnodeTypeList.add("SCSCF");
			audioCap-=1500;
		}
		while(mediaCap >0){
			vnodeTypeList.add("MS");
			mediaCap-=100;
		}
		
		switch(strategy){
		case 0:
			break;
			
		default:
			break;
		}
		
		return vnodeTypeList;
	}

	
}
