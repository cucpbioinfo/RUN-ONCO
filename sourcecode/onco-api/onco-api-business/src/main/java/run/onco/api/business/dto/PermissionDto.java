package run.onco.api.business.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @author Neda Peyrone
 *
 */
@JsonInclude(Include.NON_NULL)
public class PermissionDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -660022465262167486L;

	private List<MenuDto> menuGroups;
	private List<String> dynamicContents;

	public PermissionDto() {

	}

	public PermissionDto(List<MenuDto> menuGroups) {
		this.menuGroups = menuGroups;
	}

	public List<MenuDto> getMenuGroups() {
		return menuGroups;
	}

	public void setMenuGroups(List<MenuDto> menuGroups) {
		this.menuGroups = menuGroups;
	}

	public List<String> getDynamicContents() {
		return dynamicContents;
	}

	public void setDynamicContents(List<String> dynamicContents) {
		this.dynamicContents = dynamicContents;
	}

}
