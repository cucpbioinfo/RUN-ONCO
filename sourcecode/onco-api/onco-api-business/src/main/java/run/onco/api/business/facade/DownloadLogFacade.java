package run.onco.api.business.facade;

import run.onco.api.business.dto.AttachmentDto;
import run.onco.api.business.dto.DownloadLogDto;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface DownloadLogFacade {

	public DownloadLogDto addDownloadLog(DownloadLogDto downloadLogDto);
	
	public AttachmentDto downloadSourceCode(DownloadLogDto downloadLogDto); 
}
