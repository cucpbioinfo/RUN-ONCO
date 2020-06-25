package run.onco.api.common.dto;

import java.io.Serializable;
import java.util.List;

public class PageDto<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6657407611573744229L;

	public static final long DEFAULT_OFFSET = 0;
	public static final int DEFAULT_MAX_NO_OF_ROWS = 100;

	private int offset;
	private int limit;
	private long totalElements;
	private List<T> elements;
	private String sortBy;
	private Boolean sortAsc;

	public PageDto() {

	}

	public PageDto(List<T> elements, long totalElements, int offset, int limit) {
		this.elements = elements;
		this.totalElements = totalElements;
		this.offset = offset;
		this.limit = limit;
	}

	public boolean hasMore() {
		return totalElements > offset + limit;
	}

	public boolean hasPrevious() {
		return offset > 0 && totalElements > 0;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public int getOffset() {
		return offset;
	}

	public int getLimit() {
		return limit;
	}

	public List<T> getElements() {
		return elements;
	}

	public String getSortBy() {
		return sortBy;
	}

	public Boolean getSortAsc() {
		return sortAsc;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

}