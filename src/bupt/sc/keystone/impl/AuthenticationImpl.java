package bupt.sc.keystone.impl;

import javax.jws.WebService;

import bupt.sc.keystone.api.Authentication;
import bupt.sc.keystone.model.UserInfo;
import bupt.sc.keystone.service.UserInfoService;

@WebService
public class AuthenticationImpl implements Authentication {

	private UserInfoService userInfoService;
	
	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	@Override
	public boolean authentication(String userName, String pwd, String userType) {
		UserInfo userInfo =  userInfoService.getInfoByName(userName, userType);
		if(userInfo != null && userInfo.getPwd().equals(pwd))
			return true;
		else
			return false;
	}
	@Override
	public boolean isDuplicate(String userName, String userType) {
		return checkUserExist(userName,userType);
	}

	@Override
	public boolean login(String userName, String pwd, String userType) {
		UserInfo userInfo =  userInfoService.getInfoByName(userName, userType);
		if(userInfo != null && userInfo.getPwd().equals(pwd)){
			userInfo.setUserState(UserInfo.STATE_LOGIN);
			userInfoService.saveInfo(userInfo);
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean logout(String userName, String userType) {
		UserInfo userInfo =  userInfoService.getInfoByName(userName, userType);
		if(userInfo != null){
			userInfo.setUserState(UserInfo.STATE_LOGOUT);
			userInfoService.saveInfo(userInfo);
			return true;
		}
		else
			return false;
	}

	@Override
	public boolean checkUserExist(String userName, String userType) {
		return userInfoService.checkUserExist(userName, userType);
	}

}
