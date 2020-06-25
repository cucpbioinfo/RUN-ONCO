package run.onco.api.rest.controller;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import run.onco.api.business.dto.VisualDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.facade.VisualizationFacade;
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
public class VisualizationController {

	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private VisualizationFacade visualFacade;
	
	@ControllerLogging(AppConstants.ACT_CODE_GEN_VIS_R_WATERFALL)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GEN_VIS_R_WATERFALL, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<VisualDto> genVisRWaterFall(@RequestBody
			@ValidCondition({
			}) ServiceRequest<VisualDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GEN_VIS_R_WATERFALL);
			
			Double x = 0.7;
			int y = 5;
			int z = 3;
			
			VisualDto input = request.getData();
			VisualDto output = visualFacade.executeR(input.getFileName(), x, y, z);
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
}
