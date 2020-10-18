package run.onco.api.business.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class DataVersionDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5950772016312767560L;

	private Integer id;
	private String version;
	private String dataDate;
	private String type;
	private String status;
	private String updateUser;
	private String updateDate;
	private String isDefault;

	private Long requestedUserId;
	private AttachmentDto attachment;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public Long getRequestedUserId() {
		return requestedUserId;
	}

	public void setRequestedUserId(Long requestedUserId) {
		this.requestedUserId = requestedUserId;
	}

	public AttachmentDto getAttachment() {
		return attachment;
	}

	public void setAttachment(AttachmentDto attachment) {
		this.attachment = attachment;
	}

}
