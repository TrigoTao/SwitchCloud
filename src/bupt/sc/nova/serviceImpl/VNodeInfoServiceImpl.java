package bupt.sc.nova.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import bupt.sc.nova.model.VNodeInfo;
import bupt.sc.nova.service.VNodeInfoService;

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

	@SuppressWarnings("unchecked")
	@Override
	public List<VNodeInfo> getAllVNodes() {
		Query query = entityManager.createQuery("select e from VNodeInfo e");
		return query.getResultList();
	}

}
