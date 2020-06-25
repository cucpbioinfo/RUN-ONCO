package run.onco.api.persist.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.utils.StringUtil;
import run.onco.api.persist.dao.PathologicalDao;
import run.onco.api.persist.entity.TbTPathological;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class PathologicalDaoImpl implements PathologicalDao {

	@PersistenceContext
	protected EntityManager em;
	
	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class); 
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbTPathological> getPathoListByDiagnosisId(Long diagnosisId, String status) {
		StringBuilder sb = new StringBuilder()
				.append("select p from TbTPathological p ")
				.append("where p.diagnosis.id = :diagnosisId ")
				.append("and p.status = :status ");
		
		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("diagnosisId", diagnosisId);
		q.setParameter("status", status);
		List<TbTPathological> result = (List<TbTPathological>) q.getResultList();
		
		return result == null || result.size() == 0 ? null : result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TbTPathological> searchPatientPathoByPathoNo(Long patientId, String pathoNo, String status, int pageNumber, int pageSize) {
		
		StringBuilder sb = new StringBuilder()
				.append("select pt from TbTPathological pt ")
				.append("inner join pt.diagnosis d ")
				.append("inner join d.clinicalData c ")
				.append("inner join c.patient p ")
				.append("where p.id = :patientId ");

		if (status != null) {
			sb.append("and pt.status = :status ");
		}
		if (!StringUtil.isNullOrEmpty(pathoNo)) {
			sb.append("and pt.pathologyNo like :pathoNo");
		}

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("patientId", patientId);
		
		if (!StringUtil.isNullOrEmpty(pathoNo)) {
			q.setParameter("pathoNo", "%" + pathoNo + "%");
		}
		
		if (status != null) {
			q.setParameter("status", status);
		}

		q.setFirstResult((pageNumber - 1) * pageSize);
		q.setMaxResults(pageSize);
		List<TbTPathological> result = (List<TbTPathological>) q.getResultList();
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean findDuplicatePathoNo(Long pathoId, String pathoNo) {
		
		StringBuilder sb = new StringBuilder()
				.append("select count(p.id) from TbTPathological p ")
				.append("where p.status = :status ")
				.append("and p.pathologyNo = :pathoNo ");

		if (pathoId != null) {
			sb.append("and p.id <> :pathoId ");
		}

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		q.setParameter("pathoNo", pathoNo);

		if (pathoId != null) {
			q.setParameter("pathoId", pathoId);
		}

		List<Long> result = (List<Long>) q.getResultList();
		Long totalRecords = result.get(0);
		return totalRecords == 0 ? false : true;
	}
}
