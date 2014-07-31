package bupt.sc.neutron.api;

import java.util.List;

import bupt.sc.neutron.model.SubnetInfo;

public interface SubnetManager {
	public SubnetInfo checkSubnet(int subnetId);
	public List<SubnetInfo> checkAllSubnets();
}
