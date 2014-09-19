package bupt.sc.nova.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/************************************************************************
	+------------+-------------+------+-----+---------+-------+
	| Field      | Type        | Null | Key | Default | Extra |
	+------------+-------------+------+-----+---------+-------+
	| nodeId     | varchar(36) | NO   | PRI | NULL    |       |
	| nodeName   | varchar(20) | NO   |     | NULL    |       |
	| nodeType   | varchar(20) | NO   |     | NULL    |       |
	| state      | varchar(20) | NO   |     | NULL    |       |
	| ipAddr     | varchar(16) | NO   |     | NULL    |       |
	| hm_ip      | varchar(16) | NO   |     | NULL    |       |
	| createTime | varchar(20) | NO   |     | NULL    |       |
	| subnetId   | int(11)     | NO   |     | NULL    |       |
	+------------+-------------+------+-----+---------+-------+
*************************************************************************/

@Entity
public class VNNodeInfo {
	public static final String STATE_RUNNING = "running";
	
	@Id
	private String nodeId;
	
	@NotNull private String nodeName;
	@NotNull private String nodeType;
	@NotNull private String state;
	@NotNull private String ipAddr;
	@NotNull private String hm_ip;
	@NotNull private Date createTime;
	@NotNull private int subnetId;
	
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getHm_ip() {
		return hm_ip;
	}
	public void setHm_ip(String hm_ip) {
		this.hm_ip = hm_ip;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getSubnetId() {
		return subnetId;
	}
	public void setSubnetId(int subnetId) {
		this.subnetId = subnetId;
	}
}
