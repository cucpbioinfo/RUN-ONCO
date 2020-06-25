package run.onco.api.rest.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import run.onco.api.business.dto.MasterDataDto;
import run.onco.api.business.dto.PatientDto;
import run.onco.api.business.dto.SampleVariantDto;
import run.onco.api.business.dto.VariantDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.facade.VariantCallFacade;
import run.onco.api.business.message.InquiryOmics;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;
import run.onco.api.common.aspect.ControllerLogging;
import run.onco.api.common.aspect.MandatoryField;
import run.onco.api.common.aspect.ValidCondition;
import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.dto.DataItem;
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
public class VariantCallController {
	
	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private VariantCallFacade variantCallFacade;

	@ControllerLogging(AppConstants.ACT_CODE_GET_SAMPLE_SOURCE_LIST)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_SAMPLE_SOURCE_LIST, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<MasterDataDto<DataItem>> getSampleSourceList(@RequestBody
			@ValidCondition({
				@MandatoryField("data.id")
			}) ServiceRequest<PatientDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_SAMPLE_SOURCE_LIST);
			MasterDataDto<DataItem> output = variantCallFacade.getSourceSampleListByPatientId(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_DELETE_VARIANT_CALL)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_DELETE_VARIANT_CALL, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<?> deleteVariantCall(@RequestBody
			@ValidCondition({
//				@MandatoryField("data.ref")
			}) ServiceRequest<SampleVariantDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_DELETE_VARIANT_CALL);
			variantCallFacade.deleteVariantCall(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_GET_VARIANT_CALL)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_VARIANT_CALL, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<?> getVariantAnnotations(@RequestBody
			@ValidCondition({
//				@MandatoryField("data.ref")
			}) ServiceRequest<DataTableRequest<SampleVariantDto>> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_VARIANT_CALL);
			DataTableResults<VariantDto> output = variantCallFacade.getVariantAnnotations(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}

	@ControllerLogging(AppConstants.ACT_CODE_GET_VARIANT_CALLS_BY_SAMPLE_ID)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_VARIANT_CALLS_BY_SAMPLE_ID, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void getVariantCallsBySampleId(@RequestBody
			@ValidCondition({
				@MandatoryField("data.sampleVcfIds")
			}) ServiceRequest<InquiryOmics> request, HttpServletResponse response) throws IOException  {
		
		Header header = request.getHeader();
		final ObjectMapper mapper = new ObjectMapper();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_VARIANT_CALLS_BY_SAMPLE_ID);
			List<VariantDto> variants = variantCallFacade.getVariantAnnotationsBySampleId(request.getData());
			ServiceResponse<List<VariantDto>> results = ServiceUtil.buildResponse(header, MessageCode.SUCCESS, variants);
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			mapper.writeValue(out, results);
		    final byte[] data = out.toByteArray();
		    
		    InputStream is = new ByteArrayInputStream(data);
			response.setContentType("application/json");
			
			int read=0;
		    byte[] bytes = new byte[AppConstants.BYTES_DOWNLOAD];
		    OutputStream os = response.getOutputStream();

		    while((read = is.read(bytes))!= -1) {
		        os.write(bytes, 0, read);
		    }
		    
		    os.flush();
		    os.close();
		    is.close();
		} catch (ServiceException | ValidationException e) {
			ServiceException ex = (ServiceException) e;
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			ServiceResponse<?> jsonObject = ServiceUtil.buildResponse(header, ex.getErrorCode(), ex.getErrorDesc());
			out.print(mapper.writeValueAsString(jsonObject));
			out.flush();
			out.close();
		}
	}

}
