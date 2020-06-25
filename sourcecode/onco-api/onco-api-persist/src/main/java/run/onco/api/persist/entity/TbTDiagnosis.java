package run.onco.api.persist.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Entity
@Table(name = "TB_T_DIAGNOSIS")
public class TbTDiagnosis implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7952702542406744067L;

	private Long id;
	private Date diagnosisDate;
	private String primaryDxCode;
	private String primaryDxName;
	private TbTClinicalData clinicalData;
	private String status;
	private List<TbTPathological> pathoList;
	private TbTCancerStage cancerStage;
	private List<TbTClinicalCourse> clinicalCourseList;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DIAGNOSIS_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DIAGNOSIS_DATE")
	public Date getDiagnosisDate() {
		return diagnosisDate;
	}

	public void setDiagnosisDate(Date diagnosisDate) {
		this.diagnosisDate = diagnosisDate;
	}

	@ManyToOne
	@JoinColumn(name = "CLINICAL_DATA_ID")
	public TbTClinicalData getClinicalData() {
		return clinicalData;
	}

	public void setClinicalData(TbTClinicalData clinicalData) {
		this.clinicalData = clinicalData;
	}

	@Column(name = "STATUS", length = 50)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(mappedBy = "diagnosis", fetch = FetchType.LAZY)
	public List<TbTPathological> getPathoList() {
		return pathoList;
	}

	public void setPathoList(List<TbTPathological> pathoList) {
		this.pathoList = pathoList;
	}

	@OneToOne
	@JoinColumn(name = "CANCER_STAGE_ID")
	public TbTCancerStage getCancerStage() {
		return cancerStage;
	}

	public void setCancerStage(TbTCancerStage cancerStage) {
		this.cancerStage = cancerStage;
	}

	@OneToMany(mappedBy = "diagnosis", fetch = FetchType.LAZY)
	public List<TbTClinicalCourse> getClinicalCourseList() {
		return clinicalCourseList;
	}

	public void setClinicalCourseList(List<TbTClinicalCourse> clinicalCourseList) {
		this.clinicalCourseList = clinicalCourseList;
	}

	@Column(name = "PRIMARY_DX_CODE", length = 20)
	public String getPrimaryDxCode() {
		return primaryDxCode;
	}

	public void setPrimaryDxCode(String primaryDxCode) {
		this.primaryDxCode = primaryDxCode;
	}

	@Column(name = "PRIMARY_DX_NAME", length = 150)
	public String getPrimaryDxName() {
		return primaryDxName;
	}

	public void setPrimaryDxName(String primaryDxName) {
		this.primaryDxName = primaryDxName;
	}
	
}
