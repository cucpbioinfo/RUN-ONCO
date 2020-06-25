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
import run.onco.api.common.utils.DateUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.RnaSeqDao;
import run.onco.api.persist.dao.impl.Dao;
import run.onco.api.persist.entity.TbTAttachment;
import run.onco.api.persist.entity.TbTRnaSeq;
import run.onco.api.persist.entity.TbTSampleRnaSeq;
import run.onco.api.service.RnaSeqService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class RnaSeqServiceImpl implements RnaSeqService {

	private final static Logger logger = Logger.getLogger(RnaSeqServiceImpl.class);
	
	@Autowired
	private Dao dao;
	
	@Autowired
	private RnaSeqDao rnaSeqDao;

//	@Override
//	@Transactional(readOnly = false)
//	public void uploadSampleRnaSeq(TbTSampleRnaSeq sampleRnaSeq) {
//
//		try {
//			logger.info("I:--START--:--Upload SampleRnaSeq--");
//			dao.saveOrUpdate(sampleRnaSeq);
//
//			TbTAttachment attachment = sampleRnaSeq.getAttachment();
//			dao.saveOrUpdate(attachment);
//			
//			logger.info("O:--SUCCESS--:--Upload SampleRnaSeq--");
//		} catch (Exception ex) {
//			logger.error("DB Exception :\n", ex);
//			logger.info(String.format("O:--FAIL--:--Save SampleRnaSeq--:errMsg/%s", ex.getMessage()));
//			throw new DbException(MessageCode.ERROR_DATABASE);
//		}
//	}

	@Override
	@Transactional(readOnly = false)
	public void deleteRnaSeq(TbTSampleRnaSeq sampleRnaSeq) {

		try {

			Long sampleId = sampleRnaSeq.getId();
			logger.info(String.format("I:--START--:--Delete RnaSeq--:sampleId/%s", sampleId));

			// Delete RNA-Seq
			rnaSeqDao.deleteRnaSeqList(sampleId);
			
			// Delete attachment
			TbTAttachment attachment = sampleRnaSeq.getAttachment();

			if (attachment != null) {
				dao.delete(attachment);
			}
			
			dao.delete(sampleRnaSeq);
			logger.info("O:--SUCCESS--:--Delete RnaSeq--");
			
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Delete RnaSeq--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

//	@Override
//	public TbTSampleRnaSeq getSampleRnaSeqById(Long sampleId) {
//		
//		try {
//			logger.info(String.format("I:--START--:--Get SampleRnaSeq--:sampleId/%s", sampleId));
//			TbTSampleRnaSeq sampleRnaSeq = dao.get(TbTSampleRnaSeq.class, sampleId);
//			logger.info("O:--SUCCESS--:--Get SampleRnaSeq--");
//			return sampleRnaSeq;
//		} catch (Exception ex) {
//			logger.error("DB Exception :\n", ex);
//			logger.info(String.format("O:--FAIL--:--Get SampleRnaSeq--:errMsg/%s", ex.getMessage()));
//			throw new DbException(MessageCode.ERROR_DATABASE);
//		}
//	}
//
//	@Override
//	public List<TbTSampleRnaSeq> getSampleRnaSeqListByPatientId(Long patientId) {
//		
//		try {
//			logger.info(String.format("I:--START--:--Get SampleRnaSeq List by PatientId--:patientId/%s", patientId));
//			List<TbTSampleRnaSeq> rnaSeqList = rnaSeqDao.getRnaSeqListByPatientId(patientId);
//			logger.info("O:--SUCCESS--:--Get SampleRnaSeq List by PatientId");
//			return rnaSeqList;
//		} catch (Exception ex) {
//			logger.error("DB Exception :\n", ex);
//			logger.info(String.format("O:--FAIL--:--Get SampleRnaSeq List by PatientId--:errMsg/%s", ex.getMessage()));
//			throw new DbException(MessageCode.ERROR_DATABASE);
//		}
//	}

	@Override
	public int countRnaSeqUsingQuery(Map<String, Object> criteria) {
		
		try {
			logger.info(String.format("I:--START--:--Get RnaSeq PaginatedCount--:variantCallId/%s", criteria.get("sampleId")));
			int count = rnaSeqDao.countRnaSeqUsingQuery(criteria);
			logger.info("O:--SUCCESS--:--Get RnaSeq PaginatedCount--");
			return count;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get RnaSeq PaginatedCount--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbTRnaSeq> listPaginatedRnaSeqUsingQuery(Map<String, Object> criteria) {
		
		try {
			Long sampleId = AppUtil.getMapValue(criteria, "sampleId");
			
			// Paging
			int startIndex =  AppUtil.getMapValue(criteria, "startIndex");
			int fetchSize = AppUtil.getMapValue(criteria, "fetchSize");
			String sortBy = AppUtil.getMapValue(criteria, "sortBy");
			Boolean sortAsc = AppUtil.getMapValue(criteria, "sortAsc");
			
			logger.info(String.format("I:--START--:--Get RnaSeq List--:sampleId/%s:startIndex/%s:fetchSize/%s:sortBy/%s:sortAsc/%s", sampleId, startIndex, fetchSize, sortBy, sortAsc));
			List<TbTRnaSeq> rnaSeqList = rnaSeqDao.listPaginatedRnaSeqUsingQuery(criteria);
			logger.info("O:--SUCCESS--:--Get RnaSeq List--");
			return rnaSeqList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get RnaSeq List--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

//	@Override
//	public int countSampleRnaSeqUsingQuery(String status) {
//		
//		try {
//			logger.info("I:--START--:--Get SampleRnaSeq PaginatedCount--");
//			int count = rnaSeqDao.countSampleRnaSeqUsingQuery(status);
//			logger.info("O:--SUCCESS--:--Get SampleRnaSeq PaginatedCount--");
//			return count;
//		} catch (Exception ex) {
//			logger.error("DB Exception :\n", ex);
//			logger.info(String.format("O:--FAIL--:--Get SampleRnaSeq PaginatedCount--:errMsg/%s", ex.getMessage()));
//			throw new DbException(MessageCode.ERROR_DATABASE);
//		}
//	}
//
//	@Override
//	public List<TbTSampleRnaSeq> listPaginatedSampleRnaSeqUsingQuery(String status, int startIndex, int fetchSize) {
//		
//		try {
//			logger.info(String.format("I:--START--:--Get SampleRnaSeq List--:startIndex/%s:fetchSize/%s", startIndex, fetchSize));
//			List<TbTSampleRnaSeq> sampleRnaSeqList = rnaSeqDao.listPaginatedSampleRnaSeqUsingQuery(status, startIndex, fetchSize);
//			logger.info("O:--SUCCESS--:--Get SampleRnaSeq List--");
//			return sampleRnaSeqList;
//		} catch (Exception ex) {
//			logger.error("DB Exception :\n", ex);
//			logger.info(String.format("O:--FAIL--:--Get SampleRnaSeq List--:errMsg/%s", ex.getMessage()));
//			throw new DbException(MessageCode.ERROR_DATABASE);
//		}
//	}

	@Override
	@Transactional(readOnly = false)
	public void saveRnaSeqList(TbTSampleRnaSeq sampleRnaSeq, List<TbTRnaSeq> rnaSeqList) {
		
		try {
			logger.info("I:--START--:--Save RnaSeq List--");

			for (TbTRnaSeq rnaSeq : rnaSeqList) {
				dao.saveOrUpdate(rnaSeq);
			}

			sampleRnaSeq.setStatus(AppConstants.STATUS_ACTIVE);
			sampleRnaSeq.setImportDataDate(DateUtil.getCurrentDate());
			dao.saveOrUpdate(sampleRnaSeq);
			
			logger.info("O:--SUCCESS--:--Save RnaSeq List--");
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Save RnaSeq List--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<Object[]> getRnaSeqListForClustergrammer(Long sampleId, Integer cancerGeneGrpId) {
		
		try {
			logger.info("I:--START--:--Get RnaSeqList for Clustergrammer--");
			List<Object[]> rnaSeqList = rnaSeqDao.getRnaSeqListForClustergrammer(sampleId, cancerGeneGrpId);
			logger.info("O:--SUCCESS--:--Get RnaSeqList for Clustergrammer--");
			return rnaSeqList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get RnaSeqList for Clustergrammer--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

//	@Override
//	public List<TbTSampleRnaSeq> getSampleRnaSeqListByBiospecimens(Long[] ids) {
//		
//		try {
//			logger.info("I:--START--:--Get SampleRnaSeq List by IndivPatient--");
//			List<TbTSampleRnaSeq> sampleRnaSeqList = rnaSeqDao.getSampleRnaSeqListByBiospecimens(ids);
//			logger.info("O:--SUCCESS--:--Get SampleRnaSeq List by IndivPatient--");
//			return sampleRnaSeqList;
//		} catch (Exception ex) {
//			logger.error("DB Exception :\n", ex);
//			logger.info(String.format("O:--FAIL--:--Get RnaSeqList by IndivPatient--:errMsg/%s", ex.getMessage()));
//			throw new DbException(MessageCode.ERROR_DATABASE);
//		}
//	}

//	@Override
//	public List<Object[]> getRnaSeqListForIdeogram(Long sampleId, Long patientId, Integer cancerGeneGrpId) {
//		
//		try {
//			logger.info("I:--START--:--Get RnaSeqList for Ideogram--");
//			List<Object[]> rnaSeqList = rnaSeqDao.getRnaSeqListForIdeogram(sampleId, patientId, cancerGeneGrpId);
//			logger.info("O:--SUCCESS--:--Get RnaSeqList for Ideogram--");
//			return rnaSeqList;
//		} catch (Exception ex) {
//			logger.error("DB Exception :\n", ex);
//			logger.info(String.format("O:--FAIL--:--Get RnaSeqList for Ideogram--:errMsg/%s", ex.getMessage()));
//			throw new DbException(MessageCode.ERROR_DATABASE);
//		}
//	}

//	@Override
//	public List<TbTSampleRnaSeq> getSampleRnaSeqListBySampleIds(Long[] ids) {
//		
//		try {
//			logger.info("I:--START--:--Get SampleRnaSeq List for SampleIds--");
//			List<TbTSampleRnaSeq> sampleRnaSeqList = rnaSeqDao.getSampleRnaSeqListBySampleIds(ids);
//			logger.info("O:--SUCCESS--:--Get SampleRnaSeq List for SampleIds--");
//			return sampleRnaSeqList;
//		} catch (Exception ex) {
//			logger.error("DB Exception :\n", ex);
//			logger.info(String.format("O:--FAIL--:--Get SampleRnaSeq List for SampleIds--:errMsg/%s", ex.getMessage()));
//			throw new DbException(MessageCode.ERROR_DATABASE);
//		}
//	}
//
//	@Override
//	public List<TbTSampleRnaSeq> getActiveSampleRnaSeqList() {
//		
//		try {
//			logger.info("I:--START--:--Get Active SampleRnaSeq List--");
//			List<TbTSampleRnaSeq> sampleRnaSeqList = rnaSeqDao.getActiveSampleRnaSeqList();
//			logger.info("O:--SUCCESS--:--Get Active SampleRnaSeq List--");
//			return sampleRnaSeqList;
//		} catch (Exception ex) {
//			logger.error("DB Exception :\n", ex);
//			logger.info(String.format("O:--FAIL--:--Get Active SampleRnaSeq List--:errMsg/%s", ex.getMessage()));
//			throw new DbException(MessageCode.ERROR_DATABASE);
//		}
//	}
//
//	@Override
//	public boolean findDuplicateUploadSampleRnaSeq(Long biospecimenId) {
//		
//		try {
//			logger.info("I:--START--:--Find Duplicate Upload RnaSeq--");
//			boolean isDuplicate = rnaSeqDao.findDuplicateUploadSampleRnaSeq(biospecimenId);
//			logger.info("O:--SUCCESS--:--Find Duplicate Upload RnaSeq--");
//			return isDuplicate;
//		} catch (Exception ex) {
//			logger.error("DB Exception :\n", ex);
//			logger.info(String.format("O:--FAIL--:--Find Duplicate Upload RnaSeq--:errMsg/%s", ex.getMessage()));
//			throw new DbException(MessageCode.ERROR_DATABASE);
//		}
//	}

}
