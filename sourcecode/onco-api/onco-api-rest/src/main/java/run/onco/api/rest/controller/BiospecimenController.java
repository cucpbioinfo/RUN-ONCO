package run.onco.api.rest.controller;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import run.onco.api.business.dto.BiospecimenDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.facade.BiospecimenFacade;
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
import run.onco.api.common.utils.ValidationUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */
@RestController
@RequestMapping("/" + AppConstants.SECURITY_CONTEXT)
public class BiospecimenController {

	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private BiospecimenFacade biospecimenFacade;
	
	@ControllerLogging(AppConstants.ACT_CODE_SEARCH_BIOSPECIMEN)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_SEARCH_BIOSPECIMEN, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<DataTableResults<BiospecimenDto>> searchBiospecimen(@RequestBody
			@ValidCondition({
//				@MandatoryField("data.ref")
			}) ServiceRequest<DataTableRequest<BiospecimenDto>> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_SEARCH_BIOSPECIMEN);
			DataTableResults<BiospecimenDto> output = biospecimenFacade.searchBiospecimen(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_GET_BIOSPECIMEN)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_BIOSPECIMEN, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<BiospecimenDto> getBiospecimen(@RequestBody
			@ValidCondition({
				@MandatoryField("data.id")
			}) ServiceRequest<BiospecimenDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_BIOSPECIMEN);
			BiospecimenDto output = biospecimenFacade.getBiospecimenById(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}

	@ControllerLogging(AppConstants.ACT_CODE_DELETE_BIOSPECIMEN)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_DELETE_BIOSPECIMEN, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<BiospecimenDto> deleteBiospecimen(@RequestBody
			@ValidCondition({
				@MandatoryField("data.id")
			}) ServiceRequest<BiospecimenDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_DELETE_BIOSPECIMEN);
			biospecimenFacade.deleteBiospecimen(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_SAVE_BIOSPECIMEN)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_SAVE_BIOSPECIMEN, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<BiospecimenDto> saveClinicalData(@RequestBody
			@ValidCondition({
//				@MandatoryField("data.patient")
			}) ServiceRequest<BiospecimenDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_SAVE_BIOSPECIMEN);
			BiospecimenDto output = biospecimenFacade.saveBiospecimen(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_FIND_DUPLICATE_BIO_REF)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_FIND_DUPLICATE_BIO_REF, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<BiospecimenDto> findDuplicateBioRef(@RequestBody
			@ValidCondition({
				@MandatoryField("data.ref")
			}) ServiceRequest<BiospecimenDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_FIND_DUPLICATE_BIO_REF);
			ValidationUtil.validateOpts(request.getData());
			biospecimenFacade.findDuplicateBioRef(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
}
