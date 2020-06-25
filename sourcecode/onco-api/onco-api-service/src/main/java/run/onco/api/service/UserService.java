package run.onco.api.service;

import run.onco.api.persist.entity.TbMUser;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface UserService {

	public TbMUser login(final String username, final String password);
	
	public TbMUser getActiveUserById(final Long userId);
	
	public TbMUser getUserByUsername(final String username);
}
