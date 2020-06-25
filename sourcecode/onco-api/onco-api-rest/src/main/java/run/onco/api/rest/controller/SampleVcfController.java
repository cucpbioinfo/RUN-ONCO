package run.onco.api.rest.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.ValidationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import run.onco.api.business.dto.AttachmentDto;
import run.onco.api.business.dto.PatientDto;
import run.onco.api.business.dto.SampleVariantDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.facade.SampleVcfFacade;
import run.onco.api.business.message.InquiryOmics;
import run.onco.api.common.aspect.ControllerLogging;
import run.onco.api.common.aspect.MandatoryField;
import run.onco.api.common.aspect.ValidCondition;
import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.message.Header;
import run.onco.api.common.message.ServiceRequest;
import run.onco.api.common.message.ServiceResponse;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.common.utils.ServiceUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */
@RestController
@RequestMapping("/" + AppConstants.SECURITY_CONTEXT)
public class SampleVcfController {

private final static Logger logger = Logger.getLogger(SampleVcfController.class);
	
	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private SampleVcfFacade sampleVcfFacade;
	
	@SuppressWarnings("resource")
	@ControllerLogging(AppConstants.ACT_CODE_UPLOAD_SAMPLE_VCF)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_UPLOAD_SAMPLE_VCF, method = { RequestMethod.POST })
	public void uploadSampleVcf(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		ObjectMapper mapper = new ObjectMapper();

		Header header = null;
		Part responseHeader = AppUtil.getPartByName(request, "responseHeader");
		if (responseHeader != null) {
			header = mapper.readValue(responseHeader.getInputStream(), Header.class);
		}
		
		try {
			SampleVariantDto sampleVariantDto = null;
			Part responseData = AppUtil.getPartByName(request, "responseData");
			if (responseData != null) {
				sampleVariantDto = mapper.readValue(responseData.getInputStream(), SampleVariantDto.class);
			}
			
			File temp = File.createTempFile("upl-vcf", ".tmp");
			FileOutputStream file = new FileOutputStream(temp);
			Part importFile = AppUtil.getPartByName(request, "importFile");
			
			if (importFile != null) {
				
				logger.info("O:--Get File Size--:fileSize/" + importFile.getSize());
				logger.info("O:--Get Max File Upload Size--:fileSize/" + AppConstants.MAX_FILE_UPLOAD_SIZE);

				if (importFile.getSize() > AppConstants.MAX_FILE_UPLOAD_SIZE) {
					String errorDesc = String.format("File size limit exceeded %s", AppUtil.getReadableFileSizeString(AppConstants.MAX_FILE_UPLOAD_SIZE, true));
					throw new ServiceException(MessageCode.ERROR_INVALID_FORMAT.getCode(), errorDesc);
				}
				
				String fileName = importFile.getSubmittedFileName();
				
				AttachmentDto attachmentDto = new AttachmentDto();
				attachmentDto.setFileName(fileName);
				attachmentDto.setFileSize(importFile.getSize());
				attachmentDto.setTempFilePath(temp.getAbsolutePath());
				attachmentDto.setStatus(AppConstants.STATUS_ACTIVE);
				attachmentDto.setContentType(importFile.getContentType());
				sampleVariantDto.setAttachment(attachmentDto);
				
				logger.info(String.format("O:--Get File Info--:detail/%s", attachmentDto.toString()));
				
				int length = (int) importFile.getSize();
				byte[] bytes = new byte[AppConstants.BYTES_UPLOAD];
				
				InputStream is = importFile.getInputStream();
				
				while (length > 0) {
					int read = is.read(bytes, 0, Math.min(bytes.length, length));
				    if (read > 0) {
				         file.write(bytes, 0, read);
				         length -= read;
				    }
				       
				    if (read < 0)
				    		throw new IOException("end reached before all data read"); 
				}
				
				sampleVcfFacade.uploadSampleVcf(sampleVariantDto);
			}
			
			file.close();
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			ServiceResponse<?> jsonObject = ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
			out.print(mapper.writeValueAsString(jsonObject));
			out.flush();
			out.close();

		} catch (Exception e) {
			ServiceException ex = (ServiceException) e;
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			ServiceResponse<?> jsonObject = ServiceUtil.buildResponse(header, ex.getErrorCode(), ex.getErrorDesc());
			out.print(mapper.writeValueAsString(jsonObject));
			out.flush();
			out.close();
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_GET_SAMPLE_VCF_LIST_BY_BIOSPECIMENS)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_SAMPLE_VCF_LIST_BY_BIOSPECIMENS, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<?> getSampleVcfListByBiospecimens(@RequestBody
			@ValidCondition({
//				@MandatoryField("data.ref")
			}) ServiceRequest<InquiryOmics> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_SAMPLE_VCF_LIST_BY_BIOSPECIMENS);
			List<SampleVariantDto> sampleVariantDtoList = sampleVcfFacade.getSampleVcfListByBiospecimenIds(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, sampleVariantDtoList);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_GET_SAMPLE_VCF_LIST_BY_PATIENT)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_SAMPLE_VCF_LIST_BY_PATIENT, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<List<SampleVariantDto>> getSampleVcfListByPatientId(@RequestBody
			@ValidCondition({
				@MandatoryField("data.id")
			}) ServiceRequest<PatientDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_SAMPLE_VCF_LIST_BY_PATIENT);
			List<SampleVariantDto> variantCallList = sampleVcfFacade.getSampleVcfListByPatientId(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, variantCallList);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
}
