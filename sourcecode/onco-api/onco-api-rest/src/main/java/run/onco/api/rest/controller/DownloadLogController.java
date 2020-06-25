package run.onco.api.rest.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import run.onco.api.business.dto.AttachmentDto;
import run.onco.api.business.dto.DownloadLogDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.facade.DownloadLogFacade;
import run.onco.api.common.aspect.ControllerLogging;
import run.onco.api.common.aspect.MandatoryField;
import run.onco.api.common.aspect.ValidCondition;
import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.constants.ConfigurationConstants;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.message.Header;
import run.onco.api.common.message.ServiceRequest;
import run.onco.api.common.message.ServiceResponse;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.common.utils.ServiceUtil;
import run.onco.api.common.utils.ValidationUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */
@RestController
public class DownloadLogController {

	@Autowired
	private Environment env;

	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private DownloadLogFacade downloadLogFacade;
	
	private String getString(String key) {
		return env.getProperty(key);
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_REGISTER_TO_DOWNLOAD)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_REGISTER_TO_DOWNLOAD, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<DownloadLogDto> registerToDownload(@RequestBody
			@ValidCondition({
			}) ServiceRequest<DownloadLogDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_REGISTER_TO_DOWNLOAD);
			DownloadLogDto downloadLogDto = downloadLogFacade.addDownloadLog(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, downloadLogDto);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_DOWNLOAD_SOURCE_CODE)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_DOWNLOAD_SOURCE_CODE, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void downloadSourceCode(@RequestBody
			@ValidCondition({
				@MandatoryField("data.id"),
				@MandatoryField("data.token")
			}) ServiceRequest<DownloadLogDto> request, HttpServletResponse response) throws IOException {
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_DOWNLOAD_SOURCE_CODE);
			ValidationUtil.validateOpts(request.getData());
			
			AttachmentDto attachment = downloadLogFacade.downloadSourceCode(request.getData());
			String absolutePath = String.format("%s%s", getString(ConfigurationConstants.SOURCECODE_FILE_PATH), attachment.getFilePath());
			String fileDest = String.format("%s/%s", absolutePath, attachment.getFileName());
			
			System.out.println("------> fileDest : " + fileDest);
						
			InputStream is = new FileInputStream(fileDest);
		    response.setHeader("Content-Disposition", "attachment; filename=\"" + attachment.getFileName() + "\"");
		    response.setContentType("application/octet-stream");
		    
		    Path zippath = Paths.get(fileDest);
		    response.setContentLength((int)Files.size(zippath));

		    int read = 0;
		    byte[] bytes = new byte[AppConstants.BYTES_DOWNLOAD];
		    OutputStream os = response.getOutputStream();

		    while((read = is.read(bytes))!= -1) {
		        os.write(bytes, 0, read);
		    }
		    
		    os.flush();
		    os.close();
		    is.close();
		} catch (ServiceException | ValidationException e) {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			ServiceException ex = (ServiceException) e;
			ServiceResponse<?> jsonObject = ServiceUtil.buildResponse(request.getHeader(), ex.getErrorCode(), ex.getErrorDesc());
			out.print(jsonObject);
			out.flush();
			out.close();
		} 
	}
}
