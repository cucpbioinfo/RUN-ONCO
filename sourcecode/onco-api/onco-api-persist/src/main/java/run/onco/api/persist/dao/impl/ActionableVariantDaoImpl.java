package run.onco.api.persist.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.persist.dao.ActionableVariantDao;
import run.onco.api.persist.entity.TbMActionableVariant;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class ActionableVariantDaoImpl implements ActionableVariantDao {

	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbMActionableVariant> getActionableVariantList(String transcript) {
		
		StringBuilder sb = new StringBuilder()
				.append("select a from TbMActionableVariant a ")
				.append("where a.isoform = :transcript ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("transcript", transcript);
		List<TbMActionableVariant> result = (List<TbMActionableVariant>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}
	

	@Override
	@SuppressWarnings("unchecked")
	public List<TbMActionableVariant> getActionableVariantList(Integer[] ids) {
		
		StringBuilder sb = new StringBuilder()
				.append("select a from TbMActionableVariant a ")
				.append("where a.id IN :ids ");
		
		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameterList("ids", ids);
		List<TbMActionableVariant> result = (List<TbMActionableVariant>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}
}
