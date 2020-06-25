package run.onco.api.persist.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.dto.Paging;
import run.onco.api.common.utils.StringUtil;
import run.onco.api.persist.dao.BatchJobDao;
import run.onco.api.persist.entity.TbCBatchJobInstance;
import run.onco.api.persist.entity.TbTBatchJob;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class BatchJobDaoImpl implements BatchJobDao {

	@PersistenceContext
	protected EntityManager em;
	
	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class); 
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbTBatchJob> listPaginatedBatchJobsUsingQuery(Map<String, Object> criteria, Paging paging) {
		StringBuilder sb = new StringBuilder()
				.append("select b from TbTBatchJob b ")
				.append("join b.batchJobInst inst ")
				.append("where inst.status = :activeStatus ");

		if (criteria.get("jobKey") != null) {
			sb.append("and inst.jobKey = :jobKey ");
		}
		
		// Paging
		int startIndex = paging.getStartIndex();
		int fetchSize = paging.getFetchSize();
		String sortBy = paging.getSortBy();
		Boolean sortAsc = paging.getSortAsc();

		// Order by
		if ("id".equals(sortBy)) {
			sb.append(String.format("order by b.id %s", sortAsc ? "asc" : "desc"));
		}

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("activeStatus", AppConstants.STATUS_ACTIVE);
		
		if (criteria.get("jobKey") != null) {
			q.setParameter("jobKey", criteria.get("jobKey"));
		}
		
		q.setFirstResult(startIndex);
		q.setMaxResults(fetchSize);

		List<TbTBatchJob> result = (List<TbTBatchJob>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int countBatchJobsUsingQuery(Map<String, Object> criteria) {
		StringBuilder sb = new StringBuilder()
				.append("select count(b.id) from TbTBatchJob b ")
				.append("join b.batchJobInst inst ")
				.append("where inst.status = :activeStatus ");

		if (criteria.get("jobKey") != null) {
			sb.append("and inst.jobKey = :jobKey ");
		}

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("activeStatus", AppConstants.STATUS_ACTIVE);
		
		if (criteria.get("jobKey") != null) {
			q.setParameter("jobKey", criteria.get("jobKey"));
		}

		List<Long> result = (List<Long>) q.getResultList();

		return result == null || result.size() == 0 ? 0 : result.get(0).intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbCBatchJobInstance> getActiveBatchJobInstances() {
		StringBuilder sb = new StringBuilder()
				.append("select b from TbCBatchJobInstance b ")
				.append("where b.status = :activeStatus ");
		
		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("activeStatus", AppConstants.STATUS_ACTIVE);
		
		List<TbCBatchJobInstance> result = (List<TbCBatchJobInstance>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public TbTBatchJob getBatchJobByJobId(Long jobId) {
		StringBuilder sb = new StringBuilder()
				.append("select b from TbTBatchJob b ")
				.append("where b.id = jobId ");
		
		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("jobId", jobId);
		
		List<TbTBatchJob> result = (List<TbTBatchJob>) q.getResultList();

		return result == null || result.size() == 0 ? null : result.get(0);
	}
}
