package run.onco.api.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.dto.Paging;
import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.DynamicContentDao;
import run.onco.api.persist.dao.impl.Dao;
import run.onco.api.persist.entity.TbCDynamicContent;
import run.onco.api.service.DynamicContentService;

@Service
@Transactional(readOnly = true)
public class DynamicContentServiceImpl implements DynamicContentService {

	private final static Logger logger = Logger.getLogger(DynamicContentServiceImpl.class);
	
	@Autowired
	private Dao dao;
	
	@Autowired
	private DynamicContentDao dynamicContentDao;
	
	@Override
	public List<TbCDynamicContent> getActiveDynamicContentList() {
		
		try {
			logger.info("I:--START--:--Get Active DynamicContent List--");
			List<TbCDynamicContent> componentList = dynamicContentDao.getActiveDynamicContentList();
			logger.info("O:--SUCCESS--:--Get Active Component List--");
			return componentList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Active DynamicContent List--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbCDynamicContent> listPaginatedDynamicContentUsingQuery(Map<String, Object> criteria, Paging paging) {

		try {
			logger.info(String.format("I:--START--:--Search DynamicContent--:dataVerId/%s", criteria.get("dataVerId")));
			List<TbCDynamicContent> dynamicContentList = dynamicContentDao.listPaginatedDynamicContentUsingQuery(criteria, paging);
			logger.info("O:--SUCCESS--:--Search DynamicContent--");
			return dynamicContentList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Search DynamicContent--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public int countDynamicContentUsingQuery(Map<String, Object> criteria, Paging paging) {

		try {
			logger.info("I:--START--:--Get DynamicContent PaginatedCount--");
			int count = dynamicContentDao.countDynamicContentUsingQuery(criteria, paging);
			logger.info("O:--SUCCESS--:--Get DynamicContent PaginatedCount--");
			return count;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get DynamicContent PaginatedCount--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public boolean findDuplicateComponentName(String componentName, Integer id) {

		try {
			logger.info(String.format("I:--START--:--Find Duplicate ComponentName--:componentName/%s", componentName));
			boolean isDuplicate = dynamicContentDao.findDuplicateComponentName(componentName, id);
			logger.info(String.format("O:--SUCCESS--:--Find Duplicate ComponentName--:isDuplicate/%s", isDuplicate));
			return isDuplicate;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Find Duplicate ComponentName--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void saveDynamicContent(TbCDynamicContent dynamicContent) {
		
		try {
			logger.info("I:--START--:--Save DynamicContent--");
			dao.saveOrUpdate(dynamicContent);
			logger.info("O:--SUCCESS--:--Save DynamicContent--");
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Save DynamicContent--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbCDynamicContent getDynamicContentById(Integer id) {
		
		try {
			logger.info(String.format("I:--START--:--Get DynamicContent--:id/%s", id));
			TbCDynamicContent dynamicContent = dao.get(TbCDynamicContent.class, id);
			logger.info("O:--SUCCESS--:--Get DynamicContent--");
			 return dynamicContent;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get DynamicContent--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

}
