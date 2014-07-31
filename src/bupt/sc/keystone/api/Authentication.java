package bupt.sc.keystone.api;

import javax.jws.WebService;

@WebService
public interface Authentication{
	public boolean authentication(String userName, String pwd , String userType);
	public boolean checkUserExist(String userName, String userType);
	public boolean isDuplicate(String userName, String userType);
	public boolean login(String userName, String pwd, String userType);
	public boolean logout(String userName, String userType);
}