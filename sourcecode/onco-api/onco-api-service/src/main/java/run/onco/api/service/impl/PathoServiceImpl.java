package run.onco.api.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.dto.Paging;
import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.PathologicalDao;
import run.onco.api.persist.entity.TbTPathological;
import run.onco.api.service.PathoService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class PathoServiceImpl implements PathoService {

	private final static Logger logger = Logger.getLogger(PathoServiceImpl.class);
	
	@Autowired
	private PathologicalDao pathologicalDao;

	@Override
	public List<TbTPathological> getPathoListByDiagnosisId(Long diagnosisId, String status) {
		
		try {
			logger.info(String.format("I:--START--:--Get PathoList by diagnosisId--:diagnosisId/%s:status/%s", diagnosisId, status));
			List<TbTPathological> pathoList = pathologicalDao.getPathoListByDiagnosisId(diagnosisId, status);
			logger.info("O:--SUCCESS--:--Get PathoList by diagnosisId--");
			return pathoList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get PathoList by diagnosisId--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
	
	@Override
	public List<TbTPathological> searchPatientPathoByPathoNo(Long patientId, String pathoNo, String status, Paging paging) {
		
		try {
			int pageNo = paging.getStartIndex();
			int pageSize = paging.getFetchSize();
			
			logger.info(String.format("I:--START--:--Search PatientPatho by PathoNo--:patientId/%s:pathoNo/%s:status/%s:pageNo/%s:pageSize/%s", patientId, pathoNo, status, pageNo, pageSize));
			List<TbTPathological> pathoList = pathologicalDao.searchPatientPathoByPathoNo(patientId, pathoNo, status, paging.getStartIndex(), paging.getFetchSize());
			logger.info(String.format("O:--SUCCESS--:--Search PatientPatho by Ref--:doesObjectExist/%s", AppUtil.isNotEmpty(pathoList)));
			return pathoList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Search PatientPatho by PathoNo--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public boolean findDuplicatePathoNo(Long pathoId, String pathoNo) {
		
		try {
			logger.info(String.format("I:--START--:--Find Duplicate PathoNo--:pathoId/%s:pathoNo/%s", pathoId, pathoNo));
			boolean isDuplicate = pathologicalDao.findDuplicatePathoNo(pathoId, pathoNo);
			logger.info(String.format("O:--SUCCESS--:--Find Duplicate PathoNo--:isDuplicate/%s", isDuplicate));
			return isDuplicate;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Find Duplicate PathoNo--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
}
