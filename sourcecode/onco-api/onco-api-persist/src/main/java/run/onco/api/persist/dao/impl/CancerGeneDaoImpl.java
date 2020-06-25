package run.onco.api.persist.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.persist.dao.CancerGeneDao;
import run.onco.api.persist.entity.TbMCancerGeneGroup;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class CancerGeneDaoImpl implements CancerGeneDao {

	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TbMCancerGeneGroup> getActiveCancerGeneGroups() {
		StringBuilder sb = new StringBuilder()
				.append("select g from TbMCancerGeneGroup g ")
				.append("where g.status = :status ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		List<TbMCancerGeneGroup> result = (List<TbMCancerGeneGroup>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}
	
}
