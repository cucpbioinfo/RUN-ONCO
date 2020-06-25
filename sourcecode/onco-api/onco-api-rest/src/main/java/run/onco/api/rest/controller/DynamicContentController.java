package run.onco.api.rest.controller;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import run.onco.api.business.dto.DynamicContentDto;
import run.onco.api.business.dto.MasterDataDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.facade.DynamicContentFacade;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;
import run.onco.api.common.aspect.ControllerLogging;
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
public class DynamicContentController {
	
	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private DynamicContentFacade dynamicComponentFacade;

	@ControllerLogging(AppConstants.ACT_CODE_GET_DYNAMIC_CONTENT_LIST)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_DYNAMIC_CONTENT_LIST, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<MasterDataDto<DynamicContentDto>> getDynamicContentList(@RequestBody
			@ValidCondition({
			}) ServiceRequest<?> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_DYNAMIC_CONTENT_LIST);
			MasterDataDto<DynamicContentDto> componentList = dynamicComponentFacade.getActiveDynamicContentList();
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, componentList);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_SEARCH_DYNAMIC_CONTENT)
	@RequestMapping(value = "/" + AppConstants.SECURITY_CONTEXT + "/" + AppConstants.ACT_CODE_SEARCH_DYNAMIC_CONTENT, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<DataTableResults<DynamicContentDto>> searchDynamicContent(@RequestBody
			@ValidCondition({
			}) ServiceRequest<DataTableRequest<DynamicContentDto>> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_SEARCH_DYNAMIC_CONTENT);
			DataTableResults<DynamicContentDto> output = dynamicComponentFacade.searchDynamicContent(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_FIND_DUPLICATE_COMPONENT_NAME)
	@RequestMapping(value = "/" + AppConstants.SECURITY_CONTEXT + "/" + AppConstants.ACT_CODE_FIND_DUPLICATE_COMPONENT_NAME, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<?> findDupeComponentName(@RequestBody
			@ValidCondition({
			}) ServiceRequest<DynamicContentDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_FIND_DUPLICATE_COMPONENT_NAME);
			dynamicComponentFacade.findDuplicateComponentName(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_SAVE_DYNAMIC_CONTENT)
	@RequestMapping(value = "/" + AppConstants.SECURITY_CONTEXT + "/" + AppConstants.ACT_CODE_SAVE_DYNAMIC_CONTENT, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<?> saveDynamicContent(@RequestBody
			@ValidCondition({
			}) ServiceRequest<DynamicContentDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_SAVE_DYNAMIC_CONTENT);
			dynamicComponentFacade.saveDynamicContent(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
}
