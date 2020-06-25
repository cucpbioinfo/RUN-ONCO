package run.onco.api.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "TB_T_BIOSPECIMEN")
public class TbTBiospecimen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4051371164149971901L;

	private Long id;
	private TbMSampleType sampleType;
	private TbMBloodType bloodType;
	private TbMTissueType tissueType;
	private TbMCellType cellType;
	private TbMCellLineType cellLineType;
	private TbMFreezeMethod freezeMethod;
	private TbMPreserveMethod preserveMethod;
	private Date collectSampleDate;
	private BigDecimal initialWeight;
	private Date freezeDate;
	private String status;
	private Date createDate;
	private Date updateDate;
	private TbMUser createUser;
	private TbMUser updateUser;
	private String refNo;
	private TbMPatient patient;
	private TbTPathological pathological;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BIOSPECIMEN_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "SAMPLE_TYPE_ID")
	public TbMSampleType getSampleType() {
		return sampleType;
	}

	public void setSampleType(TbMSampleType sampleType) {
		this.sampleType = sampleType;
	}

	@ManyToOne
	@JoinColumn(name = "BLOOD_TYPE_ID")
	public TbMBloodType getBloodType() {
		return bloodType;
	}

	public void setBloodType(TbMBloodType bloodType) {
		this.bloodType = bloodType;
	}

	@ManyToOne
	@JoinColumn(name = "TISSUE_TYPE_ID")
	public TbMTissueType getTissueType() {
		return tissueType;
	}

	public void setTissueType(TbMTissueType tissueType) {
		this.tissueType = tissueType;
	}

	@ManyToOne
	@JoinColumn(name = "CELL_TYPE_ID")
	public TbMCellType getCellType() {
		return cellType;
	}

	public void setCellType(TbMCellType cellType) {
		this.cellType = cellType;
	}

	@ManyToOne
	@JoinColumn(name = "CELL_LINE_TYPE_ID")
	public TbMCellLineType getCellLineType() {
		return cellLineType;
	}

	public void setCellLineType(TbMCellLineType cellLineType) {
		this.cellLineType = cellLineType;
	}

	@ManyToOne
	@JoinColumn(name = "FREEZE_METHOD_ID")
	public TbMFreezeMethod getFreezeMethod() {
		return freezeMethod;
	}

	public void setFreezeMethod(TbMFreezeMethod freezeMethod) {
		this.freezeMethod = freezeMethod;
	}

	@ManyToOne
	@JoinColumn(name = "PRESERVE_METHOD_ID")
	public TbMPreserveMethod getPreserveMethod() {
		return preserveMethod;
	}

	public void setPreserveMethod(TbMPreserveMethod preserveMethod) {
		this.preserveMethod = preserveMethod;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "COLLECT_SAMPLE_DATE")
	public Date getCollectSampleDate() {
		return collectSampleDate;
	}

	public void setCollectSampleDate(Date collectSampleDate) {
		this.collectSampleDate = collectSampleDate;
	}

	@Column(name = "INITIAL_WEIGHT", precision = 7, scale = 2)
	public BigDecimal getInitialWeight() {
		return initialWeight;
	}

	public void setInitialWeight(BigDecimal initialWeight) {
		this.initialWeight = initialWeight;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FREEZE_DATE")
	public Date getFreezeDate() {
		return freezeDate;
	}

	public void setFreezeDate(Date freezeDate) {
		this.freezeDate = freezeDate;
	}

	@Column(name = "STATUS", length = 50)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@ManyToOne
	@JoinColumn(name = "CREATE_USER_ID")
	public TbMUser getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TbMUser createUser) {
		this.createUser = createUser;
	}

	@ManyToOne
	@JoinColumn(name = "UPDATE_USER_ID")
	public TbMUser getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(TbMUser updateUser) {
		this.updateUser = updateUser;
	}

	@Column(name = "REF_NO", length = 20)
	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	@ManyToOne
	@JoinColumn(name = "PATIENT_ID")
	public TbMPatient getPatient() {
		return patient;
	}

	public void setPatient(TbMPatient patient) {
		this.patient = patient;
	}

	@ManyToOne
	@JoinColumn(name = "PATHOLOGICAL_ID")
	public TbTPathological getPathological() {
		return pathological;
	}

	public void setPathological(TbTPathological pathological) {
		this.pathological = pathological;
	}

}
