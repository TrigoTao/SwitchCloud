package bupt.sc.heartbeat.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**********************************************************
+--------+-------------+------+-----+---------+-------+
| Field  | Type        | Null | Key | Default | Extra |
+--------+-------------+------+-----+---------+-------+
| VM_ID  | varchar(60) | NO   |     | NULL    |       |
| VM_MAC | varchar(24) | NO   | PRI | NULL    |       |
| VM_IP  | varchar(16) | NO   |     | NULL    |       |
| HM_IP  | varchar(16) | NO   |     | NULL    |       |
+--------+-------------+------+-----+---------+-------+
***********************************************************/

@Entity
public class VMInfo {
	@Id String vmMac;
	@NotNull String vmId, vmIp, hmIp;
	
	public String getVmMac() {
		return vmMac;
	}
	public void setVmMac(String vmMac) {
		this.vmMac = vmMac;
	}
	public String getVmId() {
		return vmId;
	}
	public void setVmId(String vmId) {
		this.vmId = vmId;
	}
	public String getVmIp() {
		return vmIp;
	}
	public void setVmIp(String vmIp) {
		this.vmIp = vmIp;
	}
	public String getHmIp() {
		return hmIp;
	}
	public void setHmIp(String hmIp) {
		this.hmIp = hmIp;
	}
}
