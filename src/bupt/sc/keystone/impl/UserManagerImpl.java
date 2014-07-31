package bupt.sc.keystone.impl;

import java.util.List;

import javax.jws.WebService;

import bupt.sc.keystone.api.UserManager;
import bupt.sc.keystone.model.UserInfo;
import bupt.sc.keystone.service.UserInfoService;

@WebService
public class UserManagerImpl implements UserManager {
	UserInfoService userInfoService;
	
	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	@Override
	public int register(String userName, String pwd, String userType) {
		if(!userInfoService.checkUserExist(userName, userType)){
			UserInfo userInfo = new UserInfo();
			userInfo.setUserName(userName);
			userInfo.setPwd(pwd);
			userInfo.setUserType(userType);
			userInfoService.saveInfo(userInfo);
			
			return userInfo.getId();
		}
		
		return 0;
	}

	@Override
	public int addAdmin(String name, String pwd) {
		return register(name,pwd,UserInfo.TYPE_MANAGER);
	}

	@Override
	public boolean deleteAdmin(String name) {
		return userInfoService.deleteInfoByName(name, UserInfo.TYPE_MANAGER);
	}

	@Override
	public int addUser(String name, String pwd) {
		return register(name,pwd,UserInfo.TYPE_USER);
	}

	@Override
	public boolean deleteUser(String name) {
		return userInfoService.deleteInfoByName(name, UserInfo.TYPE_USER);
	}

	@Override
	public boolean changeAuthen(String name, String fromType, String toType) {
		 UserInfo info = userInfoService.getInfoByName(name, fromType);
		 if(info != null){ info.setUserType(toType); userInfoService.saveInfo(info); return true;}
		 else return false; 
	}

	@Override
	public UserInfo checkInfo(String name, String userType) {
		return userInfoService.getInfoByName(name, userType);
	}

	@Override
	public List<UserInfo> checkAllInfo() {
		return userInfoService.getAllIno();
	}

}
