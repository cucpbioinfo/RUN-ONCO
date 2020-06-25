package run.onco.api.rest.controller;

import java.util.LinkedHashMap;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import run.onco.api.business.dto.ActionableVariantDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.facade.DrugFacade;
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
public class DrugController {

	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private DrugFacade drugFacade;

	@SuppressWarnings("unchecked")
	@ControllerLogging(AppConstants.ACT_CODE_GET_ACTIONABLE_VARIATNT_BY_GENE)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_ACTIONABLE_VARIATNT_BY_GENE, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<List<ActionableVariantDto>> getVariantCallListByRef(@RequestBody
			@ValidCondition({
				@MandatoryField("data.geneName")
			}) ServiceRequest<?> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_ACTIONABLE_VARIATNT_BY_GENE);
			LinkedHashMap<String, String> lhm = (LinkedHashMap<String, String>)request.getData();
			List<ActionableVariantDto> actionableVariantList = drugFacade.getActionableVariantByGeneName(lhm.get("geneName"));
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, actionableVariantList);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
}
