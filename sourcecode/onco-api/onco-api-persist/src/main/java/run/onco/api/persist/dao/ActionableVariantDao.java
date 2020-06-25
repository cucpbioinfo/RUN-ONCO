package run.onco.api.persist.dao;

import java.util.List;

import run.onco.api.persist.entity.TbMActionableVariant;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface ActionableVariantDao {

	public List<TbMActionableVariant> getActionableVariantList(final String transcript);

	public List<TbMActionableVariant> getActionableVariantList(Integer[] ids);
}
