package run.onco.api.business.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class ClinicalDataDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 836503566117736924L;

	private String ref;
	private Long id;
	private String status;
	private PatientDto patient;
	private List<DiagnosisDto> diagnosisList;

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PatientDto getPatient() {
		return patient;
	}

	public void setPatient(PatientDto patient) {
		this.patient = patient;
	}

	public List<DiagnosisDto> getDiagnosisList() {
		return diagnosisList;
	}

	public void setDiagnosisList(List<DiagnosisDto> diagnosisList) {
		this.diagnosisList = diagnosisList;
	}

}
