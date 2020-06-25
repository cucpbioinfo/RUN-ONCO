package run.onco.api.persist.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Entity
@Table(name = "TB_T_CANCER_STAGE")
public class TbTCancerStage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4544987459860772008L;

	private Long id;
	private Integer ajcc;
	private Integer stageType; // Type of stage
	private Integer cTStage; // cT stage (only neoadjuvant setting)
	private Integer cTSubstage;
	private Integer cNStage; // cN stage (only neoadjuvant setting)
	private Integer cNSubstage;
	private Integer pTStage;
	private Integer pTSubstage;
	private Integer pNStage;
	private Integer pNSubstage;
	private Integer ypTStage;
	private Integer ypTSubstage;
	private Integer ypNStage;
	private Integer ypNSubstage;
	private Integer mStage;
	private Integer mSubstage;
	private Integer clinicalStage; // clinical stage
	private Integer pathologicalStage; // pathological stage
	private Integer pathoSubstage;
	private TbTDiagnosis diagnosis;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "STAGE_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "AJCC")
	public Integer getAjcc() {
		return ajcc;
	}

	public void setAjcc(Integer ajcc) {
		this.ajcc = ajcc;
	}

	@Column(name = "STAGE_TYPE")
	public Integer getStageType() {
		return stageType;
	}

	public void setStageType(Integer stageType) {
		this.stageType = stageType;
	}

	@Column(name = "CT_STAGE")
	public Integer getcTStage() {
		return cTStage;
	}

	public void setcTStage(Integer cTStage) {
		this.cTStage = cTStage;
	}

	@Column(name = "CN_STAGE")
	public Integer getcNStage() {
		return cNStage;
	}

	public void setcNStage(Integer cNStage) {
		this.cNStage = cNStage;
	}

	@Column(name = "PT_STAGE")
	public Integer getpTStage() {
		return pTStage;
	}

	public void setpTStage(Integer pTStage) {
		this.pTStage = pTStage;
	}

	@Column(name = "PN_STAGE")
	public Integer getpNStage() {
		return pNStage;
	}

	public void setpNStage(Integer pNStage) {
		this.pNStage = pNStage;
	}

	@Column(name = "YPT_STAGE")
	public Integer getYpTStage() {
		return ypTStage;
	}

	public void setYpTStage(Integer ypTStage) {
		this.ypTStage = ypTStage;
	}

	@Column(name = "YPN_STAGE")
	public Integer getYpNStage() {
		return ypNStage;
	}

	public void setYpNStage(Integer ypNStage) {
		this.ypNStage = ypNStage;
	}

	@Column(name = "M_STAGE")
	public Integer getmStage() {
		return mStage;
	}

	public void setmStage(Integer mStage) {
		this.mStage = mStage;
	}

	@Column(name = "CLINICAL_STAGE")
	public Integer getClinicalStage() {
		return clinicalStage;
	}

	public void setClinicalStage(Integer clinicalStage) {
		this.clinicalStage = clinicalStage;
	}

	@Column(name = "PATHOLOGICAL_STAGE")
	public Integer getPathologicalStage() {
		return pathologicalStage;
	}

	public void setPathologicalStage(Integer pathologicalStage) {
		this.pathologicalStage = pathologicalStage;
	}

	@OneToOne
	@JoinColumn(name = "DIAGNOSIS_ID")
	public TbTDiagnosis getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(TbTDiagnosis diagnosis) {
		this.diagnosis = diagnosis;
	}
	
	@Column(name = "CT_SUBSTAGE")
	public Integer getcTSubstage() {
		return cTSubstage;
	}

	public void setcTSubstage(Integer cTSubstage) {
		this.cTSubstage = cTSubstage;
	}

	@Column(name = "CN_SUBSTAGE")
	public Integer getcNSubstage() {
		return cNSubstage;
	}

	public void setcNSubstage(Integer cNSubstage) {
		this.cNSubstage = cNSubstage;
	}

	@Column(name = "PT_SUBSTAGE")
	public Integer getpTSubstage() {
		return pTSubstage;
	}

	public void setpTSubstage(Integer pTSubstage) {
		this.pTSubstage = pTSubstage;
	}

	@Column(name = "PN_SUBSTAGE")
	public Integer getpNSubstage() {
		return pNSubstage;
	}

	public void setpNSubstage(Integer pNSubstage) {
		this.pNSubstage = pNSubstage;
	}

	@Column(name = "YPT_SUBSTAGE")
	public Integer getYpTSubstage() {
		return ypTSubstage;
	}

	public void setYpTSubstage(Integer ypTSubstage) {
		this.ypTSubstage = ypTSubstage;
	}

	@Column(name = "YPN_SUBSTAGE")
	public Integer getYpNSubstage() {
		return ypNSubstage;
	}

	public void setYpNSubstage(Integer ypNSubstage) {
		this.ypNSubstage = ypNSubstage;
	}

	@Column(name = "M_SUBSTAGE")
	public Integer getmSubstage() {
		return mSubstage;
	}

	public void setmSubstage(Integer mSubstage) {
		this.mSubstage = mSubstage;
	}

	@Column(name = "PATHO_SUBSTAGE")
	public Integer getPathoSubstage() {
		return pathoSubstage;
	}

	public void setPathoSubstage(Integer pathoSubstage) {
		this.pathoSubstage = pathoSubstage;
	}

	
}
