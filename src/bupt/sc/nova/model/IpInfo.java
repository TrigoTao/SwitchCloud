package bupt.sc.nova.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


/****************************************************************
	+--------+-------------+------+-----+---------+-------+
	| Field  | Type        | Null | Key | Default | Extra |
	+--------+-------------+------+-----+---------+-------+
	| ip     | varchar(16) | NO   | PRI | NULL    |       |
	| status | char(4)     | NO   |     | NULL    |       |
	| vmid   | varchar(36) | YES  |     | NULL    |       |
	+--------+-------------+------+-----+---------+-------+
****************************************************************/

@Entity
public class IpInfo {
	public static final String STATE_FREE = "free";
	public static final String STATE_USED = "used";
	
	@Id
	private String ip;
	@NotNull private String status;
	private String vmid;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVmid() {
		return vmid;
	}
	public void setVmid(String vmid) {
		this.vmid = vmid;
	}
	
}
