package run.onco.api.business.message.pagination;

import java.util.List;

import run.onco.api.business.dto.ColumnDefDto;

/**
 * 
 * @author Neda Peyrone
 *
 * @param <T>
 */
public class DataTableResults<T> {

	public static final long DEFAULT_OFFSET = 0;
	public static final int DEFAULT_MAX_NO_OF_ROWS = 100;

	private int offset;
	private int limit;
	private long totalElements;
	private List<T> elements;
	private String sortBy;
	private Boolean sortAsc;
	private List<ColumnDefDto> columns;

	public DataTableResults() {

	}

	public DataTableResults(List<T> elements, long totalElements, int offset, int limit) {
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

	public List<ColumnDefDto> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnDefDto> columns) {
		this.columns = columns;
	}
}
