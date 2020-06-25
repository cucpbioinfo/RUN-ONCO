package run.onco.api.rest.controller;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import run.onco.api.business.dto.PatientDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.facade.PatientFacade;
import run.onco.api.business.message.InquiryPatient;
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
public class PatientController {

	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private PatientFacade patientFacade;
	
	@ControllerLogging(AppConstants.ACT_CODE_SEARCH_PATIENT_BY_REF)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_SEARCH_PATIENT_BY_REF, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<List<PatientDto>> searchPatientByRef(@RequestBody
			@ValidCondition({
//				@MandatoryField("data.patient.hn")
			}) ServiceRequest<InquiryPatient> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_SEARCH_PATIENT_BY_REF);
			List<PatientDto> patientList = patientFacade.searchPatientByRef(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, patientList);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_SEARCH_PATIENT)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_SEARCH_PATIENT, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<DataTableResults<PatientDto>> searchPatient(@RequestBody
			@ValidCondition({
				@MandatoryField("data.criteria"),
				@MandatoryField("data.paging")
			}) ServiceRequest<DataTableRequest<PatientDto>> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_SEARCH_PATIENT);
			DataTableResults<PatientDto> output = patientFacade.searchPatient(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_SAVE_PATIENT)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_SAVE_PATIENT, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<PatientDto> savePatient(@RequestBody
			@ValidCondition({
//				@MandatoryField("data.patient")
			}) ServiceRequest<PatientDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_SAVE_PATIENT);
			PatientDto output = patientFacade.savePatient(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_DELETE_PATIENT)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_DELETE_PATIENT, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<PatientDto> deletePatient(@RequestBody
			@ValidCondition({
				@MandatoryField("data.id")
			}) ServiceRequest<PatientDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_DELETE_PATIENT);
			patientFacade.deletePatient(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_GET_PATIENT)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_PATIENT, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<PatientDto> getPatient(@RequestBody
			@ValidCondition({
				@MandatoryField("data.id")
			}) ServiceRequest<PatientDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_PATIENT);
			PatientDto output = patientFacade.getPatientById(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_FIND_DUPLICATE_HN)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_FIND_DUPLICATE_HN, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<Boolean> findDuplicateHn(@RequestBody
			@ValidCondition({
				@MandatoryField("data.hn")
			}) ServiceRequest<PatientDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_FIND_DUPLICATE_HN);
			patientFacade.findDuplicateHn(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
}
