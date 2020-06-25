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
import run.onco.api.common.utils.StringUtil;
import run.onco.api.persist.dao.IcdDao;
import run.onco.api.persist.entity.TbMIcd;
import run.onco.api.persist.entity.TbMIcdO;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class IcdDaoImpl implements IcdDao {

	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbMIcd> searchIcd(Map<String, Object> criteria, Paging paging) {
		
		String name = AppUtil.getMapValue(criteria, "name");
		String version = AppUtil.getMapValue(criteria, "version");
		String isDefault = AppUtil.getMapValue(criteria, "isDefault");
	
		StringBuilder sb = new StringBuilder()
				.append("select i from TbMIcd i ")
				.append("where 1=1 ");
		
		if (!StringUtil.isNullOrWhitespace(name)) {
			sb.append("and i.name like :name ");
		}
		if (!StringUtil.isNullOrWhitespace(version)) {
			sb.append("and i.dataVersion.version = :version ");
		}
		if (!StringUtil.isNullOrWhitespace(isDefault)) {
			sb.append("and i.dataVersion.isDefault = :isDefault ");
		}

		// Criteria
		Query<?> q = getSession().createQuery(sb.toString());
		
		if (!StringUtil.isNullOrWhitespace(name)) {
			q.setParameter("name", "%"+name+"%");
		}
		if (!StringUtil.isNullOrWhitespace(version)) {
			q.setParameter("version", version);
		}
		if (!StringUtil.isNullOrWhitespace(isDefault)) {
			q.setParameter("isDefault", isDefault);
		}
		
		q.setFirstResult(paging.getStartIndex());
		q.setMaxResults(paging.getFetchSize());

		List<TbMIcd> result = (List<TbMIcd>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbMIcdO> searchIcdO(Map<String, Object> criteria, Paging paging) {
		
		String name = AppUtil.getMapValue(criteria, "name");
		String version = AppUtil.getMapValue(criteria, "version");
		String isDefault = AppUtil.getMapValue(criteria, "isDefault");
		
		StringBuilder sb = new StringBuilder()
				.append("select i from TbMIcdO i ")
				.append("where 1=1 ");
		
		if (!StringUtil.isNullOrWhitespace(name)) {
			sb.append("and i.name like :name ");
		}
		if (!StringUtil.isNullOrWhitespace(version)) {
			sb.append("and i.dataVersion.version = :version ");
		}
		if (!StringUtil.isNullOrWhitespace(isDefault)) {
			sb.append("and i.dataVersion.isDefault = :isDefault ");
		}
		
		// Criteria
		Query<?> q = getSession().createQuery(sb.toString());
		
		if (!StringUtil.isNullOrWhitespace(name)) {
			q.setParameter("name", "%"+name+"%");
		}
		if (!StringUtil.isNullOrWhitespace(version)) {
			q.setParameter("version", version);
		}
		if (!StringUtil.isNullOrWhitespace(isDefault)) {
			q.setParameter("isDefault", isDefault);
		}
		
		q.setFirstResult(paging.getStartIndex());
		q.setMaxResults(paging.getFetchSize());

		List<TbMIcdO> result = (List<TbMIcdO>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbMIcd> listPaginatedIcdUsingQuery(Map<String, Object> criteria, Paging paging) {
		StringBuilder sb = new StringBuilder()
				.append("select i from TbMIcd i ")
				.append("where 1=1 ");
		
		Integer dataVerId =  (Integer) criteria.get("dataVerId");

		if (dataVerId != null) {
			sb.append("and i.dataVersion.id = :dataVerId ");
		}
		
		// Paging
		int startIndex = paging.getStartIndex();
		int fetchSize = paging.getFetchSize();
		String sortBy = paging.getSortBy();
		Boolean sortAsc = paging.getSortAsc();

		// Order by
		if ("code".equals(sortBy)) {
			sb.append(String.format("order by i.code %s", sortAsc ? "asc" : "desc"));
		}

		Query<?> q = getSession().createQuery(sb.toString());
		
		if (dataVerId != null) {
			q.setParameter("dataVerId", dataVerId);
		}
		
		q.setFirstResult(startIndex);
		q.setMaxResults(fetchSize);

		List<TbMIcd> result = (List<TbMIcd>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int countIcdUsingQuery(Map<String, Object> criteria, Paging paging) {
		StringBuilder sb = new StringBuilder()
				.append("select count(i.id) from TbMIcd i ")
				.append("where 1=1 ");
		
		Integer dataVerId =  (Integer) criteria.get("dataVerId");

		if (dataVerId != null) {
			sb.append("and i.dataVersion.id = :dataVerId ");
		}
		
		Query<?> q = getSession().createQuery(sb.toString());
		
		if (dataVerId != null) {
			q.setParameter("dataVerId", dataVerId);
		}

		List<Long> result = (List<Long>) q.getResultList();

		return result == null || result.size() == 0 ? 0 : result.get(0).intValue();
	}

	@Override
	public void deleteIcdList(Integer dataVerId) {
		String hql = "delete from TbMIcd i where i.dataVersion.id = :dataVerId";
		Query<?> q = getSession().createQuery(hql);
		q.setParameter("dataVerId", dataVerId);
		q.executeUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbMIcdO> listPaginatedIcdOUsingQuery(Map<String, Object> criteria, Paging paging) {
		StringBuilder sb = new StringBuilder()
				.append("select i from TbMIcdO i ")
				.append("where 1=1 ");
		
		Integer dataVerId =  (Integer) criteria.get("dataVerId");

		if (dataVerId != null) {
			sb.append("and i.dataVersion.id = :dataVerId ");
		}
		
		// Paging
		int startIndex = paging.getStartIndex();
		int fetchSize = paging.getFetchSize();
		String sortBy = paging.getSortBy();
		Boolean sortAsc = paging.getSortAsc();

		// Order by
		if ("code".equals(sortBy)) {
			sb.append(String.format("order by i.code %s", sortAsc ? "asc" : "desc"));
		}

		Query<?> q = getSession().createQuery(sb.toString());
		
		if (dataVerId != null) {
			q.setParameter("dataVerId", dataVerId);
		}
		
		q.setFirstResult(startIndex);
		q.setMaxResults(fetchSize);

		List<TbMIcdO> result = (List<TbMIcdO>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int countIcdOUsingQuery(Map<String, Object> criteria, Paging paging) {
		
		StringBuilder sb = new StringBuilder()
				.append("select count(i.id) from TbMIcdO i ")
				.append("where 1=1 ");
		
		Integer dataVerId =  (Integer) criteria.get("dataVerId");

		if (dataVerId != null) {
			sb.append("and i.dataVersion.id = :dataVerId ");
		}
		
		Query<?> q = getSession().createQuery(sb.toString());
		
		if (dataVerId != null) {
			q.setParameter("dataVerId", dataVerId);
		}

		List<Long> result = (List<Long>) q.getResultList();

		return result == null || result.size() == 0 ? 0 : result.get(0).intValue();
	}

	@Override
	public void deleteIcdOList(Integer dataVerId) {
		String hql = "delete from TbMIcdO i where i.dataVersion.id = :dataVerId";
		Query<?> q = getSession().createQuery(hql);
		q.setParameter("dataVerId", dataVerId);
		q.executeUpdate();
	}
}
