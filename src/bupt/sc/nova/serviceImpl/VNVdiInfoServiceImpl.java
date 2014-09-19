package bupt.sc.nova.serviceImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import bupt.sc.nova.model.VNVdiInfo;
import bupt.sc.nova.service.VNVdiInfoService;

@Transactional
public class VNVdiInfoServiceImpl implements VNVdiInfoService {
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public VNVdiInfo getVNVdiInfo(String type) {
		return entityManager.find(VNVdiInfo.class, type);
	}

}
