package run.onco.api.service;

import java.util.List;
import java.util.Map;

import run.onco.api.common.dto.Paging;
import run.onco.api.persist.entity.TbCRole;
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
	
	public List<TbMUser> listPaginatedUserUsingQuery(final Map<String, Object> criteria, Paging paging);
	
	public int countUserUsingQuery(Map<String, Object> criteria, Paging paging);
	
	public List<TbCRole> getActiveRoles();
	
	public boolean findDuplicateUsername(final String username, final Long id);
	
	public void saveUser(TbMUser user);
	
	public TbMUser getUserById(final Long userId);
	
	public void deleteUser(TbMUser user);
}
