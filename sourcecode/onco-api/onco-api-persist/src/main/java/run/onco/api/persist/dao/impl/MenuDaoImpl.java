package run.onco.api.persist.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.persist.dao.MenuDao;
import run.onco.api.persist.entity.TbCMenu;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class MenuDaoImpl implements MenuDao {

	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TbCMenu> getMenusByUserId(Long userId) {
		StringBuilder sb = new StringBuilder()
				.append("select distinct m from TbCScreen s ")
				.append("join s.menu m ")
				.append("join s.roles r ")
				.append("join r.users u ")
				.append("where m.status = :status ")
				.append("and u.id = :userId");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		q.setParameter("userId", userId);
		List<TbCMenu> result = (List<TbCMenu>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}
	
}
