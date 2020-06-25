package run.onco.api.business.message.pagination;

import run.onco.api.common.dto.Paging;

/**
 * 
 * @author Neda Peyrone
 *
 * @param <T>
 */
public class DataTableRequest<T> {

	private T criteria;
	private Paging paging;

	public T getCriteria() {
		return criteria;
	}

	public void setCriteria(T criteria) {
		this.criteria = criteria;
	}

	public Paging getPaging() {
		return paging;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

}
