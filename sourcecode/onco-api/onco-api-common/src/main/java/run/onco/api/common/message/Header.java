package run.onco.api.common.message;

import java.io.Serializable;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.utils.CheckDateFormat;
import run.onco.api.common.utils.DateUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class Header implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7821082702371677555L;

	private String referenceNo;
	private String transactionDate;
	private String systemCode;
	private String serviceName;
	private String channelId;
	private String clientIp;
	private String token;
	
	@CheckDateFormat(pattern = DateUtil.yyyyMMddHHmmss)
	@Size(min = 14, max = 14)
	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	@Pattern(regexp = AppConstants.REF_NO_PATTERN, flags = Flag.CASE_INSENSITIVE)
	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	@JsonIgnore
	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		String NEW_LINE = System.getProperty("line.separator");

		result.append(this.getClass().getName() + " Object {" + NEW_LINE);
		result.append(" TransactionDate: " + this.transactionDate + NEW_LINE);
		result.append(" ServiceName: " + this.serviceName + NEW_LINE);
		result.append(" SystemCode: " + this.systemCode + NEW_LINE);
		result.append(" ReferenceNo: " + this.referenceNo + NEW_LINE);
		result.append(" ChannelId: " + this.channelId + NEW_LINE);
		result.append(" ClientIp: " + this.clientIp + NEW_LINE);
		result.append(" Token: " + this.token + NEW_LINE);
		result.append("}");

		return result.toString();
	}
}