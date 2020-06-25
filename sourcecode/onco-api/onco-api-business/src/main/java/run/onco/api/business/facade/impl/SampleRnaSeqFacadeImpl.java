package run.onco.api.business.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import run.onco.api.business.dto.AttachmentDto;
import run.onco.api.business.dto.BiospecimenDto;
import run.onco.api.business.dto.PathologicalDto;
import run.onco.api.business.dto.PatientDto;
import run.onco.api.business.dto.SampleRnaSeqDto;
import run.onco.api.business.facade.SampleRnaSeqFacade;
import run.onco.api.business.message.InquiryOmics;
import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.constants.ConfigurationConstants;
import run.onco.api.common.dto.DataItem;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.exception.ValidationException;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.DateUtil;
import run.onco.api.common.utils.FileUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.common.utils.StringUtil;
import run.onco.api.persist.entity.TbCParameter;
import run.onco.api.persist.entity.TbMPatient;
import run.onco.api.persist.entity.TbMUser;
import run.onco.api.persist.entity.TbTAttachment;
import run.onco.api.persist.entity.TbTBiospecimen;
import run.onco.api.persist.entity.TbTPathological;
import run.onco.api.persist.entity.TbTSampleRnaSeq;
import run.onco.api.service.AttachmentService;
import run.onco.api.service.ClinicalDataService;
import run.onco.api.service.MasterDataService;
import run.onco.api.service.PatientService;
import run.onco.api.service.SampleRnaSeqService;
import run.onco.api.service.UserService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class SampleRnaSeqFacadeImpl implements SampleRnaSeqFacade {
	
	private final static Logger logger = Logger.getLogger(RnaSeqFacadeImpl.class);
	
	@Autowired
	private Environment env;
	
	@Autowired
	private SampleRnaSeqService sampleRnaSeqService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private ClinicalDataService clinicalDataService;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
	private MasterDataService masterDataService;
	
	@Autowired
	private UserService userService;
	
	private String getString(String key) {
		return env.getProperty(key);
	}

	@Override
	public void uploadSamplRnaSeq(SampleRnaSeqDto input) {

		try {
			logger.info("I:--START--:--Upload SampleRnaSeq--");

			TbTSampleRnaSeq sampleRnaSeq = new TbTSampleRnaSeq();
			
			TbMUser user = null;
			if (input.getUserId() != null) {
				user = userService.getActiveUserById(input.getUserId());
				
				if (user == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "User does not exist.");
				}
			}
				
			if (input.getId() != null) {
				sampleRnaSeq = sampleRnaSeqService.getSampleRnaSeqById(input.getId());
					
				if (sampleRnaSeq == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "SampleRnaSeq does not exist.");
				}
				
				sampleRnaSeq.setCreateUser(user);
				sampleRnaSeq.setCreateDate(DateUtil.getCurrentDate());
			}
			
			TbMPatient patient = null;
			if (input.getPatient() != null) {
				patient = patientService.getPatientById(input.getPatient().getId(), AppConstants.STATUS_ACTIVE);
				
				if (patient == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Patient does not exist.");
				}

				sampleRnaSeq.setPatient(patient);
			}
			
			if (input.getBiospecimen() != null) {
				TbTBiospecimen biospecimen = clinicalDataService.getBiospecimenById(input.getBiospecimen().getId());
				
				if (biospecimen == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Biospecimen does not exist.");
				}

				sampleRnaSeq.setBiospecimen(biospecimen);
			}
			
			boolean isDuplicate = sampleRnaSeqService.findDuplicateUploadSampleRnaSeq(sampleRnaSeq.getBiospecimen().getId());
			if (isDuplicate) {
				throw new ServiceException(MessageCode.ERROR_DUPLICATED.getCode(), "Sample RNA-Seq already exists in the system.");
			}
			
			AttachmentDto attachmentDto = input.getAttachment();
			if (attachmentDto != null) {
				TbTAttachment attachment = new TbTAttachment();
					
				if (attachmentDto.getAttachmentId() != null) {
						
					attachment = attachmentService.getAttachmentById(attachmentDto.getAttachmentId());
						
					if (attachment == null) {
						throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Attachment does not exist.");
					}
				}
					
				attachment.setStatus(attachmentDto.getStatus());
				
				if (!StringUtil.isNullOrWhitespace(attachmentDto.getTempFilePath())) {
					String tempFile = attachmentDto.getTempFilePath();
					
					String relativePath = "/" + patient.getHn();
					String absolutePath = String.format("%s%s", getString(ConfigurationConstants.DATA_FILE_PATH), relativePath);
						
					FileUtil.createDirectory(absolutePath);
					String fileDest = String.format("%s/%s", absolutePath, attachmentDto.getFileName());
					FileUtil.moveFile(tempFile, fileDest);
					
					attachment.setFileName(attachmentDto.getFileName());
					attachment.setContentType(attachmentDto.getContentType());
					attachment.setFileSize(attachmentDto.getFileSize());
					attachment.setFilePath(relativePath);
					attachment.setUuid(AppUtil.unique());
					attachment.setCreateDate(DateUtil.getCurrentDate());
					attachment.setCreateUser(user);
				}
					
				sampleRnaSeq.setAttachment(attachment);
			}

			sampleRnaSeq.setStatus(AppConstants.STATUS_DRAFT);
			sampleRnaSeq.setCreateDate(DateUtil.getCurrentDate());
			sampleRnaSeqService.uploadSampleRnaSeq(sampleRnaSeq);

			logger.info("O:--SUCCESS--:--Upload SampleRnaSeq--");
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Upload SampleRnaSeq--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Upload SampleRnaSeq--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Upload SampleRnaSeq--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
	

	@Override
	public List<SampleRnaSeqDto> getSampleRnaSeqListByBiospecimens(InquiryOmics input) {
		
		try {
			
			logger.info("I:--START--:--Get SampleRnaSeqList for Biospecimens--");
			
			List<BiospecimenDto> biospecimenDtoList = input.getBiospecimenList();
			Long[] ids = biospecimenDtoList.stream().map(BiospecimenDto::getId).toArray(Long[]::new);
			
			List<TbTSampleRnaSeq> sampleRnaSeqList = sampleRnaSeqService.getSampleRnaSeqListByBiospecimens(ids);
			
			if (sampleRnaSeqList == null || sampleRnaSeqList.size() == 0) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<SampleRnaSeqDto> dataList = new ArrayList<SampleRnaSeqDto>();
			
			for (TbTSampleRnaSeq sampleRnaSeq : sampleRnaSeqList) {
				SampleRnaSeqDto sampleRnaSeqDto = new SampleRnaSeqDto();
				sampleRnaSeqDto.setId(sampleRnaSeq.getId());
				sampleRnaSeqDto.setStatus(sampleRnaSeq.getStatus());

				TbTBiospecimen biospecimen = clinicalDataService.getBiospecimenById(sampleRnaSeq.getBiospecimen().getId());

				if (biospecimen != null) {
					BiospecimenDto biospecimenDto = new BiospecimenDto();
					biospecimenDto.setId(biospecimen.getId());

					if (biospecimen.getRefNo() != null) {
						biospecimenDto.setRef(biospecimen.getRefNo());
					}

					if (biospecimen.getBloodType() != null) {
						biospecimenDto.setBloodType(new DataItem(biospecimen.getBloodType().getCode(), biospecimen.getBloodType().getName()));
					}

					if (biospecimen.getCellLineType() != null) {
						biospecimenDto.setCellLineType(new DataItem(biospecimen.getCellLineType().getCode(), biospecimen.getCellLineType().getName()));
					}

					if (biospecimen.getCellType() != null) {
						biospecimenDto.setCellType(new DataItem(biospecimen.getCellType().getCode(), biospecimen.getCellType().getName()));
					}

					if (biospecimen.getCollectSampleDate() != null) {
						biospecimenDto.setCollectSampleDate(DateUtil.formatDate(biospecimen.getCollectSampleDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
					}

					if (biospecimen.getFreezeDate() != null) {
						biospecimenDto.setFreezeDate(DateUtil.formatDate(biospecimen.getFreezeDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
					}

					if (biospecimen.getFreezeMethod() != null) {
						biospecimenDto.setFreezeMethod(new DataItem(biospecimen.getFreezeMethod().getCode(), biospecimen.getFreezeMethod().getName()));
					}

					if (biospecimen.getInitialWeight() != null) {
						biospecimenDto.setInitialWeight(biospecimen.getInitialWeight().toString());
					}

					if (biospecimen.getPreserveMethod() != null) {
						biospecimenDto.setPreserveMethod(new DataItem(biospecimen.getPreserveMethod().getCode(), biospecimen.getPreserveMethod().getName()));
					}

					if (biospecimen.getSampleType() != null) {
						biospecimenDto.setSampleType(new DataItem(biospecimen.getSampleType().getCode(), biospecimen.getSampleType().getName()));
					}

					if (biospecimen.getTissueType() != null) {
						biospecimenDto.setTissueType(new DataItem(biospecimen.getTissueType().getCode(), biospecimen.getTissueType().getName()));
					}
					
					logger.info(String.format("O:--Get Biospecimen--:biospecimenId/%s", biospecimenDto.getId()));
					sampleRnaSeqDto.setBiospecimen(biospecimenDto);
				}

				if (sampleRnaSeq.getAttachment() != null) {
					TbTAttachment attachment = sampleRnaSeq.getAttachment();

					AttachmentDto attachmentDto = new AttachmentDto();
					attachmentDto.setAttachmentId(attachment.getId());
					attachmentDto.setContentType(attachment.getContentType());
					attachmentDto.setFileName(attachment.getFileName());
					attachmentDto.setFilePath(attachment.getFilePath());
					attachmentDto.setFileSize(attachment.getFileSize());
					attachmentDto.setUuid(attachment.getUuid());
					attachmentDto.setStatus(attachment.getStatus());
					sampleRnaSeqDto.setAttachment(attachmentDto);
				}
				
				dataList.add(sampleRnaSeqDto);
			}
			
			logger.info("O:--SUCCESS--:--Get SampleRnaSeqList for Biospecimens--");
			return dataList;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get SampleRnaSeqList for Biospecimens--:errorMsg/%s", ex.getMessage()));	
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get SampleRnaSeqList for Biospecimens--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get SampleRnaSeqList for Biospecimens--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public List<SampleRnaSeqDto> getActiveSampleRnaSeqList() {
		
		try {
			logger.info("I:--START--:--Get Active SampleRnaSeq List--");
			
			List<TbTSampleRnaSeq> sampleRnaSeqList = sampleRnaSeqService.getActiveSampleRnaSeqList();
			
			if (sampleRnaSeqList == null || sampleRnaSeqList.size() == 0) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<SampleRnaSeqDto> dataList = new ArrayList<SampleRnaSeqDto>();

			for (TbTSampleRnaSeq sampleRnaSeq : sampleRnaSeqList) {
				SampleRnaSeqDto sampleRnaSeqDto = new SampleRnaSeqDto();
				sampleRnaSeqDto.setId(sampleRnaSeq.getId());
				sampleRnaSeqDto.setStatus(sampleRnaSeq.getStatus());

				TbTBiospecimen biospecimen = clinicalDataService.getBiospecimenById(sampleRnaSeq.getBiospecimen().getId());

				if (biospecimen != null) {
					BiospecimenDto biospecimenDto = new BiospecimenDto();
					biospecimenDto.setId(biospecimen.getId());

					if (biospecimen.getRefNo() != null) {
						biospecimenDto.setRef(biospecimen.getRefNo());
					}

					if (biospecimen.getBloodType() != null) {
						biospecimenDto.setBloodType(new DataItem(biospecimen.getBloodType().getCode(), biospecimen.getBloodType().getName()));
					}

					if (biospecimen.getCellLineType() != null) {
						biospecimenDto.setCellLineType(new DataItem(biospecimen.getCellLineType().getCode(), biospecimen.getCellLineType().getName()));
					}

					if (biospecimen.getCellType() != null) {
						biospecimenDto.setCellType(new DataItem(biospecimen.getCellType().getCode(), biospecimen.getCellType().getName()));
					}

					if (biospecimen.getCollectSampleDate() != null) {
						biospecimenDto.setCollectSampleDate(DateUtil.formatDate(biospecimen.getCollectSampleDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
					}

					if (biospecimen.getFreezeDate() != null) {
						biospecimenDto.setFreezeDate(DateUtil.formatDate(biospecimen.getFreezeDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
					}

					if (biospecimen.getFreezeMethod() != null) {
						biospecimenDto.setFreezeMethod(new DataItem(biospecimen.getFreezeMethod().getCode(), biospecimen.getFreezeMethod().getName()));
					}

					if (biospecimen.getInitialWeight() != null) {
						biospecimenDto.setInitialWeight(biospecimen.getInitialWeight().toString());
					}

					if (biospecimen.getPreserveMethod() != null) {
						biospecimenDto.setPreserveMethod(new DataItem(biospecimen.getPreserveMethod().getCode(), biospecimen.getPreserveMethod().getName()));
					}

					if (biospecimen.getSampleType() != null) {
						biospecimenDto.setSampleType(new DataItem(biospecimen.getSampleType().getCode(), biospecimen.getSampleType().getName()));
					}

					if (biospecimen.getTissueType() != null) {
						biospecimenDto.setTissueType(new DataItem(biospecimen.getTissueType().getCode(), biospecimen.getTissueType().getName()));
					}
					
					if (biospecimen.getPathological() != null) {
						PathologicalDto pathoDto = new PathologicalDto();
						pathoDto.setPathologyNo(biospecimen.getPathological().getPathologyNo());
						biospecimenDto.setPathological(pathoDto);
					}
					
					logger.info(String.format("O:--Get Biospecimen--:biospecimenId/%s", biospecimenDto.getId()));
					sampleRnaSeqDto.setBiospecimen(biospecimenDto);
				}

				if (sampleRnaSeq.getAttachment() != null) {
					TbTAttachment attachment = sampleRnaSeq.getAttachment();

					AttachmentDto attachmentDto = new AttachmentDto();
					attachmentDto.setAttachmentId(attachment.getId());
					attachmentDto.setContentType(attachment.getContentType());
					attachmentDto.setFileName(attachment.getFileName());
					attachmentDto.setFilePath(attachment.getFilePath());
					attachmentDto.setFileSize(attachment.getFileSize());
					attachmentDto.setUuid(attachment.getUuid());
					attachmentDto.setStatus(attachment.getStatus());
					sampleRnaSeqDto.setAttachment(attachmentDto);
				}
				
				dataList.add(sampleRnaSeqDto);
			}

			logger.info(String.format("O:--SUCCESS--:--Get Active SampleRnaSeq List--:list size/%s", dataList.size()));
			return dataList;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get Active SampleRnaSeq List--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get Active SampleRnaSeq List--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get Active SampleRnaSeq List--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
	

	@Override
	public List<SampleRnaSeqDto> getSampleRnaSeqListByPatientId(PatientDto input) {
		
		try {
			logger.info("I:--START--:--Get SampleRnaSeq List by PatientId--");
			
			TbMPatient patient = null;
			
			if (input.getId() != null) {
				patient = patientService.getPatientById(input.getId(), AppConstants.STATUS_ACTIVE);
				
				if (patient == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Patient does not exist.");
				}
			}
			
			List<TbTSampleRnaSeq> sampleRnaSeqList = sampleRnaSeqService.getSampleRnaSeqListByPatientId(patient.getId());
			
			if (sampleRnaSeqList == null || sampleRnaSeqList.size() == 0) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<SampleRnaSeqDto> dataList = new ArrayList<SampleRnaSeqDto>();

			for (TbTSampleRnaSeq sampleRnaSeq : sampleRnaSeqList) {
				SampleRnaSeqDto sampleRnaSeqDto = new SampleRnaSeqDto();
				sampleRnaSeqDto.setId(sampleRnaSeq.getId());
				sampleRnaSeqDto.setStatus(sampleRnaSeq.getStatus());

				TbTBiospecimen biospecimen = clinicalDataService.getBiospecimenById(sampleRnaSeq.getBiospecimen().getId());

				if (biospecimen != null) {
					BiospecimenDto biospecimenDto = new BiospecimenDto();
					biospecimenDto.setId(biospecimen.getId());

					if (biospecimen.getRefNo() != null) {
						biospecimenDto.setRef(biospecimen.getRefNo());
					}

					if (biospecimen.getBloodType() != null) {
						biospecimenDto.setBloodType(new DataItem(biospecimen.getBloodType().getCode(), biospecimen.getBloodType().getName()));
					}

					if (biospecimen.getCellLineType() != null) {
						biospecimenDto.setCellLineType(new DataItem(biospecimen.getCellLineType().getCode(), biospecimen.getCellLineType().getName()));
					}

					if (biospecimen.getCellType() != null) {
						biospecimenDto.setCellType(new DataItem(biospecimen.getCellType().getCode(), biospecimen.getCellType().getName()));
					}

					if (biospecimen.getCollectSampleDate() != null) {
						biospecimenDto.setCollectSampleDate(DateUtil.formatDate(biospecimen.getCollectSampleDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
					}

					if (biospecimen.getFreezeDate() != null) {
						biospecimenDto.setFreezeDate(DateUtil.formatDate(biospecimen.getFreezeDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
					}

					if (biospecimen.getFreezeMethod() != null) {
						biospecimenDto.setFreezeMethod(new DataItem(biospecimen.getFreezeMethod().getCode(), biospecimen.getFreezeMethod().getName()));
					}

					if (biospecimen.getInitialWeight() != null) {
						biospecimenDto.setInitialWeight(biospecimen.getInitialWeight().toString());
					}

					if (biospecimen.getPreserveMethod() != null) {
						biospecimenDto.setPreserveMethod(new DataItem(biospecimen.getPreserveMethod().getCode(), biospecimen.getPreserveMethod().getName()));
					}

					if (biospecimen.getSampleType() != null) {
						biospecimenDto.setSampleType(new DataItem(biospecimen.getSampleType().getCode(), biospecimen.getSampleType().getName()));
					}

					if (biospecimen.getTissueType() != null) {
						biospecimenDto.setTissueType(new DataItem(biospecimen.getTissueType().getCode(), biospecimen.getTissueType().getName()));
					}
					
					TbTPathological patho = biospecimen.getPathological();
					
					if (patho != null) {
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
						biospecimenDto.setPathological(pathoDto);
					}
					
					logger.info(String.format("O:--Get Biospecimen--:biospecimenId/%s", biospecimenDto.getId()));
					sampleRnaSeqDto.setBiospecimen(biospecimenDto);
				}

				if (sampleRnaSeq.getAttachment() != null) {
					TbTAttachment attachment = sampleRnaSeq.getAttachment();

					AttachmentDto attachmentDto = new AttachmentDto();
					attachmentDto.setAttachmentId(attachment.getId());
					attachmentDto.setContentType(attachment.getContentType());
					attachmentDto.setFileName(attachment.getFileName());
					attachmentDto.setFilePath(attachment.getFilePath());
					attachmentDto.setFileSize(attachment.getFileSize());
					attachmentDto.setUuid(attachment.getUuid());
					attachmentDto.setStatus(attachment.getStatus());
					sampleRnaSeqDto.setAttachment(attachmentDto);
				}
				
				dataList.add(sampleRnaSeqDto);
			}

			logger.info(String.format("O:--SUCCESS--:--Get SampleRnaSeq List by PatientId--:list size/%s", dataList.size()));
			return dataList;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get SampleRnaSeq List by PatientId--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get SampleRnaSeq List by PatientId--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get SampleRnaSeq by PatientId--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
}
