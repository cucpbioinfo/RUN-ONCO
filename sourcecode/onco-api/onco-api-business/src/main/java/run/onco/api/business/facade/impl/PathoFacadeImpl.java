package run.onco.api.business.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import run.onco.api.business.dto.PathologicalDto;
import run.onco.api.business.dto.PatientDto;
import run.onco.api.business.facade.PathoFacade;
import run.onco.api.business.message.InquiryPatho;
import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.dto.DataItem;
import run.onco.api.common.dto.Paging;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.exception.ValidationException;
import run.onco.api.common.utils.DateUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.entity.TbCParameter;
import run.onco.api.persist.entity.TbTPathological;
import run.onco.api.service.MasterDataService;
import run.onco.api.service.PathoService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class PathoFacadeImpl implements PathoFacade {
	
	private final static Logger logger = Logger.getLogger(PathoFacadeImpl.class);
	
	@Autowired
	private MasterDataService masterDataService;
	
	@Autowired
	private PathoService pathoService;

	@Override
	public List<PathologicalDto> searchPatientPathoByPathoNo(InquiryPatho searchCriteria) {
		
		try {
			logger.info("I:--START--:--Search PatientPatho by PathoNo--");

			PatientDto patientData = searchCriteria.getPatient();
			PathologicalDto pathoData = searchCriteria.getPatho();
			Paging paging =  searchCriteria.getPaging();
			
			List<TbTPathological> pathoList = pathoService.searchPatientPathoByPathoNo(patientData.getId(), pathoData.getPathologyNo(), AppConstants.STATUS_ACTIVE, paging);
			
			if (pathoList == null || pathoList.size() == 0) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<PathologicalDto> pathoDtoList = new ArrayList<PathologicalDto>();
			
			for (TbTPathological patho : pathoList) {
				PathologicalDto pathoDto = new PathologicalDto();
				pathoDto.setId(patho.getId());
				pathoDto.setPathologyNo(patho.getPathologyNo());
				pathoDto.setResectionBiopsySite(patho.getResectionBiopsySite());
				pathoDto.setStatus(patho.getStatus());
				
				if (patho.getHistologicGrade() != null) {
					TbCParameter histologicGrade = masterDataService.getById(TbCParameter.class, patho.getHistologicGrade());
						
					if(histologicGrade != null) {
						pathoDto.setHistologicGrade(new DataItem(histologicGrade.getCode(), histologicGrade.getName()));
					}
				}
				
				if (patho.getProcedure() != null) {
					DataItem tissueProcedure = new DataItem(patho.getProcedure().getCode(), patho.getProcedure().getName());
					pathoDto.setTissueProcedure(tissueProcedure);
				}
				
				if (patho.getTissueReportDate() != null) {
					pathoDto.setTissueReportDate(DateUtil.formatDate(patho.getTissueReportDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
				}
				
				if (patho.getTissueType() != null) {
					DataItem tissueType = new DataItem(patho.getTissueType().getCode(), patho.getTissueType().getName());
					pathoDto.setTissueType(tissueType);
				}
				
				DataItem morphology = new DataItem(patho.getMorphologyCode(), patho.getMorphologyName());
				pathoDto.setMorphology(morphology);
				
				pathoDtoList.add(pathoDto);
			}
			
			logger.info("O:--SUCCESS--:--Search PatientPatho by PathoNo--");
			return pathoDtoList;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Search PatientPatho by PathoNo--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Search PatientPatho by PathoNo--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Search PatientPatho by PathoNo--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void findDuplicatePathoNo(PathologicalDto pathoDto) {
		
		try {
			Long pathoId = pathoDto.getId();
			String pathoNo = pathoDto.getPathologyNo();
			
			logger.info(String.format("I:--START--:--Find Duplicate PathoNo--:pathoId/%s:pathoNo/%s", pathoId, pathoNo));
			boolean isDuplicate = pathoService.findDuplicatePathoNo(pathoId, pathoNo);
			
			if (isDuplicate) {
				throw new ServiceException(MessageCode.ERROR_DUPLICATED.getCode(), "Patho No. already exists in the system.");
			}
			
			logger.info("O:--SUCCESS--:--Find Duplicate PathoNo--");
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Find Duplicate PathoNo--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Find Duplicate PathoNo--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Find Duplicate PathoNo--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
}
