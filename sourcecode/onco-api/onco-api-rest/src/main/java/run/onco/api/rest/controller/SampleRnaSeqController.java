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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import run.onco.api.business.dto.AttachmentDto;
import run.onco.api.business.dto.PatientDto;
import run.onco.api.business.dto.SampleRnaSeqDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.facade.SampleRnaSeqFacade;
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
public class SampleRnaSeqController {

	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private SampleRnaSeqFacade sampleRnaSeqFacade;

	@SuppressWarnings("resource")
	@ControllerLogging(AppConstants.ACT_CODE_UPLOAD_SAMPLE_RNA_SEQ)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_UPLOAD_SAMPLE_RNA_SEQ, method = { RequestMethod.POST })
	public void uploadSampleRnaSeq(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		ObjectMapper mapper = new ObjectMapper();

		Header header = null;
		Part responseHeader = AppUtil.getPartByName(request, "responseHeader");
		if (responseHeader != null) {
			header = mapper.readValue(responseHeader.getInputStream(), Header.class);
		}
		
		try {
			SampleRnaSeqDto sampleRnaSeqDto = null;
			Part responseData = AppUtil.getPartByName(request, "responseData");
			if (responseData != null) {
				sampleRnaSeqDto = mapper.readValue(responseData.getInputStream(), SampleRnaSeqDto.class);
			}
			
			File temp = File.createTempFile("upl-rna-seq", ".tmp");
			FileOutputStream file = new FileOutputStream(temp);
//			List<Part> fileParts = request.getParts().stream().filter(part -> "importFile".equals(part.getName())).collect(Collectors.toList());
			Part importFile = AppUtil.getPartByName(request, "importFile");
			
			if (importFile != null) {
				
				if (importFile.getSize() > AppConstants.MAX_FILE_UPLOAD_SIZE) {
					String errorDesc = String.format("File size limit exceeded %s", AppUtil.getReadableFileSizeString(AppConstants.MAX_FILE_UPLOAD_SIZE, true));
					throw new ServiceException(MessageCode.ERROR_INVALID_FORMAT.getCode(), errorDesc);
				}
				
				String fileName = importFile.getSubmittedFileName();
				System.out.println("-----> fileName : " + fileName);
				
				AttachmentDto attachmentDto = new AttachmentDto();
				attachmentDto.setFileName(fileName);
				attachmentDto.setFileSize(importFile.getSize());
				attachmentDto.setTempFilePath(temp.getAbsolutePath());
				attachmentDto.setStatus(AppConstants.STATUS_ACTIVE);
				attachmentDto.setContentType(importFile.getContentType());
				sampleRnaSeqDto.setAttachment(attachmentDto);
				
				int length = (int) importFile.getSize();
				byte[] bytes = new byte[512];
				
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
				
				sampleRnaSeqFacade.uploadSamplRnaSeq(sampleRnaSeqDto);
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
	
	@ControllerLogging(AppConstants.ACT_CODE_GET_SAMPLE_RNA_SEQ_LIST)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_SAMPLE_RNA_SEQ_LIST, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<List<SampleRnaSeqDto>> getSampleRnaSeqListByPatientId(@RequestBody
			@ValidCondition({
				@MandatoryField("data.id")
			}) ServiceRequest<PatientDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_SAMPLE_RNA_SEQ_LIST);
			List<SampleRnaSeqDto> sampleRnaSeqList = sampleRnaSeqFacade.getSampleRnaSeqListByPatientId(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, sampleRnaSeqList);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_GET_SAMPLE_RNA_SEQ_LIST_BY_BIOSPECIMENS)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_SAMPLE_RNA_SEQ_LIST_BY_BIOSPECIMENS, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<?> getSampleRnaSeqListByBiospecimens(@RequestBody
			@ValidCondition({
//				@MandatoryField("data.ref")
			}) ServiceRequest<InquiryOmics> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_SAMPLE_RNA_SEQ_LIST_BY_BIOSPECIMENS);
			List<SampleRnaSeqDto> sampleRnaSeqList = sampleRnaSeqFacade.getSampleRnaSeqListByBiospecimens(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, sampleRnaSeqList);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_GET_ACTIVE_SAMPLE_RNA_SEQ_LIST)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_ACTIVE_SAMPLE_RNA_SEQ_LIST, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<?> getActiveSampleRnaSeqList(@RequestBody
			@ValidCondition({
//				@MandatoryField("data.ref")
			}) ServiceRequest<InquiryOmics> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_ACTIVE_SAMPLE_RNA_SEQ_LIST);
			List<SampleRnaSeqDto> sampleRnaSeqList = sampleRnaSeqFacade.getActiveSampleRnaSeqList();
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, sampleRnaSeqList);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
}
