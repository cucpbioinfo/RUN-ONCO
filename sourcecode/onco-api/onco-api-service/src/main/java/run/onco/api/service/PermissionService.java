package run.onco.api.service;

import java.util.List;

import run.onco.api.persist.entity.TbCDynamicContent;
import run.onco.api.persist.entity.TbCMenu;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface PermissionService {

	public List<TbCMenu> getMenusByUserId(final Long userId);
	
	public List<TbCDynamicContent> getDynamicConntentsByUserId(final Long userId);
}
