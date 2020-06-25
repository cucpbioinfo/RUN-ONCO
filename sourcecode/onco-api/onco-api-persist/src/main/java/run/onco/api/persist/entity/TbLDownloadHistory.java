package run.onco.api.persist.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Entity
@Table(name = "TB_L_DOWNLOAD_HIST")
public class TbLDownloadHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 683275696590927124L;

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String affiliate;
	private String isAgree;
	private Date createDate;
	private Date updateDate;
	private String scVersion;
	private Integer numOfDownloads;
	private String downloadRef;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "HIST_ID")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "FIRST_NAME", length = 150)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "LAST_NAME", length = 150)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "AFFILIATE")
	public String getAffiliate() {
		return affiliate;
	}

	public void setAffiliate(String affiliate) {
		this.affiliate = affiliate;
	}

	@Column(name = "IS_AGREE")
	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
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

	@Column(name = "SC_VERSION", length = 50)
	public String getScVersion() {
		return scVersion;
	}

	public void setScVersion(String scVersion) {
		this.scVersion = scVersion;
	}

	@Column(name = "NUM_OF_DOWNLOADS")
	public Integer getNumOfDownloads() {
		return numOfDownloads;
	}

	public void setNumOfDownloads(Integer numOfDownloads) {
		this.numOfDownloads = numOfDownloads;
	}

	@Column(name = "DOWNLOAD_REF")
	public String getDownloadRef() {
		return downloadRef;
	}

	public void setDownloadRef(String downloadRef) {
		this.downloadRef = downloadRef;
	}

}
