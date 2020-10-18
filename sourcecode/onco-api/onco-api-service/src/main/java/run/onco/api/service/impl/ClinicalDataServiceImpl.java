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
import run.onco.api.persist.dao.BiospecimenDao;
import run.onco.api.persist.dao.ClinicalCourseDao;
import run.onco.api.persist.dao.ClinicalDataDao;
import run.onco.api.persist.dao.DiagnosisDao;
import run.onco.api.persist.dao.impl.Dao;
import run.onco.api.persist.entity.TbTBiospecimen;
import run.onco.api.persist.entity.TbTCancerStage;
import run.onco.api.persist.entity.TbTClinicalCourse;
import run.onco.api.persist.entity.TbTClinicalData;
import run.onco.api.persist.entity.TbTDiagnosis;
import run.onco.api.persist.entity.TbTPathological;
import run.onco.api.service.ClinicalDataService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class ClinicalDataServiceImpl implements ClinicalDataService {

	private final static Logger logger = Logger.getLogger(ClinicalDataServiceImpl.class);
	
	@Autowired
	private Dao dao;

	@Autowired
	private DiagnosisDao diagnosisDao;
	
	@Autowired
	private ClinicalDataDao clinicalDataDao;
	
	@Autowired
	private ClinicalCourseDao clinicalCourseDao;
	
	@Autowired
	private BiospecimenDao biospecimenDao;
	
	@Override
	@Transactional(readOnly = false)
	@SuppressWarnings("unchecked")
	public void saveClinicalData(HashMap<String, Object> map) {
		
		try {
			logger.info("I:--START--:--Save ClinicalData--");
			
			TbTClinicalData clinicalData = (TbTClinicalData) map.get("CLINICAL_DATA");
			List<TbTDiagnosis> diagnosisList = (List<TbTDiagnosis>) map.get("DIAGNOSIS_LIST");
			
			if(clinicalData != null) {
				dao.saveOrUpdate(clinicalData);
			}
			
			if(diagnosisList != null && diagnosisList.size() > 0) {
				
				for (TbTDiagnosis diagnosis : diagnosisList) {
					logger.info(String.format("O:--Get Diagnosis--:diagnosisId/%s:status/%s", diagnosis.getId(), diagnosis.getStatus()));
					dao.saveOrUpdate(diagnosis);
					
					// Pathological
					List<TbTPathological> pathoList = diagnosis.getPathoList();
					
					if (pathoList != null && pathoList.size() > 0) {
						for (TbTPathological patho : pathoList) {
							logger.info(String.format("O:--Get Patho--:pathoId/%s:status/%s", patho.getId(), patho.getStatus()));
							dao.saveOrUpdate(patho);
						}
					}
					
					// CancerStage
					TbTCancerStage cancerStage = diagnosis.getCancerStage();
					
					if (cancerStage != null) {
						logger.info(String.format("O:--Get CancerStage--:cancerStageId/%s", cancerStage.getId()));
						dao.saveOrUpdate(cancerStage);
					}
					
					// ClinicalCourse
					List<TbTClinicalCourse> clinicalCourseList = diagnosis.getClinicalCourseList();
					
					if (clinicalCourseList != null && clinicalCourseList.size() > 0) {
						for (TbTClinicalCourse clinicalCourse : clinicalCourseList) {
							logger.info(String.format("O:--Get ClinicalCourse--:clinicalCourseId/%s:status/%s", clinicalCourse.getId(), clinicalCourse.getStatus()));
							dao.saveOrUpdate(clinicalCourse);
						}
					}
				}
			}
			
			logger.info("O:--SUCCESS--:--Save ClinicalData--");
			
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Save ClinicalData--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbTClinicalData getClinicalDataByRef(final String ref, final String status) {
		
		try {
			logger.info(String.format("I:--START--:--Get ClinicalData by Ref--:ref/%s:status/%s", ref, status));
			TbTClinicalData clinicalData = clinicalDataDao.getClinicalDataByRef(ref, status);
			logger.info("O:--SUCCESS--:--Get ClinicalData by Ref--");
			return clinicalData;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get ClinicalData by Ref--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
	
	@Override
	public List<TbTDiagnosis> getDiagnosisListByClinicalDataId(Long clinicalDataId) {
		try {
			logger.info(String.format("I:--START--:--Get DiagnosisList by ClinicalDataId--:clinicalDataId/%s", clinicalDataId));
			List<TbTDiagnosis> diagnosisList = diagnosisDao.getDiagonoseListByClinicalDataId(clinicalDataId);
			logger.info("O:--SUCCESS--:--Get DiagnosisList by ClinicalDataId");
			return diagnosisList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get DiagnosisList by ClinicalDataId--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbTClinicalData> listPaginatedClinicalDataUsingQuery(Map<String, Object> criteria, Paging paging) {
		
		try {
			String refNo = AppUtil.getMapValue(criteria, "refNo");
			String status = AppUtil.getMapValue(criteria, "status");
			
			logger.info(String.format("I:--START--:--Search ClinicalData--:refNo/%s:status/%s", refNo, status));
			List<TbTClinicalData> clinicalDataList = clinicalDataDao.listPaginatedClinicalDataUsingQuery(criteria, paging);
			logger.info("O:--SUCCESS--:--Search ClinicalData--");
			return clinicalDataList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Search ClinicalData--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public int countClinicalDataUsingQuery(Map<String, Object> criteria, Paging paging) {
		
		try {
			logger.info(String.format("I:--START--:--Get ClinicalData PaginatedCount--:refNo/%s", criteria.get("refNo")));
			int count = clinicalDataDao.countClinicalDataUsingQuery(criteria, paging);
			logger.info("O:--SUCCESS--:--Get ClinicalData PaginatedCount--");
			return count;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get ClinicalData PaginatedCount--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public void deleteClinicalData(TbTClinicalData clinicalData) {
		
		try {
			logger.info(String.format("I:--START--:--Delete ClinicalData--:refNo/%s", clinicalData.getRefNo()));
			clinicalData.setStatus(AppConstants.STATUS_INACTIVE);
			dao.saveOrUpdate(clinicalData);
			logger.info("O:--SUCCESS--:--Delete ClinicalData--");
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Delete ClinicalData--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbTBiospecimen getBiospecimenById(Long biospecimenId) {
		
		try {
			logger.info(String.format("I:--START--:--Get Biospecimen by ID--:biospecimenId/%s", biospecimenId));
			TbTBiospecimen biospecimen = dao.get(TbTBiospecimen.class, biospecimenId);
			logger.info("O:--SUCCESS--:--Get Biospecimen by ID--");
			return biospecimen;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Biospecimen by ID--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbTPathological getPathologicalById(Long pathoId) {
		
		try {
			logger.info(String.format("I:--START--:--Get Patho by ID--:pathoId/%s", pathoId));
			TbTPathological patho = dao.get(TbTPathological.class, pathoId);
			logger.info("O:--SUCCESS--:--Get Patho by ID--");
			return patho;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Patho by ID--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbTCancerStage getCancerStageById(Long cancerStageId) {
		
		try {
			logger.info(String.format("I:--START--:--Get CancerStage by ID--:cancerStageId/%s", cancerStageId));
			TbTCancerStage cancerStage = dao.get(TbTCancerStage.class, cancerStageId);
			logger.info("O:--SUCCESS--:--Get CancerStage by ID--");
			return cancerStage;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get CancerStage by ID--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbTClinicalCourse getClinicalCourseById(Long clinicalCourseId) {
		
		try {
			logger.info(String.format("I:--START--:--Get ClinicalCourse by ID--:clinicalCourseId/%s", clinicalCourseId));
			TbTClinicalCourse clinicalCourse = dao.get(TbTClinicalCourse.class, clinicalCourseId);
			logger.info("O:--SUCCESS--:--Get ClinicalCourse by ID--");
			return clinicalCourse;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get ClinicalCourse by ID--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbTClinicalData getClinicalDataById(Long clinicalDataId, String status) {
		
		try {
			logger.info(String.format("I:--START--:--Get ClinicalData by ID--:clinicalDataId/%s:status/%s", clinicalDataId, status));
			TbTClinicalData clinicalData = clinicalDataDao.getClinicalDataById(clinicalDataId, status);
			logger.info("O:--SUCCESS--:--Get ClinicalData by ID--");
			return clinicalData;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get ClinicalData by ID--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbTDiagnosis getDiagnosisById(Long diagnosisId) {
		
		try {
			logger.info(String.format("I:--START--:--Get Diagnosis by ID--:diagnosisId/%s", diagnosisId));
			TbTDiagnosis diagnosis = dao.get(TbTDiagnosis.class, diagnosisId);
			logger.info("O:--SUCCESS--:--Get Diagnosis by ID--");
			return diagnosis;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get ClinicalData by ID--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbTClinicalCourse> getClinicalCourseListByDiagnosisId(Long diagnosisId, String status) {

		try {
			logger.info(String.format("I:--START--:--Get ClinicalCourseList by DiagnosisID--:diagnosisId/%s:status/%s", diagnosisId, status));
			List<TbTClinicalCourse> clinicalCourseList = clinicalCourseDao.getClinicalCourseListByDiagnosisId(diagnosisId, status);
			logger.info("O:--SUCCESS--:--Get ClinicalCourseList by DiagnosisID--");
			return clinicalCourseList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get ClinicalCourseList by DiagnosisID--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbTBiospecimen> getActiveฺBiospecimenListByPatientId(Long patientId) {
		
		try {
			logger.info(String.format("I:--START--:--Get Active BiospecimenList by PatientID--:patientId/%s", patientId));
			List<TbTBiospecimen> biospecimenList = biospecimenDao.getActiveฺBiospecimenListByPatientId(patientId);
			logger.info("O:--SUCCESS--:--Get Active BiospecimenList by PatientId--");
			return biospecimenList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Active BiospecimenList by PatientID--:errorDesc/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
}
