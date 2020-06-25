package run.onco.api.persist.dao.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.common.utils.AppUtil;
import run.onco.api.persist.dao.RnaSeqDao;
import run.onco.api.persist.entity.TbTRnaSeq;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class RnaSeqDaoImpl implements RnaSeqDao {
	
	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbTRnaSeq> getRnaSeqListBySampleId(Long sampleId) {
		
		StringBuilder sb = new StringBuilder()
				.append("select r from TbTRnaSeq r inner join r.sampleRnaSeq s ")
				.append("where s.id = :sampleId ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("sampleId", sampleId);
		List<TbTRnaSeq> result = (List<TbTRnaSeq>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int countRnaSeqUsingQuery(Map<String, Object> criteria) {
		
		StringBuilder sb = new StringBuilder()
				.append("select count(r.id) from TbTRnaSeq r inner join r.sampleRnaSeq s ")
				.append("where 1=1 ");

		if (criteria.get("sampleId") != null) {
			sb.append("and s.id = :sampleId ");
		}
		
		Long sampleId = AppUtil.getMapValue(criteria, "sampleId");

		Query<?> q = getSession().createQuery(sb.toString());

		if (sampleId != null) {
			q.setParameter("sampleId", sampleId);	
		}

		// q.setParameter("feature", "ENST00000275493");
		List<Long> result = (List<Long>) q.getResultList();

		return result == null || result.size() == 0 ? 0 : result.get(0).intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbTRnaSeq> listPaginatedRnaSeqUsingQuery(Map<String, Object> criteria) {
		
		StringBuilder sb = new StringBuilder()
				.append("select r from TbTRnaSeq r inner join r.sampleRnaSeq s ")
				.append("where 1=1 ");

		if (criteria.get("sampleId") != null) {
			sb.append("and s.id = :sampleId ");
		}
		
		// Paging
		int startIndex =  AppUtil.getMapValue(criteria, "startIndex");
		int fetchSize = AppUtil.getMapValue(criteria, "fetchSize");
		String sortBy = AppUtil.getMapValue(criteria, "sortBy");
		Boolean sortAsc = AppUtil.getMapValue(criteria, "sortAsc");

		// Order by
//		if ("variant".equals(sortBy)) {
//			sb.append(String.format("order by concat(v.chromosome, v.position, v.refAllele, v.altAllele) %s", sortAsc ? "asc" : "desc"));
//		} else if ("chromDisplay".equals(sortBy)) {
//			sb.append(String.format("order by v.chromosome %s", sortAsc ? "asc" : "desc"));
//		} else if ("position".equals(sortBy)) {
//			sb.append(String.format("order by v.position %s", sortAsc ? "asc" : "desc"));
//		} else if ("refAllele".equals(sortBy)) {
//			sb.append(String.format("order by v.refAllele %s", sortAsc ? "asc" : "desc"));
//		} else if ("altAllele".equals(sortBy)) {
//			sb.append(String.format("order by v.altAllele %s", sortAsc ? "asc" : "desc"));
//		} else if ("consequence".equals(sortBy)) {
//			sb.append(String.format("order by a.consequence %s", sortAsc ? "asc" : "desc"));
//		} else if ("feature".equals(sortBy)) {
//			sb.append(String.format("order by a.feature %s", sortAsc ? "asc" : "desc"));
//		} else if ("afTumor".equals(sortBy)) {
//			sb.append(String.format("order by r.sampleType desc, r.alleleFrequency %s", sortAsc ? "asc" : "desc"));
//		} else if ("afNormal".equals(sortBy)) {
//			sb.append(String.format("order by r.sampleType asc, r.alleleFrequency %s", sortAsc ? "asc" : "desc"));
//		}  else if ("oncoKbAnnotated".equals(sortBy)) {
//			sb.append(String.format("order by a.oncoKbAnnotated %s nulls last", sortAsc ? "asc" : "desc"));
//		} else {
//			sb.append(String.format("order by a.oncoKbAnnotated desc nulls last"));
//		}
		
		Long sampleId = AppUtil.getMapValue(criteria, "sampleId");
//		String chromosome = AppUtil.getMapValue(criteria, "chromosome");
//		Long position = AppUtil.getMapValue(criteria, "position");

		Query<?> q = getSession().createQuery(sb.toString());

		if (sampleId != null) {
			q.setParameter("sampleId", sampleId);
		}
		
		// q.setParameter("feature", "ENST00000275493");
		// q.setFirstResult((pageNumber - 1) * pageSize);
		// q.setMaxResults(pageSize);
		
		q.setFirstResult(startIndex);
		q.setMaxResults(fetchSize);

		List<TbTRnaSeq> result = (List<TbTRnaSeq>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getRnaSeqListForClustergrammer(Long sampleId, Integer cancerGeneGrpId) {
		
		StringBuilder sb = new StringBuilder()
				.append("select r.geneId, r.geneName, r.ref, r.start, r.end, r.tpm ")
				.append("from TbTRnaSeq r, TbMCancerGene g,")
				.append("TbTSampleRnaSeq s, TbMPatient p ")
				.append("where r.geneName = g.geneName ")
				.append("and g.cancerGeneGroup.id = :cancerGeneGrpId ")
				.append("and s.id = :sampleId ")
//				.append("and p.id = :patientId ")
				.append("and r.sampleRnaSeq.id = s.id ")
//				.append("and s.patient.id = p.id ")
//				.append("and r.tpm > 0 ")
				.append("order by g.geneName asc");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("sampleId", sampleId);
//		q.setParameter("patientId", patientId);
		q.setParameter("cancerGeneGrpId", cancerGeneGrpId);
		List<Object[]> result = (List<Object[]>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getRnaSeqListForIdeogram(Long sampleId, Long patientId, Integer cancerGeneGrpId) {
		
		StringBuilder sb = new StringBuilder()
				.append("select r.geneId, r.geneName, r.ref, r.start, r.end ")
				.append("from TbTRnaSeq r, TbMCancerGene g, TbTSampleRnaSeq s, ")
				.append("TbMPatient p where r.geneName = g.geneName ")
				.append("and g.cancerGeneGroup.id = :cancerGeneGrpId ")
				.append("and s.id = :sampleId ")
				.append("and p.id = :patientId ")
				.append("and s.patient.id = p.id ")
				.append("and r.sampleRnaSeq.id = s.id ")
				.append("and r.tpm > 0 ")
				.append("order by g.geneName asc");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("sampleId", sampleId);
		q.setParameter("patientId", patientId);
		q.setParameter("cancerGeneGrpId", cancerGeneGrpId);
		List<Object[]> result = (List<Object[]>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}


	@Override
	public void deleteRnaSeqList(Long sampleId) {
		String hql = "delete from TbTRnaSeq r where r.sampleRnaSeq.id = :sampleId";
		Query<?> q = getSession().createQuery(hql);
		q.setParameter("sampleId", sampleId);
		q.executeUpdate();
	}
	
}
