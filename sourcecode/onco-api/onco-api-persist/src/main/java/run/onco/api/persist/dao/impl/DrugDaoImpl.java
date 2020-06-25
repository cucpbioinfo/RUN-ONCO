package run.onco.api.persist.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.persist.dao.DrugDao;
import run.onco.api.persist.entity.TbMActionableVariant;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class DrugDaoImpl implements DrugDao {
	
	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TbMActionableVariant> getActionableVariantListByGeneName(String geneName) {
		StringBuilder sb = new StringBuilder()
				.append("select a from TbMActionableVariant a ")
				.append("where a.gene = :geneName ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("geneName", geneName);
		List<TbMActionableVariant> result = (List<TbMActionableVariant>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}

}
