package bupt.sc.heartbeat.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**********************************************************
+--------+-------------+------+-----+---------+-------+
| Field  | Type        | Null | Key | Default | Extra |
+--------+-------------+------+-----+---------+-------+
| HM_MAC | varchar(24) | NO   | PRI | NULL    |       |
| HM_IP  | varchar(16) | NO   |     | NULL    |       |
+--------+-------------+------+-----+---------+-------+
************************************************************/

@Entity
public class HostInfo {
	@Id
	private String Mac;
	
	@NotNull private String Ip;

	public String getMac() {
		return Mac;
	}

	public void setMac(String mac) {
		Mac = mac;
	}

	public String getIp() {
		return Ip;
	}

	public void setIp(String ip) {
		Ip = ip;
	}
}
