package run.onco.api.business.facade.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import run.onco.api.business.dto.PatientDto;
import run.onco.api.business.dto.SurvivalFollowupDto;
import run.onco.api.business.facade.PatientFacade;
import run.onco.api.business.message.InquiryPatient;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;
import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.dto.DataItem;
import run.onco.api.common.dto.Paging;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.exception.ValidationException;
import run.onco.api.common.utils.DateUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.common.utils.StringUtil;
import run.onco.api.persist.entity.TbMGender;
import run.onco.api.persist.entity.TbMPatient;
import run.onco.api.persist.entity.TbMRace;
import run.onco.api.persist.entity.TbMUser;
import run.onco.api.persist.entity.TbMVitalStatus;
import run.onco.api.persist.entity.TbTSurvivalFollowup;
import run.onco.api.service.MasterDataService;
import run.onco.api.service.PatientService;
import run.onco.api.service.UserService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class PatientFacadeImpl implements PatientFacade {

	private final static Logger logger = Logger.getLogger(PatientFacadeImpl.class);
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MasterDataService masterDataService;
	
	@Autowired
	private UserService userService;

	@Override
	public List<PatientDto> searchPatientByRef(InquiryPatient searchCriteria) {
		
		try {
			logger.info("I:--START--:--Search Patient by Ref--");

			PatientDto input = searchCriteria.getPatient();
			Paging paging =  searchCriteria.getPaging();
			
			List<TbMPatient> patientList = patientService.searchPatientByRef(input.getHn(), AppConstants.STATUS_ACTIVE, paging);
			
			if(patientList == null || patientList.size() == 0) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<PatientDto> patientDtoList = new ArrayList<PatientDto>();
			
			for (TbMPatient patient : patientList) {
				PatientDto patientDto = new PatientDto();
				patientDto.setId(patient.getId());
				patientDto.setHn(patient.getHn());
				patientDto.setWeight(patient.getWeight());
				patientDto.setHeight(patient.getHeight());
				
				if(patient.getRace() != null) {
					patientDto.setRace(new DataItem(patient.getRace().getCode(), patient.getRace().getName()));
				}
				
				if(patient.getGender() != null) {
					patientDto.setGender(new DataItem(patient.getGender().getCode(), patient.getGender().getName()));
				}
				
				if(patient.getBirthDate() != null) {
					patientDto.setBirthDate(DateUtil.formatDate(patient.getBirthDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
				}
				
				patientDtoList.add(patientDto);
			}
			
			logger.info("O:--SUCCESS--:--Search Patient by Ref--");
			return patientDtoList;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Search Patient by Ref--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Search Patient by Ref--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Search Patient by Ref--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public DataTableResults<PatientDto> searchPatient(DataTableRequest<PatientDto> req) {
		
		try {

			PatientDto searchCriteria = req.getCriteria();
			
			String hn = searchCriteria.getHn();
			String status = searchCriteria.getStatus();
			Paging paging = req.getPaging();

			logger.info(String.format("I:--START--:--Search Patient--:hn/%s:status/%s", hn, status));
			
			HashMap<String, Object> criteria = new HashMap<String, Object>();
			criteria.put("hn", hn);
			criteria.put("status", status);
			
			if (paging != null) {
				criteria.put("startIndex", paging.getStartIndex());
				criteria.put("fetchSize", paging.getFetchSize());
				criteria.put("sortBy", paging.getSortBy());
				criteria.put("sortAsc", paging.getSortAsc());
			} else {
				criteria.put("startIndex", 0);
				criteria.put("fetchSize", 15);
			}
			
			int paginatedCount = patientService.countPatientUsingQuery(criteria);
			
			List<TbMPatient> patientList = patientService.listPaginatedPatientUsingQuery(criteria);
			
			if (patientList == null || patientList.size() == 0) {
				logger.info(String.format("O:--FAIL--:--Search ClinicalData--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc()));
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<PatientDto> dataList = new ArrayList<PatientDto>();
			
			for (TbMPatient patient : patientList) {
				PatientDto patientDto = new PatientDto();
				patientDto.setId(patient.getId());
				patientDto.setHn(patient.getHn());
				dataList.add(patientDto);
			}
			
			DataTableResults<PatientDto> results = new DataTableResults<PatientDto>(dataList, paginatedCount, 0, 15);
			return results;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get Patient by ID--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get Patient by ID--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Save Patient--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
	
	private TbMPatient preparePatient(PatientDto patientDto) {
		logger.debug(String.format("I:--START--:--Prepare Patient--:id/%s", patientDto.getId()));

		TbMPatient patient = this.findPatientById(patientDto.getId());
		
		if (patientDto.getUserId() != null) {
			TbMUser user = userService.getActiveUserById(patientDto.getUserId());
			
			if (user == null) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "User does not exist.");
			}
			
			if (patientDto.getId() == null) {
				patient.setCreateUser(user);
				patient.setCreateDate(DateUtil.getCurrentDate());
			}
			
			patient.setUpdateUser(user);
			patient.setUpdateDate(DateUtil.getCurrentDate());
		}
		
		if (AppConstants.EMPTY_STRING.equals(patientDto.getHn())) {
			patient.setHn(null);
		} else {
			patient.setHn(patientDto.getHn());
		}

		if (patientDto.getRace() != null) {
			String raceCode = StringUtil.nullSafeTrim(patientDto.getRace().getCode());

			if (AppConstants.EMPTY_STRING.equals(raceCode)) {
				patient.setRace(null);
			} else if (!StringUtil.isNullOrEmpty(raceCode)) {
				TbMRace race = masterDataService.getByCode(TbMRace.class, raceCode);

				if (race == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "RaceCode does not exist.");
				}

				patient.setRace(race);
			}
		}

		if (patientDto.getGender() != null) {
			String genderCode = StringUtil.nullSafeTrim(patientDto.getGender().getCode());

			if (AppConstants.EMPTY_STRING.equals(genderCode)) {
				patient.setGender(null);
			} else if (!StringUtil.isNullOrEmpty(genderCode)) {
				TbMGender gender = masterDataService.getByCode(TbMGender.class, genderCode);

				if (gender == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "GenderCode does not exist.");
				}

				patient.setGender(gender);
			}
		}

		if (AppConstants.EMPTY_STRING.equals(patientDto.getBirthDate())) {
			patient.setBirthDate(null);
		} else if (!StringUtil.isNullOrEmpty(patientDto.getBirthDate())) {
			patient.setBirthDate(DateUtil.parseDate(patientDto.getBirthDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
		}

		patient.setWeight(patientDto.getWeight());
		patient.setHeight(patientDto.getHeight());
		patient.setStatus(AppConstants.STATUS_ACTIVE);

		return patient;
	}
	
//	private String generateRefNo(String prefix, String dataType) {
//		logger.debug(String.format("I:--START--:--Generate Reference No--:dataType/%s", dataType));
//		Date currentDate = DateUtil.getCurrentDate();
//		Integer seqNo = configService.getCurrentSequence(dataType, currentDate);
//		String refNo = prefix + StringUtil.padLeftZeros(dataType, 2) + DateUtil.formatDate(currentDate, "yyyyMM") + String.format("%06d", seqNo);
//		logger.debug("O:--SUCCESS--:--Generate Reference No--:refNo/" + refNo);
//		return refNo;
//	}

	@Override
	public PatientDto savePatient(PatientDto patientDto) {
		
		try {
			
			TbMPatient patient = this.preparePatient(patientDto);
			List<TbTSurvivalFollowup> survivalFollowupList = prepareSurvivalFollowup(patientDto.getSurvivalFollowupList(), patient);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("PATIENT", patient);
			map.put("SURVIVAL_FOLLOWUP_LIST", survivalFollowupList);
			patientService.savePatient(map);
			
			PatientDto output = new PatientDto();
			output.setHn(patient.getHn());
			return output;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Save Patient--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Save Patient--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Save Patient--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
	
	private List<TbTSurvivalFollowup> prepareSurvivalFollowup(List<SurvivalFollowupDto> survivalFollowupDtoList, TbMPatient patient) {
		
		List<TbTSurvivalFollowup> survivalFollowupList = new ArrayList<TbTSurvivalFollowup>();
		
		for(SurvivalFollowupDto survivalFollowupDto : survivalFollowupDtoList) {

			TbTSurvivalFollowup survivalFollowup =  new TbTSurvivalFollowup();
	
			if (survivalFollowupDto.getId() != null) {
				survivalFollowup = patientService.getSurvivalFollowupById(survivalFollowupDto.getId());
				
				if (survivalFollowup == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "SurvivalFollowup does not exist.");
				}
			}
			
			survivalFollowup.setId(survivalFollowupDto.getId());
			survivalFollowup.setPatient(patient);
			survivalFollowup.setStatus(survivalFollowupDto.getStatus());
			
			if (AppConstants.EMPTY_STRING.equals(survivalFollowupDto.getLastFollowupDate())) {
				survivalFollowup.setLastFollowupDate(null);
			} else if (!StringUtil.isNullOrEmpty(survivalFollowupDto.getLastFollowupDate())) {
				survivalFollowup.setLastFollowupDate(DateUtil.parseDate(survivalFollowupDto.getLastFollowupDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
			}
			
			if (AppConstants.EMPTY_STRING.equals(survivalFollowupDto.getRecordDate())) {
				survivalFollowup.setRecordDate(null);
			} else if (!StringUtil.isNullOrEmpty(survivalFollowupDto.getRecordDate())) {
				survivalFollowup.setRecordDate(DateUtil.parseDate(survivalFollowupDto.getRecordDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
			}
			
			if (AppConstants.EMPTY_STRING.equals(survivalFollowupDto.getDeathDate())) {
				survivalFollowup.setDeathDate(null);
			} else if (!StringUtil.isNullOrEmpty(survivalFollowupDto.getDeathDate())) {
				survivalFollowup.setDeathDate(DateUtil.parseDate(survivalFollowupDto.getDeathDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
			}
	
			if (survivalFollowupDto.getVitalStatus() != null) {
				String vitalStatusCode = StringUtil.nullSafeTrim(survivalFollowupDto.getVitalStatus().getCode());
	
				if (AppConstants.EMPTY_STRING.equals(vitalStatusCode)) {
					survivalFollowup.setVitalStatus(null);
				} else {
					TbMVitalStatus vitalStatus = masterDataService.getByCode(TbMVitalStatus.class, vitalStatusCode);
	
					if (vitalStatus == null) {
						throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "vitalStatus does not exist.");
					} else {
						survivalFollowup.setVitalStatus(vitalStatus);
					}
				}
			}
			
			survivalFollowupList.add(survivalFollowup);
		}
		
		return survivalFollowupList;
	}

	@Override
	public void deletePatient(PatientDto input) {
		
		try {
			logger.info(String.format("I:--START--:--Delete Patient by ID--:id/%s", input.getId()));
			TbMPatient patient = this.findPatientById(input.getId());
			patientService.deletePatient(patient);
			logger.info("O:--SUCCESS--:--Delete Patient by ID--");
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Delete Patient by ID--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Delete Patient by ID--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Delete Patient by ID--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public PatientDto getPatientById(PatientDto input) {
		
		try {
			logger.info(String.format("I:--START--:--Get Patient by ID--:id/%s", input.getId()));
			
			TbMPatient patient = this.findPatientById(input.getId());
			
			PatientDto output = new PatientDto();
			output.setId(patient.getId());
			output.setHn(patient.getHn());
			output.setBirthDate(DateUtil.formatDate(patient.getBirthDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
			
			if (patient.getGender() != null) {
				output.setGender(new DataItem(patient.getGender().getCode(), patient.getGender().getName()));
			}
			
			if (patient.getRace() != null) {
				output.setRace(new DataItem(patient.getRace().getCode(), patient.getRace().getName()));
			}
			
			output.setWeight(patient.getWeight());
			output.setHeight(patient.getHeight());
			
			List<TbTSurvivalFollowup> survivalFollowupList = patientService.getSurvivalFollowupListByPatientId(patient.getId(), AppConstants.STATUS_ACTIVE);
			
			if (survivalFollowupList != null && survivalFollowupList.size() > 0) {
				
				List<SurvivalFollowupDto> survivalFollowupDtoList = new ArrayList<SurvivalFollowupDto>();
				 
				for (TbTSurvivalFollowup survivalFollowup : survivalFollowupList) {
				
					SurvivalFollowupDto survivalFollowupDto = new SurvivalFollowupDto();
					survivalFollowupDto.setId(survivalFollowup.getId());
					survivalFollowupDto.setStatus(survivalFollowup.getStatus());
					
					if (survivalFollowup.getDeathDate() != null) {
						survivalFollowupDto.setDeathDate(DateUtil.formatDate(survivalFollowup.getDeathDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
					}
					if (survivalFollowup.getLastFollowupDate() != null) {
						survivalFollowupDto.setLastFollowupDate(DateUtil.formatDate(survivalFollowup.getLastFollowupDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
					}
					if (survivalFollowup.getRecordDate() != null) {
						survivalFollowupDto.setRecordDate(DateUtil.formatDate(survivalFollowup.getRecordDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
					}
					if (survivalFollowup.getVitalStatus() != null) {
						survivalFollowupDto.setVitalStatus(new DataItem(survivalFollowup.getVitalStatus().getCode(), survivalFollowup.getVitalStatus().getName()));
					}
					
					survivalFollowupDtoList.add(survivalFollowupDto);
				}
				
				output.setSurvivalFollowupList(survivalFollowupDtoList);
			}
			
			return output;
			
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get Patient by ID--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get Patient by ID--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get Patient by ID--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void findDuplicateHn(PatientDto patientDto) {

		try {
			Long patientId = patientDto.getId();
			String hn = patientDto.getHn();
			
			logger.info(String.format("I:--START--:--Find Duplicate HN--:patientId/%s:hn/%s", patientId, hn));
			boolean isDuplicate = patientService.findDuplicateHn(patientId, hn);
			
			if (isDuplicate) {
				throw new ServiceException(MessageCode.ERROR_DUPLICATED.getCode(), "HN already exists in the system.");
			}
			
			logger.info("O:--SUCCESS--:--Find Duplicate HN--");
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Find Duplicate HN--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Find Duplicate HN--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Find Duplicate HN--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
	
	private TbMPatient findPatientById(Long patientId) {
		
		TbMPatient patient = new TbMPatient();
		
		if (patientId != null) {
			patient = patientService.getPatientById(patientId, AppConstants.STATUS_ACTIVE);
			
			if (patient == null) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Patient does not exist.");
			}
		}
		
		return patient;
	}
}
