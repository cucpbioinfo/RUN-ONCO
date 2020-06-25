package run.onco.batch.api.controller;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.utils.FileUtil;
import run.onco.api.common.utils.StringUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */
@RestController
public class ConfigController {

	private final static Logger logger = Logger.getLogger(ConfigController.class);
	
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_APP_VERSION, method = { RequestMethod.GET }, produces = MediaType.TEXT_PLAIN_VALUE)
	public String getAppVersion() {
		logger.info("I:--START--:--Get App Version--");
		String appVersion = FileUtil.readStringFromResourceFile("version.txt");
		String msg = StringUtil.isNullOrEmpty(appVersion) ? "FAIL" : "SUCCESS";
		logger.info(String.format("O:--%s--:--Get App Version--:appVersion/%s", msg, appVersion));
		return appVersion;
	}
}
