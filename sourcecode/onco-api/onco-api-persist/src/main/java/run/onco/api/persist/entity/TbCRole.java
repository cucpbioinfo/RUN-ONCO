package run.onco.api.persist.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "TB_C_ROLE")
public class TbCRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2655628817677119680L;

	private Integer id;
	public String code;
	public String name;
	private String status;
	private Integer seqNo;
	private Date createDate;
	private Date updateDate;
	private TbMUser createUser;
	private TbMUser updateUser;
	private List<TbCScreen> screens = new ArrayList<>();
	private List<TbCDynamicContent> dynamicContents = new ArrayList<>();
	private List<TbMUser> users = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ROLE_CODE", length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "ROLE_NAME", length = 100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "STATUS", length = 50)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Column(name = "SEQ_NO")
	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
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
	
	@ManyToMany(cascade = { CascadeType.PERSIST,  CascadeType.MERGE })
	@JoinTable(name = "TB_T_ROLE_SCREEN",
	        joinColumns = @JoinColumn(name = "ROLE_ID"),
	        inverseJoinColumns = @JoinColumn(name = "SCREEN_ID")
	)
    public List<TbCScreen> getScreens() {
		return screens;
	}
	
	public void setScreens(List<TbCScreen> screens) {
		this.screens = screens;
	}
	
	@ManyToMany(cascade = { CascadeType.PERSIST,  CascadeType.MERGE })
	@JoinTable(name = "TB_T_ROLE_DYNA_CONTENT",
	        joinColumns = @JoinColumn(name = "ROLE_ID"),
	        inverseJoinColumns = @JoinColumn(name = "COMP_ID")
	)
	public List<TbCDynamicContent> getDynamicContents() {
		return dynamicContents;
	}

	public void setDynamicContents(List<TbCDynamicContent> dynamicContents) {
		this.dynamicContents = dynamicContents;
	}

	@ManyToMany(mappedBy = "roles")
	public List<TbMUser> getUsers() {
		return users;
	}

	public void setUsers(List<TbMUser> users) {
		this.users = users;
	}
	
}
