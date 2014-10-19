package bupt.sc.neutron.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/*
+------------+-------------+------+-----+---------+----------------+
| Field      | Type        | Null | Key | Default | Extra          |
+------------+-------------+------+-----+---------+----------------+
| id         | int(11)     | NO   | PRI | NULL    | auto_increment |
| type       | varchar(10) | YES  |     | NULL    |                |
| ipAddr     | varchar(20) | YES  |     | NULL    |                |
| createtime | datetime    | YES  |     | NULL    |                |
| vmid       | varchar(36) | YES  |     | NULL    |                |
| hm_ip      | varchar(16) | YES  |     | NULL    |                |
| subnetid   | int(11)     | YES  |     | NULL    |                |
| state      | varchar(30) | YES  |     | NULL    |                |
+------------+-------------+------+-----+---------+----------------+
 */
@Entity
public class VNodeInfo {
	public static final Integer STATE_CREATING = 0;
	public static final Integer STATE_START = 1;
	
	@Id
	@GeneratedValue
	private Integer id;
	
	private String type;
	private String ipAddr;
	private String hmIP;
	private String vmid;
	private Integer state;
	
	@NotNull
	private Date createTime;
	private Date modifyTime;
	
	@ManyToOne //@NotNull
    @JoinColumn(name = "subnetId")
    private SubnetInfo subnet;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getHmIP() {
		return hmIP;
	}

	public void setHmIP(String hmIP) {
		this.hmIP = hmIP;
	}

	public String getVmid() {
		return vmid;
	}

	public void setVmid(String vmid) {
		this.vmid = vmid;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public SubnetInfo getSubnet() {
		return subnet;
	}

	public void setSubnet(SubnetInfo subnet) {
		this.subnet = subnet;
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
