package bupt.sc.neutron.service;

import java.util.List;

import bupt.sc.neutron.model.SubnetInfo;

public interface SubnetInfoService {
	public void saveSubnetInfo(SubnetInfo subnetInfo);
	public SubnetInfo getSubnet(int subnetId);
	public SubnetInfo getSubnetProxy(int subnetId);
	public List<SubnetInfo> getAllSubnets();
}
