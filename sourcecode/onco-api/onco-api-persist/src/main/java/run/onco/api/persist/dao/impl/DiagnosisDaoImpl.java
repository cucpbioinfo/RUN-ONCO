package run.onco.api.persist.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.persist.dao.DiagnosisDao;
import run.onco.api.persist.entity.TbTDiagnosis;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class DiagnosisDaoImpl implements DiagnosisDao {

	@PersistenceContext
	protected EntityManager em;
	
	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class); 
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbTDiagnosis> getDiagonoseListByClinicalDataId(final Long clinicalDataId) {
		StringBuilder sb = new StringBuilder()
				.append("select d from TbTDiagnosis d ")
				.append("where d.clinicalData.id = :clinicalDataId ");
		
		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("clinicalDataId", clinicalDataId);
		List<TbTDiagnosis> result = (List<TbTDiagnosis>) q.getResultList();
		
		return result == null || result.size() == 0 ? null : result;
	}
}
