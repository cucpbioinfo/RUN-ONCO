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
@Table(name = "TB_T_SURVIVAL_FOLLOWUP")
public class TbTSurvivalFollowup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6309682695150072603L;

	private Long id;
	private TbMVitalStatus vitalStatus; // Y,N
	private Date lastFollowupDate; // last follow up date
	private Date recordDate;
	private TbMPatient patient;
	private String status;
	private Date deathDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FOLLOWUP_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "VITAL_STATUS_ID")
	public TbMVitalStatus getVitalStatus() {
		return vitalStatus;
	}

	public void setVitalStatus(TbMVitalStatus vitalStatus) {
		this.vitalStatus = vitalStatus;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_FOLLOWUP_DATE")
	public Date getLastFollowupDate() {
		return lastFollowupDate;
	}

	public void setLastFollowupDate(Date lastFollowupDate) {
		this.lastFollowupDate = lastFollowupDate;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RECORD_DATE")
	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	
	@ManyToOne
	@JoinColumn(name = "PATIENT_ID")
	public TbMPatient getPatient() {
		return patient;
	}

	public void setPatient(TbMPatient patient) {
		this.patient = patient;
	}

	@Column(name = "STATUS", length = 50)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DEATH_DATE")
	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

}
