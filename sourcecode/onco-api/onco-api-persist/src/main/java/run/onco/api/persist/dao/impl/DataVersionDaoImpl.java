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
import run.onco.api.persist.dao.DataVersionDao;
import run.onco.api.persist.entity.TbCDataVersion;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class DataVersionDaoImpl implements DataVersionDao {
	
	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TbCDataVersion> listPaginatedDataVersionUsingQuery(Map<String, Object> criteria, Paging paging) {
		StringBuilder sb = new StringBuilder()
				.append("select c from TbCDataVersion c ")
				.append("where 1=1 ");

		if (criteria.get("type") != null) {
			sb.append("and c.type = :type ");
		}
		if (!StringUtil.isNullOrWhitespace((String)criteria.get("version"))) {
			sb.append("and c.version like :version ");
		}
		
		// Paging
		int startIndex = paging.getStartIndex();
		int fetchSize = paging.getFetchSize();
		String sortBy = paging.getSortBy();
		Boolean sortAsc = paging.getSortAsc();

		// Order by
		if ("version".equals(sortBy)) {
			sb.append(String.format("order by c.version %s", sortAsc ? "asc" : "desc"));
		}

		Query<?> q = getSession().createQuery(sb.toString());
		
		if (criteria.get("type") != null) {
			q.setParameter("type", criteria.get("type"));
		}
		if (!StringUtil.isNullOrWhitespace((String)criteria.get("version"))) {
			q.setParameter("version", criteria.get("version"));
		}
		
		q.setFirstResult(startIndex);
		q.setMaxResults(fetchSize);

		List<TbCDataVersion> result = (List<TbCDataVersion>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int countDataVersionUsingQuery(Map<String, Object> criteria, Paging paging) {
		StringBuilder sb = new StringBuilder()
				.append("select count(c.id) from TbCDataVersion c ")
				.append("where 1=1 ");

		if (criteria.get("type") != null) {
			sb.append("and c.type = :type ");
		}
		if (!StringUtil.isNullOrWhitespace((String)criteria.get("version"))) {
			sb.append("and c.version like :version ");
		}

		Query<?> q = getSession().createQuery(sb.toString());
		
		if (criteria.get("type") != null) {
			q.setParameter("type", criteria.get("type"));
		}
		if (!StringUtil.isNullOrWhitespace((String)criteria.get("version"))) {
			q.setParameter("version", criteria.get("version"));
		}

		List<Long> result = (List<Long>) q.getResultList();

		return result == null || result.size() == 0 ? 0 : result.get(0).intValue();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean findDuplicateDataVersion(String type, String version, Integer id) {
		StringBuilder sb = new StringBuilder()
				.append("select count(d.id) from TbCDataVersion d ")
				.append("where d.type = :type ")
				.append("and d.version = :version ");
		
		if (id != null) {
			sb.append("and d.id <> :id");
		}

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("type", type);
		q.setParameter("version", version);
		
		if (id != null) {
			sb.append("and d.id <> :id");
		}

		List<Long> result = (List<Long>) q.getResultList();
		Long totalRecords = result.get(0);
		return totalRecords == 0 ? false : true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbCDataVersion> getMarkAsDefaultDataVersionsByType(String type, Integer id) {
		StringBuilder sb = new StringBuilder()
				.append("select d from TbCDataVersion d ")
				.append("where d.type = :type ")
				.append("and d.isDefault = :isDefault ");
		
		if (id != null) {
			sb.append("and d.id <> :id");
		}

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("type", type);
		q.setParameter("isDefault", AppConstants.YES);
		
		if (id != null) {
			q.setParameter("id", id);
		}
		
		List<TbCDataVersion> result = (List<TbCDataVersion>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}

}
