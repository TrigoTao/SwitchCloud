package bupt.sc.keystone.api;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import bupt.sc.keystone.api.Authentication;
import bupt.sc.keystone.model.UserInfo;

public class AuthenticationTest{
	private static final String addr = "http://localhost:8080/SwitchCloud/webservices/keystone/auth";
	private Authentication client;
	
	@Before
	public void setupBeforeClass(){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(Authentication.class);
		factory.setAddress(addr);
		client = (Authentication) factory.create();
	}

	@Test
	public void testAuthentication(){
		boolean res = client.authentication("root", "root", UserInfo.TYPE_MANAGER);
		Assert.assertTrue(res);
		res = client.authentication("root", "root", UserInfo.TYPE_USER);
		Assert.assertFalse(res);
		//Assert.assertEquals(0, res);
	}
	@Test
	public void testExistUser(){
		boolean res = client.isDuplicate("root", UserInfo.TYPE_MANAGER);
		Assert.assertTrue(res);
		res = client.isDuplicate("root", UserInfo.TYPE_USER);
		Assert.assertFalse(res);
	}
	@Test
	public void testLoginLogout(){
		boolean res = client.login("root", "root", UserInfo.TYPE_MANAGER);
		Assert.assertTrue(res);
		res = client.login("root", "root", UserInfo.TYPE_USER);
		Assert.assertFalse(res);
		res = client.logout("root", UserInfo.TYPE_MANAGER);
		Assert.assertTrue(res);
		res = client.logout("root", UserInfo.TYPE_USER);
		Assert.assertFalse(res);
	}
}
