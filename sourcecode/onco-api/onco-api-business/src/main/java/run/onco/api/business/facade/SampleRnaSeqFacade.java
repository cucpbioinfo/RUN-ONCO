package run.onco.api.business.facade;

import java.util.List;

import run.onco.api.business.dto.PatientDto;
import run.onco.api.business.dto.SampleRnaSeqDto;
import run.onco.api.business.message.InquiryOmics;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface SampleRnaSeqFacade {

	public void uploadSamplRnaSeq(SampleRnaSeqDto sampleRnaSeqDto);
	
	public List<SampleRnaSeqDto> getSampleRnaSeqListByPatientId(PatientDto patient);
	
	public List<SampleRnaSeqDto> getSampleRnaSeqListByBiospecimens(InquiryOmics input);
	
	public List<SampleRnaSeqDto> getActiveSampleRnaSeqList();
}
