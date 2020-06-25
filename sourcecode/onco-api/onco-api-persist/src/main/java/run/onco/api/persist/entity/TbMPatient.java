package run.onco.api.persist.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Entity
@Table(name = "TB_M_PATIENT")
public class TbMPatient implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7834699935002792247L;

	private Long id;
	private TbMRace race;
	private TbMGender gender;
	private Date birthDate;
	private BigDecimal weight;
	private BigDecimal height;
	private Date deathDate;
	private String status;
	private Date createDate;
	private Date updateDate;
	private TbMUser createUser;
	private TbMUser updateUser;
	private String hn;
	private List<TbTSurvivalFollowup> survivalFollowupList;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PATIENT_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "RACE_ID")
	public TbMRace getRace() {
		return race;
	}

	public void setRace(TbMRace race) {
		this.race = race;
	}

	@ManyToOne
	@JoinColumn(name = "GENDER_ID")
	public TbMGender getGender() {
		return gender;
	}

	public void setGender(TbMGender gender) {
		this.gender = gender;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTH_DATE")
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name = "WEIGHT", precision = 5, scale = 2)
	public BigDecimal getWeight() {
		return weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

	@Column(name = "HEIGHT", precision = 5, scale = 2)
	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DEATH_DATE")
	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
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

	@Column(name = "HN", length = 20)
	public String getHn() {
		return hn;
	}

	public void setHn(String hn) {
		this.hn = hn;
	}

	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
	public List<TbTSurvivalFollowup> getSurvivalFollowupList() {
		return survivalFollowupList;
	}

	public void setSurvivalFollowupList(List<TbTSurvivalFollowup> survivalFollowupList) {
		this.survivalFollowupList = survivalFollowupList;
	}

}
