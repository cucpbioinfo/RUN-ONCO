package run.onco.api.business.message;

import java.io.Serializable;

import run.onco.api.business.dto.PatientDto;
import run.onco.api.common.dto.Paging;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class InquiryPatient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8696086327928171368L;

	private PatientDto patient;
	private Paging paging;

	public PatientDto getPatient() {
		return patient;
	}

	public void setPatient(PatientDto patient) {
		this.patient = patient;
	}

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

}
