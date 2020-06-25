package run.onco.api.business.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import run.onco.api.common.dto.DataItem;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class ClinicalCourseDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5866010719785209736L;

	private Long id;
	private DataItem recurrenceStatus;
	private String recurrenceDate;
	private String status;
	private String recordDate;

	public DataItem getRecurrenceStatus() {
		return recurrenceStatus;
	}

	public void setRecurrenceStatus(DataItem recurrenceStatus) {
		this.recurrenceStatus = recurrenceStatus;
	}

	public String getRecurrenceDate() {
		return recurrenceDate;
	}

	public void setRecurrenceDate(String recurrenceDate) {
		this.recurrenceDate = recurrenceDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

}
