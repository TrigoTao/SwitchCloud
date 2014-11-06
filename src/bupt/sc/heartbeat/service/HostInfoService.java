package bupt.sc.heartbeat.service;

import java.util.List;

import bupt.sc.heartbeat.model.HostInfo;

public interface HostInfoService {
	void save(HostInfo host);
	void upsert(HostInfo host);
	List<HostInfo> getInfoByIp(String Ip);
	List<HostInfo> getHostInfos();
}
