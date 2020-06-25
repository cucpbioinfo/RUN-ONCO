package run.onco.api.persist.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.persist.dao.SampleRnaSeqDao;
import run.onco.api.persist.entity.TbTSampleRnaSeq;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class SampleRnaSeqDaoImpl implements SampleRnaSeqDao {

	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TbTSampleRnaSeq> getRnaSeqListByPatientId(Long patientId) {
		
		StringBuilder sb = new StringBuilder()
				.append("select s from TbTSampleRnaSeq s ")
				.append("where s.patient.id = :patientId ")
				.append("and s.patient.status = :activeStatus ")
				.append("and s.biospecimen.status = :activeStatus ")
				.append("and s.status <> :inactiveStatus");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("patientId", patientId);
		q.setParameter("activeStatus", AppConstants.STATUS_ACTIVE);
		q.setParameter("inactiveStatus", AppConstants.STATUS_INACTIVE);
		List<TbTSampleRnaSeq> result = (List<TbTSampleRnaSeq>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int countSampleRnaSeqUsingQuery(String status) {
		
		StringBuilder sb = new StringBuilder()
				.append("select s from TbTSampleRnaSeq s ")
				.append("where s.biospecimen.status = :status ")
				.append("and s.status = :status ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("status", status);
		
		List<String> result = (List<String>) q.getResultList();
		return result == null || result.size() == 0 ? 0 : result.size();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbTSampleRnaSeq> listPaginatedSampleRnaSeqUsingQuery(String status, int startIndex, int fetchSize) {
		
		StringBuilder sb = new StringBuilder()
				.append("select s from TbTSampleRnaSeq s ")
				.append("where s.biospecimen.status = :status ")
				.append("and s.status = :status ");
		
		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("status", status);
		
		q.setFirstResult(startIndex);
		q.setMaxResults(fetchSize);

		List<TbTSampleRnaSeq> result = (List<TbTSampleRnaSeq>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TbTSampleRnaSeq> getSampleRnaSeqListByBiospecimens(Long[] ids) {
		
		StringBuilder sb = new StringBuilder()
				.append("select s from TbTSampleRnaSeq s inner join s.biospecimen b ")
				.append("where b.id IN :ids ");
		
		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameterList("ids", ids);
		List<TbTSampleRnaSeq> result = (List<TbTSampleRnaSeq>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TbTSampleRnaSeq> getSampleRnaSeqListBySampleIds(Long[] ids) {
		
		StringBuilder sb = new StringBuilder()
				.append("select s from TbTSampleRnaSeq s ")
				.append("where s.id IN :ids ");
		
		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameterList("ids", ids);
		List<TbTSampleRnaSeq> result = (List<TbTSampleRnaSeq>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<TbTSampleRnaSeq> getActiveSampleRnaSeqList() {
		
		StringBuilder sb = new StringBuilder()
				.append("select s from TbTSampleRnaSeq s ")
				.append("where s.status = :status");
		
		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("status", AppConstants.STATUS_ACTIVE);
		List<TbTSampleRnaSeq> result = (List<TbTSampleRnaSeq>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean findDuplicateUploadSampleRnaSeq(Long biospecimenId) {
		StringBuilder sb = new StringBuilder()
				.append("select count(s.id) from TbTSampleRnaSeq s ")
				.append("where s.biospecimen.id = :biospecimenId");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("biospecimenId", biospecimenId);

		List<Long> result = (List<Long>) q.getResultList();
		Long totalRecords = result.get(0);
		return totalRecords == 0 ? false : true;
	}
}
