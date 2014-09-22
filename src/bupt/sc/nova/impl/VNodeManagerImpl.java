package bupt.sc.nova.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.jws.WebService;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import bupt.sc.neutron.model.SubnetInfo;
import bupt.sc.neutron.service.SubnetInfoService;
import bupt.sc.nova.api.IVirtualNetManager;
import bupt.sc.nova.api.VNodeManager;
import bupt.sc.nova.model.VNodeInfo;
import bupt.sc.nova.service.VNodeInfoService;
import bupt.sc.nova.statistic.VNodeInfoIaaS;
import bupt.sc.utils.CloudConfig;
import bupt.sc.utils.ConfigInstance;
import bupt.sc.utils.CoreUtil;

import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebService
public class VNodeManagerImpl implements VNodeManager {
	private VNodeInfoService vNodeInfoService;
	private SubnetInfoService subnetInfoService;
	
	private final Logger logger = LogManager.getLogger(VNodeManagerImpl.class.getName()); 
	
	public VNodeInfoService getvNodeInfoService() {
		return vNodeInfoService;
	}

	public void setvNodeInfoService(VNodeInfoService vNodeInfoService) {
		this.vNodeInfoService = vNodeInfoService;
	}

	public SubnetInfoService getSubnetInfoService() {
		return subnetInfoService;
	}

	public void setSubnetInfoService(SubnetInfoService subnetInfoService) {
		this.subnetInfoService = subnetInfoService;
	}

	@Override
	public int addVNode(String nodeType, int subnetId) throws FileNotFoundException {
		SubnetInfo subnetInfo = subnetInfoService.getSubnet(subnetId);
		if(null != subnetInfo){
			//System.out.println(subnetInfo.getId());
			
			VNodeInfo vNodeInfo = new VNodeInfo(); 
			vNodeInfo.setState(VNodeInfo.STATE_CREATING);
			vNodeInfo.setCreateTime(new Date());
			vNodeInfo.setSubnet(subnetInfo);
			vNodeInfoService.saveVNodeInfo(vNodeInfo);
			
			CloudConfig cc = ConfigInstance.getCloudConfig();
			String targetEendPoint = "http://" + cc.getCloudIp() + cc.getVirtualNetServiceSuffix();
	
			logger.debug("[Cloud Debug] targetEendPoint= " + targetEendPoint);
			IVirtualNetManager ivNetManager = CoreUtil.getRemoteT(targetEendPoint, IVirtualNetManager.class);
			Service service = new Service();
			Call call;
			try {
				call = (Call) service.createCall();
				call.setTargetEndpointAddress(new URL(targetEendPoint));
				QName q = new QName("http://vnapi.control.pl.nos.bupt", "VNodeInfoIaaS"); // step
																							// 1
				BeanSerializerFactory bsf = new BeanSerializerFactory(VNodeInfoIaaS.class, q); // step
																								// 2
				BeanDeserializerFactory bdf = new BeanDeserializerFactory(VNodeInfoIaaS.class, q); // step
																									// 3
				call.registerTypeMapping(VNodeInfoIaaS.class, q, bsf, bdf); // step
				call.setOperationName(new QName(targetEendPoint, "addVNodeToIaaS"));
	
				Object[] o = new Object[] { nodeType, subnetId };
				VNodeInfoIaaS vnodeInIaaS = (VNodeInfoIaaS) call.invoke(o);
	
				//System.out.println(vnodeInIaaS.getCreateTime());
				//System.out.println(vnodeInIaaS.getSubnetId());
				
				vNodeInfo.setIpAddr(vnodeInIaaS.getIpAddr());
				vNodeInfo.setCreateTime(new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss").parse(vnodeInIaaS.getCreateTime())); 
				vNodeInfo.setVmid(vnodeInIaaS.getNodeId());
				vNodeInfo.setHmIP(vnodeInIaaS.getHm_ip());
				vNodeInfo.setState(VNodeInfo.STATE_START);
				vNodeInfoService.saveVNodeInfo(vNodeInfo);
			} catch (Exception e) {
				logger.error("/n Exception at VNodeManager.addVNode /n");
				e.printStackTrace();
			}
			
			return vNodeInfo.getId();
		}
		return -1;
	}

	@Override
	public boolean deleteVNode(int vnodeId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public VNodeInfo checkVNode(int vnodeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] checkVNodesInSubnet(int subnet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] checkAllVNodes() {
		// TODO Auto-generated method stub
		return null;
	}

}
