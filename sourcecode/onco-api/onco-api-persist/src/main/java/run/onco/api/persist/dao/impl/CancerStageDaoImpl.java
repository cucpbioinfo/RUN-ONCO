package run.onco.api.persist.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.persist.dao.CancerStageDao;
import run.onco.api.persist.entity.TbTCancerStage;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class CancerStageDaoImpl implements CancerStageDao {

	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public TbTCancerStage getCancerStageByDiagnosisId(Long diagnosisId) {
		StringBuilder sb = new StringBuilder()
				.append("select c from TbTCancerStage c ")
				.append("where c.diagnosis.id = :diagnosisId ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("diagnosisId", diagnosisId);
		List<TbTCancerStage> result = (List<TbTCancerStage>) q.getResultList();

		return result == null || result.size() == 0 ? null : result.get(0);
	}
}
