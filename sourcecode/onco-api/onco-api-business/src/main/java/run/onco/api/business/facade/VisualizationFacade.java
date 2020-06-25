package run.onco.api.business.facade;

import run.onco.api.business.dto.VisualDto;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface VisualizationFacade {

	public VisualDto executeR(final String fileName, final double x, final int y, final int z);
}
