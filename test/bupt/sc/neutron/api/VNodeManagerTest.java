package bupt.sc.neutron.api;

import java.io.FileNotFoundException;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bupt.sc.neutron.api.VNodeManager;

public class VNodeManagerTest {
	private static final String addr = "http://localhost:8080/SwitchCloud/webservices/neutron/vnm";
	private VNodeManager client;
	
	@Before
	public void setupBeforeClass(){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(VNodeManager.class);
		factory.setAddress(addr);
		client = (VNodeManager) factory.create();
		//client = (VNodeManager)getRemoteT(addr, VNodeManagerTest.class);
	}

	@Test
	public void testAddVNode() throws FileNotFoundException{
		int res = client.addVNode("SCSCF", 0);
		Assert.assertEquals(-1, res);
		res = client.addVNode("SCSCF", 1);
		Assert.assertNotEquals(-1, res);
	}
}
