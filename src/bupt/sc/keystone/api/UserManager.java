package bupt.sc.keystone.api;

import java.util.List;

import javax.jws.WebService;

import bupt.sc.keystone.model.UserInfo;

@WebService
public interface UserManager {
	public int register(String userName, String pwd, String userType);
	public int addAdmin(String name, String pwd);
    public boolean deleteAdmin(String name);
    public int addUser(String name, String pwd);
    public boolean deleteUser(String name);
    public boolean changeAuthen(String name,  String fromType, String toType);
    public UserInfo checkInfo(String name, String userType);
    public List<UserInfo> checkAllInfo();
}
