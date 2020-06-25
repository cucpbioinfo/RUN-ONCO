package run.onco.api.service;

import java.util.List;

import run.onco.api.persist.entity.TbMActionableVariant;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface DrugService {

	public List<TbMActionableVariant> getActionableVariantListByGeneName(final String geneName);
}
