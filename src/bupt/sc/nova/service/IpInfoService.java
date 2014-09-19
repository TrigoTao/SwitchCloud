package bupt.sc.nova.service;

public interface IpInfoService {
	public void releaseIp(String ip);
	public void setUseIp(String ip);
	public String leaseIp();
}
