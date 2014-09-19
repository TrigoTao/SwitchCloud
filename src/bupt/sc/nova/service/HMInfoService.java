package bupt.sc.nova.service;

import java.util.List;

import bupt.sc.nova.model.HMInfo;


public interface HMInfoService {
	public List<HMInfo> getHMInfos();
	public void insertIp(String mac, String ip);
}
