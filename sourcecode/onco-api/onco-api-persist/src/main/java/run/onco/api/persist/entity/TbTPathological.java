package run.onco.api.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Entity
@Table(name = "TB_T_PATHOLOGICAL")
public class TbTPathological implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2076546543332861727L;

	private Long id;
	private String pathologyNo; // pathology number
	private Date tissueReportDate; // tissue pathology report date
	private String resectionBiopsySite; // site of resection or biopsy
	private TbMTissueProcedure procedure; // Procedure (tissue assessment)
	private TbMTissueType tissueType; // type of tissue
//	private TbMIcdO morphology; // morphology
	private String morphologyCode;
	private String morphologyName;
	private Integer histologicGrade; // histologic grade
	private TbTDiagnosis diagnosis;
	private String status;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PATHOLOGICAL_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PATHO_NO", length=20)
	public String getPathologyNo() {
		return pathologyNo;
	}

	public void setPathologyNo(String pathologyNo) {
		this.pathologyNo = pathologyNo;
	}
	
	@Temporal(TemporalType.DATE)
	@Column(name = "TISSUE_REPORT_DATE")
	public Date getTissueReportDate() {
		return tissueReportDate;
	}

	public void setTissueReportDate(Date tissueReportDate) {
		this.tissueReportDate = tissueReportDate;
	}

	@Column(name = "RESECTION_BIOPSY_SITE")
	public String getResectionBiopsySite() {
		return resectionBiopsySite;
	}

	public void setResectionBiopsySite(String resectionBiopsySite) {
		this.resectionBiopsySite = resectionBiopsySite;
	}

	@ManyToOne
	@JoinColumn(name = "TISSUE_PROCEDURE_ID")
	public TbMTissueProcedure getProcedure() {
		return procedure;
	}

	public void setProcedure(TbMTissueProcedure procedure) {
		this.procedure = procedure;
	}

	@ManyToOne
	@JoinColumn(name = "TISSUE_TYPE_ID")
	public TbMTissueType getTissueType() {
		return tissueType;
	}

	public void setTissueType(TbMTissueType tissueType) {
		this.tissueType = tissueType;
	}

	@Column(name = "HISTOLOGIC_GRADE")
	public Integer getHistologicGrade() {
		return histologicGrade;
	}

	public void setHistologicGrade(Integer histologicGrade) {
		this.histologicGrade = histologicGrade;
	}
	
//	@ManyToOne
//	@JoinColumn(name = "MORPHOLOGY_ID")
//	public TbMIcdO getMorphology() {
//		return morphology;
//	}
//
//	public void setMorphology(TbMIcdO morphology) {
//		this.morphology = morphology;
//	}
	
	@Column(name = "MORPHOLOGY_CODE", length = 20)
	public String getMorphologyCode() {
		return morphologyCode;
	}

	public void setMorphologyCode(String morphologyCode) {
		this.morphologyCode = morphologyCode;
	}

	@Column(name = "MORPHOLOGY_NAME", length = 150)
	public String getMorphologyName() {
		return morphologyName;
	}

	public void setMorphologyName(String morphologyName) {
		this.morphologyName = morphologyName;
	}

	@ManyToOne
	@JoinColumn(name = "DIAGNOSIS_ID")
	public TbTDiagnosis getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(TbTDiagnosis diagnosis) {
		this.diagnosis = diagnosis;
	}

	@Column(name = "STATUS", length = 50)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
