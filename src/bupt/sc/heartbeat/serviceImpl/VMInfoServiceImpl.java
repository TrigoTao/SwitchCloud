package bupt.sc.heartbeat.serviceImpl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import bupt.sc.heartbeat.model.VMInfo;
import bupt.sc.heartbeat.service.VMInfoService;

@Transactional
public class VMInfoServiceImpl implements VMInfoService {
	private EntityManager em;

	public EntityManager getEm() {
		return em;
	}
	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public void save(VMInfo vm) {
		if(vm.getVmMac()!=null) em.merge(vm); else em.persist(vm);
	}

	@Override
	public void deleteByHmIp(String HmIp) {
		em.createQuery("delete from VMInfo where hmIp = :hmIp").setParameter("hmIp", HmIp).executeUpdate();
	}

}
