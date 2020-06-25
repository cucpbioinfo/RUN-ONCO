package run.onco.api.business.dto;

import java.io.Serializable;
import java.math.BigDecimal;
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
public class PatientDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 156280941555197024L;

	private DataItem race;
	private DataItem gender;
	private String birthDate;
	private BigDecimal weight;
	private BigDecimal height;
	private String hn;
	private String status;
	private Long id;
	private List<SurvivalFollowupDto> survivalFollowupList;
	private Long userId;

	public DataItem getRace() {
		return race;
	}

	public void setRace(DataItem race) {
		this.race = race;
	}

	public DataItem getGender() {
		return gender;
	}

	public void setGender(DataItem gender) {
		this.gender = gender;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public String getHn() {
		return hn;
	}

	public void setHn(String hn) {
		this.hn = hn;
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

	public List<SurvivalFollowupDto> getSurvivalFollowupList() {
		return survivalFollowupList;
	}

	public void setSurvivalFollowupList(List<SurvivalFollowupDto> survivalFollowupList) {
		this.survivalFollowupList = survivalFollowupList;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
