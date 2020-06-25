package run.onco.api.business.facade;

import java.util.List;

import run.onco.api.business.dto.ActionableVariantDto;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface DrugFacade {
	
	public void test();

	List<ActionableVariantDto> getActionableVariantByGeneName(String geneName);
}
