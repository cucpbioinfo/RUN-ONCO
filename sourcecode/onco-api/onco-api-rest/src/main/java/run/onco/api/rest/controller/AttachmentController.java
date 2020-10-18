package run.onco.api.rest.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import run.onco.api.business.dto.AttachmentDto;
import run.onco.api.business.facade.AttachmentFacade;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.message.InquiryAttachment;
import run.onco.api.common.aspect.ControllerLogging;
import run.onco.api.common.aspect.MandatoryField;
import run.onco.api.common.aspect.ValidCondition;
import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.constants.ConfigurationConstants;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.message.ServiceRequest;
import run.onco.api.common.message.ServiceResponse;
import run.onco.api.common.utils.ServiceUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Configuration
@RestController
@RequestMapping("/" + AppConstants.SECURITY_CONTEXT)
public class AttachmentController {
	
	@Autowired
	private Environment env;

	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private AttachmentFacade attachmentFacade;
	
	private String getString(String key) {
		return env.getProperty(key);
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_DOWNLOAD_FILE)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_DOWNLOAD_FILE, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void downloadFile(@RequestBody
			@ValidCondition({
				@MandatoryField("data.attachment")
			}) ServiceRequest<InquiryAttachment> request, HttpServletResponse response) throws IOException  {
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_DOWNLOAD_FILE);
			
			AttachmentDto attachment = attachmentFacade.getAttachmentById(request.getData().getAttachment().getAttachmentId());
			String absolutePath = String.format("%s%s", getString(ConfigurationConstants.DATA_FILE_PATH), attachment.getFilePath());
			String fileDest = String.format("%s/%s", absolutePath, attachment.getFileName());
			
			InputStream is = new FileInputStream(fileDest);
		    response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getFileName() + "\"");

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
			ServiceResponse<?> jsonObject = ServiceUtil.buildResponse(request.getHeader(), ex.getErrorCode(), ex.getErrorDesc());
			ObjectMapper mapper = new ObjectMapper();
			out.print(mapper.writeValueAsString(jsonObject));
			out.flush();
			out.close();
		} 
	}
}
