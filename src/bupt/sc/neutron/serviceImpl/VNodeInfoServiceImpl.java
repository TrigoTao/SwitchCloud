package bupt.sc.neutron.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import bupt.sc.neutron.model.VNodeInfo;
import bupt.sc.neutron.service.VNodeInfoService;

@Transactional
public class VNodeInfoServiceImpl implements VNodeInfoService {
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void saveVNodeInfo(VNodeInfo vNodeInfo) {
		if(vNodeInfo.getId() !=  null) entityManager.merge(vNodeInfo); else entityManager.persist(vNodeInfo);
	}

	@Override
	public VNodeInfo getVNode(int vNodeId) {
		return entityManager.getReference(VNodeInfo.class, vNodeId);
	}

	@Override
	public List<VNodeInfo> getAllVNodes() {
		TypedQuery<VNodeInfo>  query = entityManager.createQuery("select e from VNodeInfo e", VNodeInfo.class);
		return query.getResultList();
	}
	
	@Override
	public void deleteInfo(VNodeInfo vNodeInfo) {
		entityManager.remove(vNodeInfo);
	}
	
	@Override
	public List<VNodeInfo> getVNodesBySubnetId(int subnetId) {
		TypedQuery<VNodeInfo> query = entityManager.createQuery("select e from VNodeInfo e where e.subnet.id=:id" , VNodeInfo.class)
				.setParameter(":id", subnetId);
		return query.getResultList();
	}

}
