package run.onco.api.service;

import java.util.List;
import java.util.Map;

import run.onco.api.persist.entity.TbMActionableVariant;
import run.onco.api.persist.entity.TbTSampleVariant;
import run.onco.api.persist.entity.TbTVariantAnnotation;
import run.onco.api.persist.entity.TbTVariantCall;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface VariantCallService {
	
	public void deleteVariantCall(TbTSampleVariant sampleVariant);

	public List<TbTVariantCall> getVariantCallListBySampleId(final Long sampleId);
	
	public List<TbTVariantAnnotation> listPaginatedVariantsUsingQuery(Map<String, Object> criteria);
	
	public int countVariantsUsingQuery(Map<String, Object> criteria);
	
	public List<TbMActionableVariant> getActinableVariantList(final String transcript);
	
	public List<TbTVariantAnnotation> getVariantAnnotationListByTranscript(final String transcript);
	
	public TbTVariantAnnotation getVariantAnnotationById(final Long variantId);
	
	public void saveVariantCallList(Long sampleId, List<TbTVariantCall> variantCallList); 

	public Map<Long, Integer[]> getAnnotateVariantListBySampleId(final Long sampleId);

	public void saveAnnotateVariant(Long sampleId, Map<Long, Integer[]> hashMap);
	
	public List<Object[]> listPaginatedVariantComparisonUsingQuery(Map<String, Object> criteria);
	
	public int countVariantComparisonUsingQuery(Map<String, Object> criteria);
	
	public List<Object[]> getVariantAnnotationListBySampleId(Long sampleId);
}
