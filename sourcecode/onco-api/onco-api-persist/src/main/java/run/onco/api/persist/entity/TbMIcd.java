package run.onco.api.persist.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Entity
@Table(name = "TB_M_ICD") // WHO | Internation Classification of Diseases (ICD)
public class TbMIcd implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -425434995143809482L;

	private Integer id;
	private String code;
	private String name;
	private String description;
	private TbCDataVersion dataVersion;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ICD_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ICD_CODE", length = 20)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "ICD_NAME", length = 150)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "ICD_DESC", length = 500)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToOne
	@JoinColumn(name = "DATA_VER_ID")
	public TbCDataVersion getDataVersion() {
		return dataVersion;
	}

	public void setDataVersion(TbCDataVersion dataVersion) {
		this.dataVersion = dataVersion;
	}
	
}
