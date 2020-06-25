package run.onco.batch.api.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.constants.ConfigurationConstants;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.FileUtil;
import run.onco.api.common.vcf.VcfFileReader;
import run.onco.api.common.vcf.model.VariantAnnotationBean;
import run.onco.api.common.vcf.model.VariantCallBean;
import run.onco.api.common.vcf.model.VariantResultBean;
import run.onco.api.persist.entity.TbTAttachment;
import run.onco.api.persist.entity.TbTSampleVariant;
import run.onco.api.persist.entity.TbTVariantAnnotation;
import run.onco.api.persist.entity.TbTVariantCall;
import run.onco.api.persist.entity.TbTVariantResult;
import run.onco.api.service.AttachmentService;
import run.onco.api.service.SampleVcfService;
import run.onco.api.service.VariantCallService;
import run.onco.batch.api.service.VariantCallBatchService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class VariantCallBatchServiceImpl implements VariantCallBatchService {

	private final static Logger logger = Logger.getLogger(VariantCallBatchServiceImpl.class);
	
	@Autowired
	private Environment env;

	@Autowired
	private VariantCallService variantCallService;
	
	@Autowired
	private SampleVcfService sampleVcfService;
	
	@Autowired
	private AttachmentService attachmentService;
	
	private String getString(String key) {
		return env.getProperty(key);
	}

	@Override
	public void importVariantCallFiles() throws IOException {
		
		logger.info("I:--START--:--Import Variant Call Files--");
		
		int startIndex = 0;
		int fetchSize = 2;
		
		HashMap<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("status", AppConstants.STATUS_DRAFT);
		criteria.put("startIndex", startIndex);
		criteria.put("fetchSize", fetchSize);
		
		int totalRecords = sampleVcfService.countSampleVcfUsingQuery(criteria);
		
		if (totalRecords > 0) {
			
			List<TbTSampleVariant> sampleVcfList = sampleVcfService.listPaginatedSampleVcfUsingQuery(criteria);
			
			logger.debug(String.format("O:--Get SampleVcfList--:sampleVcfList size/%s", !AppUtil.isObjectEmpty(sampleVcfList) ? sampleVcfList.size() : 0));
			
			for (TbTSampleVariant sampleVcf : sampleVcfList) {
				
				sampleVcfService.updateSampleVcfStatus(sampleVcf.getId(), AppConstants.STATUS_PROCESSING);
				
				Long attachmentId = sampleVcf.getAttachment().getId();
				TbTAttachment attachment = attachmentService.getAttachmentById(attachmentId);
				
				if (attachment != null) {
					String relativePath = String.format("%s/%s", attachment.getFilePath(), attachment.getFileName());
					String absolutePath = String.format("%s%s", getString(ConfigurationConstants.DATA_FILE_PATH), relativePath);
					
					logger.debug(String.format("O:--Get Attachment--:relativePath/%s:absolutePath/%s", relativePath, absolutePath));
					
					byte[] byteArray = FileUtil.readBytesFromFile(absolutePath);
					List<VariantCallBean> variantCallBeanList = VcfFileReader.parse(byteArray);
							
					List<TbTVariantCall> variantCallList = new ArrayList<TbTVariantCall>();
	
					for (VariantCallBean variantCallBean : variantCallBeanList) {
						TbTVariantCall variantCall = new TbTVariantCall();
						variantCall.setChromosome(variantCallBean.getChromosome());
						variantCall.setVariant(variantCallBean.getVariant());
						variantCall.setPosition(variantCallBean.getPosition());
						variantCall.setRefAllele(variantCallBean.getRefAllele());
						variantCall.setAltAllele(variantCallBean.getAltAllele());
						variantCall.setQuality(variantCallBean.getQuality());
						variantCall.setFilter(variantCallBean.getFilter());
	
						List<TbTVariantAnnotation> variantAnnotationList = new ArrayList<TbTVariantAnnotation>();
						List<VariantAnnotationBean> variantAnnotationBeanList = variantCallBean.getVariantAnnotationList();
	
						for (VariantAnnotationBean variantAnnotationBean : variantAnnotationBeanList) {
							TbTVariantAnnotation variantAnnotation = new TbTVariantAnnotation();
							variantAnnotation.setAllele(variantAnnotationBean.getAllele());
							variantAnnotation.setConsequence(variantAnnotationBean.getConsequence());
							variantAnnotation.setImpact(variantAnnotationBean.getImpact());
							variantAnnotation.setSymbol(variantAnnotationBean.getSymbol());
							variantAnnotation.setGene(variantAnnotationBean.getGene());
							variantAnnotation.setFeatureType(variantAnnotationBean.getFeatureType());
							variantAnnotation.setFeature(variantAnnotationBean.getFeature());
							variantAnnotation.setBiotype(variantAnnotationBean.getBiotype());
							variantAnnotation.setExon(variantAnnotationBean.getExon());
							variantAnnotation.setIntron(variantAnnotationBean.getIntron());
							variantAnnotation.setHgvsc(variantAnnotationBean.getHgvsc());
							variantAnnotation.setHgvsp(variantAnnotationBean.getHgvsp());
							variantAnnotation.setCdnaPosition(variantAnnotationBean.getCdnaPosition());
							variantAnnotation.setCdsPosition(variantAnnotationBean.getCdsPosition());
							variantAnnotation.setProteinPosition(variantAnnotationBean.getProteinPosition());
							variantAnnotation.setAminoAcids(variantAnnotationBean.getAminoAcids());
							variantAnnotation.setCodons(variantAnnotationBean.getCodons());
							variantAnnotation.setExistingVariantion(variantAnnotationBean.getExistingVariantion());
							variantAnnotation.setDistance(variantAnnotationBean.getDistance());
							variantAnnotation.setStrand(variantAnnotationBean.getStrand());
							variantAnnotation.setFlags(variantAnnotationBean.getFlags());
							variantAnnotation.setSymbolSource(variantAnnotationBean.getSymbolSource());
							variantAnnotation.setHgncId(variantAnnotationBean.getHgncId());
							variantAnnotation.setVariantCall(variantCall);
							variantAnnotation.setSampleVariant(sampleVcf);
							variantAnnotationList.add(variantAnnotation);
						}
							
						List<TbTVariantResult> variantResultList = new ArrayList<TbTVariantResult>();
						List<VariantResultBean> variantResultBeanList = variantCallBean.getVariantResultList();
							
						for (VariantResultBean variantResultBean : variantResultBeanList) {
							TbTVariantResult variantResult = new TbTVariantResult();
							variantResult.setGenotype(variantResultBean.getGenotype());
							variantResult.setRefAlleleDepth(variantResultBean.getRefAlleleDepth());
							variantResult.setAltAlleleDepth(variantResultBean.getAltAlleleDepth());
							variantResult.setAlleleFrequency(variantResultBean.getAlleleFrequency());
							variantResult.setAltAlleleF1r2(variantResultBean.getAltAlleleF1r2());
							variantResult.setAltAlleleF2r1(variantResultBean.getAltAlleleF2r1());
							variantResult.setOxogReadCounts(variantResultBean.getOxogReadCounts());
							variantResult.setRefAlleleQss(variantResultBean.getRefAlleleQss());
							variantResult.setAltAlleleQss(variantResultBean.getAltAlleleQss());
							variantResult.setRefAlleleF1r2(variantResultBean.getRefAlleleF1r2());
							variantResult.setRefAlleleF2r1(variantResultBean.getRefAlleleF2r1());
							variantResult.setSampleType(variantResultBean.getSampleType());
							variantResult.setSampleVariant(sampleVcf);
							variantResult.setVariantCall(variantCall);
							variantResultList.add(variantResult);
						}
							
						variantCall.setVariantResultList(variantResultList);
						variantCall.setVariantAnnotationList(variantAnnotationList);
						variantCall.setSampleVariant(sampleVcf);
							
						variantCallList.add(variantCall);
					}

					variantCallService.saveVariantCallList(sampleVcf.getId(), variantCallList);
				}
			}	
		}
		
		logger.info("O:--SUCCESS--:--Import Variant Call Files--");
	}

	@Override
	public void annotateVariant() {
		
		logger.info("I:--START--:--Annotate Variant--");

		int startIndex = 0;
		int fetchSize = 2;
		
		HashMap<String, Object> criteria = new HashMap<String, Object>();
		criteria.put("status", AppConstants.STATUS_WAIT_VARIANTS_ANNOTATED);
		criteria.put("startIndex", startIndex);
		criteria.put("fetchSize", fetchSize);
		
		int totalRecords = sampleVcfService.countSampleVcfUsingQuery(criteria);
		
		if (totalRecords > 0) {
			
			List<TbTSampleVariant> sampleVcfList = sampleVcfService.listPaginatedSampleVcfUsingQuery(criteria);
			
			logger.debug(String.format("O:--Get SampleVcfList--:sampleVcfList size/%s", !AppUtil.isObjectEmpty(sampleVcfList) ? sampleVcfList.size() : 0));
			
			for (TbTSampleVariant sampleVcf : sampleVcfList) {
				sampleVcfService.updateSampleVcfStatus(sampleVcf.getId(), AppConstants.STATUS_PROCESSING);
				Map<Long, Integer[]> hashMap = variantCallService.getAnnotateVariantListBySampleId(sampleVcf.getId());
				
				if (hashMap != null) {
					variantCallService.saveAnnotateVariant(sampleVcf.getId(), hashMap);
				}
			}
		}
		
		logger.info("O:--SUCCESS--:--Annotate Variant--");
	}
}
