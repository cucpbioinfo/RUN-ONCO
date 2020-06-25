package run.onco.api.business.facade.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import run.onco.api.business.dto.ColumnDefDto;
import run.onco.api.business.dto.MasterDataDto;
import run.onco.api.business.dto.PatientDto;
import run.onco.api.business.dto.SampleVariantDto;
import run.onco.api.business.dto.VariantDto;
import run.onco.api.business.dto.VariantResultDto;
import run.onco.api.business.facade.VariantCallFacade;
import run.onco.api.business.message.InquiryOmics;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;
import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.constants.VcfConstants;
import run.onco.api.common.dto.DataItem;
import run.onco.api.common.dto.Paging;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.exception.ValidationException;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.entity.TbMPatient;
import run.onco.api.persist.entity.TbTBiospecimen;
import run.onco.api.persist.entity.TbTSampleVariant;
import run.onco.api.persist.entity.TbTVariantAnnotation;
import run.onco.api.persist.entity.TbTVariantCall;
import run.onco.api.persist.entity.TbTVariantResult;
import run.onco.api.service.ClinicalDataService;
import run.onco.api.service.PatientService;
import run.onco.api.service.SampleVcfService;
import run.onco.api.service.UserService;
import run.onco.api.service.VariantCallService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class VariantCallFacadeImpl implements VariantCallFacade {

	private final static Logger logger = Logger.getLogger(VariantCallFacadeImpl.class);

	@Autowired
	private Environment env;
	
	@Autowired
	private SampleVcfService sampleVcfService;
	
	@Autowired
	private VariantCallService variantCallService;

	@Autowired
	private ClinicalDataService clinicalDataService;

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private UserService userService;
	
	private String getString(String key) {
		return env.getProperty(key);
	}

	@Override
	public MasterDataDto<DataItem> getSourceSampleListByPatientId(PatientDto input) {

		try {
			logger.info(String.format("I:--START--:--Get SourceSample List by PatientId--:patientId/%s", input.getId()));

			TbMPatient patient = null;
			
			if (input.getId() != null) {
				patient = patientService.getPatientById(input.getId(), AppConstants.STATUS_ACTIVE);
				
				if (patient == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Patient does not exist.");
				}
			}
			
			List<TbTBiospecimen> biospecimenList = clinicalDataService.getActiveฺBiospecimenListByPatientId(patient.getId());
			
			if (biospecimenList == null || biospecimenList.size() == 0) {
				logger.info(String.format("O:--FAIL--:--Get Active Biospecimen List--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc()));
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}	

			List<DataItem> dataList = new ArrayList<DataItem>();

			for (TbTBiospecimen biospecimen : biospecimenList) {
				DataItem data = new DataItem(biospecimen.getId().toString(), String.format("%s (%s)", biospecimen.getRefNo(), biospecimen.getSampleType().getName()));
				dataList.add(data);
			}

			logger.info(String.format("O:--SUCCESS--:--Get SourceSample List by PatientId--:genderList size/%s", dataList.size()));

			MasterDataDto<DataItem> masterDataDto = new MasterDataDto<DataItem>(dataList);
			return masterDataDto;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get SourceSample List by PatientId--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get SourceSample List by PatientId--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get SourceSample List by PatientId--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void deleteVariantCall(SampleVariantDto sampleVariantDto) {
		
		try {
			logger.info(String.format("I:--START--:--Delete VariantCall--:sampleId/%s", sampleVariantDto.getId()));
			
			TbTSampleVariant sampleVariant = sampleVcfService.getSampleVariantById(sampleVariantDto.getId());
			
			if (sampleVariant == null) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "VariantCall does not exist.");
			}
			
			variantCallService.deleteVariantCall(sampleVariant);
			
			logger.info("O:--SUCCESS--:--Delete VariantCall--");
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Delete VariantCall--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Delete VariantCall--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Delete VariantCall--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public DataTableResults<VariantDto> getVariantAnnotations(DataTableRequest<SampleVariantDto> req) {
		
		try {
			SampleVariantDto input = req.getCriteria();
			Paging paging = req.getPaging();
			
			logger.info(String.format("I:--START--:--Get SampleVariant--:sampleId/%s", input.getId()));
			
			TbTSampleVariant sampleVariant = null;
			
			if (input.getId() != null) {
				sampleVariant = sampleVcfService.getSampleVariantById(input.getId());
				
				if (sampleVariant == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "SampleVariant does not exist.");
				}
			}

			HashMap<String, Object> criteria = new HashMap<String, Object>();
			criteria.put("sampleId", sampleVariant.getId());
			
			if (input.getVariant() != null) {
				criteria.put("chromosome", input.getVariant().getChromosome());
				criteria.put("position", input.getVariant().getPosition());
			}
			
			if (paging != null) {
				criteria.put("startIndex", paging.getStartIndex());
				criteria.put("fetchSize", paging.getFetchSize());
				criteria.put("sortBy", paging.getSortBy());
				criteria.put("sortAsc", paging.getSortAsc());
			} else {
				criteria.put("startIndex", 0);
				criteria.put("fetchSize", 15);
			}
			
			// Get variant call
			int paginatedCount = variantCallService.countVariantsUsingQuery(criteria);
				
			List<VariantDto> variantList = new ArrayList<VariantDto>();
				
			// Get variant annotation
			List<TbTVariantAnnotation> variantAnnotationList = variantCallService.listPaginatedVariantsUsingQuery(criteria);
					
			if (variantAnnotationList != null && variantAnnotationList.size() > 0) { 
						
				for (TbTVariantAnnotation variantAnnotation : variantAnnotationList) {
							
					VariantDto variant = new VariantDto();
							
					TbTVariantCall variantCall = variantAnnotation.getVariantCall();
							
					variant.setChromosome(variantCall.getChromosome());
					variant.setPosition(variantCall.getPosition());
					variant.setRefAllele(variantCall.getRefAllele());
					variant.setAltAllele(variantCall.getAltAllele());

					variant.setVariantId(variantAnnotation.getId());
					variant.setAllele(variantAnnotation.getAllele());
					variant.setConsequence(variantAnnotation.getConsequence());
					variant.setImpact(variantAnnotation.getImpact());
					variant.setSymbol(variantAnnotation.getSymbol());
					variant.setGene(variantAnnotation.getGene());
					variant.setFeatureType(variantAnnotation.getFeatureType());
					variant.setFeature(variantAnnotation.getFeature());
					variant.setBiotype(variantAnnotation.getBiotype());
					variant.setExon(variantAnnotation.getExon());
					variant.setIntron(variantAnnotation.getIntron());
					variant.setHgvsp(variantAnnotation.getHgvsp());
					variant.setHgvsc(variantAnnotation.getHgvsc());
					variant.setCdnaPosition(variantAnnotation.getCdnaPosition());
					variant.setCdsPosition(variantAnnotation.getCdsPosition());
					variant.setProteinPosition(variantAnnotation.getProteinPosition());
					variant.setAminoAcids(variantAnnotation.getAminoAcids());
					variant.setCodons(variantAnnotation.getCodons());
					variant.setExistingVariantion(variantAnnotation.getExistingVariantion());
					variant.setDistance(variantAnnotation.getDistance());
					variant.setStrand(variantAnnotation.getStrand());
					variant.setFlags(variantAnnotation.getFlags());
					variant.setSymbolSource(variantAnnotation.getSymbolSource());
					variant.setHgncId(variantAnnotation.getHgncId());
					variant.setOncoKbAnnotated(variantAnnotation.getOncoKbAnnotated());

					List<TbTVariantResult> variantResultList = variantCall.getVariantResultList();
							
					VariantResultDto tumor = retrieveSample(variantResultList, VcfConstants.SAMPLE_TYPE_TUMOR);
					variant.setTumor(tumor);
							
					VariantResultDto normal = retrieveSample(variantResultList, VcfConstants.SAMPLE_TYPE_NORMAL);
					variant.setNormal(normal);
							
					variantList.add(variant);
				}
			}
			
			logger.info("O:--SUCCESS--:--Get SampleVariant--");
			
			DataTableResults<VariantDto> results = new DataTableResults<VariantDto>(variantList, paginatedCount, 0, 15);
			return results;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get SampleVariant--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get SampleVariant--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get SampleVariant--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
	
	private static VariantResultDto retrieveSample(List<TbTVariantResult> variantResultList, String sampleType) {
		TbTVariantResult sample = variantResultList.stream().filter(x -> x.getSampleType().equalsIgnoreCase(sampleType)).collect(AppUtil.toSingleton());
		
		if (sample != null) {
			VariantResultDto variantResult = new VariantResultDto();
			variantResult.setRefAlleleDepth(sample.getRefAlleleDepth());
			variantResult.setAltAlleleDepth(sample.getAltAlleleDepth());
			variantResult.setAlleleFrequency(sample.getAlleleFrequency());
			return variantResult;
		}
		
		return null;
	}
	
	@Override
	@SuppressWarnings("serial")
	public DataTableResults<Object[]> getVariantComparison(DataTableRequest<InquiryOmics> req) {
		
		try {
			
			InquiryOmics input = req.getCriteria();
			Paging paging = req.getPaging();

			logger.info("I:--START--:--Get Variant Comparison--");
			
			HashMap<String, Object> criteria = new HashMap<String, Object>();
			criteria.put("ids", input.getSampleVcfIds());
			
			if (paging != null) {
				criteria.put("startIndex", paging.getStartIndex());
				criteria.put("fetchSize", paging.getFetchSize());
				criteria.put("sortBy", paging.getSortBy());
				criteria.put("sortAsc", paging.getSortAsc());
			} else {
				criteria.put("startIndex", 0);
				criteria.put("fetchSize", 15);
			}
			
			// Get variant comparison
			int paginatedCount = variantCallService.countVariantComparisonUsingQuery(criteria);
			
			if (paginatedCount == 0) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<Object[]> results = variantCallService.listPaginatedVariantComparisonUsingQuery(criteria);
			
			List<ColumnDefDto> columns = new ArrayList<ColumnDefDto>() {
				{
					add(new ColumnDefDto("OncoKB", 110, "oncoKbAnnotated", "text-center"));
					add(new ColumnDefDto("Variant", 200, "variant", "text-left"));
					add(new ColumnDefDto("Chrom", 100, "chromDisplay", "text-center"));
					add(new ColumnDefDto("Position", 150, "position", "text-left"));
					add(new ColumnDefDto("Ref. Allelle", 150, "refAllele", "text-center"));
					add(new ColumnDefDto("Alt. Allelle", 150, "altAllele", "text-center"));
					add(new ColumnDefDto("Gene", 100, "symbol", "text-center"));
				}
			};
			
			for (int i = 1; i <= input.getSampleVcfIds().length; i++) {
				columns.add(new ColumnDefDto("#" + i, null, "", "text-center"));
			}
			
			logger.info(String.format("O:--SUCCESS--:--Get Variant Comparison--:result size/%s", AppUtil.isObjectEmpty(results) ? 0 : results.size()));
			
			DataTableResults<Object[]> dt = new DataTableResults<Object[]>(results, paginatedCount, 0, 15);
			dt.setColumns(columns);
			return dt;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get Variant Comparison--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get Variant Comparison--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get Variant Comparison--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public List<VariantDto> getVariantAnnotationsBySampleId(InquiryOmics inquiryOmics) {
		
		try {
			
			Long[] sampleVcfIds = inquiryOmics.getSampleVcfIds();
			
			logger.info(String.format("I:--START--:--Get VariantAnnotations by SampleID--:sampleId/%s", sampleVcfIds[0]));
			
			List<VariantDto> variantList = new ArrayList<VariantDto>();
		
			List<Object[]> list = variantCallService.getVariantAnnotationListBySampleId(sampleVcfIds[0]);
			
			if (!AppUtil.isObjectEmpty(list)) {
				Stream<VariantDto> s = list.stream().map(obj -> {
					VariantDto variantDto = new VariantDto();
					variantDto.setChromosome((String)obj[0]);
					variantDto.setPosition(Long.parseLong(obj[1].toString()));
					variantDto.setRefAllele((String)obj[2]);
					variantDto.setAltAllele((String)obj[3]);
					variantDto.setAllele((String)obj[4]);
					variantDto.setConsequence((String)obj[5]);
					variantDto.setImpact((String)obj[6]);
					variantDto.setSymbol((String)obj[7]);
					variantDto.setGene((String)obj[8]);
					variantDto.setFeatureType((String)obj[9]);
					variantDto.setFeature((String)obj[10]);
					variantDto.setBiotype((String)obj[11]);
					variantDto.setExon((String)obj[12]);
					variantDto.setIntron((String)obj[13]);
					variantDto.setHgvsp((String)obj[14]);
					variantDto.setHgvsc((String)obj[15]);
					variantDto.setCdnaPosition((String)obj[16]);
					variantDto.setCdsPosition((String)obj[17]);
					variantDto.setProteinPosition((String)obj[18]);
					variantDto.setAminoAcids((String)obj[19]);
					variantDto.setCodons((String)obj[20]);
					variantDto.setExistingVariantion((String)obj[21]);
					variantDto.setDistance((String)obj[22]);
					variantDto.setStrand((String)obj[23]);
					variantDto.setFlags((String)obj[24]);
					variantDto.setSymbolSource((String)obj[25]);
					variantDto.setHgncId((String)obj[26]);
					variantDto.setOncoKbAnnotated((String)obj[27]);
					
					VariantResultDto tumor = new VariantResultDto();
					tumor.setAlleleFrequency(new BigDecimal(obj[28].toString()));
					tumor.setAltAlleleDepth(new BigDecimal(obj[29].toString()));
					variantDto.setTumor(tumor);
					
					VariantResultDto normal = new VariantResultDto();
					normal.setAlleleFrequency(new BigDecimal(obj[30].toString()));
					normal.setAltAlleleDepth(new BigDecimal(obj[31].toString()));
					variantDto.setNormal(normal);
					
					return variantDto;
				});
				
				variantList = s.collect(Collectors.toList());
			}
			
			return variantList;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get VariantAnnotations by SampleID--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get VariantAnnotations by SampleID--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get VariantAnnotations by SampleID--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

}
