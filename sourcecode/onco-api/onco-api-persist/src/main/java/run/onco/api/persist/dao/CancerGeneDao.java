package run.onco.api.persist.dao;

import java.util.List;

import run.onco.api.persist.entity.TbMCancerGeneGroup;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface CancerGeneDao {

	public List<TbMCancerGeneGroup> getActiveCancerGeneGroups();
}
