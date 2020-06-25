package run.onco.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.dto.Paging;
import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.PatientDao;
import run.onco.api.persist.dao.SurvivalFollowupDao;
import run.onco.api.persist.dao.impl.Dao;
import run.onco.api.persist.entity.TbMPatient;
import run.onco.api.persist.entity.TbTSurvivalFollowup;
import run.onco.api.service.PatientService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class PatientServiceImpl implements PatientService {

	private final static Logger logger = Logger.getLogger(PatientServiceImpl.class);

	@Autowired
	private Dao dao;

	@Autowired
	private PatientDao patientDao;
	
	@Autowired
	private SurvivalFollowupDao survivalFollowupDao;

	@Override
	public TbMPatient getPatientByRef(String ref, String status) {

		try {
			logger.info(String.format("I:--START--:--Get Patient by Ref--:ref/%s:status/%s", ref, status));
			TbMPatient patient = patientDao.getPatientByRef(ref, status);
			logger.info(String.format("O:--SUCCESS--:--Get Patient by Ref--:doesObjectExist/%s", AppUtil.isNotEmpty(patient)));
			return patient;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Patient by Ref--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbMPatient getPatientById(Long patientId, String status) {

		try {
			logger.info(String.format("I:--START--:--Get Patient by ID--:patientId/%s:status/%s", patientId, status));
			TbMPatient patient = patientDao.getPatientById(patientId, status);
			logger.info(String.format("O:--SUCCESS--:--Get Patient by ID--:doesObjectExist/%s", AppUtil.isNotEmpty(patient)));
			return patient;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Patient by ID--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbMPatient> searchPatientByRef(String refNo, String status, Paging paging) {
		
		try {
			int pageNo = paging.getStartIndex();
			int pageSize = paging.getFetchSize();
			
			logger.info(String.format("I:--START--:--Search Patient by Ref--:refNo/%s:status/%s:pageNo/%s:pageSize/%s", refNo, status, pageNo, pageSize));
			List<TbMPatient> patientList = patientDao.searchPatientByRef(refNo, status, paging.getStartIndex(), paging.getFetchSize());
			logger.info(String.format("O:--SUCCESS--:--Search Patient by Ref--:doesObjectExist/%s", AppUtil.isNotEmpty(patientList)));
			return patientList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Search Patient by Ref--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public int countPatientUsingQuery(Map<String, Object> criteria) {
	
		try {
			logger.info(String.format("I:--START--:--Get ClinicalData PaginatedCount--:hn/%s", criteria.get("hn")));
			int count = patientDao.countPatientUsingQuery(criteria);
			logger.info("O:--SUCCESS--:--Get ClinicalData PaginatedCount--");
			return count;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get ClinicalData PaginatedCount--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbMPatient> listPaginatedPatientUsingQuery(Map<String, Object> criteria) {
		
		try {
			String hn = AppUtil.getMapValue(criteria, "hn");
			String status = AppUtil.getMapValue(criteria, "status");
			
			// Paging
			int startIndex =  AppUtil.getMapValue(criteria, "startIndex");
			int fetchSize = AppUtil.getMapValue(criteria, "fetchSize");
			String sortBy = AppUtil.getMapValue(criteria, "sortBy");
			Boolean sortAsc = AppUtil.getMapValue(criteria, "sortAsc");
			
			logger.info(String.format("I:--START--:--Search Patient--:hn/%s:status/%s:startIndex/%s:fetchSize/%s:sortBy/%s:sortAsc/%s", hn, status, startIndex, fetchSize, sortBy, sortAsc));
			List<TbMPatient> patientList = patientDao.listPaginatedPatientUsingQuery(criteria);
			logger.info("O:--SUCCESS--:--Search Patient--");
			return patientList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Search Patient--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = false)
	public void savePatient(HashMap<String, Object> map) {
		
		try {
			logger.info("I:--START--:--Save ClinicalData--");
			
			TbMPatient patient = (TbMPatient) map.get("PATIENT");
			List<TbTSurvivalFollowup> survivalFollowupList = (List<TbTSurvivalFollowup>) map.get("SURVIVAL_FOLLOWUP_LIST");
			
			if(patient != null) {
				dao.saveOrUpdate(patient);
			}
			
			if (survivalFollowupList != null && survivalFollowupList.size() > 0) {
				
				for (TbTSurvivalFollowup survivalFollowup : survivalFollowupList) {
					logger.info(String.format("O:--Get SurvivalFollowup--:survivalFollowupId/%s:status/%s", survivalFollowup.getId(), survivalFollowup.getStatus()));
					dao.saveOrUpdate(survivalFollowup);
				}
			}
			
			logger.info("O:--SUCCESS--:--Save Patient--");
			
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Save ClinicalData--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
	
	@Override
	public TbTSurvivalFollowup getSurvivalFollowupById(Long survivalFollowupId) {
		
		try {
			logger.info(String.format("I:--START--:--Get SurvivalFollowup by ID--:survivalFollowupId/%s", survivalFollowupId));
			TbTSurvivalFollowup survivalFollowup = survivalFollowupDao.getSurvivalFollowupById(survivalFollowupId);
			logger.info("O:--SUCCESS--:--Get SurvivalFollowUp by ID");
			return  survivalFollowup;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get SurvivalFollowup by ID--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deletePatient(TbMPatient patient) {
		
		try {
			logger.info(String.format("I:--START--:--Delete Patient by ID--:id/%s", patient.getId()));
			patient.setStatus(AppConstants.STATUS_INACTIVE);
			dao.saveOrUpdate(patient);
			logger.info("O:--SUCCESS--:--Delete Patient by ID--");
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Delete Patient by ID--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbTSurvivalFollowup> getSurvivalFollowupListByPatientId(Long patientId, String status) {
		
		try {
			logger.info(String.format("I:--START--:--Get survivalFollowupList by PatientId--:survivalFollowupId/%s:status/%s", patientId, status));
			List<TbTSurvivalFollowup> survivalFollowupList = survivalFollowupDao.getSurvivalFollowupListByPatientId(patientId, status);
			logger.info("O:--SUCCESS--:--Get survivalFollowupList by PatientId");
			return  survivalFollowupList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get SurvivalFollowup by ID--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public boolean findDuplicateHn(Long patientId, String hn) {
		
		try {
			logger.info(String.format("I:--START--:--Find Duplicate HN--:patientId/%s:hn/%s", patientId, hn));
			boolean isDuplicate = patientDao.findDuplicateHn(patientId, hn);
			logger.info(String.format("O:--SUCCESS--:--Find Duplicate HN--:isDuplicate/%s", isDuplicate));
			return isDuplicate;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Find Duplicate HN--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
}
