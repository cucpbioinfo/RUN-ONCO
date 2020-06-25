package run.onco.api.business.facade;

import java.util.List;

import run.onco.api.business.dto.MasterDataDto;
import run.onco.api.business.dto.PatientDto;
import run.onco.api.business.dto.SampleVariantDto;
import run.onco.api.business.dto.VariantDto;
import run.onco.api.business.message.InquiryOmics;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;
import run.onco.api.common.dto.DataItem;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface VariantCallFacade {

	public MasterDataDto<DataItem> getSourceSampleListByPatientId(PatientDto patient);
	
	public void deleteVariantCall(SampleVariantDto sampleVariantDto);
	
	public DataTableResults<VariantDto> getVariantAnnotations(DataTableRequest<SampleVariantDto> req);
	
	public DataTableResults<Object[]> getVariantComparison(DataTableRequest<InquiryOmics> req);
	
	public List<VariantDto> getVariantAnnotationsBySampleId(InquiryOmics inquiryOmics);
	
}