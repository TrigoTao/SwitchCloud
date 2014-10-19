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
	public static final Integer STATE_CREATING = 0;
	public static final Integer STATE_READY = 1;//done
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@ManyToOne @NotNull
    @JoinColumn(name = "userId")
    private UserInfo user;
	@NotNull
	private String userName;
	@NotNull
	private Integer state;
	@NotNull
	private Date createTime;
	private Date modifyTime;
	
	public Integer getId() {
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
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
	
	@Override
	public String toString() {
		return "SubnetInfo [id=" + id + ", user=" + user + ", userName="
				+ userName + ", state=" + state + ", createTime=" + createTime
				+ ", modifyTime=" + modifyTime + "]";
	}
}
