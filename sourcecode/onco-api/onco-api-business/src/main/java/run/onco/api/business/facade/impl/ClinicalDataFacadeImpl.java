package run.onco.api.business.facade.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import run.onco.api.business.dto.CancerStageDto;
import run.onco.api.business.dto.ClinicalCourseDto;
import run.onco.api.business.dto.ClinicalDataDto;
import run.onco.api.business.dto.DiagnosisDto;
import run.onco.api.business.dto.PathologicalDto;
import run.onco.api.business.dto.PatientDto;
import run.onco.api.business.facade.ClinicalDataFacade;
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
import run.onco.api.persist.entity.TbCParameter;
import run.onco.api.persist.entity.TbMPatient;
import run.onco.api.persist.entity.TbMTissueProcedure;
import run.onco.api.persist.entity.TbMTissueType;
import run.onco.api.persist.entity.TbTCancerStage;
import run.onco.api.persist.entity.TbTClinicalCourse;
import run.onco.api.persist.entity.TbTClinicalData;
import run.onco.api.persist.entity.TbTDiagnosis;
import run.onco.api.persist.entity.TbTPathological;
import run.onco.api.service.ClinicalDataService;
import run.onco.api.service.ConfigService;
import run.onco.api.service.MasterDataService;
import run.onco.api.service.PathoService;
import run.onco.api.service.PatientService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class ClinicalDataFacadeImpl implements ClinicalDataFacade {

	private final static Logger logger = Logger.getLogger(ClinicalDataFacadeImpl.class);

	@Autowired
	private MasterDataService masterDataService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private ClinicalDataService clinicalDataService;
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private PathoService pathoService;

	@Override
	public ClinicalDataDto saveClinicalData(ClinicalDataDto clinicalDataDto) {

		try {
			
			logger.info("I:--START--:--Save ClinicalData--");
		
			TbTClinicalData clinicalData = null;

			if (clinicalDataDto.getId() != null) {
				clinicalData = clinicalDataService.getClinicalDataById(clinicalDataDto.getId(), AppConstants.STATUS_ACTIVE);

				if (clinicalData == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "ClinicalData does not exist.");
				}
			} else {
				clinicalData = new TbTClinicalData();
				clinicalData.setRefNo(generateRefNo("REF", AppConstants.DATA_TYPE_CLINICAL_DATA));
				clinicalData.setStatus(AppConstants.STATUS_ACTIVE);
			}

			// Patient
			TbMPatient patient = null;
			
			if (clinicalDataDto.getPatient() != null) {
				PatientDto patientDto = clinicalDataDto.getPatient();
				
				if (patientDto.getId() != null) {
					patient = patientService.getPatientById(patientDto.getId(), AppConstants.STATUS_ACTIVE);
					 
					if (patient == null) {
						throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Patient does not exist.");
					}
					 
					clinicalData.setPatient(patient);
				}
			}

			// Diagnosis
			List<TbTDiagnosis> diagnosisList = prepareDiagnosis(clinicalDataDto.getDiagnosisList(), clinicalData);

			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("CLINICAL_DATA", clinicalData);
			map.put("DIAGNOSIS_LIST", diagnosisList);
			clinicalDataService.saveClinicalData(map);
			
			ClinicalDataDto output = new ClinicalDataDto();
			output.setRef(clinicalData.getRefNo());
			
			PatientDto patientDto = new PatientDto();
			patientDto.setHn(patient.getHn());
			output.setPatient(patientDto);
			
			logger.info("O:--SUCCESS--:--Save ClinicalData--");
			return output;
		} catch (ValidationException ex) {
			logger.info(String.format("O:--FAIL--:--Save ClinicalData--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.info(String.format("O:--FAIL--:--Save ClinicalData--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.info(String.format("O:--FAIL--:--Save ClinicalData--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	private List<TbTDiagnosis> prepareDiagnosis(List<DiagnosisDto> diagnosisDtoList, TbTClinicalData clinicalData) {

		List<TbTDiagnosis> diagnosisList = new ArrayList<TbTDiagnosis>();

		for (DiagnosisDto diagnosisDto : diagnosisDtoList) {

			TbTDiagnosis diagnosis = new TbTDiagnosis();
			
			if (diagnosisDto.getId() != null) {
				diagnosis = clinicalDataService.getDiagnosisById(diagnosisDto.getId());
				
				if (diagnosis == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Diagnosis does not exist.");
				}
			}
			
			if (diagnosisDto.getPrimaryDiagnosis() != null) {
				diagnosis.setPrimaryDxCode(StringUtil.nullSafeTrim(diagnosisDto.getPrimaryDiagnosis().getCode()));
				diagnosis.setPrimaryDxName(StringUtil.nullSafeTrim(diagnosisDto.getPrimaryDiagnosis().getName()));
			}

			if (!StringUtil.isNullOrEmpty(diagnosisDto.getDiagnosisDate())) {
				diagnosis.setDiagnosisDate(DateUtil.parseDate(diagnosisDto.getDiagnosisDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
			}

			diagnosis.setClinicalData(clinicalData);
			
			// Pathological
			if (diagnosisDto.getPathoList() != null) {
				List<TbTPathological> pathoList = this.preparePathological(diagnosisDto.getPathoList(), diagnosis);
				diagnosis.setPathoList(pathoList);
			}
			
			// CancerStage
			if (diagnosisDto.getCancerStage() != null) {
				TbTCancerStage cancerStage = this.prepareCancerStage(diagnosisDto.getCancerStage(), diagnosis);
				diagnosis.setCancerStage(cancerStage);
			}
			
			// ClinicalCourse
			if (diagnosisDto.getClinicalCourseList() != null) {
				List<TbTClinicalCourse> clinicalCourseList = this.prepareClinicalCourse(diagnosisDto.getClinicalCourseList(), diagnosis);
				diagnosis.setClinicalCourseList(clinicalCourseList);
			}
			
			diagnosis.setStatus(diagnosisDto.getStatus());
			diagnosisList.add(diagnosis);
		}

		return diagnosisList;
	}

	private List<TbTPathological> preparePathological(List<PathologicalDto> pathoDtoList, TbTDiagnosis diagnosis) {
		
		List<TbTPathological> pathoList = new ArrayList<TbTPathological>();
		
		for(PathologicalDto pathoDto : pathoDtoList) {
			
			TbTPathological pathological = new TbTPathological();
			
			if (pathoDto.getId() != null) {
				pathological = clinicalDataService.getPathologicalById(pathoDto.getId());
				
				if (pathological == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Pathological does not exist.");
				}
			}
			
			pathological.setDiagnosis(diagnosis);
			
			String pathoNo = StringUtil.nullSafeTrim(pathoDto.getPathologyNo());
	
			if (AppConstants.EMPTY_STRING.equals(pathoNo)) {
				pathological.setPathologyNo(null);
			} else {
				pathological.setPathologyNo(pathoNo);
			}
	
			String tissueReportDate = StringUtil.nullSafeTrim(pathoDto.getTissueReportDate());
	
			if (AppConstants.EMPTY_STRING.equals(tissueReportDate)) {
				pathological.setTissueReportDate(null);
			} else {
				pathological.setTissueReportDate(DateUtil.parseDate(tissueReportDate, DateUtil.FORMAT_STORE_PROCEDURE_DATE));
			}
	
			String resectionBiopsySite = StringUtil.nullSafeTrim(pathoDto.getResectionBiopsySite());
	
			if (AppConstants.EMPTY_STRING.equals(resectionBiopsySite)) {
				pathological.setResectionBiopsySite(null);
			} else {
				pathological.setResectionBiopsySite(resectionBiopsySite);
			}
	
			if (pathoDto.getTissueProcedure() != null) {
				String procedureCode = StringUtil.nullSafeTrim(pathoDto.getTissueProcedure().getCode());
	
				if (AppConstants.EMPTY_STRING.equals(procedureCode)) {
					pathological.setProcedure(null);
				} else {
					TbMTissueProcedure procedure = masterDataService.getByCode(TbMTissueProcedure.class, procedureCode);
	
					if (procedure == null) {
						throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "TissueProcedureCode does not exist.");
					} else {
						pathological.setProcedure(procedure);
					}
				}
			}
	
			if (pathoDto.getTissueType() != null) {
				String tissueTypeCode = StringUtil.nullSafeTrim(pathoDto.getTissueType().getCode());
	
				if (AppConstants.EMPTY_STRING.equals(tissueTypeCode)) {
					pathological.setTissueType(null);
				} else {
					TbMTissueType tissueType = masterDataService.getByCode(TbMTissueType.class, tissueTypeCode);
	
					if (tissueType == null) {
						throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "TissueTypeCode does not exist.");
					} else {
						pathological.setTissueType(tissueType);
					}
				}
			}
			
			if (pathoDto.getMorphology() != null) {
				pathological.setMorphologyCode(StringUtil.nullSafeTrim(pathoDto.getMorphology().getCode()));
				pathological.setMorphologyName(StringUtil.nullSafeTrim(pathoDto.getMorphology().getName()));
			}
	
			if (pathoDto.getHistologicGrade() != null) {
				String gradeCode = StringUtil.nullSafeTrim(pathoDto.getHistologicGrade().getCode());
	
				if (AppConstants.EMPTY_STRING.equals(gradeCode)) {
					pathological.setHistologicGrade(null);
				} else {
					TbCParameter param = masterDataService.getByCode(TbCParameter.class, gradeCode, AppConstants.Parameter.PATHO_STAGE.getValue());
	
					if (param == null) {
						throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "HistologicGrade does not exist.");
					} else {
						pathological.setHistologicGrade(param.getId());
					}
				}
			}
			
			pathological.setStatus(pathoDto.getStatus());
			pathoList.add(pathological);
		}

		return pathoList;
	}

	private TbTCancerStage prepareCancerStage(CancerStageDto cancerStageDto, TbTDiagnosis diagnosis) {

		TbTCancerStage cancerStage = new TbTCancerStage();

		if (cancerStageDto.getId() != null) {
			cancerStage = clinicalDataService.getCancerStageById(cancerStageDto.getId());
			
			if (cancerStage == null) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "CancerStage does not exist.");
			}
		}

		cancerStage.setDiagnosis(diagnosis);
		
		if (cancerStageDto.getAjcc() != null) {
			String ajccCode = StringUtil.nullSafeTrim(cancerStageDto.getAjcc().getCode());

			if (AppConstants.EMPTY_STRING.equals(ajccCode)) {
				cancerStage.setAjcc(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, ajccCode, AppConstants.Parameter.AJCC.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "AJCC does not exist.");
				} else {
					cancerStage.setAjcc(param.getId());
				}
			}
		}

		if (cancerStageDto.getStageType() != null) {
			String stageTypeCode = StringUtil.nullSafeTrim(cancerStageDto.getStageType().getCode());

			if (AppConstants.EMPTY_STRING.equals(stageTypeCode)) {
				cancerStage.setStageType(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, stageTypeCode, AppConstants.Parameter.STAGE_TYPE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "StageType does not exist.");
				} else {
					cancerStage.setStageType(param.getId());
				}
			}
		}

		if (cancerStageDto.getcTStage() != null) {
			String ctStageCode = StringUtil.nullSafeTrim(cancerStageDto.getcTStage().getCode());

			if (AppConstants.EMPTY_STRING.equals(ctStageCode)) {
				cancerStage.setcTStage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, ctStageCode, AppConstants.Parameter.T_STAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "cTStage does not exist.");
				} else {
					cancerStage.setcTStage(param.getId());
				}
			}
		}
		
		if (cancerStageDto.getcTSubstage() != null) {
			String ctSubstageCode = StringUtil.nullSafeTrim(cancerStageDto.getcTSubstage().getCode());
			
			if (AppConstants.EMPTY_STRING.equals(ctSubstageCode)) {
				cancerStage.setcTSubstage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, ctSubstageCode, AppConstants.Parameter.SUBSTAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "cTSubstage does not exist.");
				} else {
					cancerStage.setcTSubstage(param.getId());
				}
			}
		}

		if (cancerStageDto.getcNStage() != null) {
			String cnStageCode = StringUtil.nullSafeTrim(cancerStageDto.getcNStage().getCode());

			if (AppConstants.EMPTY_STRING.equals(cnStageCode)) {
				cancerStage.setcNStage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, cnStageCode, AppConstants.Parameter.N_STAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "cNStage does not exist.");
				} else {
					cancerStage.setcNStage(param.getId());
				}
			}
		}
		
		if (cancerStageDto.getcNSubstage() != null) {
			String cnSubstageCode = StringUtil.nullSafeTrim(cancerStageDto.getcNSubstage().getCode());
			
			if (AppConstants.EMPTY_STRING.equals(cnSubstageCode)) {
				cancerStage.setcNSubstage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, cnSubstageCode, AppConstants.Parameter.SUBSTAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "cNSubstage does not exist.");
				} else {
					cancerStage.setcNSubstage(param.getId());
				}
			}
		}

		if (cancerStageDto.getpTStage() != null) {
			String ptStageCode = StringUtil.nullSafeTrim(cancerStageDto.getpTStage().getCode());

			if (AppConstants.EMPTY_STRING.equals(ptStageCode)) {
				cancerStage.setpTStage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, ptStageCode, AppConstants.Parameter.T_STAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "pTStage does not exist.");
				} else {
					cancerStage.setpTStage(param.getId());
				}
			}
		}
		
		if (cancerStageDto.getpTSubstage() != null) {
			String ptSubstageCode = StringUtil.nullSafeTrim(cancerStageDto.getpTSubstage().getCode());
			
			if (AppConstants.EMPTY_STRING.equals(ptSubstageCode)) {
				cancerStage.setpTSubstage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, ptSubstageCode, AppConstants.Parameter.SUBSTAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "ptSubstage does not exist.");
				} else {
					cancerStage.setpTStage(param.getId());
				}
			}
		}

		if (cancerStageDto.getpNStage() != null) {
			String pnStageCode = StringUtil.nullSafeTrim(cancerStageDto.getpNStage().getCode());

			if (AppConstants.EMPTY_STRING.equals(pnStageCode)) {
				cancerStage.setpNStage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, pnStageCode, AppConstants.Parameter.N_STAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "pNStage does not exist.");
				} else {
					cancerStage.setpNStage(param.getId());
				}
			}
		}
		
		if (cancerStageDto.getpNSubstage() != null) {
			String pnSubstageCode = StringUtil.nullSafeTrim(cancerStageDto.getpNSubstage().getCode());

			if (AppConstants.EMPTY_STRING.equals(pnSubstageCode)) {
				cancerStage.setpNSubstage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, pnSubstageCode, AppConstants.Parameter.SUBSTAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "pNSubstage does not exist.");
				} else {
					cancerStage.setpNSubstage(param.getId());
				}
			}
		}

		if (cancerStageDto.getYpTStage() != null) {
			String yptStageCode = StringUtil.nullSafeTrim(cancerStageDto.getYpTStage().getCode());

			if (AppConstants.EMPTY_STRING.equals(yptStageCode)) {
				cancerStage.setYpTStage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, yptStageCode, AppConstants.Parameter.T_STAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "yptStage does not exist.");
				} else {
					cancerStage.setYpTStage(param.getId());
				}
			}
		}
		
		if (cancerStageDto.getYpTSubstage() != null) {
			String yptSubstageCode = StringUtil.nullSafeTrim(cancerStageDto.getYpTSubstage().getCode());

			if (AppConstants.EMPTY_STRING.equals(yptSubstageCode)) {
				cancerStage.setYpTSubstage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, yptSubstageCode, AppConstants.Parameter.SUBSTAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "yptSubstage does not exist.");
				} else {
					cancerStage.setYpTSubstage(param.getId());
				}
			}
		}

		if (cancerStageDto.getYpNStage() != null) {
			String ypnStageCode = StringUtil.nullSafeTrim(cancerStageDto.getYpNStage().getCode());

			if (AppConstants.EMPTY_STRING.equals(ypnStageCode)) {
				cancerStage.setYpNStage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, ypnStageCode, AppConstants.Parameter.N_STAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "ypnStage does not exist.");
				} else {
					cancerStage.setYpNStage(param.getId());
				}
			}
		}
		
		if (cancerStageDto.getYpNSubstage() != null) {
			String ypnSubstageCode = StringUtil.nullSafeTrim(cancerStageDto.getYpNSubstage().getCode());

			if (AppConstants.EMPTY_STRING.equals(ypnSubstageCode)) {
				cancerStage.setYpNStage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, ypnSubstageCode, AppConstants.Parameter.SUBSTAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "ypnSubstage does not exist.");
				} else {
					cancerStage.setYpNSubstage(param.getId());
				}
			}
		}

		if (cancerStageDto.getmStage() != null) {
			String mStageCode = StringUtil.nullSafeTrim(cancerStageDto.getmStage().getCode());

			if (AppConstants.EMPTY_STRING.equals(mStageCode)) {
				cancerStage.setmStage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, mStageCode, AppConstants.Parameter.M_STAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "mtStage does not exist.");
				} else {
					cancerStage.setmStage(param.getId());
				}
			}
		}
		
		if (cancerStageDto.getmSubstage() != null) {
			String mSubstageCode = StringUtil.nullSafeTrim(cancerStageDto.getmStage().getCode());

			if (AppConstants.EMPTY_STRING.equals(mSubstageCode)) {
				cancerStage.setmStage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, mSubstageCode, AppConstants.Parameter.SUBSTAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "mtSubstage does not exist.");
				} else {
					cancerStage.setmSubstage(param.getId());
				}
			}
		}

		if (cancerStageDto.getClinicalStage() != null) {
			String clinicalStageCode = StringUtil.nullSafeTrim(cancerStageDto.getClinicalStage().getCode());

			if (AppConstants.EMPTY_STRING.equals(clinicalStageCode)) {
				cancerStage.setClinicalStage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, clinicalStageCode, AppConstants.Parameter.CLINICAL_STAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "ClinicalStageCode does not exist.");
				} else {
					cancerStage.setClinicalStage(param.getId());
				}
			}
		}

		if (cancerStageDto.getPathoStage() != null) {
			String pathologicalStageCode = StringUtil.nullSafeTrim(cancerStageDto.getPathoStage().getCode());

			if (AppConstants.EMPTY_STRING.equals(pathologicalStageCode)) {
				cancerStage.setPathologicalStage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, pathologicalStageCode,
						AppConstants.Parameter.PATHO_STAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "PathoStageCode does not exist.");
				} else {
					cancerStage.setPathologicalStage(param.getId());
				}
			}
		}
		
		if (cancerStageDto.getPathoSubstage() != null) {
			String pathoSubstageCode = StringUtil.nullSafeTrim(cancerStageDto.getPathoStage().getCode());

			if (AppConstants.EMPTY_STRING.equals(pathoSubstageCode)) {
				cancerStage.setPathoSubstage(null);
			} else {
				TbCParameter param = masterDataService.getByCode(TbCParameter.class, pathoSubstageCode, AppConstants.Parameter.SUBSTAGE.getValue());

				if (param == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "PathoSubstageCode does not exist.");
				} else {
					cancerStage.setPathoSubstage(param.getId());
				}
			}
		}

		return cancerStage;
	}

	private List<TbTClinicalCourse> prepareClinicalCourse(List<ClinicalCourseDto> clinicalCourseDtoList, TbTDiagnosis diagnosis) {
		
		List<TbTClinicalCourse> clinicalCourseList = new ArrayList<TbTClinicalCourse>();

		for (ClinicalCourseDto clinicalCourseDto : clinicalCourseDtoList) {

			TbTClinicalCourse clinicalCourse = new TbTClinicalCourse();
			
			if (clinicalCourseDto.getId() != null) {
				clinicalCourse = clinicalDataService.getClinicalCourseById(clinicalCourseDto.getId());
				
				if (clinicalCourse == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "ClinicalCourse does not exist.");
				}
			}
			
			clinicalCourse.setDiagnosis(diagnosis);
	
			if (clinicalCourseDto.getRecurrenceStatus() != null) {
				String recurrenceStatusCode = StringUtil.nullSafeTrim(clinicalCourseDto.getRecurrenceStatus().getCode());
	
				if (AppConstants.EMPTY_STRING.equals(recurrenceStatusCode)) {
					clinicalCourse.setRecurrenceStatus(null);
				} else {
					TbCParameter param = masterDataService.getByCode(TbCParameter.class, recurrenceStatusCode, AppConstants.Parameter.RECUR_STATUS.getValue());
	
					if (param == null) {
						throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "recurrenceStatus does not exist.");
					} else {
						clinicalCourse.setRecurrenceStatus(param.getId());
					}
				}
			}
	
			if (AppConstants.EMPTY_STRING.equals(clinicalCourseDto.getRecurrenceDate())) {
				clinicalCourse.setRecurrenceDate(null);
			} else if (!StringUtil.isNullOrEmpty(clinicalCourseDto.getRecurrenceDate())) {
				clinicalCourse.setRecurrenceDate(DateUtil.parseDate(clinicalCourseDto.getRecurrenceDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
			}
			
			if (AppConstants.EMPTY_STRING.equals(clinicalCourseDto.getRecordDate())) {
				clinicalCourse.setRecordDate(null);
			} else if (!StringUtil.isNullOrEmpty(clinicalCourseDto.getRecordDate())) {
				clinicalCourse.setRecordDate(DateUtil.parseDate(clinicalCourseDto.getRecordDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
			}
			
			clinicalCourse.setStatus(clinicalCourseDto.getStatus());
			clinicalCourseList.add(clinicalCourse);
		}

		return clinicalCourseList;
	}

	private String generateRefNo(String prefix, String dataType) {
		logger.debug(String.format("I:--START--:--Generate Reference No--:dataType/%s", dataType));
		Date currentDate = DateUtil.getCurrentDate();
		Integer seqNo = configService.getCurrentSequence(dataType, currentDate);
		String refNo = prefix + StringUtil.padLeftZeros(dataType, 2) + DateUtil.formatDate(currentDate, "yyyyMM") + String.format("%06d", seqNo);
		logger.debug("O:--SUCCESS--:--Generate Reference No--:refNo/" + refNo);
		return refNo;
	}

	@Override
	public DataTableResults<ClinicalDataDto> searchClinicalData(DataTableRequest<ClinicalDataDto> req) {

		try {

			ClinicalDataDto searchCriteria = req.getCriteria();
			
			String refNo = searchCriteria.getRef();
			String status = searchCriteria.getStatus();
			Paging paging = req.getPaging();
			
			HashMap<String, Object> criteria = new HashMap<String, Object>();
			criteria.put("refNo", refNo);
			criteria.put("status", status);

			String hn = null;
			if (searchCriteria.getPatient() != null) {
				hn = searchCriteria.getPatient().getHn();
				criteria.put("hn", hn);
			}

			logger.info(String.format("I:--START--:--Search ClinicalData--:refNo/%s:hn/%s:status/%s", refNo, hn, status));
			
			int paginatedCount = clinicalDataService.countClinicalDataUsingQuery(criteria, paging);
			
			List<TbTClinicalData> clinicalDataList = clinicalDataService.listPaginatedClinicalDataUsingQuery(criteria, paging);
			
			if (clinicalDataList == null || clinicalDataList.size() == 0) {
				logger.info(String.format("O:--FAIL--:--Search ClinicalData--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc()));
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<ClinicalDataDto> dataList = new ArrayList<ClinicalDataDto>();
			
			for (TbTClinicalData clinicalData : clinicalDataList) {
				ClinicalDataDto clinicalDataDto = new ClinicalDataDto();
				clinicalDataDto.setId(clinicalData.getId());
				clinicalDataDto.setRef(clinicalData.getRefNo());
				
				if(clinicalData.getPatient() != null) {
					TbMPatient patient = clinicalData.getPatient();
					
					PatientDto patientDto = new PatientDto();
					patientDto.setId(patient.getId());
					patientDto.setHn(patient.getHn());
					clinicalDataDto.setPatient(patientDto);
				}
				
				dataList.add(clinicalDataDto);
			}
			
			logger.info(String.format("O:--SUCCESS--:--Search ClinicalData--:paginatedCount/%s", paginatedCount));
			DataTableResults<ClinicalDataDto> results = new DataTableResults<ClinicalDataDto>(dataList, paginatedCount, 0, 15);
			return results;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get ClinicalData by ID--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get ClinicalData by ID--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Save ClinicalData--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public ClinicalDataDto getClinicalDataById(ClinicalDataDto input) {
		
		try {
			logger.info(String.format("I:--START--:--Get ClinicalData by ID--:id/%s", input.getId()));
			
			TbTClinicalData clinicalData = null;
			
			if (input.getId() != null) {
				clinicalData = clinicalDataService.getClinicalDataById(input.getId(), AppConstants.STATUS_ACTIVE);
				
				if (clinicalData == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Clinical data does not exist.");
				}
			}
			
			ClinicalDataDto output = new ClinicalDataDto();
			output.setId(clinicalData.getId());
			output.setRef(clinicalData.getRefNo());
			
			TbMPatient patient = clinicalData.getPatient();
			
			if(patient == null) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Patient does not exist.");
			}
			
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
			
			List<TbTDiagnosis> diagnosisList = clinicalDataService.getDiagnosisListByClinicalDataId(clinicalData.getId());
			
			if(diagnosisList != null && diagnosisList.size() > 0) {
				
				List<DiagnosisDto> diagnosisDtoList = new ArrayList<DiagnosisDto>();
				
				for (TbTDiagnosis diagnosis : diagnosisList) {
					DiagnosisDto diagnosisDto = new DiagnosisDto();
					diagnosisDto.setId(diagnosis.getId());
					
					if (diagnosis.getDiagnosisDate() != null) {
						diagnosisDto.setDiagnosisDate(DateUtil.formatDate(diagnosis.getDiagnosisDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
					}
					
					DataItem primaryDiagnosis = new DataItem(diagnosis.getPrimaryDxCode(), diagnosis.getPrimaryDxName());
					diagnosisDto.setPrimaryDiagnosis(primaryDiagnosis);
					diagnosisDto.setStatus(diagnosis.getStatus());
					
					// Get patho list
					List<TbTPathological> pathoList = pathoService.getPathoListByDiagnosisId(diagnosis.getId(), AppConstants.STATUS_ACTIVE);
					
					if (pathoList != null && pathoList.size() > 0) {
						
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
						
						diagnosisDto.setPathoList(pathoDtoList);
					}
					
					// CancerStage
					if (diagnosis.getCancerStage() != null) {
						
						CancerStageDto cancerStageDto = this.prepareCancerStage(diagnosis.getCancerStage());
						diagnosisDto.setCancerStage(cancerStageDto);
					}
					
					// ClinicalCourse
					List<TbTClinicalCourse> clinicalCourseList = clinicalDataService.getClinicalCourseListByDiagnosisId(diagnosis.getId(), AppConstants.STATUS_ACTIVE);
					
					if (clinicalCourseList != null && clinicalCourseList.size() > 0) {
						
						List<ClinicalCourseDto> clinicalCourseDtoList = new ArrayList<ClinicalCourseDto>();
						
						for (TbTClinicalCourse clinicalCourse : clinicalCourseList) {
							
							ClinicalCourseDto clinicalCourseDto = new ClinicalCourseDto();
							clinicalCourseDto.setId(clinicalCourse.getId());
							clinicalCourseDto.setStatus(clinicalCourse.getStatus());
							
							if (clinicalCourse.getRecurrenceDate() != null) {
								clinicalCourseDto.setRecurrenceDate(DateUtil.formatDate(clinicalCourse.getRecurrenceDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
							}
							
							if (clinicalCourse.getRecurrenceStatus() != null) {
								TbCParameter recurrenceStatus = masterDataService.getById(TbCParameter.class, clinicalCourse.getRecurrenceStatus());
								
								if(recurrenceStatus != null) {
									clinicalCourseDto.setRecurrenceStatus(new DataItem(recurrenceStatus.getCode(), recurrenceStatus.getName()));
								}
							}
							
							if (clinicalCourse.getRecordDate() != null) {
								clinicalCourseDto.setRecordDate(DateUtil.formatDate(clinicalCourse.getRecordDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
							}
							
							clinicalCourseDtoList.add(clinicalCourseDto);
						}
						
						diagnosisDto.setClinicalCourseList(clinicalCourseDtoList);
					}
					
					diagnosisDtoList.add(diagnosisDto);
				}
				
				output.setDiagnosisList(diagnosisDtoList);
			}
			
			logger.info("O:--SUCCESS--:--Get ClinicalData by ID--");
			return output;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get ClinicalData by ID--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get ClinicalData by ID--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get ClinicalData by ID--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
	
	private CancerStageDto prepareCancerStage(TbTCancerStage cancerStage) {
	
		CancerStageDto cancerStageDto = new CancerStageDto();
		cancerStageDto.setId(cancerStage.getId());
			
		if (cancerStage.getAjcc() != null) {
			TbCParameter ajcc = masterDataService.getById(TbCParameter.class, cancerStage.getAjcc());

			if (ajcc != null) {
				cancerStageDto.setAjcc(new DataItem(ajcc.getCode(), ajcc.getName()));
			}
		}

		if (cancerStage.getStageType() != null) {
			TbCParameter stageType = masterDataService.getById(TbCParameter.class, cancerStage.getStageType());

			if (stageType != null) {
				cancerStageDto.setStageType(new DataItem(stageType.getCode(), stageType.getName()));
			}
		}

		if (cancerStage.getcTStage() != null) {
			TbCParameter cTStage = masterDataService.getById(TbCParameter.class, cancerStage.getcTStage());

			if (cTStage != null) {
				cancerStageDto.setcTStage(new DataItem(cTStage.getCode(), cTStage.getName()));
			}
		}

		if (cancerStage.getcTSubstage() != null) {
			TbCParameter cTSubstage = masterDataService.getById(TbCParameter.class, cancerStage.getcTSubstage());

			if (cTSubstage != null) {
				cancerStageDto.setcTSubstage(new DataItem(cTSubstage.getCode(), cTSubstage.getName()));
			}
		}

		if (cancerStage.getcNStage() != null) {
			TbCParameter cNStage = masterDataService.getById(TbCParameter.class, cancerStage.getcNStage());

			if (cNStage != null) {
				cancerStageDto.setcNStage(new DataItem(cNStage.getCode(), cNStage.getName()));
			}
		}

		if (cancerStage.getcNSubstage() != null) {
			TbCParameter cNSubstage = masterDataService.getById(TbCParameter.class, cancerStage.getcNSubstage());

			if (cNSubstage != null) {
				cancerStageDto.setcNSubstage(new DataItem(cNSubstage.getCode(), cNSubstage.getName()));
			}
		}

		if (cancerStage.getpTStage() != null) {
			TbCParameter pTStage = masterDataService.getById(TbCParameter.class, cancerStage.getpTStage());

			if (pTStage != null) {
				cancerStageDto.setpTStage(new DataItem(pTStage.getCode(), pTStage.getName()));
			}
		}

		if (cancerStage.getpTSubstage() != null) {
			TbCParameter pTSubstage = masterDataService.getById(TbCParameter.class, cancerStage.getpTSubstage());

			if (pTSubstage != null) {
				cancerStageDto.setpTSubstage(new DataItem(pTSubstage.getCode(), pTSubstage.getName()));
			}
		}

		if (cancerStage.getpNStage() != null) {
			TbCParameter pNStage = masterDataService.getById(TbCParameter.class, cancerStage.getpNStage());

			if (pNStage != null) {
				cancerStageDto.setpNStage(new DataItem(pNStage.getCode(), pNStage.getName()));
			}
		}

		if (cancerStage.getpNSubstage() != null) {
			TbCParameter pNSubstage = masterDataService.getById(TbCParameter.class, cancerStage.getpNSubstage());

			if (pNSubstage != null) {
				cancerStageDto.setpNSubstage(new DataItem(pNSubstage.getCode(), pNSubstage.getName()));
			}
		}

		if (cancerStage.getYpTStage() != null) {
			TbCParameter ypTStage = masterDataService.getById(TbCParameter.class, cancerStage.getYpTStage());

			if (ypTStage != null) {
				cancerStageDto.setYpTStage(new DataItem(ypTStage.getCode(), ypTStage.getName()));
			}
		}

		if (cancerStage.getYpTSubstage() != null) {
			TbCParameter ypTSubstage = masterDataService.getById(TbCParameter.class, cancerStage.getYpTSubstage());

			if (ypTSubstage != null) {
				cancerStageDto.setYpTSubstage(new DataItem(ypTSubstage.getCode(), ypTSubstage.getName()));
			}
		}

		if (cancerStage.getYpNStage() != null) {
			TbCParameter ypNStage = masterDataService.getById(TbCParameter.class, cancerStage.getYpNStage());

			if (ypNStage != null) {
				cancerStageDto.setYpNStage(new DataItem(ypNStage.getCode(), ypNStage.getName()));
			}
		}

		if (cancerStage.getYpNSubstage() != null) {
			TbCParameter ypNSubstage = masterDataService.getById(TbCParameter.class, cancerStage.getYpNSubstage());

			if (ypNSubstage != null) {
				cancerStageDto.setYpNSubstage(new DataItem(ypNSubstage.getCode(), ypNSubstage.getName()));
			}
		}

		if (cancerStage.getmStage() != null) {
			TbCParameter mStage = masterDataService.getById(TbCParameter.class, cancerStage.getmStage());

			if (mStage != null) {
				cancerStageDto.setmStage(new DataItem(mStage.getCode(), mStage.getName()));
			}
		}

		if (cancerStage.getmSubstage() != null) {
			TbCParameter mSubstage = masterDataService.getById(TbCParameter.class, cancerStage.getmSubstage());

			if (mSubstage != null) {
				cancerStageDto.setmSubstage(new DataItem(mSubstage.getCode(), mSubstage.getName()));
			}
		}

		if (cancerStage.getClinicalStage() != null) {
			TbCParameter clinicalStage = masterDataService.getById(TbCParameter.class, cancerStage.getClinicalStage());

			if (clinicalStage != null) {
				cancerStageDto.setClinicalStage(new DataItem(clinicalStage.getCode(), clinicalStage.getName()));
			}
		}

		if (cancerStage.getPathologicalStage() != null) {
			TbCParameter pathoStage = masterDataService.getById(TbCParameter.class, cancerStage.getPathologicalStage());

			if (pathoStage != null) {
				cancerStageDto.setPathoStage(new DataItem(pathoStage.getCode(), pathoStage.getName()));
			}
		}

		if (cancerStage.getPathoSubstage() != null) {
			TbCParameter pathoSubstage = masterDataService.getById(TbCParameter.class, cancerStage.getPathoSubstage());

			if (pathoSubstage != null) {
				cancerStageDto.setPathoSubstage(new DataItem(pathoSubstage.getCode(), pathoSubstage.getName()));
			}
		}
		
		return cancerStageDto;
	}
	
	@Override
	public void deleteClinicalData(ClinicalDataDto clinicalDataDto) {
		
		try {
			logger.info(String.format("I:--START--:--Delete ClinicalData--:ref/%s", clinicalDataDto.getRef()));
			
			if (!StringUtil.isNullOrWhitespace(clinicalDataDto.getRef())) {
				TbTClinicalData clinicalData = clinicalDataService.getClinicalDataByRef(clinicalDataDto.getRef(), AppConstants.STATUS_ACTIVE);

				if (clinicalData == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "ClinicalData does not exist.");
				}
				
				clinicalDataService.deleteClinicalData(clinicalData);
			}
			
			logger.info("O:--SUCCESS--:--Delete ClinicalData--");
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Delete ClinicalData--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Delete ClinicalData--:errorDesc/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Delete ClinicalData--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
		
	}
}
