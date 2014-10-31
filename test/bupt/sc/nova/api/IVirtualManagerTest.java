package bupt.sc.nova.api;

import org.junit.Before;
import org.junit.Test;

import bupt.sc.utils.CoreUtil;

public class IVirtualManagerTest {
	private static final String addr = "http://localhost:8080/SwitchCloud/webservices/nova/ivm";
	private IVirtualManager client;
	
	@Before
	public void setupBeforeClass(){
		client = (IVirtualManager) CoreUtil.getRemoteT(addr, IVirtualManager.class);
		//client = (VNodeManager)getRemoteT(addr, VNodeManagerTest.class);
	}
	
	@Test
	public void deleteVMTest(){
		client.deleteVM("9248be8e-8bf8-4384-9934-4d906bd581e3");
	}
}
