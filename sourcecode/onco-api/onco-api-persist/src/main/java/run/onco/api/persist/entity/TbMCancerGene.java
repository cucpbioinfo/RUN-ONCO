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
import javax.persistence.UniqueConstraint;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Entity
@Table(name = "TB_M_CANCER_GENE",  uniqueConstraints={
		@UniqueConstraint(columnNames={"GRP_GENE_ID", "GENE_NAME"})
})
public class TbMCancerGene implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7990002021117834465L;

	private Integer id;
	private String geneName;
	private Date createDate;
	private Date updateDate;
	private TbMUser createUser;
	private TbMUser updateUser;
	private TbMCancerGeneGroup cancerGeneGroup;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "GENE_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "GENE_NAME", length = 250)
	public String getGeneName() {
		return geneName;
	}

	public void setGeneName(String geneName) {
		this.geneName = geneName;
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
	@JoinColumn(name = "GRP_GENE_ID")
	public TbMCancerGeneGroup getCancerGeneGroup() {
		return cancerGeneGroup;
	}

	public void setCancerGeneGroup(TbMCancerGeneGroup cancerGeneGroup) {
		this.cancerGeneGroup = cancerGeneGroup;
	}

}
