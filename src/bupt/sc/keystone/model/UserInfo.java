package bupt.sc.keystone.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;

@Entity
public class UserInfo {
	public static final int STATE_LOGOUT = 0;
	public static final int STATE_LOGIN  = 1;
	
	public static final String TYPE_MANAGER = "MANAGER";
	public static final String TYPE_USER    = "USER";
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@NaturalId
	@NotNull
	private String userName;
	@NotNull
	private String pwd;
	@NotNull
	private String userType;
	@NotNull
	private Integer userState;
	
	public UserInfo() {
		userState = STATE_LOGOUT;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer userId) {
		this.id = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Integer getUserState() {
		return userState;
	}
	public void setUserState(Integer userState) {
		this.userState = userState;
	}
}
