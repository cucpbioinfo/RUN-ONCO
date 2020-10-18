package run.onco.api.business.facade.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import run.onco.api.business.dto.AttachmentDto;
import run.onco.api.business.facade.AttachmentFacade;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.exception.ValidationException;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.entity.TbTAttachment;
import run.onco.api.service.AttachmentService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class AttachmentFacadeImpl implements AttachmentFacade {
	
	private final static Logger logger = Logger.getLogger(AttachmentFacadeImpl.class);
	
	@Autowired
	private AttachmentService attachmentService;

	@Override
	public AttachmentDto getAttachmentById(Long attachmentId) {
	
		try {
			logger.info(String.format("I:--START--:--Get Attachment--:attachmentId/%s", attachmentId));
			TbTAttachment attachment = attachmentService.getAttachmentById(attachmentId);
			
			if (attachment == null) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Attachment does not exist.");
			}
			
			AttachmentDto attachmentDto = new AttachmentDto();
			attachmentDto.setAttachmentId(attachment.getId());
			attachmentDto.setContentType(attachment.getContentType());
			attachmentDto.setFileName(attachment.getFileName());
			attachmentDto.setFilePath(attachment.getFilePath());
			attachmentDto.setFileSize(attachment.getFileSize());
			attachmentDto.setUuid(attachment.getUuid());
			attachmentDto.setStatus(attachment.getStatus());
			return attachmentDto;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get Attachment--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get Attachment--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get Attachment--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

}
