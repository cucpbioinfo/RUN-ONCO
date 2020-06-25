package run.onco.api.persist.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.persist.dao.UserDao;
import run.onco.api.persist.entity.TbMUser;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	protected EntityManager em;
	
	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class); 
	}

	@Override
	public TbMUser login(final String username, final String password) {
		StringBuilder sb = new StringBuilder()
				.append("select ur from TbMUser ur ")
				.append("where ur.username = :username and ur.password = :password ")
				.append("and ur.status = :status ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("username", username);
		q.setParameter("password", password);
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		List<TbMUser> result = (List<TbMUser>) q.getResultList();

		return result == null || result.size() == 0 ? null : result.get(0);
	}

	@Override
	public TbMUser getActiveUserById(Long userId) {
		StringBuilder sb = new StringBuilder()
				.append("select ur from TbMUser ur ")
				.append("where ur.id = :id ")
				.append("and ur.status = :status");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("id", userId);
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		List<TbMUser> result = (List<TbMUser>) q.getResultList();

		return result == null || result.size() == 0 ? null : result.get(0);
	}

	@Override
	public TbMUser getActiveUserByUsername(String username) {
		StringBuilder sb = new StringBuilder()
				.append("select ur from TbMUser ur ")
				.append("where ur.username = :username ")
				.append("and ur.status = :status");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("username", username);
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		List<TbMUser> result = (List<TbMUser>) q.getResultList();

		return result == null || result.size() == 0 ? null : result.get(0);
	}
}
