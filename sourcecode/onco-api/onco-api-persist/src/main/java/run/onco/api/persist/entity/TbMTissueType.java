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
@Table(name = "TB_M_TISSUE_TYPE")
public class TbMTissueType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5989192039606081585L;

	private Integer id;
	private String code;
	private String name;
	private String type;
	private String status;
	private Date createDate;
	private Date updateDate;
	private TbMUser createUser;
	private TbMUser updateUser;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TISSUE_TYPE_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "TISSUE_TYPE_CODE", length = 2)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "TISSUE_TYPE_NAME", length = 30)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "DATA_TYPE", length = 1)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

}
