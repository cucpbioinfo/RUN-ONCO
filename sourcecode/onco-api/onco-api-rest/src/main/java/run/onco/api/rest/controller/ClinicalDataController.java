package run.onco.api.rest.controller;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import run.onco.api.business.dto.ClinicalDataDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.facade.ClinicalDataFacade;
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
public class ClinicalDataController {

	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private ClinicalDataFacade clinicalDataFacade;
	
	@ControllerLogging(AppConstants.ACT_CODE_SAVE_CLINICAL_DATA)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_SAVE_CLINICAL_DATA, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<ClinicalDataDto> saveClinicalData(@RequestBody
			@ValidCondition({
				@MandatoryField("data.patient")
			}) ServiceRequest<ClinicalDataDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_SAVE_CLINICAL_DATA);
			ClinicalDataDto output = clinicalDataFacade.saveClinicalData(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_SEARCH_CLINICAL_DATA)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_SEARCH_CLINICAL_DATA, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<DataTableResults<ClinicalDataDto>> searchClinicalData(@RequestBody
			@ValidCondition({
				@MandatoryField("data.criteria"),
				@MandatoryField("data.paging")
			}) ServiceRequest<DataTableRequest<ClinicalDataDto>> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_SEARCH_CLINICAL_DATA);
			DataTableResults<ClinicalDataDto> output = clinicalDataFacade.searchClinicalData(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_GET_CLINICAL_DATA)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_CLINICAL_DATA, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<ClinicalDataDto> getClinicalData(@RequestBody
			@ValidCondition({
				@MandatoryField("data.id")
			}) ServiceRequest<ClinicalDataDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_CLINICAL_DATA);
			ClinicalDataDto output = clinicalDataFacade.getClinicalDataById(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}

	@ControllerLogging(AppConstants.ACT_CODE_DELETE_CLINICAL_DATA)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_DELETE_CLINICAL_DATA, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<ClinicalDataDto> deleteClinicalData(@RequestBody
			@ValidCondition({
				@MandatoryField("data.ref")
			}) ServiceRequest<ClinicalDataDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_DELETE_CLINICAL_DATA);
			clinicalDataFacade.deleteClinicalData(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
}
