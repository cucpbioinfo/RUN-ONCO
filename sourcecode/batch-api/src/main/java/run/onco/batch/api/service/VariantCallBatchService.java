package run.onco.batch.api.service;

import java.io.IOException;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface VariantCallBatchService {

	public void importVariantCallFiles() throws IOException;
	
	public void annotateVariant();
}
