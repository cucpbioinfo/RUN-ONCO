package run.onco.api.business.message;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import run.onco.api.business.dto.BiospecimenDto;
import run.onco.api.business.dto.PatientDto;
import run.onco.api.common.dto.DataItem;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class InquiryOmics implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4948013072744965151L;

	private PatientDto patient;
	private List<BiospecimenDto> biospecimenList;
	private Long[] sampleVcfIds;
	private Long[] sampleRnaSeqIds;
	private DataItem sequenceType;
	private DataItem cancerGeneGroup;

	// Provide fields for interacting with STRINGdb of protein-protein interaction networks.
	private List<String> geneList;
	private List<String> evidenceTypes;

	public PatientDto getPatient() {
		return patient;
	}

	public void setPatient(PatientDto patient) {
		this.patient = patient;
	}

	public List<BiospecimenDto> getBiospecimenList() {
		return biospecimenList;
	}

	public void setBiospecimenList(List<BiospecimenDto> biospecimenList) {
		this.biospecimenList = biospecimenList;
	}

	public List<String> getGeneList() {
		return geneList;
	}

	public void setGeneList(List<String> geneList) {
		this.geneList = geneList;
	}

	public List<String> getEvidenceTypes() {
		return evidenceTypes;
	}

	public void setEvidenceTypes(List<String> evidenceTypes) {
		this.evidenceTypes = evidenceTypes;
	}

	public DataItem getSequenceType() {
		return sequenceType;
	}

	public void setSequenceType(DataItem sequenceType) {
		this.sequenceType = sequenceType;
	}

	public Long[] getSampleVcfIds() {
		return sampleVcfIds;
	}

	public void setSampleVcfIds(Long[] sampleVcfIds) {
		this.sampleVcfIds = sampleVcfIds;
	}

	public Long[] getSampleRnaSeqIds() {
		return sampleRnaSeqIds;
	}

	public void setSampleRnaSeqIds(Long[] sampleRnaSeqIds) {
		this.sampleRnaSeqIds = sampleRnaSeqIds;
	}

	public DataItem getCancerGeneGroup() {
		return cancerGeneGroup;
	}

	public void setCancerGeneGroup(DataItem cancerGeneGroup) {
		this.cancerGeneGroup = cancerGeneGroup;
	}
	
}
