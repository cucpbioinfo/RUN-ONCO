package run.onco.api.business.message;

import java.io.Serializable;

import run.onco.api.business.dto.PathologicalDto;
import run.onco.api.business.dto.PatientDto;
import run.onco.api.common.dto.Paging;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class InquiryPatho implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7472131814770687433L;

	private PatientDto patient;
	private PathologicalDto patho;
	private Paging paging;

	public PathologicalDto getPatho() {
		return patho;
	}

	public void setPatho(PathologicalDto patho) {
		this.patho = patho;
	}

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	public PatientDto getPatient() {
		return patient;
	}

	public void setPatient(PatientDto patient) {
		this.patient = patient;
	}

}
