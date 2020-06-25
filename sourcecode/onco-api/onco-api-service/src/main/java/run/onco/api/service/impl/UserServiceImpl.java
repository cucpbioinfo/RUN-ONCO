package run.onco.api.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.UserDao;
import run.onco.api.persist.entity.TbMUser;
import run.onco.api.service.UserService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private final static Logger logger = Logger.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Override
	public TbMUser login(final String username, final String password) {
		
		try {
			logger.info(String.format("I:--START--:--Login--:username/%s", username));
			TbMUser user = userDao.login(username, password);
			logger.info("O:--SUCCESS--:--Login--");
			return user;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Login--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbMUser getActiveUserById(Long userId) {

		try {
			logger.info(String.format("I:--START--:--Get User by ID--:userId/%s", userId));
			TbMUser user = userDao.getActiveUserById(userId);
			logger.info("O:--SUCCESS--:--Get User by ID--");
			return user;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get User by ID--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbMUser getUserByUsername(String username) {
		
		try {
			logger.info(String.format("I:--START--:--Get User by username--:username/%s", username));
			TbMUser user = userDao.getActiveUserByUsername(username);
			logger.info("O:--SUCCESS--:--Get User by Username--");
			return user;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get User by ID--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
}
