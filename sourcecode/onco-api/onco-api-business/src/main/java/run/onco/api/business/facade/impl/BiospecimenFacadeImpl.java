package run.onco.api.business.facade.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import run.onco.api.business.dto.BiospecimenDto;
import run.onco.api.business.dto.PathologicalDto;
import run.onco.api.business.dto.PatientDto;
import run.onco.api.business.facade.BiospecimenFacade;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;
import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.dto.DataItem;
import run.onco.api.common.dto.Paging;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.exception.ValidationException;
import run.onco.api.common.utils.DateUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.common.utils.NumberUtil;
import run.onco.api.common.utils.StringUtil;
import run.onco.api.persist.entity.TbMBloodType;
import run.onco.api.persist.entity.TbMCellLineType;
import run.onco.api.persist.entity.TbMCellType;
import run.onco.api.persist.entity.TbMFreezeMethod;
import run.onco.api.persist.entity.TbMPatient;
import run.onco.api.persist.entity.TbMPreserveMethod;
import run.onco.api.persist.entity.TbMSampleType;
import run.onco.api.persist.entity.TbMTissueType;
import run.onco.api.persist.entity.TbTBiospecimen;
import run.onco.api.persist.entity.TbTPathological;
import run.onco.api.service.BiospecimenService;
import run.onco.api.service.ClinicalDataService;
import run.onco.api.service.MasterDataService;
import run.onco.api.service.PatientService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class BiospecimenFacadeImpl implements BiospecimenFacade {

	private final static Logger logger = Logger.getLogger(BiospecimenFacadeImpl.class);
	
	@Autowired
	private BiospecimenService biospecimenService;
	
	@Autowired
	private MasterDataService masterDataService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private ClinicalDataService clinicalDataService;

	@Override
	public DataTableResults<BiospecimenDto> searchBiospecimen(DataTableRequest<BiospecimenDto> req) {
		
		try {

			BiospecimenDto searchCriteria = req.getCriteria();
			
			String refNo = searchCriteria.getRef();
			String status = searchCriteria.getStatus();
			Paging paging = req.getPaging();
			
			logger.info(String.format("I:--START--:--Search Biospecimen--:status/%s", status));
			
			HashMap<String, Object> criteria = new HashMap<String, Object>();
			criteria.put("refNo", refNo);
			criteria.put("status", status);
			
			String hn = null;
			if (searchCriteria.getPatient() != null) {
				hn = searchCriteria.getPatient().getHn();
				criteria.put("hn", hn);
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
			
			int paginatedCount = biospecimenService.countBiospecimenUsingQuery(criteria);
			
			List<TbTBiospecimen> biospecimenList = biospecimenService.listPaginatedBiospecimenUsingQuery(criteria);
			
			if (biospecimenList == null || biospecimenList.size() == 0) {
				logger.info(String.format("O:--FAIL--:--Search Biospecimen--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc()));
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<BiospecimenDto> dataList = new ArrayList<BiospecimenDto>();
			
			for (TbTBiospecimen biospecimen : biospecimenList) {
				
				BiospecimenDto biospecimenDto = new BiospecimenDto();
				biospecimenDto.setId(biospecimen.getId());
				biospecimenDto.setRef(biospecimen.getRefNo());
				
				if (biospecimen.getPatient() != null) {
					TbMPatient patient = biospecimen.getPatient();
					
					PatientDto patientDto = new PatientDto();
					patientDto.setId(patient.getId());
					patientDto.setHn(patient.getHn());
					biospecimenDto.setPatient(patientDto);
				}
				
				dataList.add(biospecimenDto);
			}
			
			logger.info(String.format("O:--SUCCESS--:--Search Biospecimen--:paginatedCount/%s", paginatedCount));
			DataTableResults<BiospecimenDto> results = new DataTableResults<BiospecimenDto>(dataList, paginatedCount, 0, 15);
			return results;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Search Biospecimen--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Search Biospecimen--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Search Biospecimen--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
	
	private TbTBiospecimen prepareBiospecimen(BiospecimenDto biospecimenDto) {

		TbTBiospecimen  biospecimen = new TbTBiospecimen();
			
		if (biospecimenDto.getId() != null) {
			biospecimen = biospecimenService.getBiospecimenById(biospecimenDto.getId(), AppConstants.STATUS_ACTIVE);
				
			if (biospecimen == null) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Biospecimen does not exist.");
			}
		}
			
		// Patient
		if (biospecimenDto.getPatient() != null) {
			PatientDto patientDto = biospecimenDto.getPatient();
				
			if (patientDto.getId() != null) {
				TbMPatient patient = patientService.getPatientById(patientDto.getId(), AppConstants.STATUS_ACTIVE);
					 
				if (patient == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Patient does not exist.");
				}
					
				biospecimen.setPatient(patient);
			}
		}
			
		if (biospecimenDto.getPathological() != null) {
			PathologicalDto pathoDto = biospecimenDto.getPathological();
				
			if (pathoDto.getId() != null) {
				TbTPathological patho = clinicalDataService.getPathologicalById(pathoDto.getId());
					
				if (patho == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Pathological does not exist.");
				}
					
				biospecimen.setPathological(patho);
			}
		}
			
		if (AppConstants.EMPTY_STRING.equals(biospecimenDto.getRef())) {
			biospecimen.setRefNo(null);
		} else if (!StringUtil.isNullOrEmpty(biospecimenDto.getRef())) {
			biospecimen.setRefNo(biospecimenDto.getRef());
		}
			
		if (biospecimenDto.getSampleType() != null) {
			String sampleTypeCode = StringUtil.nullSafeTrim(biospecimenDto.getSampleType().getCode());

			if (AppConstants.EMPTY_STRING.equals(sampleTypeCode)) {
				biospecimen.setSampleType(null);
			} else {
				TbMSampleType sampleType = masterDataService.getByCode(TbMSampleType.class, sampleTypeCode);

				if (sampleType == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "SampleTypeCode does not exist.");
				} else {
					biospecimen.setSampleType(sampleType);
				}
			}
		}

		if (biospecimenDto.getBloodType() != null) {
			String bloodTypeCode = StringUtil.nullSafeTrim(biospecimenDto.getBloodType().getCode());

			if (AppConstants.EMPTY_STRING.equals(bloodTypeCode)) {
				biospecimen.setBloodType(null);
			} else {
				TbMBloodType bloodType = masterDataService.getByCode(TbMBloodType.class, bloodTypeCode);

				if (bloodType == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "BloodTypeCode does not exist.");
				} else {
					biospecimen.setBloodType(bloodType);
				}
			}
		}

		if (biospecimenDto.getTissueType() != null) {
			String tissueTypeCode = StringUtil.nullSafeTrim(biospecimenDto.getTissueType().getCode());

			if (AppConstants.EMPTY_STRING.equals(tissueTypeCode)) {
				biospecimen.setTissueType(null);
			} else {
				TbMTissueType tissueType = masterDataService.getByCode(TbMTissueType.class, tissueTypeCode);

				if (tissueType == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "TissueTypeCode does not exist.");
				} else {
					biospecimen.setTissueType(tissueType);
				}
			}
		}

		if (biospecimenDto.getCellType() != null) {
			String cellTypeCode = StringUtil.nullSafeTrim(biospecimenDto.getCellType().getCode());

			if (AppConstants.EMPTY_STRING.equals(cellTypeCode)) {
				biospecimen.setCellType(null);
			} else {
				TbMCellType cellType = masterDataService.getByCode(TbMCellType.class, cellTypeCode);

				if (cellType == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "CellTypeCode does not exist.");
				} else {
					biospecimen.setCellType(cellType);
				}
			}
		}

		if (biospecimenDto.getCellLineType() != null) {
			String cellLineTypeCode = StringUtil.nullSafeTrim(biospecimenDto.getCellLineType().getCode());

			if (AppConstants.EMPTY_STRING.equals(cellLineTypeCode)) {
				biospecimen.setCellLineType(null);
			} else {
				TbMCellLineType cellLineType = masterDataService.getByCode(TbMCellLineType.class, cellLineTypeCode);

				if (cellLineType == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "CellLineTypeCode does not exist.");
				} else {
					biospecimen.setCellLineType(cellLineType);
				}
			}
		}

		if (biospecimenDto.getFreezeMethod() != null) {
			String freezeMethodCode = StringUtil.nullSafeTrim(biospecimenDto.getFreezeMethod().getCode());

			if (AppConstants.EMPTY_STRING.equals(freezeMethodCode)) {
				biospecimen.setFreezeMethod(null);
			} else {
				TbMFreezeMethod freezeMethod = masterDataService.getByCode(TbMFreezeMethod.class, freezeMethodCode);

				if (freezeMethod == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "FreezeMethodCode does not exist.");
				} else {
					biospecimen.setFreezeMethod(freezeMethod);
				}
			}
		}

		if (biospecimenDto.getPreserveMethod() != null) {
			String preserveMethodCode = StringUtil.nullSafeTrim(biospecimenDto.getPreserveMethod().getCode());

			if (AppConstants.EMPTY_STRING.equals(preserveMethodCode)) {
				biospecimen.setPreserveMethod(null);
			} else {
				TbMPreserveMethod preserveMethod = masterDataService.getByCode(TbMPreserveMethod.class, preserveMethodCode);

				if (preserveMethod == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "PreserveMethodCode does not exist.");
				} else {
					biospecimen.setPreserveMethod(preserveMethod);
				}
			}
		}

		if (biospecimenDto.getInitialWeight() != null) {
			String initialWeight = StringUtil.nullSafeTrim(biospecimenDto.getInitialWeight());

			if (AppConstants.EMPTY_STRING.equals(initialWeight)) {
				biospecimen.setInitialWeight(null);
			} else {
				biospecimen.setInitialWeight(NumberUtil.parseDecimal(initialWeight));
			}
		}

		if (biospecimenDto.getCollectSampleDate() != null) {
			String collectSampleDate = StringUtil.nullSafeTrim(biospecimenDto.getCollectSampleDate());

			if (AppConstants.EMPTY_STRING.equals(collectSampleDate)) {
				biospecimen.setCollectSampleDate(null);
			} else {
				biospecimen.setCollectSampleDate(DateUtil.parseDate(collectSampleDate, DateUtil.FORMAT_STORE_PROCEDURE_DATE));
			}
		}

		if (biospecimenDto.getFreezeDate() != null) {
			String freezeDate = StringUtil.nullSafeTrim(biospecimenDto.getFreezeDate());

			if (AppConstants.EMPTY_STRING.equals(freezeDate)) {
				biospecimen.setFreezeDate(null);
			} else {
				biospecimen.setFreezeDate(DateUtil.parseDate(freezeDate, DateUtil.FORMAT_STORE_PROCEDURE_DATE));
			}
		}

		biospecimen.setStatus(biospecimenDto.getStatus());
		logger.info(String.format("O:--Prepare Biospecimen--:biospecimenId/%s:status/%s", biospecimen.getId(), biospecimen.getStatus()));

		return biospecimen;
	}

	@Override
	public BiospecimenDto getBiospecimenById(BiospecimenDto input) {
		
		try {
			logger.info(String.format("I:--START--:--Get Biospecimen by ID--:id/%s", input.getId()));
			
			TbTBiospecimen biospecimen = null;
			
			if (input.getId() != null) {
				biospecimen = biospecimenService.getBiospecimenById(input.getId(), AppConstants.STATUS_ACTIVE);
				
				if (biospecimen == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Biospecimen does not exist.");
				}
			}
			
			BiospecimenDto output = new BiospecimenDto();
			output.setId(biospecimen.getId());
			output.setRef(biospecimen.getRefNo());
			
			TbMPatient patient = biospecimen.getPatient();
			
			if(patient != null) {
				PatientDto patientDto = new PatientDto();
				patientDto.setId(patient.getId());
				patientDto.setHn(patient.getHn());
				patientDto.setBirthDate(DateUtil.formatDate(patient.getBirthDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
				
				if (patient.getGender() != null) {
					patientDto.setGender(new DataItem(patient.getGender().getCode(), patient.getGender().getName()));
				}
				
				if (patient.getRace() != null) {
					patientDto.setRace(new DataItem(patient.getRace().getCode(), patient.getRace().getName()));
				}
				
				patientDto.setWeight(patient.getWeight());
				patientDto.setHeight(patient.getHeight());
				output.setPatient(patientDto);
			}
			
			TbTPathological patho = biospecimen.getPathological();
			
			if (patho != null) {
				PathologicalDto pathoDto = new PathologicalDto();
				pathoDto.setId(patho.getId());
				pathoDto.setPathologyNo(patho.getPathologyNo());
				output.setPathological(pathoDto);
			}
			
			if (biospecimen.getBloodType() != null) {
				output.setBloodType(new DataItem(biospecimen.getBloodType().getCode(), biospecimen.getBloodType().getName()));
			}
			
			if (biospecimen.getCellLineType() != null) { 
				output.setCellLineType(new DataItem(biospecimen.getCellLineType().getCode(), biospecimen.getCellLineType().getName()));
			}
			
			if (biospecimen.getCellType() != null) {
				output.setCellType(new DataItem(biospecimen.getCellType().getCode(), biospecimen.getCellType().getName()));
			}
			
			if (biospecimen.getCollectSampleDate() != null) {
				output.setCollectSampleDate(DateUtil.formatDate(biospecimen.getCollectSampleDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
			}
			
			if (biospecimen.getFreezeDate() != null) {
				output.setFreezeDate(DateUtil.formatDate(biospecimen.getFreezeDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
			}
			
			if (biospecimen.getFreezeMethod() != null) {
				output.setFreezeMethod(new DataItem(biospecimen.getFreezeMethod().getCode(), biospecimen.getFreezeMethod().getName()));
			}
			
			if (biospecimen.getInitialWeight() != null) {
				output.setInitialWeight(biospecimen.getInitialWeight().toString());
			}
			
			if (biospecimen.getPreserveMethod() != null) {
				output.setPreserveMethod(new DataItem(biospecimen.getPreserveMethod().getCode(), biospecimen.getPreserveMethod().getName()));
			}
			
			if (biospecimen.getSampleType() != null) {
				output.setSampleType(new DataItem(biospecimen.getSampleType().getCode(), biospecimen.getSampleType().getName()));
			}
			
			if (biospecimen.getTissueType() != null) {
				output.setTissueType(new DataItem(biospecimen.getTissueType().getCode(), biospecimen.getTissueType().getName()));
			}
			
			output.setStatus(biospecimen.getStatus());
			logger.info("O:--SUCCESS--:--Get Biospecimen by ID--");
			return output;
		} catch (ValidationException ex) {
			logger.info(String.format("O:--FAIL--:--Get Biospecimen by ID--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.info(String.format("O:--FAIL--:--Get Biospecimen by ID--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.info(String.format("O:--FAIL--:--Save Biospecimen--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void deleteBiospecimen(BiospecimenDto input) {
		
		try {
			logger.info(String.format("I:--START--:--Delete Biospecimen by ID--:id/%s", input.getId()));
			
			if (input.getId() != null) {
				TbTBiospecimen biospecimen = biospecimenService.getBiospecimenById(input.getId(), AppConstants.STATUS_ACTIVE);
				
				if (biospecimen == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Biospecimen does not exist.");
				}
				
				biospecimenService.deleteBiospecimen(biospecimen);
			}
			
			logger.info("O:--SUCCESS--:--Delete Biospecimen by ID--");
		} catch (ValidationException ex) {
			logger.info(String.format("O:--FAIL--:--Get Biospecimen by ID--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.info(String.format("O:--FAIL--:--Get Biospecimen by ID--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.info(String.format("O:--FAIL--:--Get Biospecimen by ID--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	@Transactional(readOnly = false)
	public BiospecimenDto saveBiospecimen(BiospecimenDto biospecimenDto) {
		
		try {
			logger.info("I:--START--:--Save Biospecimen--");
			TbTBiospecimen biospecimen = this.prepareBiospecimen(biospecimenDto);
			biospecimenService.saveBiospecimen(biospecimen);

			BiospecimenDto output = new BiospecimenDto();
			output.setRef(biospecimen.getRefNo());
			logger.info("O:--SUCCESS--:--Save Biospecimen--");
			return output;
		} catch (ValidationException ex) {
			logger.info(String.format("O:--FAIL--:--Save Biospecimen--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.info(String.format("O:--FAIL--:--Save Biospecimen--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.info(String.format("O:--FAIL--:--Save Biospecimen--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void findDuplicateBioRef(BiospecimenDto biospecimenDto) {

		try {
			Long biospecimenId = biospecimenDto.getId();
			String refNo = biospecimenDto.getRef();
			
			logger.info(String.format("I:--START--:--Find Duplicate BioRef--:biospecimenId/%s:refNo/%s", biospecimenId, refNo));
			
			boolean isDuplicate = biospecimenService.findDuplicateBioRef(biospecimenId, refNo);
			
			if (isDuplicate) {
				throw new ServiceException(MessageCode.ERROR_DUPLICATED.getCode(), "Ref.No. already exists in the system.");
			}
			
		} catch (ValidationException ex) {
			logger.info(String.format("O:--FAIL--:--Find Duplicate BioRef--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.info(String.format("O:--FAIL--:--Find Duplicate BioRef--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.info(String.format("O:--FAIL--:--Find Duplicate BioRef--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
}
