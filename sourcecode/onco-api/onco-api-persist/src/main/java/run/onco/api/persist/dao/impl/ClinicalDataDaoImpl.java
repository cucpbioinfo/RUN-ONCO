package run.onco.api.persist.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.common.dto.Paging;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.persist.dao.ClinicalDataDao;
import run.onco.api.persist.entity.TbTClinicalData;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class ClinicalDataDaoImpl implements ClinicalDataDao {

	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public TbTClinicalData getClinicalDataByRef(String ref, String status) {
		StringBuilder sb = new StringBuilder()
				.append("select c from TbTClinicalData c ")
				.append("where c.refNo = :ref and c.status = :status ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("ref", ref);
		q.setParameter("status", status);
		List<TbTClinicalData> result = (List<TbTClinicalData>) q.getResultList();

		return result == null || result.size() == 0 ? null : result.get(0);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbTClinicalData> listPaginatedClinicalDataUsingQuery(Map<String, Object> criteria, Paging paging) {

		StringBuilder sb = new StringBuilder()
				.append("select c from TbTClinicalData c ")
				.append("where c.status = :status ");

		if (criteria.get("refNo") != null) {
			sb.append("and c.refNo like :refNo ");
		}
		if (criteria.get("hn") != null) {
			sb.append("and c.patient.hn like :hn ");
		}
		
		// Paging
		int startIndex = paging.getStartIndex();
		int fetchSize = paging.getFetchSize();
		String sortBy = paging.getSortBy();
		Boolean sortAsc = paging.getSortAsc();
//		int startIndex =  AppUtil.getMapValue(criteria, "startIndex");
//		int fetchSize = AppUtil.getMapValue(criteria, "fetchSize");
//		String sortBy = AppUtil.getMapValue(criteria, "sortBy");
//		Boolean sortAsc = AppUtil.getMapValue(criteria, "sortAsc");

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

		List<TbTClinicalData> result = (List<TbTClinicalData>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int countClinicalDataUsingQuery(Map<String, Object> criteria, Paging paging) {

		StringBuilder sb = new StringBuilder()
				.append("select count(c.id) from TbTClinicalData c ")
				.append("where status = :status ");

		if (criteria.get("refNo") != null) {
			sb.append("and c.refNo like :refNo ");
		}
		if (criteria.get("hn") != null) {
			sb.append("and c.patient.hn like :hn ");
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
	public TbTClinicalData getClinicalDataById(Long clinicalDataId, String status) {
		
		StringBuilder sb = new StringBuilder()
				.append("select c from TbTClinicalData c ")
				.append("where c.id = :id and c.status = :status ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("id", clinicalDataId);
		q.setParameter("status", status);
		List<TbTClinicalData> result = (List<TbTClinicalData>) q.getResultList();

		return result == null || result.size() == 0 ? null : result.get(0);
	}

//	@Override
//	@SuppressWarnings("unchecked")
//	public List<TbTClinicalData> searchClinicalData(String ref, String status, int pageNumber, int pageSize) {
//		StringBuilder sb = new StringBuilder()
//				.append("select c from TbTClinicalData c ")
//				.append("where 1=1 ");
//		
//		if (!StringUtil.isNullOrEmpty(ref)) {
//			sb.append("and c.refNo = :ref ");
//		}
//		
//		if (status != null) {
//			sb.append("and c.status = :status ");
//		}
//		
//		Query<?> q = getSession().createQuery(sb.toString());
//		
//		if (!StringUtil.isNullOrEmpty(ref)) {
//			q.setParameter("ref", ref);
//		}
//		
//		if (status != null) {
//			q.setParameter("status", status);
//		}
//
//		q.setFirstResult((pageNumber - 1) * pageSize);
//		q.setMaxResults(pageSize);
//		List<TbTClinicalData> result = (List<TbTClinicalData>) q.getResultList();
//		return result;
//	}

}
