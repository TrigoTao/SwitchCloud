package bupt.sc.utils;

public class CloudConfig {
	private String cloudIp;

	public String getCloudIp() {
		return cloudIp;
	}

	public void setCloudIp(String cloudIp) {
		this.cloudIp = cloudIp;
	}

	@Override
	public String toString() {
		return "CloudConfig [cloudIp=" + cloudIp + "]";
	}
}
