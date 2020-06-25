package run.onco.api.service;

import run.onco.api.persist.entity.TbTAttachment;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface AttachmentService {

	public TbTAttachment getAttachmentById(final Long attachmentId);
}
