package bupt.sc.utils;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

public class CoreUtil {
	@SuppressWarnings("unchecked")
	public static <T> T getRemoteT(String endPoint , Class<T> classType){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(classType);
		factory.setAddress(endPoint);
		return (T) factory.create();
	}
	
	/**
	 * This method is used to extract true vm_uuid from "vmid#vrdeport"
	 * 
	 * @param UUID_VM
	 * @return
	 */
	public static String formatVMID(String UUID_VM) {
		if (!UUID_VM.contains("#"))
			return UUID_VM;
		int start = UUID_VM.indexOf("#");
		String vmid = UUID_VM.substring(0, start);
		return vmid;
	}
}
