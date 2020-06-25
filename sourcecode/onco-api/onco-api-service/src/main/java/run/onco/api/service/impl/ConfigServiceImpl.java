package run.onco.api.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.DateUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.ConfigDao;
import run.onco.api.persist.entity.TbTSequence;
import run.onco.api.service.ConfigService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class ConfigServiceImpl implements ConfigService {

	private final static Logger logger = Logger.getLogger(ConfigServiceImpl.class);

	@Autowired
	private ConfigDao configDao;

	@Override
	public boolean checkServiceAuth(String systemCode, String channelId) {
		
		try {
			logger.info(String.format("I:--START--:--Check ServiceAuth--:systemCode/%s:channelId/%s", systemCode, channelId));
			boolean isAuthorized = configDao.checkServiceAuth(systemCode, channelId);
			logger.info(String.format("O:--SUCCESS--:--Check ServiceAuth--:isAuthorized/%s", isAuthorized));
			return isAuthorized;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Check ServiceAuth--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public Integer getCurrentSequence(String dataType, Date currentDate) {
		try {
			
			logger.info(String.format("I:--START--:--Get Current Sequence--:dataType/%s:currentDate/%s", dataType, DateUtil.formatDate(currentDate, DateUtil.FORMAT_STORE_PROCEDURE_DATE)));
			TbTSequence sequence = configDao.getCurrentSequence(dataType, currentDate);
			int currentSeq = 1;
			
			if(sequence == null) {
				sequence = new TbTSequence();
				sequence.setSequenceNo(1);
				sequence.setDataType(dataType);
				sequence.setCreateDate(currentDate);
				sequence.setUpdateDate(DateUtil.getCurrentDate());
				logger.info(String.format("O:--Get New Sequence--:seq/%d", sequence.getSequenceNo()));
			} else {
				currentSeq = sequence.getSequenceNo();
				int nextSeq = currentSeq + 1;
				sequence.setUpdateDate(DateUtil.getCurrentDate());
				sequence.setSequenceNo(nextSeq);
				logger.info(String.format("O:--Get Exist Sequence--:seq/%d", currentSeq));
			}
	
			configDao.saveNextSequence(sequence);
			logger.info("O:--SUCCESS--:--Get Current Sequence--");
			return currentSeq;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Current Sequence--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

}
