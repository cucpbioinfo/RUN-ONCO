package run.onco.api.persist.dao;

import java.util.List;
import java.util.Map;

import run.onco.api.persist.entity.TbTVariantAnnotation;
import run.onco.api.persist.entity.TbTVariantCall;
import run.onco.api.persist.entity.TbTVariantResult;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface VariantCallDao {

	public List<Object[]> getVariantAnnotationListBySampleId(final Long sampleId);
	
	public List<TbTVariantAnnotation> listPaginatedVariantsUsingQuery(Map<String, Object> criteria);
	
	public int countVariantsUsingQuery(Map<String, Object> criteria);
	
	public List<TbTVariantCall> getVariantCallListBySampleId(final Long sampleId);
	
	public List<TbTVariantAnnotation> getVariantAnnotationListByVariantCallId(final Long variantCallId); 
	
	public List<TbTVariantResult> getVariantResultListBySampleId(final Long sampleId);
	
	public List<TbTVariantAnnotation> getVariantAnnotationListByTranscript(final String transcript);
	
	public List<Object[]> getAnnotateVariantListBySampleId(final Long sampleId);
	
	public List<Object[]> listPaginatedVariantComparisonUsingQuery(Map<String, Object> criteria);
	
	public int countVariantComparisonUsingQuery(Map<String, Object> criteria);
	
	public void deleteVariantAnnotations(final Long sampleId);
	
	public void deleteVariantResults(final Long sampleId);
	
	public void deleteVariantcalls(final Long sampleId);
	
}
