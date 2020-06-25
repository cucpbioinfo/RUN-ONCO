package run.onco.api.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "TB_T_CLINICAL_COURSE")
public class TbTClinicalCourse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2773357299812099162L;

	private Long id;
	private Integer recurrenceStatus; // recurrence status : Y,N
	private Date recurrenceDate; // recurrence date
	private TbTDiagnosis diagnosis;
	private String status;
	private Date recordDate;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COURSE_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "RECURRENCE_STATUS")
	public Integer getRecurrenceStatus() {
		return recurrenceStatus;
	}

	public void setRecurrenceStatus(Integer recurrenceStatus) {
		this.recurrenceStatus = recurrenceStatus;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "RECURRENCE_DATE")
	public Date getRecurrenceDate() {
		return recurrenceDate;
	}

	public void setRecurrenceDate(Date recurrenceDate) {
		this.recurrenceDate = recurrenceDate;
	}
	
	@OneToOne
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

	@Temporal(TemporalType.DATE)
	@Column(name = "RECORD_DATE")
	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	
}
