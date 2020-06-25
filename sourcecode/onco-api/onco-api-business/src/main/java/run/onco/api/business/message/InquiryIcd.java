package run.onco.api.business.message;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import run.onco.api.business.dto.IcdDto;
import run.onco.api.common.dto.Paging;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class InquiryIcd implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6431952446491986236L;

	private IcdDto icd;
	private Paging paging;

	public IcdDto getIcd() {
		return icd;
	}

	public void setIcd(IcdDto icd) {
		this.icd = icd;
	}

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

}
