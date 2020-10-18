package run.onco.api.business.facade;

import run.onco.api.business.dto.MasterDataDto;
import run.onco.api.business.dto.RoleDto;
import run.onco.api.business.dto.UserDto;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface UserFacade {

	public DataTableResults<UserDto> searchUser(DataTableRequest<UserDto> req);
	
	public MasterDataDto<RoleDto> getActiveRoles();
	
	public void findDuplicateUsername(UserDto userDto);
	
	public void saveUser(UserDto userDto);
	
	public void deleteUser(UserDto userDto);
}
