package run.onco.api.business.dto;

import java.io.Serializable;

import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import run.onco.api.common.constants.AppConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class DownloadLogDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8459747828466942435L;

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String affiliate;
	private String isAgree;
	private String token;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Size(max = AppConstants.MAXLEN_FIRST_NAME)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Size(max = AppConstants.MAXLEN_LAST_NAME)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Size(max = AppConstants.MAXLEN_EMAIL)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Size(max = AppConstants.MAXLEN_AFFILIATE)
	public String getAffiliate() {
		return affiliate;
	}

	public void setAffiliate(String affiliate) {
		this.affiliate = affiliate;
	}
	
	@Size(max = AppConstants.MAXLEN_IS_AGREE)
	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	@Size(max = AppConstants.MAXLEN_TOKEN)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
