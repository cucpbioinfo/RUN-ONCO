package run.onco.component.api.service;

import run.onco.component.api.dto.UserDto;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface DynamicContentService {

	public void createDynamicContent(UserDto user, String component) throws Exception;
	
	public UserDto login(final String username, final String password);
}
