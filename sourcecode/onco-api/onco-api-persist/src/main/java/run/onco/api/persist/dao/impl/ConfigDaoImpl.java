package run.onco.api.persist.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.persist.dao.ConfigDao;
import run.onco.api.persist.entity.TbTSequence;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class ConfigDaoImpl implements ConfigDao {
	
	@PersistenceContext
	protected EntityManager em;
	
	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class); 
	}

	@Override
	public boolean checkServiceAuth(String systemCode, String channelId) {
		StringBuilder sb = new StringBuilder()
				.append("select sy.id from TbCSystem sy inner join sy.channelList ch ")
				.append("where sy.code = :systemCode and ch.code = :channelId ")
				.append("and sy.status = :status and ch.status = :status ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("systemCode", systemCode);
		q.setParameter("channelId", channelId);
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		List<Integer> result = (List<Integer>) q.getResultList();

		return result == null || result.size() == 0 ? false : true;
	}

	@Override
	public TbTSequence getCurrentSequence(String dataType, Date currentDate) {
		StringBuilder sb = new StringBuilder()
				.append("from TbTSequence sq where month(sq.createDate) = month( :currentDate ) ")
				.append("and year(sq.createDate) = year( :currentDate ) ")
				.append("and sq.dataType = :dataType ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("currentDate", currentDate);
		q.setParameter("dataType", dataType);
		List<TbTSequence> result = (List<TbTSequence>) q.getResultList();

		return result == null || result.size() == 0 ? null : result.get(0);
	}

	@Override
	public void saveNextSequence(TbTSequence sequence) {
		getSession().saveOrUpdate(sequence);
	}

}
