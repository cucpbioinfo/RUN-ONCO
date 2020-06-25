package run.onco.api.business.facade;

import java.util.List;

import run.onco.api.business.dto.OmicsDto;
import run.onco.api.business.dto.RnaSeqDto;
import run.onco.api.business.dto.SampleRnaSeqDto;
import run.onco.api.business.message.InquiryOmics;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface RnaSeqFacade {

//	public void uploadSamplRnaSeq(SampleRnaSeqDto input);
	
	public void deleteRnaSeq(SampleRnaSeqDto input);
	
//	public List<SampleRnaSeqDto> getSampleRnaSeqListByPatientId(PatientDto patient);
	
	public DataTableResults<RnaSeqDto> getRnaSeqList(DataTableRequest<SampleRnaSeqDto> req);
	
	public List<OmicsDto<RnaSeqDto>> getRnaSeqListForClustergrammer(InquiryOmics input);
	
//	public List<SampleRnaSeqDto> getSampleRnaSeqListByBiospecimens(InquiryOmics input);
//	
//	public List<SampleRnaSeqDto> getActiveSampleRnaSeqList();
}
