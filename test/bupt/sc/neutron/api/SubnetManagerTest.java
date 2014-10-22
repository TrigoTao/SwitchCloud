package bupt.sc.neutron.api;

import java.io.FileNotFoundException;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bupt.sc.neutron.model.UserDemand;

public class SubnetManagerTest {
	private static final String addr = "http://localhost:8080/SwitchCloud/webservices/neutron/snm";
	private SubnetManager client;
	
	@Before
	public void setupBeforeClass(){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(SubnetManager.class);
		factory.setAddress(addr);
		client = (SubnetManager) factory.create();
		//client = (VNodeManager)getRemoteT(addr, VNodeManagerTest.class);
	}

	@Test
	public void testCreateNet() throws FileNotFoundException{
		UserDemand userDemand = new UserDemand();
		userDemand.setStrategy(UserDemand.PERFORMANCE_FIRST);
		userDemand.setMediaCap(0);
		userDemand.setAudioCap(1000);
		int res = client.createNet("root", userDemand);
		Assert.assertNotEquals(0, res);
	}
}
