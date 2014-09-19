package bupt.sc.nova.serviceImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import bupt.sc.nova.model.VRDEPort;
import bupt.sc.nova.service.VRDEPortService;

@Transactional
public class VRDEPortServiceImpl implements VRDEPortService {
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void releasePort(int vrdeport){
		if(vrdeport != 0){
			VRDEPort vrde = entityManager.getReference(VRDEPort.class, vrdeport);
			vrde.setStatus(VRDEPort.STATE_FREE);
			entityManager.merge(vrde);	
		}
	}
	
	public void releasePortbyVMID(String vmid){
		if(vmid != null){
			Query query = entityManager.createQuery("update vrdeport SET status ='free',vmid=NULL WHERE vmid=:vmid")
					.setParameter("vmid", vmid);
			query.executeUpdate();
		}
	}
	
	public int leasePort(){
		Query query = entityManager.createQuery("select MIN(v.port) from vrdeport v where status='free'",VRDEPort.class);
		VRDEPort vrde = (VRDEPort) query.getSingleResult();
		// Unable the port
		vrde.setStatus(VRDEPort.STATE_USED);
		entityManager.merge(vrde);
		return vrde.getPort();
	}
	
	public void setPortVmid(String vmid,int vrdeport){
		if(vrdeport != 0){
			Query query = entityManager.createQuery("update vrdeport SET vmid=:vmid WHERE port=:port")
					.setParameter("vmid", vmid)
					.setParameter("port", vrdeport);
			query.executeUpdate();
		}
	}
	
	public static void main(String...strings ){
		VRDEPortService service = new VRDEPortServiceImpl(); 
		try{
			//int a = ii.getFirstPort("/home/jiaohuan/iaas/","vrdp.pool");
			////ii.releasePort("/home/jiaohuan/","test.pool",2);
			//System.out.println(a);
			//releasePort(5039);
			//leasePort();
			for(int i=0;i<32;i++){
				// Lease port
//				int port = leasePort();
//				System.out.println("Leased Port is : " + Integer.valueOf(port).toString());
//				setPortVmid("TEST-VMID-aaaaaa",port);
				service.releasePort(5000+i);
				
			}
//			for(int i=5000;i<5050;i++){
//				//ii.releasePort("/home/jiaohuan/iaas/","vrdp.pool",i);
//			}
		
			}catch(Exception e){
				e.printStackTrace();
		}
		
	}
	
}
