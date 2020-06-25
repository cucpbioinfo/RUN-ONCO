package run.onco.api.business.message;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import run.onco.api.business.dto.AttachmentDto;
import run.onco.api.business.dto.PatientDto;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class InquiryAttachment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4527109417966052252L;

	private PatientDto patient;
	private AttachmentDto attachment;

	public PatientDto getPatient() {
		return patient;
	}

	public void setPatient(PatientDto patient) {
		this.patient = patient;
	}

	public AttachmentDto getAttachment() {
		return attachment;
	}

	public void setAttachment(AttachmentDto attachment) {
		this.attachment = attachment;
	}

}
