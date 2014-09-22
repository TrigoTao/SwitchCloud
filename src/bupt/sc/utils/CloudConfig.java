package bupt.sc.utils;

public class CloudConfig {
	private String cloudIp;
	private String scpritsHome;
	private String vmType;
	private String nodeServiceSuffix;
	private String cloudServiceSuffix;
	private String virtualNetServiceSuffix;

	public String getCloudIp() {
		return cloudIp;
	}

	public void setCloudIp(String cloudIp) {
		this.cloudIp = cloudIp;
	}

	public String getVmType() {
		return vmType;
	}

	public void setVmType(String vmType) {
		this.vmType = vmType;
	}

	public String getScpritsHome() {
		return scpritsHome;
	}

	public void setScpritsHome(String scpritsHome) {
		this.scpritsHome = scpritsHome;
	}

	public String getNodeServiceSuffix() {
		return nodeServiceSuffix;
	}

	public void setNodeServiceSuffix(String nodeServiceSuffix) {
		this.nodeServiceSuffix = nodeServiceSuffix;
	}

	public String getCloudServiceSuffix() {
		return cloudServiceSuffix;
	}

	public void setCloudServiceSuffix(String cloudServiceSuffix) {
		this.cloudServiceSuffix = cloudServiceSuffix;
	}

	public String getVirtualNetServiceSuffix() {
		return virtualNetServiceSuffix;
	}

	public void setVirtualNetServiceSuffix(String virtualNetServiceSuffix) {
		this.virtualNetServiceSuffix = virtualNetServiceSuffix;
	}

	@Override
	public String toString() {
		return "CloudConfig [cloudIp=" + cloudIp + ", scpritsHome="
				+ scpritsHome + ", vmType=" + vmType + ", nodeServiceSuffix="
				+ nodeServiceSuffix + ", cloudServiceSuffix="
				+ cloudServiceSuffix + ", virtualNetServiceSuffix="
				+ virtualNetServiceSuffix + "]";
	}
}
