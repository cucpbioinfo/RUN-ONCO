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
import run.onco.api.persist.dao.UserDao;
import run.onco.api.persist.entity.TbMUser;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	protected EntityManager em;
	
	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class); 
	}

	@Override
	public TbMUser login(final String username, final String password) {
		StringBuilder sb = new StringBuilder()
				.append("select ur from TbMUser ur ")
				.append("where ur.username = :username and ur.password = :password ")
				.append("and ur.status = :status ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("username", username);
		q.setParameter("password", password);
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		List<TbMUser> result = (List<TbMUser>) q.getResultList();

		return result == null || result.size() == 0 ? null : result.get(0);
	}

	@Override
	public TbMUser getActiveUserById(Long userId) {
		StringBuilder sb = new StringBuilder()
				.append("select ur from TbMUser ur ")
				.append("where ur.id = :id ")
				.append("and ur.status = :status");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("id", userId);
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		List<TbMUser> result = (List<TbMUser>) q.getResultList();

		return result == null || result.size() == 0 ? null : result.get(0);
	}

	@Override
	public TbMUser getActiveUserByUsername(String username) {
		StringBuilder sb = new StringBuilder()
				.append("select ur from TbMUser ur ")
				.append("where ur.username = :username ")
				.append("and ur.status = :status");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("username", username);
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		List<TbMUser> result = (List<TbMUser>) q.getResultList();

		return result == null || result.size() == 0 ? null : result.get(0);
	}

	@Override
	public List<TbMUser> listPaginatedUserUsingQuery(Map<String, Object> criteria, Paging paging) {
		StringBuilder sb = new StringBuilder()
				.append("select ur from TbMUser ur ")
				.append("where 1=1 ");

		if (!StringUtil.isNullOrWhitespace((String)criteria.get("username"))) {
			sb.append("and ur.username = :username ");
		}
		if (!StringUtil.isNullOrWhitespace((String)criteria.get("status"))) {
			sb.append("and ur.status = :status ");
		}
		
		// Paging
		int startIndex = paging.getStartIndex();
		int fetchSize = paging.getFetchSize();
		String sortBy = paging.getSortBy();
		Boolean sortAsc = paging.getSortAsc();
		
		// Order by
		if ("username".equals(sortBy)) {
			sb.append(String.format("order by ur.username %s", sortAsc ? "asc" : "desc"));
		}

		Query<?> q = getSession().createQuery(sb.toString());
				
		if (!StringUtil.isNullOrWhitespace((String)criteria.get("username"))) {
			q.setParameter("username", criteria.get("username"));
		}
		if (!StringUtil.isNullOrWhitespace((String)criteria.get("status"))) {
			q.setParameter("status", criteria.get("status"));
		}
				
		q.setFirstResult(startIndex);
		q.setMaxResults(fetchSize);

		List<TbMUser> result = (List<TbMUser>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	public int countUserUsingQuery(Map<String, Object> criteria, Paging paging) {
		StringBuilder sb = new StringBuilder()
				.append("select count(ur.id) from TbMUser ur ")
				.append("where 1=1 ");

		if (!StringUtil.isNullOrWhitespace((String)criteria.get("username"))) {
			sb.append("and ur.username = :username ");
		}
		if (!StringUtil.isNullOrWhitespace((String)criteria.get("status"))) {
			sb.append("and ur.status = :status ");
		}

		Query<?> q = getSession().createQuery(sb.toString());
		
		if (!StringUtil.isNullOrWhitespace((String)criteria.get("username"))) {
			q.setParameter("username", criteria.get("username"));
		}
		if (!StringUtil.isNullOrWhitespace((String)criteria.get("status"))) {
			q.setParameter("status", criteria.get("status"));
		}

		List<Long> result = (List<Long>) q.getResultList();

		return result == null || result.size() == 0 ? 0 : result.get(0).intValue();
	}

	@Override
	public boolean findDuplicateUsername(String username, Long id) {

		StringBuilder sb = new StringBuilder()
				.append("select count(ur.id) from TbMUser ur ")
				.append("where ur.username = :username ");
		
		if (id != null) {
			sb.append("and ur.id <> :id");
		}

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("username", username);
		
		if (id != null) {
			q.setParameter("id", id);
		}

		List<Long> result = (List<Long>) q.getResultList();
		Long totalRecords = result.get(0);
		return totalRecords == 0 ? false : true;
	}
}
