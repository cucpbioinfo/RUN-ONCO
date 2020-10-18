package run.onco.api.business.facade.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import run.onco.api.business.dto.MasterDataDto;
import run.onco.api.business.dto.RoleDto;
import run.onco.api.business.dto.UserDto;
import run.onco.api.business.facade.UserFacade;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;
import run.onco.api.common.dto.Paging;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.exception.ValidationException;
import run.onco.api.common.utils.DateUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.entity.TbCRole;
import run.onco.api.persist.entity.TbMUser;
import run.onco.api.service.UserService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class UserFacadeImpl implements UserFacade {

	private final static Logger logger = Logger.getLogger(UserFacadeImpl.class);
	
	@Autowired
	private UserService userService;

	@Override
	public DataTableResults<UserDto> searchUser(DataTableRequest<UserDto> req) {
		
		try {
			UserDto searchCriteria = req.getCriteria();
			Paging paging = req.getPaging();
			
			logger.info("I:--START--:--Search User--");
			
			HashMap<String, Object> criteria = new HashMap<>();
			criteria.put("username", searchCriteria.getUsername());
			criteria.put("status", searchCriteria.getStatus());
			
			int paginatedCount = userService.countUserUsingQuery(criteria, paging);
			
			List<TbMUser> users = userService.listPaginatedUserUsingQuery(criteria, paging);
			
			if (users == null || users.size() == 0) {
				logger.info(String.format("O:--FAIL--:--Search User--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc()));
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<UserDto> dataList = new ArrayList<UserDto>();
			
			for(TbMUser user : users) {
				UserDto userDto = new UserDto();
				userDto.setUsername(user.getUsername());
				userDto.setFirstName(user.getFirstName());
				userDto.setLastName(user.getLastName());
				dataList.add(userDto);
			}
			
			logger.info(String.format("O:--SUCCESS--:--Search User--:paginatedCount/%s", paginatedCount));
			DataTableResults<UserDto> results = new DataTableResults<UserDto>(dataList, paginatedCount, 0, 15);
			return results;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Search User--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Search User--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Search User--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public MasterDataDto<RoleDto> getActiveRoles() {
		
		try {
			List<TbCRole> roles = userService.getActiveRoles();
			
			if (roles == null || roles.size() == 0) {
				logger.info(String.format("O:--FAIL--:--Get Active Roles--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc()));
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<RoleDto> roleDtoList = new ArrayList<>();
			
			for(TbCRole role : roles) {
				RoleDto roleDto = new RoleDto();
				roleDto.setRoleId(role.getId());
				roleDto.setCode(role.getCode());
				roleDto.setName(role.getName());
				roleDtoList.add(roleDto);
			}
			
			logger.info(String.format("O:--SUCCESS--:--Get Active Roles--:roles size/%s", roleDtoList.size()));
			
			MasterDataDto<RoleDto> masterDataDto = new MasterDataDto<RoleDto>(roleDtoList);
			return masterDataDto;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get Active Roles--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get Active Roles--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void findDuplicateUsername(UserDto userDto) {
		
		try {
			Long id = userDto.getId();
			String username = userDto.getUsername();
			
			logger.info(String.format("I:--START--:--Find Duplicate Username--:username/%s:id/%s", username, id));
			boolean isDuplicate = userService.findDuplicateUsername(username, id);
			
			if (isDuplicate) {
				throw new ServiceException(MessageCode.ERROR_DUPLICATED.getCode(), "Username already exists in the system.");
			}
			
			logger.info("O:--SUCCESS--:--Find Duplicate Username--");
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Find Duplicate Username--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Find Duplicate Username--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void saveUser(UserDto userDto) {
		
		try {
			logger.info("I:--START--:--Save User--");
			
			TbMUser requestedUser = userService.getActiveUserById(userDto.getRequestedUserId());
			if (requestedUser == null) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Requested user does not exist.");
			}
			
			TbMUser user = null;
			
			if (userDto.getId() != null) {
				user = userService.getUserById(userDto.getId());
				
				if (user == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "User does not exist.");
				}
				
				user.setUpdateUser(requestedUser);
				user.setUpdateDate(DateUtil.getCurrentDate());
			} else {
				user = new TbMUser();
				user.setUsername(userDto.getUsername());
				user.setCreateUser(requestedUser);
				user.setCreateDate(DateUtil.getCurrentDate());
			}
			
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setPassword(userDto.getPassword());
			user.setStatus(userDto.getStatus());
			userService.saveUser(user);
			logger.info("O:--SUCCESS--:--Save User--");
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Save User--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Save User--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void deleteUser(UserDto userDto) {
		
		try {
			logger.info(String.format("I:--START--:--Delete User--:id/%s", userDto.getId()));
			
			if (userDto.getId() != null) {
				TbMUser user = userService.getUserById(userDto.getId());
				
				if (user == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "User does not exist.");
				}
				
				userService.deleteUser(user);
			}
			
			logger.info("O:--SUCCESS--:--Delete User--");
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Delete User--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Delete User--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur :\n", ex);
			logger.debug(String.format("O:--FAIL--:--Delete User--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
	
}
