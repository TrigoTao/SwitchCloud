package bupt.sc.neutron.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import bupt.sc.keystone.model.UserInfo;

@Entity
public class SubnetInfo {
	public static final int STATE_LOGOUT = 0;
	public static final int STATE_LOGIN  = 1;
	
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne @NotNull
    @JoinColumn(name = "userId")
    private UserInfo user;
	@NotNull
	private String userName;
	@NotNull
	private String state;
	@NotNull
	private Date createTime;
	private Date modifyTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserInfo getUser() {
		return user;
	}
	public void setUser(UserInfo user) {
		this.user = user;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
