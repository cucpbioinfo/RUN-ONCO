package run.onco.api.persist.dao;

import java.util.List;
import java.util.Map;

import run.onco.api.common.dto.Paging;
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
	
	public List<TbMUser> listPaginatedUserUsingQuery(final Map<String, Object> criteria, Paging paging);
	
	public int countUserUsingQuery(Map<String, Object> criteria, Paging paging);
	
	public boolean findDuplicateUsername(final String username, final Long id);
}
