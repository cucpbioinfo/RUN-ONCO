package run.onco.api.persist.dao;

import java.util.List;

import run.onco.api.persist.entity.TbCMenu;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface MenuDao {

	public List<TbCMenu> getMenusByUserId(final Long userId);
}
