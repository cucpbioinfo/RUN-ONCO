package run.onco.api.business.facade;

import java.util.List;

import run.onco.api.business.dto.PatientDto;
import run.onco.api.business.dto.SampleVariantDto;
import run.onco.api.business.message.InquiryOmics;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface SampleVcfFacade {

	public List<SampleVariantDto> getSampleVcfListByPatientId(PatientDto patient);
	
	public void uploadSampleVcf(SampleVariantDto sampleVariantDto);
	
	public List<SampleVariantDto> getSampleVcfListByBiospecimenIds(InquiryOmics inquiryOmics);
}
