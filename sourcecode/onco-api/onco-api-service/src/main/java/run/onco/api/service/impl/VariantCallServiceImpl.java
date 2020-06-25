package run.onco.api.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.exception.DbException;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.DateUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.dao.ActionableVariantDao;
import run.onco.api.persist.dao.VariantCallDao;
import run.onco.api.persist.dao.impl.Dao;
import run.onco.api.persist.entity.TbMActionableVariant;
import run.onco.api.persist.entity.TbTAttachment;
import run.onco.api.persist.entity.TbTSampleVariant;
import run.onco.api.persist.entity.TbTVariantAnnotation;
import run.onco.api.persist.entity.TbTVariantCall;
import run.onco.api.service.VariantCallService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Transactional(readOnly = true)
public class VariantCallServiceImpl implements VariantCallService {

	private final static Logger logger = Logger.getLogger(VariantCallServiceImpl.class);

	@Autowired
	private Dao dao;

	@Autowired
	private VariantCallDao variantCallDao;
	
	@Autowired
	private ActionableVariantDao actionableVariantDao;
	
	@Override
	@Transactional(readOnly = false)
	public void deleteVariantCall(TbTSampleVariant sampleVariant) {

		try {
			Long sampleId = sampleVariant.getId();
			logger.info(String.format("I:--START--:--Delete VariantCall--:sampleId/%s", sampleId));

			// Delete variant annotation 
			variantCallDao.deleteVariantAnnotations(sampleId);
			
			// Delete variant result
			variantCallDao.deleteVariantResults(sampleId);
			
			// Delete variant call
			variantCallDao.deleteVariantcalls(sampleId);
			
			// Delete attachment
			TbTAttachment attachment = sampleVariant.getAttachment();
			
			if (attachment != null) {
				dao.delete(attachment);
			}
			
			dao.delete(sampleVariant);
			logger.info("O:--SUCCESS--:--Delete VariantCall--");
			
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Delete VariantCall--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbTVariantCall> getVariantCallListBySampleId(Long sampleId) {

		try {
			logger.info(String.format("I:--START--:--Get VariantCall List--:sampleId/%s", sampleId));
			List<TbTVariantCall> variantCallList = variantCallDao.getVariantCallListBySampleId(sampleId);
			logger.info("O:--SUCCESS--:--Get VariantCall List--");
			return variantCallList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get VariantCall List--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbTVariantAnnotation> listPaginatedVariantsUsingQuery(Map<String, Object> criteria) {

		try {
			Long sampleId = AppUtil.getMapValue(criteria, "sampleId");
			
			// Paging
			int startIndex =  AppUtil.getMapValue(criteria, "startIndex");
			int fetchSize = AppUtil.getMapValue(criteria, "fetchSize");
			String sortBy = AppUtil.getMapValue(criteria, "sortBy");
			Boolean sortAsc = AppUtil.getMapValue(criteria, "sortAsc");
			
			logger.info(String.format("I:--START--:--Get VariantAnnotation List--:sampleId/%s:startIndex/%s:fetchSize/%s:sortBy/%s:sortAsc/%s", sampleId, startIndex, fetchSize, sortBy, sortAsc));
			List<TbTVariantAnnotation> variantAnnotationList = variantCallDao.listPaginatedVariantsUsingQuery(criteria);
			logger.info("O:--SUCCESS--:--Get VariantAnnotation List--");
			return variantAnnotationList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get VariantAnnotation List--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public int countVariantsUsingQuery(Map<String, Object> criteria) {

		try {
			logger.info(String.format("I:--START--:--Get VariantCall PaginatedCount--:variantCallId/%s", criteria.get("sampleId")));
			int count = variantCallDao.countVariantsUsingQuery(criteria);
			logger.info("O:--SUCCESS--:--Get VariantAnnotation PaginatedCount--");
			return count;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get VariantCall PaginatedCount--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<TbMActionableVariant> getActinableVariantList(String transcript) {
		
		try {
			logger.info(String.format("I:--START--:--Get ActionableVariantList--:transcript/%s", transcript));
			List<TbMActionableVariant> actionableVariantList = actionableVariantDao.getActionableVariantList(transcript);
			logger.info("O:--SUCCESS--:--Get ActionableVariantList--");
			return actionableVariantList;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get ActionableVariantList--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}
	
	@Override
	public List<TbTVariantAnnotation> getVariantAnnotationListByTranscript(String transcript) {
		
		try {
			logger.info(String.format("I:--START--:--Get VariantAnnotation List--:transcript/%s", transcript));
			List<TbTVariantAnnotation> variantAnnotations = variantCallDao.getVariantAnnotationListByTranscript(transcript);
			logger.info("O:--SUCCESS--:--Get VariantAnnotation List--");
			return variantAnnotations;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get VariantAnnotation List--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public TbTVariantAnnotation getVariantAnnotationById(Long variantId) {
		
		try {
			logger.info(String.format("I:--START--:--Get VariantAnnotation by Id--:variantId/%s", variantId));
			TbTVariantAnnotation variantAnnotation = dao.get(TbTVariantAnnotation.class, variantId);
			logger.info("O:--SUCCESS--:--Get VariantAnnotation by Id--");
			return variantAnnotation;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get VariantAnnotation by Id--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void saveVariantCallList(Long sampleId, List<TbTVariantCall> variantCallList) {
		
		try {
			logger.info("I:--START--:--Save VariantCall List--");
			
			for (TbTVariantCall variantCall : variantCallList) {
				dao.saveOrUpdate(variantCall);
			}

			TbTSampleVariant sampleVcf = dao.get(TbTSampleVariant.class, sampleId);
			sampleVcf.setStatus(AppConstants.STATUS_WAIT_VARIANTS_ANNOTATED);
			sampleVcf.setImportDataDate(DateUtil.getCurrentDate());
			dao.saveOrUpdate(sampleVcf);
			
			logger.info("O:--SUCCESS--:--Save VariantCall List--");
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Save VariantCall List--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public Map<Long, Integer[]> getAnnotateVariantListBySampleId(Long sampleId) {

		try {
			logger.info(String.format("I:--START--:--Get AnnotateVariantList by SampleId--:sampleId/%s", sampleId));
			List<Object[]> results = variantCallDao.getAnnotateVariantListBySampleId(sampleId);
			
			if (results != null) {
				
				List<Long> listWithDuplicates = results.stream().map(x -> (Long)x[0]).collect(Collectors.toList());
				List<Long> listWithoutDuplicates = listWithDuplicates.stream().distinct().collect(Collectors.toList());
				
				HashMap<Long, Integer[]> hashMap = new HashMap<Long, Integer[]>();
				
				for (Long varAnnoId : listWithoutDuplicates) {
					
					Integer[] ids = results.stream().filter(x -> ((Long)x[0]).equals(varAnnoId)).map(x -> (Integer)x[1]).toArray(Integer[]::new);
					hashMap.put(varAnnoId, ids);
					
//					logger.info(String.format("O:--Mapping ID--:varAnnoId/%s:arrLen/%s", varAnnoId, AppUtil.isObjectEmpty(ids) ? 0 : ids.length));
//					
//					for ( Integer id :  ids ) {
//						logger.info(String.format("O:--ID--:id/%s", id));
//					}
				}
				
				return hashMap;
			}
			
			return null;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get AnnotateVariantList by SampleId--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}	
	
	@Override
	@Transactional(readOnly = false)
	public void saveAnnotateVariant(Long sampleId, Map<Long, Integer[]> hashMap) {

		try {
			logger.info(String.format("I:--START--:--Save AnnotateVariant--:sampleId/%s", sampleId));
			
			Iterator<Map.Entry<Long, Integer[]>> it = hashMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry<Long, Integer[]> pair = (Map.Entry<Long, Integer[]>)it.next();
//		        logger.info(pair.getKey() + " = " + (AppUtil.isObjectEmpty(pair.getValue()) ? 0 : StringUtil.join(pair.getValue())));
		        
		        TbTVariantAnnotation variantAnnotation = dao.get(TbTVariantAnnotation.class, pair.getKey());
		        variantAnnotation.setOncoKbAnnotated("Y");
		        dao.saveOrUpdate(variantAnnotation);
		        
		        it.remove(); // avoids a ConcurrentModificationException
		    }
			
		    TbTSampleVariant sampleVcf = dao.get(TbTSampleVariant.class, sampleId);
		    sampleVcf.setStatus(AppConstants.STATUS_ACTIVE);
		    sampleVcf.setAnnotatedDate(DateUtil.getCurrentDate());
		    dao.saveOrUpdate(sampleVcf);
		    
		    logger.info("O:--SUCCESS--:--Save AnnotateVariant--");
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Save AnnotateVariant--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<Object[]> listPaginatedVariantComparisonUsingQuery(Map<String, Object> criteria) {
		
		try {
			// Paging
			int startIndex =  AppUtil.getMapValue(criteria, "startIndex");
			int fetchSize = AppUtil.getMapValue(criteria, "fetchSize");
			String sortBy = AppUtil.getMapValue(criteria, "sortBy");
			Boolean sortAsc = AppUtil.getMapValue(criteria, "sortAsc");
			
			logger.info(String.format("I:--START--:--Get Variant Comparison--:startIndex/%s:fetchSize/%s:sortBy/%s:sortAsc/%s", startIndex, fetchSize, sortBy, sortAsc));
			List<Object[]> result =	variantCallDao.listPaginatedVariantComparisonUsingQuery(criteria);
			logger.info("O:--SUCCESS--:--Get Variant Comparison--");
			return result;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Variant Comparison--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public int countVariantComparisonUsingQuery(Map<String, Object> criteria) {
		
		try {
			logger.info("I:--START--:--Get Count VariantComparison--");
			int count =  variantCallDao.countVariantComparisonUsingQuery(criteria);
			logger.info("O:--SUCCESS--:--Get Count VariantComparison--");
			return count;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Count VariantComparison--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

	@Override
	public List<Object[]> getVariantAnnotationListBySampleId(Long sampleId) {
		
		try {
			logger.info(String.format("I:--START--:--Get VariantAnnotation List by SampleId--:sampleId/%s", sampleId));
			List<Object[]> variants = variantCallDao.getVariantAnnotationListBySampleId(sampleId);
			logger.info("O:--SUCCESS--:--Get VariantAnnotation List by SampleId--");
			return variants;
		} catch (Exception ex) {
			logger.error("DB Exception :\n", ex);
			logger.info(String.format("O:--FAIL--:--Get VariantAnnotation List--:errMsg/%s", ex.getMessage()));
			throw new DbException(MessageCode.ERROR_DATABASE);
		}
	}

}
