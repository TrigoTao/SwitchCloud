package bupt.sc.heartbeat.service;

import bupt.sc.heartbeat.model.VMInfo;

public interface VMInfoService {
	void save(VMInfo vm);
	void deleteByHmIp(String HmIp);
}
