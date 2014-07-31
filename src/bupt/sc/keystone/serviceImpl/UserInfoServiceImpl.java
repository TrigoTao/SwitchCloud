package bupt.sc.keystone.serviceImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import bupt.sc.keystone.model.UserInfo;
import bupt.sc.keystone.service.UserInfoService;

@Transactional
public class UserInfoServiceImpl implements UserInfoService{

	private EntityManager entityManager;

	public EntityManager getEntityManager() {
		return entityManager;
	}
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> getAllIno() {
		Query query = entityManager.createQuery("select e from UserInfo e");
		return query.getResultList();
	}
	@Override
	public UserInfo getInfoByName(String name, String type) {
		Query query = entityManager.createQuery("select e from UserInfo e where userName = :name and userType = :type")
					.setParameter("name", name).setParameter("type", type);
		
		try {
			return (UserInfo)query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}
	@Override
	public UserInfo getAdminByName(String name) {
		return getInfoByName(name,UserInfo.TYPE_MANAGER);
	}
	@Override
	public UserInfo getUserByName(String name) {
		return getInfoByName(name,UserInfo.TYPE_USER);
	}
	@Override
	public void saveInfo(UserInfo userInfo) {
		if(userInfo.getId() !=  null) entityManager.merge(userInfo); else entityManager.persist(userInfo);
	}
	@Override
	public boolean checkUserExist(String name, String userType) {
		UserInfo user = getInfoByName(name, userType);
		return user != null ? true : false;
	}
	@Override
	public boolean deleteInfoByName(String name, String userType) {
		UserInfo info = getInfoByName(name, userType);
		if(null != info){ 
			entityManager.remove(info);
			return true;
		}else {
			return false;
		}
	}
	

}
