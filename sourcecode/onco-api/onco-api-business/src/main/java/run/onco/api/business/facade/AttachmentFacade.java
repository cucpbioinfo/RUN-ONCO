package run.onco.api.business.facade;

import run.onco.api.business.dto.AttachmentDto;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface AttachmentFacade {

	public AttachmentDto getAttachmentById(final Long attachmentId);
}
