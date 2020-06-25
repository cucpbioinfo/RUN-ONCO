package run.onco.api.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.impl.Dao;
import run.onco.api.persist.entity.TbTAttachment;
import run.onco.api.service.AttachmentService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class AttachmentServiceImpl implements AttachmentService {

	private final static Logger logger = Logger.getLogger(AttachmentServiceImpl.class);
	
	@Autowired
	private Dao dao;

	@Override
	public TbTAttachment getAttachmentById(Long attachmentId) {
		
		try {
			logger.info(String.format("I:--START--:--Get Attachment--:id/%s", attachmentId));
			TbTAttachment attachment = dao.get(TbTAttachment.class, attachmentId);
			logger.info("O:--SUCCESS--:--Get Attachment");
			return attachment;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Attachment--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
}
