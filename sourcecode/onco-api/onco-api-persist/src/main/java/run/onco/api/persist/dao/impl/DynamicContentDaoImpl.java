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
import run.onco.api.persist.dao.DynamicContentDao;
import run.onco.api.persist.entity.TbCDynamicContent;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class DynamicContentDaoImpl implements DynamicContentDao {
	
	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TbCDynamicContent> getActiveDynamicContentList() {
		
		StringBuilder sb = new StringBuilder()
				.append("select c from TbCDynamicContent c ")
				.append("where c.status = :status ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		List<TbCDynamicContent> result = (List<TbCDynamicContent>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbCDynamicContent> listPaginatedDynamicContentUsingQuery(Map<String, Object> criteria, Paging paging) {
		
		StringBuilder sb = new StringBuilder()
				.append("select c from TbCDynamicContent c ")
				.append("where 1=1 ");
		
		String analysisName =  (String) criteria.get("analysisName");
		String componentName = (String) criteria.get("componentName");

		if (!StringUtil.isNullOrWhitespace(analysisName)) {
			sb.append("and c.analysisName like :analysisName ");
		}
		if (!StringUtil.isNullOrWhitespace(componentName)) {
			sb.append("and c.componentName like :componentName ");
		}
		
		// Paging
		int startIndex = paging.getStartIndex();
		int fetchSize = paging.getFetchSize();
		String sortBy = paging.getSortBy();
		Boolean sortAsc = paging.getSortAsc();

		// Order by
		if ("analysisName".equals(sortBy)) {
			sb.append(String.format("order by c.analysisName %s", sortAsc ? "asc" : "desc"));
		}

		Query<?> q = getSession().createQuery(sb.toString());
		
		if (!StringUtil.isNullOrWhitespace(componentName)) {
			q.setParameter("componentName", "%"+componentName+"%");
		}
		if (!StringUtil.isNullOrWhitespace(analysisName)) {
			q.setParameter("analysisName", "%"+analysisName+"%");
		}
		
		q.setFirstResult(startIndex);
		q.setMaxResults(fetchSize);

		List<TbCDynamicContent> result = (List<TbCDynamicContent>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int countDynamicContentUsingQuery(Map<String, Object> criteria, Paging paging) {
		
		StringBuilder sb = new StringBuilder()
				.append("select count(c.id) from TbCDynamicContent c ")
				.append("where 1=1 ");
		
		String analysisName =  (String) criteria.get("analysisName");
		String componentName = (String) criteria.get("componentName");

		if (!StringUtil.isNullOrWhitespace(analysisName)) {
			sb.append("and c.analysisName like :analysisName ");
		}
		if (!StringUtil.isNullOrWhitespace(componentName)) {
			sb.append("and c.componentName like :componentName ");
		}
		
		Query<?> q = getSession().createQuery(sb.toString());

		if (!StringUtil.isNullOrWhitespace(componentName)) {
			q.setParameter("componentName", "%"+componentName+"%");
		}
		if (!StringUtil.isNullOrWhitespace(analysisName)) {
			q.setParameter("analysisName", "%"+analysisName+"%");
		}

		List<Long> result = (List<Long>) q.getResultList();

		return result == null || result.size() == 0 ? 0 : result.get(0).intValue();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean findDuplicateComponentName(String componentName, Integer id) {
		
		StringBuilder sb = new StringBuilder()
				.append("select count(c.id) from TbCDynamicContent c ")
				.append("where c.componentName = :componentName ");
		
		if (id != null) {
			sb.append("and c.id <> :id");
		}

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("componentName", componentName);
		
		if (id != null) {
			q.setParameter("id", id);
		}

		List<Long> result = (List<Long>) q.getResultList();
		Long totalRecords = result.get(0);
		return totalRecords == 0 ? false : true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbCDynamicContent> getDynamicContentsByUserId(Long userId) {
		
		StringBuilder sb = new StringBuilder()
				.append("select distinct c from TbCDynamicContent c ")
				.append("join c.roles r ")
				.append("join r.users u ")
				.append("where c.status = :status ")
				.append("and u.id = :userId");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		q.setParameter("userId", userId);
		List<TbCDynamicContent> result = (List<TbCDynamicContent>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}

}
