package run.onco.api.business.facade.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import run.onco.api.business.dto.AttachmentDto;
import run.onco.api.business.dto.DownloadLogDto;
import run.onco.api.business.facade.DownloadLogFacade;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.exception.ValidationException;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.DateUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.entity.TbLDownloadHistory;
import run.onco.api.service.DownloadLogService;
import run.onco.api.service.UserService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class DownloadLogFacadeImpl implements DownloadLogFacade {
	
	private final static Logger logger = Logger.getLogger(DownloadLogFacadeImpl.class);
	
	@Autowired
	private DownloadLogService downloadLogService;
	
	@Autowired
	private UserService userService;

	@Override
	public DownloadLogDto addDownloadLog(DownloadLogDto downloadLogDto) {
		
		try {
			logger.info("I:--START--:--Add DownloadLog--");
			
			Date now = DateUtil.getCurrentDate();
			
//			TbTToken downloadRef = new TbTToken();
//			downloadRef.setTokenType("DOWNLOAD_REF");
//			downloadRef.setTokenValue(AppUtil.generateUUID());
//			downloadRef.setCreateDate(now);
//			downloadRef.setCreateUser(userService.getUserByUsername(AppConstants.SYSTEM_USER));

			TbLDownloadHistory downloadHist = new TbLDownloadHistory(); 
			downloadHist.setDownloadRef(AppUtil.generateUUID());
			downloadHist.setCreateDate(now);
			downloadHist.setFirstName(downloadLogDto.getFirstName());
			downloadHist.setLastName(downloadLogDto.getLastName());
			downloadHist.setEmail(downloadLogDto.getEmail());
			downloadHist.setIsAgree(downloadLogDto.getIsAgree());
			downloadHist.setNumOfDownloads(0);
			Long downloadLogId = downloadLogService.saveDownloadLog(downloadHist);
			
			DownloadLogDto output = new DownloadLogDto();
			output.setId(downloadLogId);
			output.setToken(downloadHist.getDownloadRef());
			logger.info("O:--SUCCESS--:--Add DownloadLog--");
			return output;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Add DownloadLog--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Add DownloadLog--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Add DownloadLog--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public AttachmentDto downloadSourceCode(DownloadLogDto downloadLogDto) {
		
		try {
			logger.info("I:--START--:--Download SourceCode--");
			
			TbLDownloadHistory downloadHist = downloadLogService.getDownloadLogById(downloadLogDto.getId());
			if (downloadHist == null) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "DownloadLog does not exist.");
			}
			
			Date dueDate = DateUtil.addMinute(downloadHist.getCreateDate(), 1); // 1 Minutes
			long diff = DateUtil.compareDate(DateUtil.getCurrentDate(), dueDate);  
			
			if(diff > 0) {
				throw new ServiceException(MessageCode.ERROR_TOKEN_UUID_EXPIRED.getCode(), MessageCode.ERROR_TOKEN_UUID_EXPIRED.getDesc());
			}
			
			Date now = DateUtil.getCurrentDate();
			int numOfDownloads = downloadHist.getNumOfDownloads();
			
			downloadHist.setScVersion("Ver 0.0.1");
			downloadHist.setNumOfDownloads(numOfDownloads + 1);
			downloadHist.setUpdateDate(now);
			
			AttachmentDto attachmentDto = new AttachmentDto();
			attachmentDto.setFilePath("");
			attachmentDto.setFileName("instascan-ios-rear-camera.zip");
			return attachmentDto;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Download SourceCode--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Download SourceCode--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Download SourceCode--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
	
}
