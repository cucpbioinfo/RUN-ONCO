package run.onco.api.persist.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Entity
@Table(name = "TB_T_SAMPLE_RNA_SEQ")
public class TbTSampleRnaSeq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1696370520350099496L;

	private Long id;
	private TbTBiospecimen biospecimen;
	private TbTAttachment attachment;
	private TbMPatient patient;
	private Date createDate;
	private TbMUser createUser;
	private List<TbTRnaSeq> rnaSeqList;
	private String status;
	private Date importDataDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SAMPLE_RNA_SEQ_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name = "BIOSPECIMEN_ID")
	public TbTBiospecimen getBiospecimen() {
		return biospecimen;
	}

	public void setBiospecimen(TbTBiospecimen biospecimen) {
		this.biospecimen = biospecimen;
	}

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "ATTACHMENT_ID")
	public TbTAttachment getAttachment() {
		return attachment;
	}

	public void setAttachment(TbTAttachment attachment) {
		this.attachment = attachment;
	}

	@ManyToOne
	@JoinColumn(name = "PATIENT_ID")
	public TbMPatient getPatient() {
		return patient;
	}

	public void setPatient(TbMPatient patient) {
		this.patient = patient;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@ManyToOne
	@JoinColumn(name = "CREATE_USER_ID")
	public TbMUser getCreateUser() {
		return createUser;
	}

	public void setCreateUser(TbMUser createUser) {
		this.createUser = createUser;
	}

	@OneToMany(mappedBy = "sampleRnaSeq", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	public List<TbTRnaSeq> getRnaSeqList() {
		return rnaSeqList;
	}

	public void setRnaSeqList(List<TbTRnaSeq> rnaSeqList) {
		this.rnaSeqList = rnaSeqList;
	}

	@Column(name = "STATUS", length = 50)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "IMPORT_DATA_DATE")
	public Date getImportDataDate() {
		return importDataDate;
	}

	public void setImportDataDate(Date importDataDate) {
		this.importDataDate = importDataDate;
	}

}