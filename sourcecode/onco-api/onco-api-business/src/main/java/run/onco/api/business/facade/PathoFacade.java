package run.onco.api.business.facade;

import java.util.List;

import run.onco.api.business.dto.PathologicalDto;
import run.onco.api.business.message.InquiryPatho;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface PathoFacade {

	public List<PathologicalDto> searchPatientPathoByPathoNo(InquiryPatho searchCriteria);
	
	public void findDuplicatePathoNo(PathologicalDto pathoDto);
}
