package run.onco.api.rest.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
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

import run.onco.api.business.dto.DataVersionDto;
import run.onco.api.business.dto.IcdDto;
import run.onco.api.business.dto.MasterDataDto;
import run.onco.api.business.dto.TemplateDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.facade.MasterDataFacade;
import run.onco.api.business.message.InquiryIcd;
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
public class IcdController {

	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private MasterDataFacade masterDataFacade;
	
	@ControllerLogging(AppConstants.ACT_CODE_FILTER_ICD)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_FILTER_ICD, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<MasterDataDto<IcdDto>> filterIcd(@RequestBody
			@ValidCondition({
			}) ServiceRequest<InquiryIcd> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_FILTER_ICD);
			MasterDataDto<IcdDto> icdList = masterDataFacade.filterIcd(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, icdList);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_FILTER_ICD_O)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_FILTER_ICD_O, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<MasterDataDto<IcdDto>> filterIcdO(@RequestBody
			@ValidCondition({
			}) ServiceRequest<InquiryIcd> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_FILTER_ICD_O);
			MasterDataDto<IcdDto> icdList = masterDataFacade.filterIcdO(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, icdList);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_SEARCH_ICD)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_SEARCH_ICD, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<DataTableResults<IcdDto>> searchIcd(@RequestBody
			@ValidCondition({
				@MandatoryField("data.criteria"),
				@MandatoryField("data.paging")
			}) ServiceRequest<DataTableRequest<IcdDto>> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_SEARCH_ICD);
			DataTableResults<IcdDto> output = masterDataFacade.searchIcd(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_UPLOAD_ICD)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_UPLOAD_ICD, method = { RequestMethod.POST })
	public void uploadIcd(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		ObjectMapper mapper = new ObjectMapper();

		Header header = null;
		Part responseHeader = AppUtil.getPartByName(request, "responseHeader");
		if (responseHeader != null) {
			header = mapper.readValue(responseHeader.getInputStream(), Header.class);
		}
		
		try {
			
			DataVersionDto dataVersionDto = null;
			Part responseData = AppUtil.getPartByName(request, "responseData");
			if (responseData != null) {
				dataVersionDto = mapper.readValue(responseData.getInputStream(), DataVersionDto.class);
			}
			
			masterDataFacade.findDuplicateDataVersion(dataVersionDto);

			List<IcdDto> icdDtoList = null;
			
			if (dataVersionDto.getId() == null) { 
				Part importFile = AppUtil.getPartByName(request, "importFile");
				
				if (importFile != null) {
		//				String fileName = importFile.getSubmittedFileName();
					int length = (int) importFile.getSize();
					
					if (length == 0) { 
						throw new ServiceException(MessageCode.ERROR_EMPTY_FILE.getCode(), MessageCode.ERROR_EMPTY_FILE.getDesc());
					}
					
					InputStream is = importFile.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					
					boolean initialized = false;
					List<String> expectedHeaders = Arrays.asList("CODE","NAME");
					icdDtoList = new ArrayList<>();
					
					while(reader.ready()) {
					     String line = reader.readLine();
					     System.out.println("---> line : " + line);
					     String[] arr = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
					     
					     if (!initialized) {
					    	 
						    	 if (!Arrays.asList(arr).containsAll(expectedHeaders)){
						    		 throw new ServiceException(MessageCode.ERROR_INVALID_FORMAT.getCode(), "Invalid CSV header.");
						    	 }
						    	 
						    	 initialized = true;
					     } else {
						     IcdDto icdDto = new IcdDto();
						     icdDto.setCode(arr[0]);
						     icdDto.setName(arr[1]);
						     icdDtoList.add(icdDto);
					     }
					}
				}
			}

			masterDataFacade.uploadIcd(dataVersionDto, icdDtoList);
			   
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			ServiceResponse<?> jsonObject = ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
			out.print(mapper.writeValueAsString(jsonObject));
			out.flush();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			ServiceException ex = (ServiceException) e;
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			ServiceResponse<?> jsonObject = ServiceUtil.buildResponse(header, ex.getErrorCode(), ex.getErrorDesc());
			out.print(mapper.writeValueAsString(jsonObject));
			out.flush();
			out.close();
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_UPLOAD_ICD_O)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_UPLOAD_ICD_O, method = { RequestMethod.POST })
	public void uploadIcdO(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		ObjectMapper mapper = new ObjectMapper();

		Header header = null;
		Part responseHeader = AppUtil.getPartByName(request, "responseHeader");
		if (responseHeader != null) {
			header = mapper.readValue(responseHeader.getInputStream(), Header.class);
		}
		
		try {
			
			DataVersionDto dataVersionDto = null;
			Part responseData = AppUtil.getPartByName(request, "responseData");
			if (responseData != null) {
				dataVersionDto = mapper.readValue(responseData.getInputStream(), DataVersionDto.class);
			}
			
			masterDataFacade.findDuplicateDataVersion(dataVersionDto);

			List<IcdDto> icdDtoList = null;
			
			if (dataVersionDto.getId() == null) { 
				Part importFile = AppUtil.getPartByName(request, "importFile");
				
				if (importFile != null) {
		//				String fileName = importFile.getSubmittedFileName();
					int length = (int) importFile.getSize();
					
					if (length == 0) { 
						throw new ServiceException(MessageCode.ERROR_EMPTY_FILE.getCode(), MessageCode.ERROR_EMPTY_FILE.getDesc());
					}
					
					InputStream is = importFile.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(is));
					
					boolean initialized = false;
					List<String> expectedHeaders = Arrays.asList("CODE","NAME");
					icdDtoList = new ArrayList<>();
					
					while(reader.ready()) {
					     String line = reader.readLine();
					     System.out.println("---> line : " + line);
					     String[] arr = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
					     
					     if (!initialized) {
					    	 
						    	 if (!Arrays.asList(arr).containsAll(expectedHeaders)){
						    		 throw new ServiceException(MessageCode.ERROR_INVALID_FORMAT.getCode(), "Invalid CSV header.");
						    	 }
						    	 
						    	 initialized = true;
					     } else {
						     IcdDto icdDto = new IcdDto();
						     icdDto.setCode(arr[0]);
						     icdDto.setName(arr[1]);
						     icdDtoList.add(icdDto);
					     }
					}
				}
			}

			masterDataFacade.uploadIcdO(dataVersionDto, icdDtoList);
			   
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			ServiceResponse<?> jsonObject = ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
			out.print(mapper.writeValueAsString(jsonObject));
			out.flush();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			ServiceException ex = (ServiceException) e;
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			ServiceResponse<?> jsonObject = ServiceUtil.buildResponse(header, ex.getErrorCode(), ex.getErrorDesc());
			out.print(mapper.writeValueAsString(jsonObject));
			out.flush();
			out.close();
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_SEARCH_ICD_O)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_SEARCH_ICD_O, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<DataTableResults<IcdDto>> searchIcdO(@RequestBody
			@ValidCondition({
				@MandatoryField("data.criteria"),
				@MandatoryField("data.paging")
			}) ServiceRequest<DataTableRequest<IcdDto>> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_SEARCH_ICD_O);
			DataTableResults<IcdDto> output = masterDataFacade.searchIcdO(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_DOWNLOAD_ICD_TEMPLATE)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_DOWNLOAD_ICD_TEMPLATE, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void downloadIcdTemplate(@RequestBody
			@ValidCondition({
				@MandatoryField("data.type"),
				@MandatoryField("data.fileName")
			}) ServiceRequest<TemplateDto> request, HttpServletResponse response) throws IOException  {
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_DOWNLOAD_ICD_TEMPLATE);
			
			TemplateDto template = request.getData();
			
			response.setContentType("text/csv");
		    response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", template.getFileName()));
		    OutputStream outputStream = response.getOutputStream();
	        String output = null;
	        
	        if ("ICD".equals(template.getType())) {
	        		output = getIcdExampleData();
	        } else {
	        		output = getIcdOExampleData();
	        }
		    
	        outputStream.write(output.getBytes());
	        outputStream.flush();
	        outputStream.close();
	        
		} catch (ServiceException | ValidationException e) {
			ServiceException ex = (ServiceException) e;
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			ServiceResponse<?> jsonObject = ServiceUtil.buildResponse(request.getHeader(), ex.getErrorCode(), ex.getErrorDesc());
			ObjectMapper mapper = new ObjectMapper();
			out.print(mapper.writeValueAsString(jsonObject));
			out.flush();
			out.close();
		} 
	}
	
	private static String getIcdExampleData() {
		StringBuilder sb = new StringBuilder();
        sb.append("CODE,NAME\n");
        sb.append("C15,Malignant neoplasm of oesophagus\n");
        sb.append("C15.0,Cervical part of oesophagus\n");
        sb.append("C15.1,Thoracic part of oesophagus\n");
        sb.append("C15.2,Abdominal part of oesophagus\n");
        sb.append("C15.3,Upper third of oesophagus\n");
        return sb.toString();
	}
	
	private static String getIcdOExampleData() {
		StringBuilder sb = new StringBuilder();
        sb.append("CODE,NAME\n");
        sb.append("8000/0,\"Neoplasm, benign\"\n");
        sb.append("8000/1,\"Neoplasm, uncertain whether benign or malignant\"\n");
        sb.append("8000/3,\"Neoplasm, malignant\"\n");
        sb.append("8000/6,\"Neoplasm, metastatic\"\n");
        sb.append("8000/9,\"Neoplasm, malignant, uncertain whether primary or metastatic\"\n");
        return sb.toString();
	}
}
