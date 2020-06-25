package run.onco.api.business.dto;

import java.io.Serializable;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class ColumnDefDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2298920899829294559L;

	private String colName;
	private Integer width;
	private String sortBy;
	private String styleClass;

	public ColumnDefDto() {

	}

	public ColumnDefDto(String colName, Integer width, String sortBy, String styleClass) {
		this.colName = colName;
		this.width = width;
		this.sortBy = sortBy;
		this.styleClass = styleClass;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

}
