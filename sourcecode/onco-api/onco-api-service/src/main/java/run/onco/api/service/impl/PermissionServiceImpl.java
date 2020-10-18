package run.onco.api.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.DynamicContentDao;
import run.onco.api.persist.dao.MenuDao;
import run.onco.api.persist.entity.TbCDynamicContent;
import run.onco.api.persist.entity.TbCMenu;
import run.onco.api.service.PermissionService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class PermissionServiceImpl implements PermissionService {

	private final static Logger logger = Logger.getLogger(PermissionServiceImpl.class);
	
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private DynamicContentDao dynamicContentDao;

	@Override
	public List<TbCMenu> getMenusByUserId(Long userId) {
		
		try {
			logger.info(String.format("I:--START--:--Get Menus by UserID--:userId/%s", userId));
			List<TbCMenu> menus = menuDao.getMenusByUserId(userId);
			logger.info("O:--SUCCESS--:--Get Menus by UserID--");
			return menus;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Menus by UserID--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbCDynamicContent> getDynamicConntentsByUserId(Long userId) {
		
		try {
			logger.info(String.format("I:--START--:--Get DynamicContents by UserID--:userId/%s", userId));
			List<TbCDynamicContent> dynamicContents = dynamicContentDao.getDynamicContentsByUserId(userId);
			logger.info("O:--SUCCESS--:--Get DynamicContents by UserID--");
			return dynamicContents;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get DynamicContents by UserID--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
}
