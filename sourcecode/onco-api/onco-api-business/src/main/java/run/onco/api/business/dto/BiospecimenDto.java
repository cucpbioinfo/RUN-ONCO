package run.onco.api.business.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.dto.DataItem;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class BiospecimenDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8604709711124044122L;

	private DataItem sampleType;
	private DataItem bloodType;
	private DataItem tissueType;
	private DataItem cellType;
	private DataItem cellLineType;
	private DataItem freezeMethod;
	private DataItem preserveMethod;
	private String collectSampleDate;
	private String freezeDate;
	private String initialWeight;
	private String ref;
	private String status;
	private Long id;
	private PatientDto patient;
	private PathologicalDto pathological;

	public DataItem getSampleType() {
		return sampleType;
	}

	public void setSampleType(DataItem sampleType) {
		this.sampleType = sampleType;
	}

	public DataItem getBloodType() {
		return bloodType;
	}

	public void setBloodType(DataItem bloodType) {
		this.bloodType = bloodType;
	}

	public DataItem getTissueType() {
		return tissueType;
	}

	public void setTissueType(DataItem tissueType) {
		this.tissueType = tissueType;
	}

	public DataItem getCellType() {
		return cellType;
	}

	public void setCellType(DataItem cellType) {
		this.cellType = cellType;
	}

	public DataItem getCellLineType() {
		return cellLineType;
	}

	public void setCellLineType(DataItem cellLineType) {
		this.cellLineType = cellLineType;
	}

	public DataItem getFreezeMethod() {
		return freezeMethod;
	}

	public void setFreezeMethod(DataItem freezeMethod) {
		this.freezeMethod = freezeMethod;
	}

	public DataItem getPreserveMethod() {
		return preserveMethod;
	}

	public void setPreserveMethod(DataItem preserveMethod) {
		this.preserveMethod = preserveMethod;
	}

	public String getCollectSampleDate() {
		return collectSampleDate;
	}

	public void setCollectSampleDate(String collectSampleDate) {
		this.collectSampleDate = collectSampleDate;
	}

	public String getFreezeDate() {
		return freezeDate;
	}

	public void setFreezeDate(String freezeDate) {
		this.freezeDate = freezeDate;
	}

	public String getInitialWeight() {
		return initialWeight;
	}

	public void setInitialWeight(String initialWeight) {
		this.initialWeight = initialWeight;
	}

	@Size(max = AppConstants.MAXLEN_BIO_REF)
	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public PathologicalDto getPathological() {
		return pathological;
	}

	public void setPathological(PathologicalDto pathological) {
		this.pathological = pathological;
	}

}
