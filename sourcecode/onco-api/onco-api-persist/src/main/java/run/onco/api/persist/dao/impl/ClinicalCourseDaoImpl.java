package run.onco.api.persist.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.persist.dao.ClinicalCourseDao;
import run.onco.api.persist.entity.TbTClinicalCourse;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class ClinicalCourseDaoImpl implements ClinicalCourseDao {
	
	@PersistenceContext
	protected EntityManager em;
	
	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class); 
	}
	 
	@Override
	@SuppressWarnings("unchecked")
	public List<TbTClinicalCourse> getClinicalCourseListByDiagnosisId(Long diagnosisId, String status) {
		StringBuilder sb = new StringBuilder()
				.append("select c from TbTClinicalCourse c ")
				.append("where c.diagnosis.id = :diagnosisId ")
				.append("and c.status = :status ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("diagnosisId", diagnosisId);
		q.setParameter("status", status);
		List<TbTClinicalCourse> result = (List<TbTClinicalCourse>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}
}
