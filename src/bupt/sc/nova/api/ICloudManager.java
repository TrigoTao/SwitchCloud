package bupt.sc.nova.api;

import java.util.List;

import javax.jws.WebService;

@WebService
public interface ICloudManager {
	public void addHM(String hm_ip);
	public String ChooseHM();
	public List<String> listHMIp();
}
