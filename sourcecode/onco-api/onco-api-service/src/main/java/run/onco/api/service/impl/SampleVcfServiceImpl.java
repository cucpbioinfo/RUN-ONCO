package run.onco.api.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.common.utils.StringUtil;
import run.onco.api.persist.dao.SampleVcfDao;
import run.onco.api.persist.dao.impl.Dao;
import run.onco.api.persist.entity.TbTAttachment;
import run.onco.api.persist.entity.TbTSampleVariant;
import run.onco.api.service.SampleVcfService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class SampleVcfServiceImpl implements SampleVcfService {
	
	private final static Logger logger = Logger.getLogger(SampleVcfServiceImpl.class);
	
	@Autowired
	private Dao dao;
	
	@Autowired
	private SampleVcfDao sampleVcfDao;
	
	@Override
	public List<TbTSampleVariant> getSampleVcfListByPatientId(Long patientId) {

		try {
			logger.info(String.format("I:--START--:--Get SampleVcf List by PatientId--:patientId/%s", patientId));
			List<TbTSampleVariant> sampleVcfList = sampleVcfDao.getVariantCallListByPatientId(patientId);
			logger.info("O:--SUCCESS--:--Get SampleVcf List by PatientId");
			return sampleVcfList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get SampleVcf List by PatientId--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbTSampleVariant getSampleVariantById(Long sampleId) {

		try {
			logger.info(String.format("I:--START--:--Get SampleVariant--:sampleId/%s", sampleId));
			TbTSampleVariant sampleVariant = dao.get(TbTSampleVariant.class, sampleId);
			logger.info("O:--SUCCESS--:--Get SampleVariant--");
			return sampleVariant;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get SampleVariant--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public void uploadSampleVcf(TbTSampleVariant sampleVariant) {
		
		try {
			logger.info("I:--START--:--Upload SampleVcf--");
			dao.saveOrUpdate(sampleVariant);

			TbTAttachment attachment = sampleVariant.getAttachment();
			dao.saveOrUpdate(attachment);
			
			logger.info("O:--SUCCESS--:--Upload SampleVcf--");
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Upload SampleVcf--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
	
	@Override
	public List<TbTSampleVariant> listPaginatedSampleVcfUsingQuery(Map<String, Object> criteria) {
		
		try {
			// Paging
			int startIndex =  AppUtil.getMapValue(criteria, "startIndex");
			int fetchSize = AppUtil.getMapValue(criteria, "fetchSize");
			
			logger.info(String.format("I:--START--:--Get SampleVcf List--:startIndex/%s:fetchSize/%s", startIndex, fetchSize));
			List<TbTSampleVariant> sampleVcfList = sampleVcfDao.listPaginatedSampleVcfUsingQuery(criteria);
			logger.info("O:--SUCCESS--:--Get SampleVcf List--");
			return sampleVcfList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get SampleVcf List--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public int countSampleVcfUsingQuery(Map<String, Object> criteria) {
		
		try {
			logger.info("I:--START--:--Get SampleVcf PaginatedCount--");
			int count = sampleVcfDao.countSampleVcfUsingQuery(criteria);
			logger.info("O:--SUCCESS--:--Get SampleVcf PaginatedCount--");
			return count;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get SampleVcf PaginatedCount--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public void updateSampleVcfStatus(Long sampleId, String status) {
		
		try {
			logger.info("I:--START--:--Save SampleVariant--");
			TbTSampleVariant sampleVcf = dao.get(TbTSampleVariant.class, sampleId);
			sampleVcf.setStatus(status);
			dao.saveOrUpdate(sampleVcf);
			logger.info("O:--SUCCESS--:--Save SampleVariant--");
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Save SampleVariant--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
	
	@Override
	public List<TbTSampleVariant> getSampleVcfListByBiospecimenIds(String seqTypeCode, Long[] ids) {
		
		try {
			logger.info(String.format("I:--START--:--Get SampleVcf List by BiospecimenIds--:ids/%s", StringUtil.join(ids)));
			List<TbTSampleVariant> sampleVcfList = sampleVcfDao.getSampleVcfListByBiospecimenIds(seqTypeCode, ids);
			logger.info("O:--SUCCESS--:--Get SampleVcf List by BiospecimenIds--");
			return sampleVcfList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get SampleVcf List by BiospecimenIds--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public boolean findDuplicateUploadSampleVcf(String seqTypeCode, Long biospecimenId) {
		
		try {
			logger.info("I:--START--:--Find Duplicate Upload RnaSeq--");
			boolean isDuplicate = sampleVcfDao.findDuplicateUploadSampleVcf(seqTypeCode, biospecimenId);
			logger.info("O:--SUCCESS--:--Find Duplicate Upload RnaSeq--");
			return isDuplicate;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Find Duplicate Upload RnaSeq--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
}
