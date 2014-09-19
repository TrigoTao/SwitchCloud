package bupt.sc.nova.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Null;


/********************************************************************
	+--------+-------------+------+-----+---------+-------+
	| Field  | Type        | Null | Key | Default | Extra |
	+--------+-------------+------+-----+---------+-------+
	| HM_MAC | varchar(24) | NO   | PRI | NULL    |       |
	| HM_IP  | varchar(16) | NO   |     | NULL    |       |
	+--------+-------------+------+-----+---------+-------+
*********************************************************************/

@Entity
public class HMInfo {
	@Id
	private String hmMac;
	
	@Null private String hmIp;

	public String getHmMac() {
		return hmMac;
	}

	public void setHmMac(String hmMac) {
		this.hmMac = hmMac;
	}

	public String getHmIp() {
		return hmIp;
	}

	public void setHmIp(String hmIp) {
		this.hmIp = hmIp;
	}
}
