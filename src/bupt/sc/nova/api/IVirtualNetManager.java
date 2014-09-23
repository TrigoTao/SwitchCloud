package bupt.sc.nova.api;

import javax.jws.WebService;

import bupt.sc.nova.model.VNNodeInfo;

@WebService
public interface IVirtualNetManager {
    public VNNodeInfo addVNodeToIaaS(String nodeType, int subnetId) ;
    public boolean deleteVNodeInIaaS(String vmid);
    public String poweroffVNodeInIaaS(String vmid);
    public String startVNodeInIaaS(String vmid);
    public VNNodeInfo checkVNodeInIaaS(String nodeId);
    public Object[] checkAllVNodeInIaaS();
}
