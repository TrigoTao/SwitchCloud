package bupt.sc.neutron.serviceImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import bupt.sc.neutron.model.UserDemand;
import bupt.sc.neutron.service.UserDemandService;

@Transactional
public class UserDemandServiceImpl implements UserDemandService {
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void saveUserDemand(UserDemand ud) {
		if(ud.getId() !=  null) entityManager.merge(ud); else entityManager.persist(ud);
	}

}
