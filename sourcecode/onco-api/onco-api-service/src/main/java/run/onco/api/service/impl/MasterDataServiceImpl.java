package run.onco.api.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.dto.DataItem;
import run.onco.api.common.dto.Paging;
import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.CancerGeneDao;
import run.onco.api.persist.dao.DataVersionDao;
import run.onco.api.persist.dao.IcdDao;
import run.onco.api.persist.dao.impl.Dao;
import run.onco.api.persist.entity.TbCDataVersion;
import run.onco.api.persist.entity.TbMCancerGeneGroup;
import run.onco.api.persist.entity.TbMIcd;
import run.onco.api.persist.entity.TbMIcdO;
import run.onco.api.persist.entity.TbMUser;
import run.onco.api.service.MasterDataService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class MasterDataServiceImpl implements MasterDataService {

	private final static Logger logger = Logger.getLogger(MasterDataServiceImpl.class);
	
	@Autowired
	private Dao dao;
	
	@Autowired
	private IcdDao icdDao;
	
	@Autowired
	private CancerGeneDao cancerGeneDao;
	
	@Autowired
	private DataVersionDao dataVersionDao;
	
	@Override
	public <T> List<DataItem> getList(Class<T> clazz, Map<String, Object> criteria, String[] fields) {
		return dao.findByCriteria(clazz, criteria, fields);
	}

	@Override
	public <T> T getByCode(Class<T> clazz, String code) {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("code", code);
		
		List<T> result = dao.findByCriteria(clazz, criteria);
		return result == null || result.size() == 0 ? null : result.get(0);
	}

	@Override
	public <T> T getByCode(Class<T> clazz, String code, String type) {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("code", code);
		criteria.put("type", type);
		
		List<T> result = dao.findByCriteria(clazz, criteria);
		return result == null || result.size() == 0 ? null : result.get(0);
	}

	@Override
	public <T> T getById(Class<T> clazz, Integer id) {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("id", id);
		
		List<T> result = dao.findByCriteria(clazz, criteria);
		return result == null || result.size() == 0 ? null : result.get(0);
	}
	
	@Override
	public <T> T getActiveById(Class<T> clazz, Integer id) {
		Map<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("id", id);
		criteria.put("status", AppConstants.STATUS_ACTIVE);
		
		List<T> result = dao.findByCriteria(clazz, criteria);
		return result == null || result.size() == 0 ? null : result.get(0);
	}

	@Override
	public <T> List<T> search(Class<T> clazz, Map<String, Object> criteria, Paging paging) {
		List<T> result = dao.search(clazz, criteria, paging);
		return result;
	}

	@Override
	public List<TbMIcd> searchIcd(Map<String, Object> criteria, Paging paging) {
		
		try {
			String version = AppUtil.getMapValue(criteria, "version");
			String name = AppUtil.getMapValue(criteria, "name");
			String status = AppUtil.getMapValue(criteria, "status");
			String isDefault = AppUtil.getMapValue(criteria, "isDefault");
		
			logger.info(String.format("I:--START--:--Search ICD--:name/%s:status/%s:version/%s:isDefault/%s", name, status, version, isDefault));
			List<TbMIcd> icdList = icdDao.searchIcd(criteria, paging);
			logger.info("O:--SUCCESS--:--Search ICD--");
			return icdList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Search ICD--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbMIcdO> searchIcdO(Map<String, Object> criteria, Paging paging) {

		try {
			String version = AppUtil.getMapValue(criteria, "version");
			String name = AppUtil.getMapValue(criteria, "name");
			String status = AppUtil.getMapValue(criteria, "status");
			String isDefault = AppUtil.getMapValue(criteria, "isDefault");
			
			logger.info(String.format("I:--START--:--Search ICD-O--:name/%s:status/%s:version/%s:isDefault/%s", name, status, version, isDefault));
			List<TbMIcdO> icdList = icdDao.searchIcdO(criteria, paging);
			logger.info("O:--SUCCESS--:--Search ICD-O--");
			return icdList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Search ICD-O--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbMCancerGeneGroup> getActiveCancerGeneGroups() {
		
		try {
			logger.info("I:--START--:--Get Active CancerGeneGroup List--");
			List<TbMCancerGeneGroup> cancerGeneGroups = cancerGeneDao.getActiveCancerGeneGroups();
			logger.info("O:--SUCCESS--:--Get Active CancerGeneGroup List--");
			return cancerGeneGroups;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Active CancerGeneGroup List--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbCDataVersion> listPaginatedDataVersionUsingQuery(Map<String, Object> criteria, Paging paging) {
		
		try {
			logger.info(String.format("I:--START--:--Search DataVersion--:type/%s:version/%s", criteria.get("type"), criteria.get("version")));
			List<TbCDataVersion> dataVersions = dataVersionDao.listPaginatedDataVersionUsingQuery(criteria, paging);
			logger.info("O:--SUCCESS--:--Search DataVersion--");
			return dataVersions;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Search DataVersion--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public int countDataVersionUsingQuery(Map<String, Object> criteria, Paging paging) {
		
		try {
			logger.info(String.format("I:--START--:--Get DataVersion PaginatedCount--:type/%s:version/%s", criteria.get("type"), criteria.get("version")));
			int count = dataVersionDao.countDataVersionUsingQuery(criteria, paging);
			logger.info("O:--SUCCESS--:--Get DataVersion PaginatedCount--");
			return count;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get DataVersion PaginatedCount--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbMIcd> listPaginatedIcdUsingQuery(Map<String, Object> criteria, Paging paging) {
		
		try {
			logger.info(String.format("I:--START--:--Search ICD--:dataVerId/%s", criteria.get("dataVerId")));
			List<TbMIcd> icdList = icdDao.listPaginatedIcdUsingQuery(criteria, paging);
			logger.info("O:--SUCCESS--:--Search ICD--");
			return icdList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Search ICD--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public int countIcdUsingQuery(Map<String, Object> criteria, Paging paging) {
		
		try {
			logger.info(String.format("I:--START--:--Get ICD PaginatedCount--:dataVerId/%s", criteria.get("dataVerId")));
			int count = icdDao.countIcdUsingQuery(criteria, paging);
			logger.info("O:--SUCCESS--:--Get ICD PaginatedCount--");
			return count;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get ICD PaginatedCount--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public boolean findDuplicateDataVersion(String type, String version, Integer id) {
		
		try {
			logger.info(String.format("I:--START--:--Find Duplicate DataVersion--:type/%s:version/%s", type, version));
			boolean isDuplicate = dataVersionDao.findDuplicateDataVersion(type, version, id);
			logger.info(String.format("O:--SUCCESS--:--Find Duplicate DataVersion--:isDuplicate/%s", isDuplicate));
			return isDuplicate;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Find Duplicate DataVersion--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public void saveIcdList(HashMap<String, Object> map) {
		
		try {
			logger.info("I:--START--:--Save ICD--");
			
			TbCDataVersion dataVersion = (TbCDataVersion) map.get("DATA_VERSION");
			
			if(dataVersion != null) {
				TbMUser user = dataVersion.getUpdateUser();
				Date timestamp = dataVersion.getUpdateDate();
				
				if (user == null) {
					user = dataVersion.getCreateUser();
					timestamp = dataVersion.getCreateDate();
				}
				
				if (AppConstants.YES.equals(dataVersion.getIsDefault())) {
					List<TbCDataVersion> markAsDefaultDataVersions = dataVersionDao.getMarkAsDefaultDataVersionsByType(dataVersion.getType(), dataVersion.getId());
					
					if (markAsDefaultDataVersions != null && markAsDefaultDataVersions.size() > 0) {
						for (TbCDataVersion defaultDataVersion : markAsDefaultDataVersions) {
							defaultDataVersion.setIsDefault(AppConstants.NO);
							defaultDataVersion.setUpdateDate(timestamp);
							defaultDataVersion.setUpdateUser(user);
							dao.saveOrUpdate(defaultDataVersion);
						}
					}
				}
				
				dao.saveOrUpdate(dataVersion);
			}
			
			List<TbMIcd> icdList = (List<TbMIcd>) map.get("ICD_LIST");
			if (icdList != null && icdList.size() > 0) {
				
				for (TbMIcd icd : icdList) {
					logger.info(String.format("O:--Get ICD--:code/%s:name/%s", icd.getCode(), icd.getName()));
					dao.saveOrUpdate(icd);
				}
			}
			
			logger.info("O:--SUCCESS--:--Save ICD--");
			
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Save ICD--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public <T> void saveOrUpdate(T entity) {
		
		try {
			logger.info("I:--START--:--Save or Update--");
			dao.saveOrUpdate(entity);
			logger.info("O:--SUCCESS--:--Save or Update--");
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Save or Update--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteIcdListByDataVersion(TbCDataVersion dataVersion) {
		
		try {
			logger.info("I:--START--:--Delete ICD list by DataVersion--");
			icdDao.deleteIcdList(dataVersion.getId());
			dao.delete(dataVersion);
			logger.info("O:--SUCCESS--:--Delete ICD list by DataVersion--");
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Delete ICD list by DataVersion--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public void saveIcdOList(HashMap<String, Object> map) {
		
		try {
			logger.info("I:--START--:--Save ICD-O--");
			
			TbCDataVersion dataVersion = (TbCDataVersion) map.get("DATA_VERSION");
			
			if(dataVersion != null) {
				TbMUser user = dataVersion.getUpdateUser();
				Date timestamp = dataVersion.getUpdateDate();
				
				if (user == null) {
					user = dataVersion.getCreateUser();
					timestamp = dataVersion.getCreateDate();
				}
				
				if (AppConstants.YES.equals(dataVersion.getIsDefault())) {
					List<TbCDataVersion> markAsDefaultDataVersions = dataVersionDao.getMarkAsDefaultDataVersionsByType(dataVersion.getType(), dataVersion.getId());
					
					if (markAsDefaultDataVersions != null && markAsDefaultDataVersions.size() > 0) {
						for (TbCDataVersion defaultDataVersion : markAsDefaultDataVersions) {
							defaultDataVersion.setIsDefault(AppConstants.NO);
							defaultDataVersion.setUpdateDate(timestamp);
							defaultDataVersion.setUpdateUser(user);
							dao.saveOrUpdate(defaultDataVersion);
						}
					}
				}
				
				dao.saveOrUpdate(dataVersion);
			}
			
			List<TbMIcdO> icdList = (List<TbMIcdO>) map.get("ICD_LIST");
			if (icdList != null && icdList.size() > 0) {
				
				for (TbMIcdO icd : icdList) {
					logger.info(String.format("O:--Get ICD-O--:code/%s:name/%s", icd.getCode(), icd.getName()));
					dao.saveOrUpdate(icd);
				}
			}
			
			logger.info("O:--SUCCESS--:--Save ICD-O--");
			
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Save ICD-O--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbMIcdO> listPaginatedIcdOUsingQuery(Map<String, Object> criteria, Paging paging) {
		
		try {
			logger.info(String.format("I:--START--:--Search ICD-O--:dataVerId/%s", criteria.get("dataVerId")));
			List<TbMIcdO> icdList = icdDao.listPaginatedIcdOUsingQuery(criteria, paging);
			logger.info("O:--SUCCESS--:--Search ICD-O--");
			return icdList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Search ICD-O--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public int countIcdOUsingQuery(Map<String, Object> criteria, Paging paging) {
		
		try {
			logger.info(String.format("I:--START--:--Get ICD-O PaginatedCount--:dataVerId/%s", criteria.get("dataVerId")));
			int count = icdDao.countIcdOUsingQuery(criteria, paging);
			logger.info("O:--SUCCESS--:--Get ICD-O PaginatedCount--");
			return count;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get ICD-O PaginatedCount--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteIcdOListByDataVersion(TbCDataVersion dataVersion) {
		
		try {
			logger.info("I:--START--:--Delete ICD list by DataVersion--");
			icdDao.deleteIcdOList(dataVersion.getId());
			dao.delete(dataVersion);
			logger.info("O:--SUCCESS--:--Delete ICD list by DataVersion--");
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Delete ICD list by DataVersion--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
}
