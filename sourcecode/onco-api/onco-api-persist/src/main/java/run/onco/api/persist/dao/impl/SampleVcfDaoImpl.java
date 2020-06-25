package run.onco.api.persist.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.persist.dao.SampleVcfDao;
import run.onco.api.persist.entity.TbTSampleVariant;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class SampleVcfDaoImpl implements SampleVcfDao {
	
	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TbTSampleVariant> getVariantCallListByPatientId(Long patientId) {
		
		StringBuilder sb = new StringBuilder()
				.append("select s from TbTSampleVariant s ")
				.append("where s.patient.id = :patientId ")
				.append("and s.patient.status = :activeStatus ")
				.append("and s.biospecimen.status = :activeStatus ")
				.append("and s.status <> :inactiveStatus");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("patientId", patientId);
		q.setParameter("activeStatus", AppConstants.STATUS_ACTIVE);
		q.setParameter("inactiveStatus", AppConstants.STATUS_INACTIVE);
		List<TbTSampleVariant> result = (List<TbTSampleVariant>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public TbTSampleVariant getVariantCallByBiospecimenId(Long biospecimenId, Integer seqTypeId) {
		
		StringBuilder sb = new StringBuilder()
				.append("select v from TbTSampleVariant v ")
				.append("where v.biospecimen.id = :biospecimenId ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("biospecimenId", biospecimenId);
		q.setParameter("seqTypeId", seqTypeId);
		List<TbTSampleVariant> result = (List<TbTSampleVariant>) q.getResultList();

		return result == null || result.size() == 0 ? null : result.get(0);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TbTSampleVariant> listPaginatedSampleVcfUsingQuery(Map<String, Object> criteria) {
		
		StringBuilder sb = new StringBuilder()
				.append("select s from TbTSampleVariant s ")
				.append("where s.biospecimen.status = :activeStatus ")
				.append("and s.status = :status ");
		
		// Paging
		int startIndex =  AppUtil.getMapValue(criteria, "startIndex");
		int fetchSize = AppUtil.getMapValue(criteria, "fetchSize");
		
		Query<?> q = getSession().createQuery(sb.toString());
		
		String status = AppUtil.getMapValue(criteria, "status");
		q.setParameter("status", status);
		q.setParameter("activeStatus", AppConstants.STATUS_ACTIVE);
		
		q.setFirstResult(startIndex);
		q.setMaxResults(fetchSize);

		List<TbTSampleVariant> result = (List<TbTSampleVariant>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int countSampleVcfUsingQuery(Map<String, Object> criteria) {
		
		StringBuilder sb = new StringBuilder()
				.append("select s from TbTSampleVariant s ")
				.append("where s.biospecimen.status = :activeStatus ")
				.append("and s.status = :status ");

		Query<?> q = getSession().createQuery(sb.toString());
		
		String status = AppUtil.getMapValue(criteria, "status");
		q.setParameter("status", status);
		q.setParameter("activeStatus", AppConstants.STATUS_ACTIVE);
		
		List<String> result = (List<String>) q.getResultList();
		return result == null || result.size() == 0 ? 0 : result.size();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<TbTSampleVariant> getSampleVcfListByBiospecimenIds(String seqTypeCode, Long[] ids) {
		
		StringBuilder sb = new StringBuilder()
				.append("select s from TbTSampleVariant s ")
				.append("where s.biospecimen.id IN :ids ")
				.append("and s.sequenceType.code = :seqTypeCode ");
		
		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameterList("ids", ids);
		q.setParameter("seqTypeCode", seqTypeCode);
		List<TbTSampleVariant> result = (List<TbTSampleVariant>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean findDuplicateUploadSampleVcf(String seqTypeCode, Long biospecimenId) {
		StringBuilder sb = new StringBuilder()
				.append("select count(s.id) from TbTSampleVariant s ")
				.append("where s.biospecimen.id = :biospecimenId ")
				.append("and s.sequenceType.code = :seqTypeCode");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("biospecimenId", biospecimenId);
		q.setParameter("seqTypeCode", seqTypeCode);

		List<Long> result = (List<Long>) q.getResultList();
		Long totalRecords = result.get(0);
		return totalRecords == 0 ? false : true;
	}
}
