package run.onco.api.persist.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.common.dto.DataItem;
import run.onco.api.common.dto.Paging;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class Dao {

	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}

	public <T> T save(final T entity) {
		return (T) getSession().save(entity);
	}

	public void delete(final Object object) {
		getSession().delete(object);
	}

	public <T> T get(final Class<T> type, final Object id) {
		return (T) getSession().find(type, id);
	}

	public <T> T merge(final T entity) {
		return (T) getSession().merge(entity);
	}

	public <T> void saveOrUpdate(final T entity) {
		getSession().saveOrUpdate(entity);
	}

	public <T> List<T> getAll(final Class<T> type) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(type);
		List<T> resultList = getSession().createQuery(criteria).getResultList();
		return resultList;
	}

	public <T> List<T> findByCriteria(final Class<T> type, Map<String, Object> map) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(type);
		Root<T> entityRoot = criteria.from(type);
		criteria.select(entityRoot);

		List<Predicate> predicates = new ArrayList<Predicate>();

		for (Map.Entry<String, Object> entry : map.entrySet()) {

			String key = entry.getKey();
			Object value = entry.getValue();
			predicates.add(builder.equal(entityRoot.get(key), value));
		}

		criteria.where(predicates.toArray(new Predicate[] {}));
		List<T> resultList = getSession().createQuery(criteria).getResultList();
		return resultList;
	}
	
	public <T> List<DataItem> findByCriteria(Class<T> type, Map<String, Object> map, String[] fields) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<DataItem> criteria = builder.createQuery(DataItem.class);
		Root<T> entityRoot = criteria.from(type);
		criteria.multiselect(entityRoot.get(fields[0]), entityRoot.get(fields[1]));

		List<Predicate> predicates = new ArrayList<Predicate>();

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			predicates.add(builder.equal(entityRoot.get(key), value));
		}

		criteria.where(predicates.toArray(new Predicate[] {}));
		
		List<DataItem> resultList = (List<DataItem>) getSession().createQuery(criteria).getResultList();
		return resultList;
	}
	
	public <T> List<T> search(final Class<T> type, Map<String, Object> searchCriteria, Paging paging) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(type);
		Root<T> entityRoot = criteria.from(type);
		criteria.select(entityRoot);

		List<Predicate> predicates = new ArrayList<Predicate>();

		for (Map.Entry<String, Object> entry : searchCriteria.entrySet()) {

			String key = entry.getKey();
			Object value = entry.getValue();
			
			if (value != null) {
				if (value instanceof Integer) {
					predicates.add(builder.equal(entityRoot.get(key), value));
				} else {
					predicates.add(builder.like(entityRoot.get(key), wrapToLike(value)));
				}
			}
			
		}

		criteria.where(predicates.toArray(new Predicate[] {}));
		
		Query<?> q = getSession().createQuery(criteria);
		q.setFirstResult(paging.getStartIndex());
		q.setMaxResults(paging.getFetchSize());
//		q.setFirstResult((pageNumber - 1) * pageSize);
//		q.setMaxResults(pageSize);
		
		List<T> resultList = (List<T>) q.getResultList();
		return resultList;
	}
	
	private static String wrapToLike(Object value) {
	    return value == null ? "%" : "%"+value+"%";
	}
}
