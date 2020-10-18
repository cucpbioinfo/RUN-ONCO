package run.onco.api.persist.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.persist.dao.RoleDao;
import run.onco.api.persist.entity.TbCRole;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class RoleDaoImpl implements RoleDao {

	@PersistenceContext
	protected EntityManager em;
	
	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class); 
	}
	
	@Override
	public List<TbCRole> getActiveRoles() {
		StringBuilder sb = new StringBuilder()
				.append("select r from TbCRole r ")
				.append("where r.status = :status ")
				.append("order by r.seqNo asc");
				
		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		List<TbCRole> result = (List<TbCRole>) q.getResultList();
		return result == null || result.size() == 0 ? null : result; 
	}
}
