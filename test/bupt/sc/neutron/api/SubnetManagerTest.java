package bupt.sc.neutron.api;

import java.io.FileNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bupt.sc.neutron.model.UserDemand;
import bupt.sc.utils.CoreUtil;

public class SubnetManagerTest {
	private static final String addr = "http://localhost:8080/SwitchCloud/webservices/neutron/snm";
	private SubnetManager client;
	
	@Before
	public void setupBeforeClass(){
		client = (SubnetManager) CoreUtil.getRemoteT(addr, SubnetManager.class);
	}

	//@Test
	public void testCreateNet() throws FileNotFoundException{
		UserDemand userDemand = new UserDemand();
		userDemand.setStrategy(UserDemand.PERFORMANCE_FIRST);
		userDemand.setMediaCap(0);
		userDemand.setAudioCap(1000);
		int res = client.createNet("root", userDemand);
		Assert.assertNotEquals(0, res);
	}
	
	@Test
	public void testDeleteNet() throws FileNotFoundException{
		boolean res = client.deleteNet("root", 27);
		Assert.assertTrue(res);
	}
}
