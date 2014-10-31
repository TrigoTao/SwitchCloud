package bupt.sc.nova.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import bupt.sc.nova.model.VNNodeInfo;
import bupt.sc.nova.service.VNNodeInfoService;

@Transactional
public class VNNodeInfoServiceImpl implements VNNodeInfoService {
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@Override
	public void add(VNNodeInfo vnNodeInfo) {
		entityManager.persist(vnNodeInfo);
	}
	
	@Override
	public void remove(VNNodeInfo vnNodeInfo) {
		entityManager.remove( entityManager.getReference(VNNodeInfo.class, vnNodeInfo.getNodeId()) );
	}
	
	@Override
	public VNNodeInfo getVNNodeInfo(String id) {
		return entityManager.find(VNNodeInfo.class, id);
	}

	@Override @SuppressWarnings("unchecked")
	public List<VNNodeInfo> getAllVNNodeInfo() {
		Query query = entityManager.createQuery("SELECT e FROM VNNodeInfo e");
	    return query.getResultList();
	}
	
	@Override @SuppressWarnings("unchecked")
	public List<VNNodeInfo> getVNNodeInfosByType(String type) {
		Query query = entityManager.createQuery("SELECT e FROM VNNodeInfo e WHERE nodeType=:type")
						.setParameter("type", type);
	    return query.getResultList();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
}
