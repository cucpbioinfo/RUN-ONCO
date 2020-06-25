package run.onco.api.rest.controller;

import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import run.onco.api.business.dto.OmicsDto;
import run.onco.api.business.dto.RnaSeqDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.facade.RnaSeqFacade;
import run.onco.api.business.facade.VariantCallFacade;
import run.onco.api.business.message.InquiryOmics;
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
@RequestMapping("/" + AppConstants.SECURITY_CONTEXT)
public class DataProcessController {

	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private VariantCallFacade variantCallFacade;
	
	@Autowired
	private RnaSeqFacade rnaSeqFacade;
	
	@ControllerLogging(AppConstants.ACT_CODE_GET_RNA_SEQ_LIST_FOR_CLUSTERGRAMMER)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_RNA_SEQ_LIST_FOR_CLUSTERGRAMMER, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<?> getRnaSeqListForClustergrammer(@RequestBody
			@ValidCondition({
//				@MandatoryField("data.ref")
			}) ServiceRequest<InquiryOmics> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_RNA_SEQ_LIST_FOR_CLUSTERGRAMMER);
			List<OmicsDto<RnaSeqDto>> sampleOmicsDtoList = rnaSeqFacade.getRnaSeqListForClustergrammer(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, sampleOmicsDtoList);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_GET_VARIANT_COMPARISON)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_VARIANT_COMPARISON, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<?> getVariantComparison(@RequestBody
			@ValidCondition({
//				@MandatoryField("data.ref")
			}) ServiceRequest<DataTableRequest<InquiryOmics>> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_VARIANT_COMPARISON);
			DataTableResults<Object[]> dt = variantCallFacade.getVariantComparison(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, dt);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
}
