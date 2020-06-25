package run.onco.api.business.message;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import run.onco.api.common.dto.DataItem;
import run.onco.api.common.dto.Paging;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class InquiryMasterData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3007941024730073403L;

	private DataItem dataItem;
	private Paging paging;

	public DataItem getDataItem() {
		return dataItem;
	}

	public void setDataItem(DataItem dataItem) {
		this.dataItem = dataItem;
	}

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

}
