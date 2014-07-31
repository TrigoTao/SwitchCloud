package bupt.sc.neutron.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import bupt.sc.neutron.model.SubnetInfo;
import bupt.sc.neutron.service.SubnetInfoService;

@Transactional
public class SubnetInfoServiceImpl implements SubnetInfoService {
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public SubnetInfo getSubnet(int subnetId) {
		return entityManager.getReference(SubnetInfo.class, subnetId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SubnetInfo> getAllSubnets() {
		Query query = entityManager.createQuery("select e from SubnetInfo e");
		return query.getResultList();
	}

}
