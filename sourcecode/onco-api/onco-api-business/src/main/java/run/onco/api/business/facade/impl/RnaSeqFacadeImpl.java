package run.onco.api.business.facade.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import run.onco.api.business.dto.OmicsDto;
import run.onco.api.business.dto.RnaSeqDto;
import run.onco.api.business.dto.SampleRnaSeqDto;
import run.onco.api.business.facade.RnaSeqFacade;
import run.onco.api.business.message.InquiryOmics;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;
import run.onco.api.common.constants.ConfigurationConstants;
import run.onco.api.common.dto.Paging;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.exception.ValidationException;
import run.onco.api.common.utils.FileUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.entity.TbMCancerGeneGroup;
import run.onco.api.persist.entity.TbTAttachment;
import run.onco.api.persist.entity.TbTRnaSeq;
import run.onco.api.persist.entity.TbTSampleRnaSeq;
import run.onco.api.service.MasterDataService;
import run.onco.api.service.RnaSeqService;
import run.onco.api.service.SampleRnaSeqService;
import run.onco.api.service.UserService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class RnaSeqFacadeImpl implements RnaSeqFacade {

	private final static Logger logger = Logger.getLogger(RnaSeqFacadeImpl.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private RnaSeqService rnaSeqService;
	
	@Autowired
	private SampleRnaSeqService sampleRnaSeqService;
	
	@Autowired
	private MasterDataService masterDataService;
	
	@Autowired
	private UserService userService;
	
	private String getString(String key) {
		return env.getProperty(key);
	}

	@Override
	public void deleteRnaSeq(SampleRnaSeqDto sampleRnqSeqDto) {
		
		try {
			logger.info(String.format("I:--START--:--Delete SampleRnaSeq--:sampleId/%s", sampleRnqSeqDto.getId()));
			
			TbTSampleRnaSeq sampleRnaSeq = sampleRnaSeqService.getSampleRnaSeqById(sampleRnqSeqDto.getId());
			
			if (sampleRnaSeq == null) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "SampleRnaSeq does not exist.");
			}
			
			TbTAttachment attachment = sampleRnaSeq.getAttachment();
			
			String absolutePath = String.format("%s%s", getString(ConfigurationConstants.DATA_FILE_PATH), attachment.getFilePath());
			String fileDest = String.format("%s/%s", absolutePath, attachment.getFileName());
			
			rnaSeqService.deleteRnaSeq(sampleRnaSeq);
			
			FileUtil.removeFile(fileDest);
			
			logger.info("O:--SUCCESS--:--Delete SampleRnaSeq--");
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Delete SampleRnaSeq--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Delete SampleRnaSeq--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Delete SampleRnaSeq--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public DataTableResults<RnaSeqDto> getRnaSeqList(DataTableRequest<SampleRnaSeqDto> req) {
		
		try {
			SampleRnaSeqDto input = req.getCriteria();
			Paging paging = req.getPaging();
			
			logger.info(String.format("I:--START--:--Get SampleRnaSeq--:sampleId/%s", input.getId()));
			
			TbTSampleRnaSeq sampleRnaSeq = null;
			
			if (input.getId() != null) {
				sampleRnaSeq = sampleRnaSeqService.getSampleRnaSeqById(input.getId());
				
				if (sampleRnaSeq == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "SampleRnaSeq does not exist.");
				}
			}

			HashMap<String, Object> criteria = new HashMap<String, Object>();
			criteria.put("sampleId", sampleRnaSeq.getId());
			
			if (paging != null) {
				criteria.put("startIndex", paging.getStartIndex());
				criteria.put("fetchSize", paging.getFetchSize());
				criteria.put("sortBy", paging.getSortBy());
				criteria.put("sortAsc", paging.getSortAsc());
			} else {
				criteria.put("startIndex", 0);
				criteria.put("fetchSize", 15);
			}
			
			// Count rna seq
			int paginatedCount = rnaSeqService.countRnaSeqUsingQuery(criteria);
				
			List<RnaSeqDto> rnaSeqDtoList = new ArrayList<RnaSeqDto>();
				
			// Get rna seq list
			List<TbTRnaSeq> rnaSeqList = rnaSeqService.listPaginatedRnaSeqUsingQuery(criteria);
			
			if (rnaSeqList != null && rnaSeqList.size() > 0) {
				
				for (TbTRnaSeq rnaSeq : rnaSeqList) {
					RnaSeqDto rnaSeqDto = new RnaSeqDto();
					rnaSeqDto.setGeneId(rnaSeq.getGeneId());
					rnaSeqDto.setGeneName(rnaSeq.getGeneName());
					rnaSeqDto.setRef(rnaSeq.getRef());
					rnaSeqDto.setStrand(rnaSeq.getStrand());
					rnaSeqDto.setStart(rnaSeq.getStart());
					rnaSeqDto.setEnd(rnaSeq.getEnd());
					rnaSeqDto.setCoverage(rnaSeq.getCoverage());
					rnaSeqDto.setFpkm(rnaSeq.getFpkm());
					rnaSeqDto.setTpm(rnaSeq.getTpm());
					rnaSeqDtoList.add(rnaSeqDto);
				}
			}
			
			logger.info("O:--SUCCESS--:--Get SampleRnaSeq--");
			
			DataTableResults<RnaSeqDto> results = new DataTableResults<RnaSeqDto>(rnaSeqDtoList, paginatedCount, 0, 15);
			return results;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get SampleRnaSeq--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get SampleRnaSeq--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get SampleRnaSeq--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
	
	@Override
	public List<OmicsDto<RnaSeqDto>> getRnaSeqListForClustergrammer(InquiryOmics input) {

		try {
			
			logger.info("I:--START--:--Get RnaSeqList for Clustergrammer--");
			
//			TbMPatient patient = null;
//			
//			if (input.getPatient().getId() != null) {
//				patient = patientService.getPatientById(input.getPatient().getId(), AppConstants.STATUS_ACTIVE);
//				
//				if (patient == null) {
//					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Patient does not exist.");
//				}
//			}
			
			TbMCancerGeneGroup cancerGeneGroup = null;
			
			if (input.getCancerGeneGroup() != null) {
				cancerGeneGroup = masterDataService.getById(TbMCancerGeneGroup.class, input.getCancerGeneGroup().getId());
				
				if (cancerGeneGroup == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "CancerGeneGroup does not exist.");
				}
			}
			
//			List<BiospecimenDto> biospecimenDtoList = input.getBiospecimenList();
//			Long[] ids = biospecimenDtoList.stream().map(BiospecimenDto::getId).toArray(Long[]::new);
			
			List<TbTSampleRnaSeq> sampleRnaSeqList = sampleRnaSeqService.getSampleRnaSeqListBySampleIds(input.getSampleRnaSeqIds());
			
			if (sampleRnaSeqList == null || sampleRnaSeqList.size() == 0) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<OmicsDto<RnaSeqDto>> omicsDtoList = new ArrayList<>();
			
			for (TbTSampleRnaSeq sampleRnaSeq : sampleRnaSeqList) {
				
				Long sampleId = sampleRnaSeq.getId();
				String fileName = sampleRnaSeq.getAttachment().getFileName();
				
				OmicsDto<RnaSeqDto> omicsDto = new OmicsDto<>();
				omicsDto.setFileName(fileName);

				List<Object[]> rnaSeqList = rnaSeqService.getRnaSeqListForClustergrammer(sampleId, cancerGeneGroup.getId());
				
				if (rnaSeqList != null && rnaSeqList.size() > 0) {
					
					List<RnaSeqDto> rnaSeqDtoList = new ArrayList<>();
					
					for (Object[] obj : rnaSeqList) {
						String geneId = (String) obj[0];
						String geneName = (String) obj[1];
						String ref = (String) obj[2];
						Integer start = (Integer) obj[3];
						Integer end = (Integer) obj[4];
						BigDecimal tpm = (BigDecimal) obj[5];
						
						RnaSeqDto rnaSeqDto = new RnaSeqDto();
						rnaSeqDto.setGeneId(geneId);
						rnaSeqDto.setGeneName(geneName);
						rnaSeqDto.setRef(ref);
						rnaSeqDto.setStart(start);
						rnaSeqDto.setEnd(end);
						rnaSeqDto.setTpm(tpm);
						rnaSeqDtoList.add(rnaSeqDto);
					}
					
					omicsDto.setOmics(rnaSeqDtoList);
				}
				
				omicsDtoList.add(omicsDto);
			}
			
			logger.info("O:--SUCCESS--:--Get RnaSeqList for Clustergrammer--");
			return omicsDtoList;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get RnaSeqList for Clustergrammer--:errorMsg/%s", ex.getMessage()));	
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get RnaSeqList for Clustergrammer--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get RnaSeqList for Clustergrammer--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
}
