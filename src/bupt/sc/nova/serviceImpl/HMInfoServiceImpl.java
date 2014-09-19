package bupt.sc.nova.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import bupt.sc.nova.model.HMInfo;
import bupt.sc.nova.service.HMInfoService;

@Transactional
public class HMInfoServiceImpl implements HMInfoService {
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override @SuppressWarnings("unchecked")
	public List<HMInfo> getHMInfos() {
		Query query = entityManager.createQuery("SELECT e FROM HMInfo e");
	    return query.getResultList();
	}
	
	@Override
	public void insertIp(String mac, String ip) {
		HMInfo hmInfo = new HMInfo();
		hmInfo.setHmIp(ip);
		hmInfo.setHmMac(mac);
		entityManager.persist(hmInfo);
	}

}
