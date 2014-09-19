package bupt.sc.nova.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


/**************************************************************
 * +----------+-------------+------+-----+---------+-------+
 * | Field    | Type        | Null | Key | Default | Extra |
 * +----------+-------------+------+-----+---------+-------+
 * | nodeType | varchar(20) | NO   | PRI | NULL    |       |
 * | vdiName  | varchar(20) | NO   |     | NULL    |       |
 * | vdiUuid  | varchar(36) | NO   |     | NULL    |       |
 * +----------+-------------+------+-----+---------+-------+
****************************************************************/

@Entity
public class VNVdiInfo {
	@Id
	private String nodeType;
	
	@NotNull String vdiName;
	@NotNull String vdiUuid;
	
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getVdiName() {
		return vdiName;
	}
	public void setVdiName(String vdiName) {
		this.vdiName = vdiName;
	}
	public String getVdiUuid() {
		return vdiUuid;
	}
	public void setVdiUuid(String vdiUuid) {
		this.vdiUuid = vdiUuid;
	}
}
