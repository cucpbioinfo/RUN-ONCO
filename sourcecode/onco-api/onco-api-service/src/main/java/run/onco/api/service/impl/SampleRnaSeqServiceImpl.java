package run.onco.api.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.SampleRnaSeqDao;
import run.onco.api.persist.dao.impl.Dao;
import run.onco.api.persist.entity.TbTAttachment;
import run.onco.api.persist.entity.TbTSampleRnaSeq;
import run.onco.api.service.SampleRnaSeqService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class SampleRnaSeqServiceImpl implements SampleRnaSeqService {

private final static Logger logger = Logger.getLogger(RnaSeqServiceImpl.class);
	
	@Autowired
	private Dao dao;
	
	@Autowired
	private SampleRnaSeqDao sampleRnaSeqDao;

	@Override
	@Transactional(readOnly = false)
	public void uploadSampleRnaSeq(TbTSampleRnaSeq sampleRnaSeq) {

		try {
			logger.info("I:--START--:--Upload SampleRnaSeq--");
			dao.saveOrUpdate(sampleRnaSeq);

			TbTAttachment attachment = sampleRnaSeq.getAttachment();
			dao.saveOrUpdate(attachment);
			
			logger.info("O:--SUCCESS--:--Upload SampleRnaSeq--");
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Save SampleRnaSeq--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbTSampleRnaSeq getSampleRnaSeqById(Long sampleId) {
		
		try {
			logger.info(String.format("I:--START--:--Get SampleRnaSeq--:sampleId/%s", sampleId));
			TbTSampleRnaSeq sampleRnaSeq = dao.get(TbTSampleRnaSeq.class, sampleId);
			logger.info("O:--SUCCESS--:--Get SampleRnaSeq--");
			return sampleRnaSeq;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get SampleRnaSeq--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbTSampleRnaSeq> getSampleRnaSeqListByPatientId(Long patientId) {
		
		try {
			logger.info(String.format("I:--START--:--Get SampleRnaSeq List by PatientId--:patientId/%s", patientId));
			List<TbTSampleRnaSeq> rnaSeqList = sampleRnaSeqDao.getRnaSeqListByPatientId(patientId);
			logger.info("O:--SUCCESS--:--Get SampleRnaSeq List by PatientId");
			return rnaSeqList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get SampleRnaSeq List by PatientId--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
	
	@Override
	public int countSampleRnaSeqUsingQuery(String status) {
		
		try {
			logger.info("I:--START--:--Get SampleRnaSeq PaginatedCount--");
			int count = sampleRnaSeqDao.countSampleRnaSeqUsingQuery(status);
			logger.info("O:--SUCCESS--:--Get SampleRnaSeq PaginatedCount--");
			return count;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get SampleRnaSeq PaginatedCount--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbTSampleRnaSeq> listPaginatedSampleRnaSeqUsingQuery(String status, int startIndex, int fetchSize) {
		
		try {
			logger.info(String.format("I:--START--:--Get SampleRnaSeq List--:startIndex/%s:fetchSize/%s", startIndex, fetchSize));
			List<TbTSampleRnaSeq> sampleRnaSeqList = sampleRnaSeqDao.listPaginatedSampleRnaSeqUsingQuery(status, startIndex, fetchSize);
			logger.info("O:--SUCCESS--:--Get SampleRnaSeq List--");
			return sampleRnaSeqList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get SampleRnaSeq List--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
	
	@Override
	public List<TbTSampleRnaSeq> getSampleRnaSeqListByBiospecimens(Long[] ids) {
		
		try {
			logger.info("I:--START--:--Get SampleRnaSeq List by IndivPatient--");
			List<TbTSampleRnaSeq> sampleRnaSeqList = sampleRnaSeqDao.getSampleRnaSeqListByBiospecimens(ids);
			logger.info("O:--SUCCESS--:--Get SampleRnaSeq List by IndivPatient--");
			return sampleRnaSeqList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get RnaSeqList by IndivPatient--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
	
	@Override
	public List<TbTSampleRnaSeq> getSampleRnaSeqListBySampleIds(Long[] ids) {
		
		try {
			logger.info("I:--START--:--Get SampleRnaSeq List for SampleIds--");
			List<TbTSampleRnaSeq> sampleRnaSeqList = sampleRnaSeqDao.getSampleRnaSeqListBySampleIds(ids);
			logger.info("O:--SUCCESS--:--Get SampleRnaSeq List for SampleIds--");
			return sampleRnaSeqList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get SampleRnaSeq List for SampleIds--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbTSampleRnaSeq> getActiveSampleRnaSeqList() {
		
		try {
			logger.info("I:--START--:--Get Active SampleRnaSeq List--");
			List<TbTSampleRnaSeq> sampleRnaSeqList = sampleRnaSeqDao.getActiveSampleRnaSeqList();
			logger.info("O:--SUCCESS--:--Get Active SampleRnaSeq List--");
			return sampleRnaSeqList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Active SampleRnaSeq List--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public boolean findDuplicateUploadSampleRnaSeq(Long biospecimenId) {
		
		try {
			logger.info("I:--START--:--Find Duplicate Upload RnaSeq--");
			boolean isDuplicate = sampleRnaSeqDao.findDuplicateUploadSampleRnaSeq(biospecimenId);
			logger.info("O:--SUCCESS--:--Find Duplicate Upload RnaSeq--");
			return isDuplicate;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Find Duplicate Upload RnaSeq--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
}
