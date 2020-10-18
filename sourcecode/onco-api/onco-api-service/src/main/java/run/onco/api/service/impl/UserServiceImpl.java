package run.onco.api.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.dto.Paging;
import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.RoleDao;
import run.onco.api.persist.dao.UserDao;
import run.onco.api.persist.dao.impl.Dao;
import run.onco.api.persist.entity.TbCRole;
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
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private Dao dao;

	@Override
	public TbMUser login(final String username, final String password) {
		
		try {
			logger.info(String.format("I:--START--:--Login--:username/%s", username));
			TbMUser user = userDao.login(username, password);
			logger.info("O:--SUCCESS--:--Login--");
			return user;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Login--:errorDesc/%s", ex.getMessage()));
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
			logger.info(String.format("O:--FAIL--:--Get User by ID--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbMUser getUserByUsername(String username) {
		
		try {
			logger.info(String.format("I:--START--:--Get User by Username--:username/%s", username));
			TbMUser user = userDao.getActiveUserByUsername(username);
			logger.info("O:--SUCCESS--:--Get User by Username--");
			return user;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get User by Username--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbMUser> listPaginatedUserUsingQuery(Map<String, Object> criteria, Paging paging) {
		try {
			
			logger.info(String.format("I:--START--:--Search User--:username/%s:status/%s", criteria.get("username"), criteria.get("status")));
			List<TbMUser> users = userDao.listPaginatedUserUsingQuery(criteria, paging);
			logger.info("O:--SUCCESS--:--Search User--");
			return users;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Search User--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public int countUserUsingQuery(Map<String, Object> criteria, Paging paging) {
		
		try {
			logger.info(String.format("I:--START--:--Get User PaginatedCount--:username/%s:status/%s", criteria.get("username"), criteria.get("status")));
			int count = userDao.countUserUsingQuery(criteria, paging);
			logger.info("O:--SUCCESS--:--Get User PaginatedCount--");
			return count;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get User PaginatedCount--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbCRole> getActiveRoles() {
		
		try {
			logger.info("I:--START--:--Get Active Roles--");
			List<TbCRole> roles = roleDao.getActiveRoles();
			logger.info("O:--SUCCESS--:--Get Active Roles--");
			return roles;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Active Roles--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public boolean findDuplicateUsername(String username, Long id) {
		
		try {
			logger.info(String.format("I:--START--:--Find Duplicate Username--:username/%s", username));
			boolean isDuplicate = userDao.findDuplicateUsername(username, id);
			logger.info(String.format("O:--SUCCESS--:--Find Duplicate Username--:isDuplicate/%s", isDuplicate));
			return isDuplicate;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Find Duplicate Username--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void saveUser(TbMUser user) {
		
		try {
			logger.info("I:--START--:--Save User--");
			dao.saveOrUpdate(user);
			logger.info("O:--SUCCESS--:--Save User--");
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Save User--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbMUser getUserById(Long userId) {
		
		try {
			logger.info(String.format("I:--START--:--Get User--:id/%s", userId));
			TbMUser user = dao.get(TbMUser.class, userId);
			logger.info("O:--SUCCESS--:--Get User--");
			 return user;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get User--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteUser(TbMUser user) {
		
		try {
			logger.info(String.format("I:--START--:--Delete User--:id/%s", user.getId()));
			dao.delete(user);
			logger.info("O:--SUCCESS--:--Delete User--");
		} catch (ConstraintViolationException ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Delete User--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DELETE_RECORD);
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Delete User--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
}
