package run.onco.api.common.dto;

import java.io.Serializable;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class Paging implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3904611885210778613L;

	private Integer startIndex;
	private Integer fetchSize;
	private Integer endIndex;
	private boolean hasMore;
	private String sortBy;
	private Boolean sortAsc;

	public Paging() {

	}

	public Paging(Integer startIndex, Integer fetchSize) {
		this.startIndex = startIndex;
		this.fetchSize = fetchSize;
	}

	public Paging(Integer startIndex, Integer endIndex, boolean hasMore) {
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.hasMore = hasMore;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(Integer fetchSize) {
		this.fetchSize = fetchSize;
	}

	public Integer getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(Integer endIndex) {
		this.endIndex = endIndex;
	}

	public boolean isHasMore() {
		return hasMore;
	}

	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public Boolean getSortAsc() {
		return sortAsc;
	}

	public void setSortAsc(Boolean sortAsc) {
		this.sortAsc = sortAsc;
	}

}
