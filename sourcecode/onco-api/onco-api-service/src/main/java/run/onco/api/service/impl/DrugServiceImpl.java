package run.onco.api.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.DrugDao;
import run.onco.api.persist.entity.TbMActionableVariant;
import run.onco.api.service.DrugService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class DrugServiceImpl implements DrugService {
	
	private final static Logger logger = Logger.getLogger(DrugServiceImpl.class);
	
	@Autowired
	private DrugDao drugDao;

	@Override
	public List<TbMActionableVariant> getActionableVariantListByGeneName(String geneName) {
		
		try {
			logger.info(String.format("I:--START--:--Get AnnotatedVariantList by GeneName--:geneName/%s", geneName));
			List<TbMActionableVariant> annotatedVariantList = drugDao.getActionableVariantListByGeneName(geneName);
			logger.info("O:--SUCCESS--:--Get AnnotatedVariantList by geneName");
			return annotatedVariantList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get AnnotatedVariantList by GeneName--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

}
