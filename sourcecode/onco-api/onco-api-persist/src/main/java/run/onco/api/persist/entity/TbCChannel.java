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
@Table(name = "TB_C_CHANNEL")
public class TbCChannel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8828334667311509796L;

	private Integer id;
	private String code;
	private String name;
	private String status;
	private TbCSystem system;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CHANNEL_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CHANNEL_CODE", length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "CHANNEL_NAME", length = 100)
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

	@ManyToOne
	@JoinColumn(name = "SYSTEM_ID")
	public TbCSystem getSystem() {
		return system;
	}

	public void setSystem(TbCSystem system) {
		this.system = system;
	}

}
