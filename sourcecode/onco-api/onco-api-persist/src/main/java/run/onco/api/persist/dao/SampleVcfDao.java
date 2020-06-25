package run.onco.api.persist.dao;

import java.util.List;
import java.util.Map;

import run.onco.api.persist.entity.TbTSampleVariant;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface SampleVcfDao {

	public List<TbTSampleVariant> getVariantCallListByPatientId(Long patientId);
	
	public TbTSampleVariant getVariantCallByBiospecimenId(final Long biospecimenId, final Integer seqTypeId);
	
	public List<TbTSampleVariant> listPaginatedSampleVcfUsingQuery(Map<String, Object> criteria);
	
	public int countSampleVcfUsingQuery(Map<String, Object> criteria);
	
	public List<TbTSampleVariant> getSampleVcfListByBiospecimenIds(final String seqTypeCode, final Long[] ids);
	
	public boolean findDuplicateUploadSampleVcf(String seqTypeCode, Long biospecimenId);
}
