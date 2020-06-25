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
public class SurvivalFollowupDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5037227115104780927L;

	private Long id;
	private DataItem vitalStatus;
	private String lastFollowupDate;
	private String deathDate;
	private String recordDate;
	private String status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DataItem getVitalStatus() {
		return vitalStatus;
	}

	public void setVitalStatus(DataItem vitalStatus) {
		this.vitalStatus = vitalStatus;
	}

	public String getLastFollowupDate() {
		return lastFollowupDate;
	}

	public void setLastFollowupDate(String lastFollowupDate) {
		this.lastFollowupDate = lastFollowupDate;
	}

	public String getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(String deathDate) {
		this.deathDate = deathDate;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
