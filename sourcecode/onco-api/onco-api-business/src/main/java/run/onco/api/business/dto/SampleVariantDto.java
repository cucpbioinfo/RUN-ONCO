package run.onco.api.business.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import run.onco.api.common.dto.DataItem;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class SampleVariantDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7483539313485009896L;

	private DataItem sequenceType;
	private AttachmentDto attachment;
	private Long id;
	private VariantDto variant;
	private PatientDto patient;
	private BiospecimenDto biospecimen;
	private String status;
	private Long requestedUserId;

	public DataItem getSequenceType() {
		return sequenceType;
	}

	public void setSequenceType(DataItem sequenceType) {
		this.sequenceType = sequenceType;
	}

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

	public VariantDto getVariant() {
		return variant;
	}

	public void setVariant(VariantDto variant) {
		this.variant = variant;
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
	
	public Long getRequestedUserId() {
		return requestedUserId;
	}

	public void setRequestedUserId(Long requestedUserId) {
		this.requestedUserId = requestedUserId;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String NEW_LINE = System.getProperty("line.separator");

		result.append(this.getClass().getName() + " Object {" + NEW_LINE);
		
		if (this.sequenceType != null) {
			result.append(" sequenceTypeCode: " + this.sequenceType.getCode() + NEW_LINE);
			result.append(" sequenceTypeName: " + this.sequenceType.getName() + NEW_LINE);
		}
		
		if (this.attachment != null) {
			result.append(" fileName: " + this.attachment.getFileName() + NEW_LINE);
			result.append(" contentType: " + this.attachment.getContentType() + NEW_LINE);
			result.append(" fileContent: " + this.attachment.getFileContent() + NEW_LINE);
		}
		
		result.append("}");

		return result.toString();
	}
}
