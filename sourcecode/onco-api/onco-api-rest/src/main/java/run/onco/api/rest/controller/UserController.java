package run.onco.api.rest.controller;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import run.onco.api.business.dto.MasterDataDto;
import run.onco.api.business.dto.RoleDto;
import run.onco.api.business.dto.UserDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.facade.UserFacade;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;
import run.onco.api.common.aspect.ControllerLogging;
import run.onco.api.common.aspect.MandatoryField;
import run.onco.api.common.aspect.ValidCondition;
import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.message.Header;
import run.onco.api.common.message.ServiceRequest;
import run.onco.api.common.message.ServiceResponse;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.common.utils.ServiceUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */
@RestController
@RequestMapping("/" + AppConstants.SECURITY_CONTEXT)
public class UserController {

	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private UserFacade userFacade;
	
	@ControllerLogging(AppConstants.ACT_CODE_SEARCH_USER)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_SEARCH_USER, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<DataTableResults<UserDto>> searchUser(@RequestBody
			@ValidCondition({
				@MandatoryField("data.criteria"),
				@MandatoryField("data.paging")
			}) ServiceRequest<DataTableRequest<UserDto>> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_SEARCH_USER);
			DataTableResults<UserDto> output = userFacade.searchUser(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_GET_ACTIVE_ROLES)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_ACTIVE_ROLES, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<MasterDataDto<RoleDto>> getActiveRoles(@RequestBody
			@ValidCondition({
			}) ServiceRequest<?> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_ACTIVE_ROLES);
			MasterDataDto<RoleDto> roles = userFacade.getActiveRoles();
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, roles);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_FIND_DUPLICATE_USERNAME)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_FIND_DUPLICATE_USERNAME, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<?> findDupeUsername(@RequestBody
			@ValidCondition({
			}) ServiceRequest<UserDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_FIND_DUPLICATE_USERNAME);
			userFacade.findDuplicateUsername(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_SAVE_USER)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_SAVE_USER, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<?> saveUser(@RequestBody
			@ValidCondition({
			}) ServiceRequest<UserDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_SAVE_USER);
			userFacade.saveUser(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_DELETE_USER)
	@RequestMapping(value = "/" + AppConstants.SECURITY_CONTEXT + "/" + AppConstants.ACT_CODE_DELETE_USER, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<?> deleteUser(@RequestBody
			@ValidCondition({
			}) ServiceRequest<UserDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_DELETE_USER);
			userFacade.deleteUser(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
}
