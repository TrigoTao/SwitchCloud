package bupt.sc.neutron.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import bupt.sc.keystone.model.UserInfo;
import bupt.sc.keystone.service.UserInfoService;
import bupt.sc.neutron.api.SubnetManager;
import bupt.sc.neutron.model.SubnetInfo;
import bupt.sc.neutron.pojo.UserDemand;
import bupt.sc.neutron.service.SubnetInfoService;

@WebService
public class SubnetManagerImpl implements SubnetManager {
	
	private SubnetInfoService subnetInfoService;
	private UserInfoService userInfoService;
	
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
		UserInfo user = userInfoService.getUserByName(userName);
		Date now = new Date();
		if(null != user){
			SubnetInfo subnetInfo = new SubnetInfo();
			subnetInfo.setUser(user);
			subnetInfo.setUserName(userName);
			subnetInfo.setState(SubnetInfo.STATE_CREATING);
			subnetInfo.setCreateTime(now);
			subnetInfo.setModifyTime(now);
			
			subnetInfoService.saveSubnetInfo(subnetInfo);
			
			ArrayList<String> vnodeTypeList = new ArrayList<String>();
			ArrayList<Integer> vnodeList = new ArrayList<Integer>();
			vnodeTypeList = divideSubnet(userDemand);
			for (String vnt : vnodeTypeList) {
				// TODO Auto-generated method stub
			}
		}
		
		return 0;
	}

	@Override
	public boolean deleteNet(String userName, int subnetId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean modifySubnet(String userName, int subnetId,
			UserDemand userDemand) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SubnetInfo checkSubnet(String userName, int subnetId) {
		// TODO Auto-generated method stub
		return null;
	}

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
