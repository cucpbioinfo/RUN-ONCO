package run.onco.api.persist.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author neda
 *
 */
@Entity
@Table(name = "TB_C_SYSTEM")
public class TbCSystem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1215855079473002710L;

	private Integer id;
	private String code;
	private String name;
	private String status;
	private List<TbCChannel> channelList;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SYSTEM_ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "SYSTEM_CODE", length = 50)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "SYSTEM_NAME", length = 100)
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

	@OneToMany(mappedBy = "system", fetch = FetchType.LAZY)
	public List<TbCChannel> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<TbCChannel> channelList) {
		this.channelList = channelList;
	}

}
