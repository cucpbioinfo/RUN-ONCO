package run.onco.batch.api.controller;

import javax.validation.ValidationException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/" + AppConstants.SECURITY_CONTEXT)
public class DataEntryController {

	@ControllerLogging(AppConstants.ACT_CODE_ADD_NEW_ENTRY)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_ADD_NEW_ENTRY, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<?> addNewEntry(@RequestBody
			@ValidCondition({
			}) ServiceRequest<?> request) {
		
		Header header = request.getHeader();
		
		try {
//			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_CANCER_GENE_GROUPS);
//			MasterDataDto<DataItem> cancerGeneGroups = masterDataFacade.getActiveCancerGeneGroups();
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, null);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
}
