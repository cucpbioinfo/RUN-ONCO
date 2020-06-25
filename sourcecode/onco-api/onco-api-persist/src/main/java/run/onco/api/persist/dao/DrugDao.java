package run.onco.api.persist.dao;

import java.util.List;

import run.onco.api.persist.entity.TbMActionableVariant;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface DrugDao {

	public List<TbMActionableVariant> getActionableVariantListByGeneName(final String geneName);
}
