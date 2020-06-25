package run.onco.api.persist.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "TB_C_SCREEN")
public class TbCScreen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7529014683312244481L;

	private Integer id;
	private String code;
	private String name;
	private String routerLink;
	private String status;
	private Date createDate;
	private Date updateDate;
	private TbMUser createUser;
	private TbMUser updateUser;
	private TbCMenu menu;
	private Integer seqNo;
    private List<TbCRole> roles = new ArrayList<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SCREEN_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "SCREEN_CODE", length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "SCREEN_NAME", length = 200)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ROUTER_LINK", length = 150)
	public String getRouterLink() {
		return routerLink;
	}

	public void setRouterLink(String routerLink) {
		this.routerLink = routerLink;
	}

	@Column(name = "STATUS")
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
	
	@ManyToOne
	@JoinColumn(name = "MENU_ID")
	public TbCMenu getMenu() {
		return menu;
	}

	public void setMenu(TbCMenu menu) {
		this.menu = menu;
	}
	
	@Column(name = "SEQ_NO")
	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	@ManyToMany(mappedBy = "screens")
	public List<TbCRole> getRoles() {
		return roles;
	}

	public void setRoles(List<TbCRole> roles) {
		this.roles = roles;
	}
}
