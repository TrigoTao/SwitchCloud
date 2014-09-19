package bupt.sc.nova.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/*************************************************************
	+--------+-------------+------+-----+---------+-------+
	| Field  | Type        | Null | Key | Default | Extra |
	+--------+-------------+------+-----+---------+-------+
	| port   | int(4)      | NO   | PRI | NULL    |       |
	| status | char(4)     | NO   |     | NULL    |       |
	| vmid   | varchar(36) | YES  |     | NULL    |       |
	+--------+-------------+------+-----+---------+-------+
**************************************************************/

@Entity
public class VRDEPort {
	public static final String STATE_FREE = "free";
	public static final String STATE_USED = "used";
	
	@Id
	private Integer port;
	
	@NotNull
	private String status;
	private String vmid;
	
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
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
