package bupt.sc.heartbeat.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import bupt.sc.heartbeat.model.HostInfo;
import bupt.sc.heartbeat.service.HostInfoService;

@Transactional
public class HostInfoServiceImpl implements HostInfoService {
	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<HostInfo> getInfoByIp(String Ip) {
		return entityManager.createQuery("select e from HostInfo e where Ip = :ip", HostInfo.class)
				.setParameter("ip", Ip).getResultList();
	}
	@Override
	public List<HostInfo> getHostInfos() {
	    return entityManager.createQuery("SELECT e FROM HMInfo e", HostInfo.class).getResultList();
	}
	
	@Override
	public void save(HostInfo host) {
		if( host.getMac() != null )  entityManager.merge(host); else entityManager.persist(host);
	}
	@Override
	public void upsert(HostInfo host) {
		HostInfo old = entityManager.getReference( HostInfo.class, host.getMac() );
		if(old == null) entityManager.persist(host); else entityManager.merge(host);
	}

}
