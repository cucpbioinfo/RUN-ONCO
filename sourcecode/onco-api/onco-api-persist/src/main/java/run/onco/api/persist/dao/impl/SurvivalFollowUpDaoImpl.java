package run.onco.api.persist.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.persist.dao.SurvivalFollowupDao;
import run.onco.api.persist.entity.TbTSurvivalFollowup;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class SurvivalFollowUpDaoImpl implements SurvivalFollowupDao {

	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public TbTSurvivalFollowup getSurvivalFollowupById(Long survivalFollowupId) {
		StringBuilder sb = new StringBuilder()
				.append("select s from TbTSurvivalFollowup s ")
				.append("where s.id = :id ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("id", survivalFollowupId);
		List<TbTSurvivalFollowup> result = (List<TbTSurvivalFollowup>) q.getResultList();

		return result == null || result.size() == 0 ? null : result.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbTSurvivalFollowup> getSurvivalFollowupListByPatientId(Long patientId, String status) {
		
		StringBuilder sb = new StringBuilder()
				.append("select s from TbTSurvivalFollowup s ")
				.append("where s.patient.id = :patientId ");
		
		if (status != null) {
			sb.append("and s.status = :status ");
		}

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("patientId", patientId);
		
		if (status != null) {
			q.setParameter("status", status);
		}
		
		List<TbTSurvivalFollowup> result = (List<TbTSurvivalFollowup>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}

}
