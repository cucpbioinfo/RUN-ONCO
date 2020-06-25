package run.onco.api.business.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import run.onco.api.common.dto.DataItem;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class DiagnosisDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7973115716246464303L;

	private String diagnosisDate;
	private DataItem primaryDiagnosis;
	private String status;
	private Long id;

	private List<PathologicalDto> pathoList;
	private CancerStageDto cancerStage;
	private List<ClinicalCourseDto> clinicalCourseList;

	public String getDiagnosisDate() {
		return diagnosisDate;
	}

	public void setDiagnosisDate(String diagnosisDate) {
		this.diagnosisDate = diagnosisDate;
	}

	public DataItem getPrimaryDiagnosis() {
		return primaryDiagnosis;
	}

	public void setPrimaryDiagnosis(DataItem primaryDiagnosis) {
		this.primaryDiagnosis = primaryDiagnosis;
	}

	public CancerStageDto getCancerStage() {
		return cancerStage;
	}

	public void setCancerStage(CancerStageDto cancerStage) {
		this.cancerStage = cancerStage;
	}

	public List<PathologicalDto> getPathoList() {
		return pathoList;
	}

	public void setPathoList(List<PathologicalDto> pathoList) {
		this.pathoList = pathoList;
	}

	public List<ClinicalCourseDto> getClinicalCourseList() {
		return clinicalCourseList;
	}

	public void setClinicalCourseList(List<ClinicalCourseDto> clinicalCourseList) {
		this.clinicalCourseList = clinicalCourseList;
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
	
}
