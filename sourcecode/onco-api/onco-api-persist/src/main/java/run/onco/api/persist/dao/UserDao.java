package run.onco.api.persist.dao;

import run.onco.api.persist.entity.TbMUser;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface UserDao {

	public TbMUser login(final String username, final String password);
	
	public TbMUser getActiveUserById(final Long userId);
	
	public TbMUser getActiveUserByUsername(final String username);
}
