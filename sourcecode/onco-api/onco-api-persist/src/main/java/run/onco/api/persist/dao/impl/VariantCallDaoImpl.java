package run.onco.api.persist.dao.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.StringUtil;
import run.onco.api.persist.dao.VariantCallDao;
import run.onco.api.persist.entity.TbTVariantAnnotation;
import run.onco.api.persist.entity.TbTVariantCall;
import run.onco.api.persist.entity.TbTVariantResult;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class VariantCallDaoImpl implements VariantCallDao {

	private final static Logger logger = Logger.getLogger(VariantCallDaoImpl.class);

	@PersistenceContext
	protected EntityManager em;

	protected Session getSession() {
		return em.unwrap(org.hibernate.Session.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getVariantAnnotationListBySampleId(Long sampleId) {

		StringBuilder sb = new StringBuilder()
				.append("SELECT VC.CHROMOSOME, VC.POSITION, VC.REF_ALLELE, VC.ALT_ALLELE, ")
				.append("		VA.ALLELE, VA.CONSEQUENCE, VA.IMPACT, VA.SYMBOL, VA.GENE, ")
				.append("		VA.FEATURE_TYPE, VA.FEATURE, VA.BIOTYPE, VA.EXON, VA.INTRON, ")
				.append("		VA.HGVSP, VA.HGVSC, VA.CDNA_POSITION, VA.CDS_POSITION, ")
				.append("		VA.PROTEIN_POSITION, VA.AMINO_ACIDS, VA.CODONS, VA.EXISTING_VARIANTION, ")
				.append(" 		VA.DISTANCE, VA.STRAND, VA.FLAGS, VA.SYMBOL_SOURCE, VA.HGNC_ID, ")
				.append(" 		VA.ONCOKB_ANNOTATED, COALESCE(VR1.ALLELE_FREQUENCY, 0) AS TUMOR_AF, ")
				.append("       COALESCE(VR1.ALT_ALLELE_DEPTH, 0) AS TUMOR_AD, ")
				.append(" 	    COALESCE(VR2.ALLELE_FREQUENCY, 0) AS NORMAL_AF, ")
				.append("       COALESCE(VR2.ALT_ALLELE_DEPTH, 0) AS NORMAL_AD ")
				.append("FROM TB_T_VARIANT_ANNOTATION VA ")
				.append("INNER JOIN TB_T_VARIANT_CALL VC ON VC.VAR_ID = VA.VAR_ID ")
				.append("INNER JOIN TB_T_VARIANT_RESULT VR1 ON VR1.VAR_ID = VC.VAR_ID AND VR1.SAMPLE_TYPE = 'T' ")
				.append("INNER JOIN TB_T_VARIANT_RESULT VR2 ON VR2.VAR_ID = VC.VAR_ID AND VR2.SAMPLE_TYPE = 'N' ")
				.append("WHERE VC.CHROMOSOME LIKE :str  AND VA.SAMPLE_VAR_ID = :sampleId ");
		
		try {
			javax.persistence.Query q = em.createNativeQuery(sb.toString());
			q.setParameter("str", "chr%");
			q.setParameter("sampleId", sampleId);
	        List<Object[]> result = q.getResultList();
	        return result == null || result.size() == 0 ? null : result;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbTVariantAnnotation> listPaginatedVariantsUsingQuery(Map<String, Object> criteria) {
		
		StringBuilder sb = new StringBuilder()
				.append("select a from TbTVariantAnnotation a inner join a.variantCall v ")
				.append("inner join v.variantResultList r inner join a.sampleVariant s ")
				.append("where 1=1 ");

		if (criteria.get("sampleId") != null) {
			sb.append("and s.id = :sampleId ");
		}
		if (criteria.get("chromosome") != null) {
			sb.append("and v.chromosome like :chromosome ");
		}
		if (criteria.get("position") != null) {
			sb.append("and cast(v.position as string) like :position ");
		}
		
		// Paging
		int startIndex =  AppUtil.getMapValue(criteria, "startIndex");
		int fetchSize = AppUtil.getMapValue(criteria, "fetchSize");
		String sortBy = AppUtil.getMapValue(criteria, "sortBy");
		Boolean sortAsc = AppUtil.getMapValue(criteria, "sortAsc");

		// Order by
		if ("variant".equals(sortBy)) {
			sb.append(String.format("order by concat(v.chromosome, v.position, v.refAllele, v.altAllele) %s", sortAsc ? "asc" : "desc"));
		} else if ("chromDisplay".equals(sortBy)) {
			sb.append(String.format("order by cast(substring(v.chromosome, 4) as int) %s", sortAsc ? "asc" : "desc"));
		} else if ("position".equals(sortBy)) {
			sb.append(String.format("order by v.position %s", sortAsc ? "asc" : "desc"));
		} else if ("refAllele".equals(sortBy)) {
			sb.append(String.format("order by v.refAllele %s", sortAsc ? "asc" : "desc"));
		} else if ("altAllele".equals(sortBy)) {
			sb.append(String.format("order by v.altAllele %s", sortAsc ? "asc" : "desc"));
		} else if ("consequence".equals(sortBy)) {
			sb.append(String.format("order by a.consequence %s", sortAsc ? "asc" : "desc"));
		} else if ("feature".equals(sortBy)) {
			sb.append(String.format("order by a.feature %s", sortAsc ? "asc" : "desc"));
		} else if ("afTumor".equals(sortBy)) {
			sb.append(String.format("order by r.sampleType desc, r.alleleFrequency %s", sortAsc ? "asc" : "desc"));
		} else if ("afNormal".equals(sortBy)) {
			sb.append(String.format("order by r.sampleType asc, r.alleleFrequency %s", sortAsc ? "asc" : "desc"));
		}  else if ("oncoKbAnnotated".equals(sortBy)) {
			sb.append(String.format("order by a.oncoKbAnnotated %s", sortAsc ? "asc" : "desc"));
		} else {
			sb.append(String.format("order by a.oncoKbAnnotated desc nulls last"));
		}
		
		Long sampleId = AppUtil.getMapValue(criteria, "sampleId");
		String chromosome = AppUtil.getMapValue(criteria, "chromosome");
		Long position = AppUtil.getMapValue(criteria, "position");

		Query<?> q = getSession().createQuery(sb.toString());

		if (sampleId != null) {
			q.setParameter("sampleId", sampleId);
		}
		if (chromosome != null) {
			q.setParameter("chromosome", "%"+chromosome+"%");
		}
		if (position != null) {
			q.setParameter("position", "%"+position+"%");
		}
		// q.setParameter("feature", "ENST00000275493");
		// q.setFirstResult((pageNumber - 1) * pageSize);
		// q.setMaxResults(pageSize);
		
		q.setFirstResult(startIndex);
		q.setMaxResults(fetchSize);

		List<TbTVariantAnnotation> result = (List<TbTVariantAnnotation>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbTVariantCall> getVariantCallListBySampleId(Long sampleId) {
		
		StringBuilder sb = new StringBuilder()
				.append("select v from TbTVariantCall v inner join v.sampleVariant s ")
				.append("where s.id = :sampleId ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("sampleId", sampleId);
		List<TbTVariantCall> result = (List<TbTVariantCall>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbTVariantAnnotation> getVariantAnnotationListByVariantCallId(Long variantCallId) {
		StringBuilder sb = new StringBuilder()
				.append("select a from TbTVariantAnnotation a inner join a.variantCall v ")
				.append("where v.id = :variantCallId ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("variantCallId", variantCallId);
		List<TbTVariantAnnotation> result = (List<TbTVariantAnnotation>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int countVariantsUsingQuery(Map<String, Object> criteria) {
		
		StringBuilder sb = new StringBuilder()
				.append("select count(a.id) from TbTVariantAnnotation a inner join a.variantCall v ")
				.append("inner join v.variantResultList r inner join a.sampleVariant s ")
				.append("where 1=1 ");

		if (criteria.get("sampleId") != null) {
			sb.append("and s.id = :sampleId ");
		}
		if (criteria.get("chromosome") != null) {
			sb.append("and v.chromosome like :chromosome ");
		}
		if (criteria.get("position") != null) {
			sb.append("and cast(v.position as string) like :position ");
		}
		
		Long sampleId = AppUtil.getMapValue(criteria, "sampleId");
		String chromosome = AppUtil.getMapValue(criteria, "chromosome");
		Long position = AppUtil.getMapValue(criteria, "position");

		Query<?> q = getSession().createQuery(sb.toString());

		if (sampleId != null) {
			q.setParameter("sampleId", sampleId);	
		}
		if (chromosome != null) {
			q.setParameter("chromosome", "%"+chromosome+"%");
		}
		if (position != null) {
			q.setParameter("position", "%"+position+"%");
		}

		// q.setParameter("feature", "ENST00000275493");
		List<Long> result = (List<Long>) q.getResultList();

		return result == null || result.size() == 0 ? 0 : result.get(0).intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbTVariantResult> getVariantResultListBySampleId(Long sampleId) {
		
		StringBuilder sb = new StringBuilder()
				.append("select v from TbTVariantResult v inner join v.sampleVariant s ")
				.append("where s.id = :sampleId ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("sampleId", sampleId);
		List<TbTVariantResult> result = (List<TbTVariantResult>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TbTVariantAnnotation> getVariantAnnotationListByTranscript(String transcript) {
		
		StringBuilder sb = new StringBuilder()
				.append("select a from TbTVariantAnnotation a ")
				.append("where a.feature = :transcript and a.annotatedDate is null ");

		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("transcript", transcript);
		List<TbTVariantAnnotation> result = (List<TbTVariantAnnotation>) q.getResultList();

		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> getAnnotateVariantListBySampleId(Long sampleId) {
		
		StringBuilder sb = new StringBuilder()
				.append("select v.id, a.id from TbTVariantAnnotation v, ")
				.append("TbMActionableVariant a ")
				.append("where a.gene = v.symbol ")
				.append("and v.featureType = 'Transcript' ")
				.append("and v.sampleVariant.id = :sampleId ")
				.append("order by v.id ");
		
		Query<?> q = getSession().createQuery(sb.toString());
		q.setParameter("sampleId", sampleId);
		
		List<Object[]> result = (List<Object[]>) q.getResultList();
		return result == null || result.size() == 0 ? null : result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> listPaginatedVariantComparisonUsingQuery(Map<String, Object> criteria) {
		
		Long[] ids =  AppUtil.getMapValue(criteria, "ids");
		
		// Paging
		int startIndex =  AppUtil.getMapValue(criteria, "startIndex");
		int fetchSize = AppUtil.getMapValue(criteria, "fetchSize");
		String sortBy = AppUtil.getMapValue(criteria, "sortBy");
		Boolean sortAsc = AppUtil.getMapValue(criteria, "sortAsc");
		
		String columns = "";
		String joinCondition = "";
//		String whereClause = "";
		
		for (int i = 1; i <= ids.length; i++) {
			columns += " $1 v$2.SAMPLE_VAR_ID as SPECIMEN_$2 ".replaceAll("\\$1", i != 1 ? ", " : "").replaceAll("\\$2", String.valueOf(i));
			
			joinCondition += (
								"LEFT JOIN TB_T_VARIANT_CALL v$1 " + 
								"	ON v$1.CHROMOSOME = var.CHROMOSOME " + 
								"		AND v$1.POSITION = var.POSITION " + 
								" 		AND v$1.REF_ALLELE = var.REF_ALLELE " + 
								" 		AND v$1.ALT_ALLELE = var.ALT_ALLELE " + 
								" 		AND v$1.SAMPLE_VAR_ID = $2 "
							)
							.replaceAll("\\$1", String.valueOf(i))
							.replaceAll("\\$2", ids[i-1].toString());
			
//			if( i == 2 ) {
//				whereClause += " v$1.SAMPLE_VAR_ID <=> v$2.SAMPLE_VAR_ID ".replaceAll("\\$1", String.valueOf(i - 1)).replaceAll("\\$2", String.valueOf(i));
//			} else if(i > 2 && i % 2 == 0) {
//				whereClause += " AND v$1.SAMPLE_VAR_ID <=> v$2.SAMPLE_VAR_ID ".replaceAll("\\$1", String.valueOf(i - 1)).replaceAll("\\$2", String.valueOf(i));
//			}
		}

		String sql = "SELECT 'Y' as ONCOKB_ANNOTATED, CONCAT(var.CHROMOSOME,':',var.POSITION,' ', var.REF_ALLELE, ' / ', var.ALT_ALLELE) as VARIANT, var.* " +
					 "FROM ( " +
					 "	SELECT CAST(SUBSTRING(var.CHROMOSOME, 4) as SIGNED) as CHROMOSOME, " +
					 "		   var.POSITION, var.REF_ALLELE, var.ALT_ALLELE, var.SYMBOL, " + columns +
					 "	FROM ( " + 
					 "			SELECT DISTINCT v.CHROMOSOME, v.POSITION, v.REF_ALLELE, v.ALT_ALLELE, a.SYMBOL " + 
					 "			FROM TB_T_VARIANT_CALL v " + 
					 "			INNER JOIN TB_T_SAMPLE_VARIANT s ON s.SAMPLE_VAR_ID = v.SAMPLE_VAR_ID " + 
					 "			INNER JOIN TB_T_VARIANT_ANNOTATION a ON a.VAR_ID = v.VAR_ID " + 
					 "			WHERE s.SAMPLE_VAR_ID IN ( " + StringUtil.join(ids) + " ) " +
					 " 			AND a.ONCOKB_ANNOTATED = 'Y' " + 
					 "	) var " + joinCondition /*+ String.format(" WHERE NOT ( %s ) ", whereClause)*/ +
					 ") var ";
		
		// Order by
		if ("variant".equals(sortBy)) {
			sql += String.format("ORDER BY CONCAT(var.CHROMOSOME, var.POSITION, var.REF_ALLELE, var.ALT_ALLELE) %s ", sortAsc ? "asc" : "desc");
		} else if ("chromDisplay".equals(sortBy)) {
			sql += String.format("ORDER BY var.CHROMOSOME %s ", sortAsc ? "asc" : "desc");
		} else if ("position".equals(sortBy)) {
			sql += String.format("ORDER BY var.POSITION %s ", sortAsc ? "asc" : "desc");
		} else if ("refAllele".equals(sortBy)) {
			sql += String.format("ORDER BY var.REF_ALLELE %s ", sortAsc ? "asc" : "desc");
		} else if ("altAllele".equals(sortBy)) {
			sql += String.format("ORDER BY var.ALT_ALLELE %s ", sortAsc ? "asc" : "desc");
		} else if ("symbol".equals(sortBy)) {
			sql += String.format("ORDER BY var.SYMBOL %s ", sortAsc ? "asc" : "desc");
		} else {
			sql += "ORDER BY var.CHROMOSOME asc, var.POSITION asc ";
		}
		
		logger.debug(String.format("I:--START--:--Get VariantComparison--:sql/%s", sql));

		try {
			javax.persistence.Query q = em.createNativeQuery(sql);
			q.setFirstResult(startIndex);
			q.setMaxResults(fetchSize);

	        List<Object[]> result = q.getResultList();
	        return result == null || result.size() == 0 ? null : result;
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int countVariantComparisonUsingQuery(Map<String, Object> criteria) {
		
		Long[] ids =  AppUtil.getMapValue(criteria, "ids");
		
		String joinCondition = "";
//		String whereClause = "";
		
		for (int i = 1; i <= ids.length; i++) {

			joinCondition += (
								"LEFT JOIN TB_T_VARIANT_CALL v$1 " + 
								"	ON v$1.CHROMOSOME = var.CHROMOSOME " + 
								"		AND v$1.POSITION = var.POSITION " + 
								" 		AND v$1.REF_ALLELE = var.REF_ALLELE " + 
								" 		AND v$1.ALT_ALLELE = var.ALT_ALLELE " + 
								" 		AND v$1.SAMPLE_VAR_ID = $2 "
							)
							.replaceAll("\\$1", String.valueOf(i))
							.replaceAll("\\$2", ids[i-1].toString());
			
//			if( i == 2 ) {
//				whereClause += " v$1.SAMPLE_VAR_ID <=> v$2.SAMPLE_VAR_ID ".replaceAll("\\$1", String.valueOf(i - 1)).replaceAll("\\$2", String.valueOf(i));
//			} else if(i > 2 && i % 2 == 0) {
//				whereClause += " AND v$1.SAMPLE_VAR_ID <=> v$2.SAMPLE_VAR_ID ".replaceAll("\\$1", String.valueOf(i - 1)).replaceAll("\\$2", String.valueOf(i));
//			}
		}
		
		String sql = "SELECT COUNT(var.POSITION) " +
					 "FROM ( " + 
					 "		SELECT DISTINCT v.CHROMOSOME, v.POSITION, v.REF_ALLELE, v.ALT_ALLELE, a.SYMBOL " + 
					 "		FROM TB_T_VARIANT_CALL v " + 
					 "		INNER JOIN TB_T_SAMPLE_VARIANT s ON s.SAMPLE_VAR_ID = v.SAMPLE_VAR_ID " + 
					 "		INNER JOIN TB_T_VARIANT_ANNOTATION a ON a.VAR_ID = v.VAR_ID " + 
					 "		WHERE s.SAMPLE_VAR_ID IN ( " + StringUtil.join(ids) + " ) " +
					 " 		AND a.ONCOKB_ANNOTATED = 'Y' " + 
					 ") var " + joinCondition /*+ String.format(" WHERE NOT ( %s ) ", whereClause)*/;
		
		logger.debug(String.format("I:--START--:--Get Count VariantComparison--:sql/%s", sql));

		try {
			javax.persistence.Query q = em.createNativeQuery(sql);
	        List<BigInteger> result = q.getResultList();
			return result == null || result.size() == 0 ? 0 : result.get(0).intValue();
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }
	}
	
	@Override
	public void deleteVariantAnnotations(Long sampleId) {
		String hql = "delete from TbTVariantAnnotation v where v.sampleVariant.id = :sampleId";
		Query<?> q = getSession().createQuery(hql);
		q.setParameter("sampleId", sampleId);
		q.executeUpdate();
	}

	@Override
	public void deleteVariantResults(Long sampleId) {
		String hql = "delete from TbTVariantResult v where v.sampleVariant.id = :sampleId";
		Query<?> q = getSession().createQuery(hql);
		q.setParameter("sampleId", sampleId);
		q.executeUpdate();
	}

	@Override
	public void deleteVariantcalls(Long sampleId) {
		String hql = "delete from TbTVariantCall v where v.sampleVariant.id = :sampleId";
		Query<?> q = getSession().createQuery(hql);
		q.setParameter("sampleId", sampleId);
		q.executeUpdate();
	}
}
