package run.onco.api.business.facade;

import java.util.List;

import run.onco.api.business.dto.PatientDto;
import run.onco.api.business.message.InquiryPatient;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface PatientFacade {

	public List<PatientDto> searchPatientByRef(final InquiryPatient searchCriteria);
	
	public DataTableResults<PatientDto> searchPatient(DataTableRequest<PatientDto> req);
	
	public PatientDto savePatient(PatientDto patientDto);
	
	public void deletePatient(PatientDto patientDto);
	
	public PatientDto getPatientById(PatientDto patientDto);
	
	public void findDuplicateHn(PatientDto patientDto);
}
