package run.onco.api.rest.controller;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import run.onco.api.business.dto.PathologicalDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.facade.PathoFacade;
import run.onco.api.business.message.InquiryPatho;
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
public class PathoController {
	
	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private PathoFacade pathoFacade;
	
	@ControllerLogging(AppConstants.ACT_CODE_SEARCH_PATIENT_PATHO_BY_PATHO_NO)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_SEARCH_PATIENT_PATHO_BY_PATHO_NO, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<List<PathologicalDto>> searchPathologicalByPathoNo(@RequestBody
			@ValidCondition({
				@MandatoryField("data.patient.id")
			}) ServiceRequest<InquiryPatho> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_SEARCH_PATIENT_PATHO_BY_PATHO_NO);
			List<PathologicalDto> pathoList = pathoFacade.searchPatientPathoByPathoNo(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, pathoList);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_FIND_DUPLICATE_PATHO_NO)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_FIND_DUPLICATE_PATHO_NO, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<Boolean> findDuplicatePathoNo(@RequestBody
			@ValidCondition({
				@MandatoryField("data.pathologyNo")
			}) ServiceRequest<PathologicalDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_FIND_DUPLICATE_PATHO_NO);
			pathoFacade.findDuplicatePathoNo(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
}
