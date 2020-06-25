package run.onco.api.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.BiospecimenDao;
import run.onco.api.persist.dao.impl.Dao;
import run.onco.api.persist.entity.TbTBiospecimen;
import run.onco.api.service.BiospecimenService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class BiospecimenServiceImpl implements BiospecimenService {
	
	private final static Logger logger = Logger.getLogger(BiospecimenServiceImpl.class);
	
	@Autowired
	private BiospecimenDao biospecimenDao;
	
	@Autowired
	private Dao dao;

	@Override
	public List<TbTBiospecimen> listPaginatedBiospecimenUsingQuery(Map<String, Object> criteria) {
		
		try {
			String hn = AppUtil.getMapValue(criteria, "hn");
			String status = AppUtil.getMapValue(criteria, "status");
			
			// Paging
			int startIndex =  AppUtil.getMapValue(criteria, "startIndex");
			int fetchSize = AppUtil.getMapValue(criteria, "fetchSize");
			String sortBy = AppUtil.getMapValue(criteria, "sortBy");
			Boolean sortAsc = AppUtil.getMapValue(criteria, "sortAsc");
			
			logger.info(String.format("I:--START--:--Search Biospecimen--:hn/%s:status/%s:startIndex/%s:fetchSize/%s:sortBy/%s:sortAsc/%s", hn, status, startIndex, fetchSize, sortBy, sortAsc));
			List<TbTBiospecimen> biospecimenList = biospecimenDao.listPaginatedBiospecimenUsingQuery(criteria);
			logger.info("O:--SUCCESS--:--Search Biospecimen--");
			return biospecimenList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Search Biospecimen--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public int countBiospecimenUsingQuery(Map<String, Object> criteria) {
		
		try {
			logger.info(String.format("I:--START--:--Get Biospecimen PaginatedCount--:refNo/%s", criteria.get("refNo")));
			int count = biospecimenDao.countBiospecimenUsingQuery(criteria);
			logger.info("O:--SUCCESS--:--Get ClinicalData PaginatedCount--");
			return count;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Biospecimen PaginatedCount--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbTBiospecimen getBiospecimenById(Long biospecimenId, String status) {

		try {
			logger.info(String.format("I:--START--:--Get Biospecimen by ID--:biospecimenId/%s:status/%s", biospecimenId, status));
			TbTBiospecimen biospecimen = biospecimenDao.getBiospecimenById(biospecimenId, status);
			logger.info("O:--SUCCESS--:--Get Biospecimen by ID--");
			return biospecimen;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Biospecimen by ID--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteBiospecimen(TbTBiospecimen biospecimen) {
		
		try {
			logger.info(String.format("I:--START--:--Delete ClinicalData--:id/%s", biospecimen.getId()));
			biospecimen.setStatus(AppConstants.STATUS_INACTIVE);
			dao.saveOrUpdate(biospecimen);
			logger.info("O:--SUCCESS--:--Delete Biospecimen--");
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Delete Biospecimen--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public void saveBiospecimen(TbTBiospecimen biospecimen) {
		
		try {
			logger.info("I:--START--:--Save Biospecimen--");
			
			if(biospecimen != null) {
				dao.saveOrUpdate(biospecimen);
			}
			
			logger.info("O:--SUCCESS--:--Save Biospecimen--");
			
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Save Biospecimen--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public boolean findDuplicateBioRef(Long biospecimenId, String refNo) {
		
		try {
			logger.info(String.format("I:--START--:--Find Duplicate BioRef--:biospecimenId/%s:refNo/%s", biospecimenId, refNo));
			boolean isDuplicate = biospecimenDao.findDuplicateBioRef(biospecimenId, refNo);
			logger.info("O:--SUCCESS--:--Find Duplicate BioRef--");
			return isDuplicate;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Find Duplicate --:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
	
}
