package run.onco.api.service;

import java.util.List;
import java.util.Map;

import run.onco.api.persist.entity.TbTSampleVariant;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface SampleVcfService {
	
	public List<TbTSampleVariant> getSampleVcfListByPatientId(Long patientId);
	
	public TbTSampleVariant getSampleVariantById(final Long sampleId);

	public void uploadSampleVcf(TbTSampleVariant sampleVariant);
	
	public List<TbTSampleVariant> listPaginatedSampleVcfUsingQuery(Map<String, Object> criteria);
	
	public int countSampleVcfUsingQuery(Map<String, Object> criteria);
	
	public void updateSampleVcfStatus(final Long sampleId, final String status);
	
	public List<TbTSampleVariant> getSampleVcfListByBiospecimenIds(final String seqTypeCode, final Long[] ids);
	
	public boolean findDuplicateUploadSampleVcf(final String seqTypeCode, final Long biospecimenId);
}
