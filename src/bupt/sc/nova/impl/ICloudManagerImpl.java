package bupt.sc.nova.impl;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import bupt.sc.heartbeat.model.HostInfo;
import bupt.sc.heartbeat.service.HostInfoService;
import bupt.sc.nova.api.ICloudManager;
import bupt.sc.nova.api.IVirtualManager;
import bupt.sc.utils.CloudConfig;
import bupt.sc.utils.ConfigInstance;

public class ICloudManagerImpl implements ICloudManager {
	private HostInfoService hmInfoServie;

	public HostInfoService getHmInfoServie() {
		return hmInfoServie;
	}

	public void setHmInfoServie(HostInfoService hmInfoServie) {
		this.hmInfoServie = hmInfoServie;
	}

	private IVirtualManager getRemoteIVirtualM(String endPoint){
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IVirtualManager.class);
		factory.setAddress(endPoint);
		return (IVirtualManager) factory.create();
	}
	
	@Override
	public void addHM(String hm_ip) {
		try {
			CloudConfig cc = ConfigInstance.getCloudConfig();
			String servicesSuffix = cc.getNodeServiceSuffix();
			if(hm_ip != null)
			{
				String targetEndPoint = "http://" + hm_ip + servicesSuffix;    
				IVirtualManager iVirtualManager = getRemoteIVirtualM(targetEndPoint);
				iVirtualManager.HMReportIP();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String ChooseHM() {
		int runningVMNo = 0;
    	int bestNodeVMNo = Integer.MAX_VALUE;
    	String bestHMIP = null;
		try{
			CloudConfig cc = ConfigInstance.getCloudConfig();
			List<String> hmIps = listHMIp(); 
			for(String ip:hmIps){
		    	String targetEndPoint = "http://" + ip + cc.getNodeServiceSuffix();
		    	IVirtualManager iVirtualManager = getRemoteIVirtualM(targetEndPoint);
		    	List<String> vmIDs = iVirtualManager.listVMIDs();
				if(vmIDs!=null){
					runningVMNo = vmIDs.size();
					if(bestNodeVMNo > runningVMNo){
						bestNodeVMNo = runningVMNo;
						bestHMIP = ip;
					}
				}
				else{
					bestHMIP = ip;
					break;
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bestHMIP;
	}

	@Override
	public List<String> listHMIp() {
		List<String> hmIPs = new ArrayList<String>();
		List<HostInfo> hmInfos = hmInfoServie.getHostInfos();
		for(HostInfo hmInfo : hmInfos){
			hmIPs.add(hmInfo.getIp());
		}

		return hmIPs;
	}

}
