package run.onco.api.persist.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.persist.dao.DownloadLogDao;
import run.onco.api.persist.entity.TbLDownloadHistory;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class DownloadLogDaoImpl implements DownloadLogDao {

	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public TbLDownloadHistory getDownloadLogById(Long downloadLogId) {
		
		StringBuilder sb = new StringBuilder()
				.append("select h from TbLDownloadHistory h ")
				.append("where h.id = :id ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("id", downloadLogId);

		List<TbLDownloadHistory> result = (List<TbLDownloadHistory>) q.getResultList();
		return result == null || result.size() == 0 ? null : result.get(0);
	}
	
}
