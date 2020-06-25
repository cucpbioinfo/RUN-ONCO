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
import run.onco.api.persist.dao.BiospecimenDao;
import run.onco.api.persist.entity.TbTBiospecimen;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class BiospecimenDaoImpl implements BiospecimenDao {

	@PersistenceContext
	protected EntityManager em;
	
	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class); 
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbTBiospecimen> getActiveฺBiospecimenListByPatientId(Long patientId) {
		StringBuilder sb = new StringBuilder()
				.append("select b from TbTBiospecimen b ")
				.append("where b.patient.id = :patientId ")
				.append("and b.status = :status");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("patientId", patientId);
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		List<TbTBiospecimen> result = (List<TbTBiospecimen>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbTBiospecimen> listPaginatedBiospecimenUsingQuery(Map<String, Object> criteria) {
		
		StringBuilder sb = new StringBuilder()
				.append("select b from TbTBiospecimen b ")
				.append("where b.status = :status ");

		if (criteria.get("refNo") != null) {
			sb.append("and b.refNo like :refNo ");
		}
		if (criteria.get("hn") != null) {
			sb.append("and b.patient.hn like :hn ");
		}
		
		// Paging
		int startIndex =  AppUtil.getMapValue(criteria, "startIndex");
		int fetchSize = AppUtil.getMapValue(criteria, "fetchSize");
		String sortBy = AppUtil.getMapValue(criteria, "sortBy");
		Boolean sortAsc = AppUtil.getMapValue(criteria, "sortAsc");

		// Order by
		if ("refNo".equals(sortBy)) {
			sb.append(String.format("order by c.refNo %s", sortAsc ? "asc" : "desc"));
		} else if ("hn".equals(sortBy)) {
			sb.append(String.format("order by c.patient.hn %s", sortAsc ? "asc" : "desc"));
		}

		String refNo = AppUtil.getMapValue(criteria, "refNo");
		String hn = AppUtil.getMapValue(criteria, "hn");
		String status = AppUtil.getMapValue(criteria, "status");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("status", status);
		
		if (refNo != null) {
			q.setParameter("refNo", "%"+refNo+"%");
		}
		if (hn != null) {
			q.setParameter("hn", "%"+hn+"%");
		}
		// q.setParameter("feature", "ENST00000275493");
		// q.setFirstResult((pageNumber - 1) * pageSize);
		// q.setMaxResults(pageSize);
		
		q.setFirstResult(startIndex);
		q.setMaxResults(fetchSize);

		List<TbTBiospecimen> result = (List<TbTBiospecimen>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int countBiospecimenUsingQuery(Map<String, Object> criteria) {
		
		StringBuilder sb = new StringBuilder()
				.append("select count(b.id) from TbTBiospecimen b ")
				.append("where b.status = :status ");

		if (criteria.get("refNo") != null) {
			sb.append("and b.refNo like :refNo ");
		}
		if (criteria.get("hn") != null) {
			sb.append("and b.patient.hn like :hn ");
		}

		String refNo = AppUtil.getMapValue(criteria, "refNo");
		String hn = AppUtil.getMapValue(criteria, "hn");
		String status = AppUtil.getMapValue(criteria, "status");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("status", status);
		
		if (refNo != null) {
			q.setParameter("refNo", "%"+refNo+"%");
		}
		if (hn != null) {
			q.setParameter("hn", "%"+hn+"%");
		}

		List<Long> result = (List<Long>) q.getResultList();

		return result == null || result.size() == 0 ? 0 : result.get(0).intValue();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public TbTBiospecimen getBiospecimenById(Long biospecimenId, String status) {
		StringBuilder sb = new StringBuilder()
				.append("select b from TbTBiospecimen b ")
				.append("where b.id = :id ")
				.append("and b.status = :status ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("id", biospecimenId);
		q.setParameter("status", status);
		List<TbTBiospecimen> result = (List<TbTBiospecimen>) q.getResultList();

		return result == null || result.size() == 0 ? null : result.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean findDuplicateBioRef(Long biospecimenId, String refNo) {
		StringBuilder sb = new StringBuilder()
				.append("select count(b.id) from TbTBiospecimen b ")
				.append("where b.status = :status ")
				.append("and b.refNo = :refNo ");

		if (biospecimenId != null) {
			sb.append("and b.id <> :biospecimenId ");
		}

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		q.setParameter("refNo", refNo);

		if (biospecimenId != null) {
			q.setParameter("biospecimenId", biospecimenId);
		}

		List<Long> result = (List<Long>) q.getResultList();
		Long totalRecords = result.get(0);
		return totalRecords == 0 ? false : true;
	}
}
