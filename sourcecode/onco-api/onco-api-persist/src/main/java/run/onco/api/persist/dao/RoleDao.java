package run.onco.api.persist.dao;

import java.util.List;

import run.onco.api.persist.entity.TbCRole;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface RoleDao {

	public List<TbCRole> getActiveRoles();
}
