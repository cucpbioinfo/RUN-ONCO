package run.onco.api.persist.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.StringUtil;
import run.onco.api.persist.dao.PatientDao;
import run.onco.api.persist.entity.TbMPatient;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class PatientDaoImpl implements PatientDao {

	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public TbMPatient getPatientByRef(String refNo, String status) {
		StringBuilder sb = new StringBuilder()
				.append("select p from TbMPatient p ")
				.append("where p.hn = :refNo ");

		if (status != null) {
			sb.append("and p.status = :status ");
		}

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("refNo", refNo);

		if (status != null) {
			q.setParameter("status", status);
		}

		List<TbMPatient> result = (List<TbMPatient>) q.getResultList();
		return result == null || result.size() == 0 ? null : result.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbMPatient> searchPatientByRef(String refNo, String status, int pageNumber, int pageSize) {

		StringBuilder sb = new StringBuilder()
				.append("select p from TbMPatient p ")
				.append("where 1=1 ");

		if (status != null) {
			sb.append("and p.status = :status ");
		}
		if (!StringUtil.isNullOrEmpty(refNo)) {
			sb.append("and p.hn like :refNo ");
		}

		Query<?> q = getSession().createQuery(sb.toString());
		
		if (status != null) {
			q.setParameter("status", status);
		}
		if (!StringUtil.isNullOrEmpty(refNo)) {
			q.setParameter("refNo", "%" + refNo + "%");
		}

		q.setFirstResult((pageNumber - 1) * pageSize);
		q.setMaxResults(pageSize);
		List<TbMPatient> result = (List<TbMPatient>) q.getResultList();
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int countPatientUsingQuery(Map<String, Object> criteria) {
		
		StringBuilder sb = new StringBuilder()
				.append("select count(p.id) from TbMPatient p ")
				.append("where p.status = :status ");

		if (criteria.get("hn") != null) {
			sb.append("and p.hn like :hn ");
		}

		String hn = AppUtil.getMapValue(criteria, "hn");
		String status = AppUtil.getMapValue(criteria, "status");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("status", status);

		if (hn != null) {
			q.setParameter("hn", "%"+hn+"%");
		}

		List<Long> result = (List<Long>) q.getResultList();

		return result == null || result.size() == 0 ? 0 : result.get(0).intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbMPatient> listPaginatedPatientUsingQuery(Map<String, Object> criteria) {
		
		StringBuilder sb = new StringBuilder()
				.append("select p from TbMPatient p ")
				.append("where p.status = :status ");

		if (criteria.get("hn") != null) {
			sb.append("and p.hn like :hn ");
		}
		
		// Paging
		int startIndex =  AppUtil.getMapValue(criteria, "startIndex");
		int fetchSize = AppUtil.getMapValue(criteria, "fetchSize");
		String sortBy = AppUtil.getMapValue(criteria, "sortBy");
		Boolean sortAsc = AppUtil.getMapValue(criteria, "sortAsc");

		// Order by
		if ("hn".equals(sortBy)) {
			sb.append(String.format("order by p.hn %s", sortAsc ? "asc" : "desc"));
		}

		String hn = AppUtil.getMapValue(criteria, "hn");
		String status = AppUtil.getMapValue(criteria, "status");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("status", status);
		
		if (hn != null) {
			q.setParameter("hn", "%"+hn+"%");
		}
		// q.setParameter("feature", "ENST00000275493");
		// q.setFirstResult((pageNumber - 1) * pageSize);
		// q.setMaxResults(pageSize);
		
		q.setFirstResult(startIndex);
		q.setMaxResults(fetchSize);

		List<TbMPatient> result = (List<TbMPatient>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public TbMPatient getPatientById(Long patientId, String status) {
		StringBuilder sb = new StringBuilder()
				.append("select p from TbMPatient p ")
				.append("where p.id = :id ");

		if (status != null) {
			sb.append("and p.status = :status ");
		}

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("id", patientId);

		if (status != null) {
			q.setParameter("status", status);
		}

		List<TbMPatient> result = (List<TbMPatient>) q.getResultList();
		return result == null || result.size() == 0 ? null : result.get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean findDuplicateHn(Long patientId, String hn) {
		StringBuilder sb = new StringBuilder()
				.append("select count(p.id) from TbMPatient p ")
				.append("where p.status = :status ")
				.append("and p.hn = :hn ");

		if (patientId != null) {
			sb.append("and p.id <> :patientId ");
		}

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		q.setParameter("hn", hn);

		if (patientId != null) {
			q.setParameter("patientId", patientId);
		}

		List<Long> result = (List<Long>) q.getResultList();
		Long totalRecords = result.get(0);
		return totalRecords == 0 ? false : true;
	}
}
