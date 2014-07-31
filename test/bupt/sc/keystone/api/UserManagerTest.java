package bupt.sc.keystone.api;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bupt.sc.keystone.model.UserInfo;

public class UserManagerTest {
	private static final String addr = "http://localhost:8080/SwitchCloud/webservices/keystone/user";
	static UserManager client;
	
	@Before
	public void setupBeforeClass(){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(UserManager.class);
		factory.setAddress(addr);
		client = (UserManager) factory.create();
	}
	
	@Test
	public void testMain(){
		int size = client.checkAllInfo().size();
		
		int res = client.register("test", "test", UserInfo.TYPE_USER);
		Assert.assertNotEquals(0, res);
		Assert.assertEquals(size+1, client.checkAllInfo().size());
		
		res = client.register("test", "test", UserInfo.TYPE_USER);
		Assert.assertEquals(0, res);
		Assert.assertEquals(size+1, client.checkAllInfo().size());
		
		Assert.assertTrue(client.deleteUser("test"));
		Assert.assertFalse(client.deleteUser("test"));	
		Assert.assertEquals(size, client.checkAllInfo().size());
	}
	
}
