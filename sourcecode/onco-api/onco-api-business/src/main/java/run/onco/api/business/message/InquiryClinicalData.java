package run.onco.api.business.message;

import java.io.Serializable;

import run.onco.api.business.dto.ClinicalDataDto;
import run.onco.api.common.dto.Paging;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class InquiryClinicalData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2270910758523388320L;

	private ClinicalDataDto criteria;
	private Paging paging;

	public ClinicalDataDto getCriteria() {
		return criteria;
	}

	public void setCriteria(ClinicalDataDto criteria) {
		this.criteria = criteria;
	}

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

}
