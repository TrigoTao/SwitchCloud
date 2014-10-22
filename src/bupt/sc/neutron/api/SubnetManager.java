package bupt.sc.neutron.api;

import java.util.List;

import javax.jws.WebService;

import bupt.sc.neutron.model.SubnetInfo;
import bupt.sc.neutron.model.UserDemand;

@WebService
public interface SubnetManager {
	public int createNet(String userName, UserDemand userDemand);
	public boolean deleteNet(String userName, int subnetId);
	public boolean modifySubnet(String userName, int subnetId, UserDemand userDemand);
	
	public SubnetInfo checkSubnet(int subnetId);
//	public SubnetInfo checkSubnet(String userName, int subnetId); 
	public List<SubnetInfo> checkAllSubnets();
}
