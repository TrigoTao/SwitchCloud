package bupt.sc.nova.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import bupt.sc.nova.model.IpInfo;
import bupt.sc.nova.service.IpInfoService;

@Transactional
public class IpInfoServiceImpl implements IpInfoService {
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void releaseIp(String ip) {
		IpInfo ipInfo= entityManager.getReference(IpInfo.class, ip);
		ipInfo.setStatus(IpInfo.STATE_FREE);
		entityManager.merge(ipInfo);
	}

	@Override
	public void setUseIp(String ip) {
		IpInfo ipInfo= entityManager.getReference(IpInfo.class, ip);
		ipInfo.setStatus(IpInfo.STATE_USED);
		entityManager.merge(ipInfo);
	}

	@Override
	public String leaseIp() {
		String allocatedIp = null;
		List<IpInfo> ipInfos = entityManager.createQuery("SELECT e FROM IpInfo e where status =:state", IpInfo.class)
								.setParameter("state", IpInfo.STATE_FREE).getResultList();
		if(!ipInfos.isEmpty())
			allocatedIp = ipInfos.get(0).getIp();
		return allocatedIp;
	}

}
