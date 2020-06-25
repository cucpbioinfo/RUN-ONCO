package run.onco.api.business.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class SampleRnaSeqDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4421544507575678807L;

	private AttachmentDto attachment;
	private Long id;
	private PatientDto patient;
	private BiospecimenDto biospecimen;
	private String status;
	private Long userId;

	public AttachmentDto getAttachment() {
		return attachment;
	}

	public void setAttachment(AttachmentDto attachment) {
		this.attachment = attachment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PatientDto getPatient() {
		return patient;
	}

	public void setPatient(PatientDto patient) {
		this.patient = patient;
	}

	public BiospecimenDto getBiospecimen() {
		return biospecimen;
	}

	public void setBiospecimen(BiospecimenDto biospecimen) {
		this.biospecimen = biospecimen;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
