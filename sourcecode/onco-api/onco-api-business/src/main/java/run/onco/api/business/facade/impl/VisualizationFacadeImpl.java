package run.onco.api.business.facade.impl;

import org.apache.log4j.Logger;
import org.rosuda.REngine.Rserve.RConnection;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import run.onco.api.business.dto.VisualDto;
import run.onco.api.business.facade.VisualizationFacade;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.r.RUtil;
import run.onco.api.common.utils.FileUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.common.utils.StringUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */

@Service
@Configuration
public class VisualizationFacadeImpl implements VisualizationFacade {
	
	private final static Logger logger = Logger.getLogger(VisualizationFacadeImpl.class);

	@Override
	public VisualDto executeR(String fileName, double x, int y, int z) {

		try {
			String outputFile = "/Users/neda/Personal/Study/Thesis/CUSB/run-onco/3_Develop/backend/onco-api/onco-api-rest/src/main/resources/output/GenVisR_waterfall_example1.pdf";
			logger.debug(String.format("I:--START--:--Execute R--:fileName/%s:x/%s:y/%s:z/%s", fileName, x, y, z));
			
			RConnection conn = null;

			conn = RUtil.openConnection(conn);
			RUtil.execute(conn, fileName, x, y, z, outputFile);

			RUtil.closeConnection(conn);

			logger.debug("O:--SUCCESS--:--Execute R--");
			
			byte[] bytes = FileUtil.readBytesFromFile(outputFile);
			String base64 = StringUtil.convertBytesToBase64(bytes);
			
			VisualDto output = new VisualDto();
			output.setContent(base64);
			output.setFileName("GenVisR_waterfall_example1.pdf");
			return output;
		} catch(Exception ex) {
			logger.error(ex);
			logger.debug(String.format("O:--FAIL--:--Execute R--:errorDesc/%s", ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_EXECUTE_R_FAIL.getCode(), ex.getMessage());
		}
	}

}
