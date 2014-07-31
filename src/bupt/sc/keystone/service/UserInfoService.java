package bupt.sc.keystone.service;

import java.util.List;

import bupt.sc.keystone.model.UserInfo;


public interface UserInfoService {	
	public boolean checkUserExist(String name, String userType);
	public UserInfo getInfoByName(String name, String userType);
	public UserInfo getAdminByName(String name);
	public UserInfo getUserByName(String name);
	public List<UserInfo> getAllIno();
	
	public void saveInfo(UserInfo userInfo);
	public boolean deleteInfoByName(String name, String userType);
}
