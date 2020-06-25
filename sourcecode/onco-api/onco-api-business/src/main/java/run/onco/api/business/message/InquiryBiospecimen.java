package run.onco.api.business.message;

import java.io.Serializable;

import run.onco.api.business.dto.BiospecimenDto;
import run.onco.api.common.dto.Paging;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class InquiryBiospecimen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5429203272756665832L;

	private BiospecimenDto biospecimenDto;
	private Paging paging;

	public BiospecimenDto getBiospecimenDto() {
		return biospecimenDto;
	}

	public void setBiospecimenDto(BiospecimenDto biospecimenDto) {
		this.biospecimenDto = biospecimenDto;
	}

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

}
