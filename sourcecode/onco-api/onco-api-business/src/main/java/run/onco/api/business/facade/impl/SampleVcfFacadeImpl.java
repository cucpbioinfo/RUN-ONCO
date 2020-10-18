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
import run.onco.api.business.dto.SampleVariantDto;
import run.onco.api.business.facade.SampleVcfFacade;
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
import run.onco.api.persist.entity.TbMSeqType;
import run.onco.api.persist.entity.TbMUser;
import run.onco.api.persist.entity.TbTAttachment;
import run.onco.api.persist.entity.TbTBiospecimen;
import run.onco.api.persist.entity.TbTPathological;
import run.onco.api.persist.entity.TbTSampleVariant;
import run.onco.api.service.AttachmentService;
import run.onco.api.service.ClinicalDataService;
import run.onco.api.service.MasterDataService;
import run.onco.api.service.PatientService;
import run.onco.api.service.SampleVcfService;
import run.onco.api.service.UserService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class SampleVcfFacadeImpl implements SampleVcfFacade {

	private final static Logger logger = Logger.getLogger(SampleVcfFacadeImpl.class);

	@Autowired
	private Environment env;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MasterDataService masterDataService;
	
	@Autowired
	private ClinicalDataService clinicalDataService;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
	private SampleVcfService sampleVcfService;
	
	private String getString(String key) {
		return env.getProperty(key);
	}
	
	@Override
	public List<SampleVariantDto> getSampleVcfListByPatientId(PatientDto input) {

		try {
			logger.info("I:--START--:--Get SampleVcf List by PatientId--");
			
			TbMPatient patient = null;
			
			if (input.getId() != null) {
				patient = patientService.getPatientById(input.getId(), AppConstants.STATUS_ACTIVE);
				
				if (patient == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Patient does not exist.");
				}
			}
			
			List<TbTSampleVariant> variantCallList = sampleVcfService.getSampleVcfListByPatientId(patient.getId());
			
			if (variantCallList == null || variantCallList.size() == 0) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<SampleVariantDto> dataList = new ArrayList<SampleVariantDto>();

			for (TbTSampleVariant sampleVariant : variantCallList) {
				SampleVariantDto sampleVariantDto = new SampleVariantDto();
				sampleVariantDto.setId(sampleVariant.getId());
				sampleVariantDto.setStatus(sampleVariant.getStatus());

				TbTBiospecimen biospecimen = clinicalDataService.getBiospecimenById(sampleVariant.getBiospecimen().getId());

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
					sampleVariantDto.setBiospecimen(biospecimenDto);
				}

				if (sampleVariant.getSequenceType() != null) {
					TbMSeqType sequenceType = sampleVariant.getSequenceType();
					sampleVariantDto.setSequenceType(new DataItem(sequenceType.getCode(), sequenceType.getName()));
				}
				
				if (sampleVariant.getAttachment() != null) {
					TbTAttachment attachment = sampleVariant.getAttachment();

					AttachmentDto attachmentDto = new AttachmentDto();
					attachmentDto.setAttachmentId(attachment.getId());
					attachmentDto.setContentType(attachment.getContentType());
					attachmentDto.setFileName(attachment.getFileName());
					attachmentDto.setFilePath(attachment.getFilePath());
					attachmentDto.setFileSize(attachment.getFileSize());
					attachmentDto.setUuid(attachment.getUuid());
					attachmentDto.setStatus(attachment.getStatus());
					sampleVariantDto.setAttachment(attachmentDto);
				}
				
				dataList.add(sampleVariantDto);
			}

			logger.info(String.format("O:--SUCCESS--:--Get SampleVcf List by PatientId--:list size/%s", dataList.size()));
			return dataList;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get SampleVcf List by PatientId--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get SampleVcf List by PatientId--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get SampleVcf List by PatientId--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void uploadSampleVcf(SampleVariantDto input) {
		
		try {
			logger.info("I:--START--:--Upload SampleVcf--");

			TbTSampleVariant sampleVariant = new TbTSampleVariant();
			
			TbMUser requestedUser = null;
			if (input.getRequestedUserId() != null) {
				requestedUser = userService.getActiveUserById(input.getRequestedUserId());
				
				if (requestedUser == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Requested user does not exist.");
				}
			}
				
			if (input.getId() != null) {
				sampleVariant = sampleVcfService.getSampleVariantById(input.getId());
					
				if (sampleVariant == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "SampleVariant does not exist.");
				}
			}
			
			TbMPatient patient = null;
			
			if (input.getPatient() != null) {
				patient = patientService.getPatientById(input.getPatient().getId(), AppConstants.STATUS_ACTIVE);
				
				if (patient == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Patient does not exist.");
				}

				sampleVariant.setPatient(patient);
			}
			
			if (input.getBiospecimen() != null) {
				TbTBiospecimen biospecimen = clinicalDataService.getBiospecimenById(input.getBiospecimen().getId());
				
				if (biospecimen == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Patient does not exist.");
				}

				sampleVariant.setBiospecimen(biospecimen);
			}

			if (input.getSequenceType() != null) {
				String sequenceTypeCode = StringUtil.nullSafeTrim(input.getSequenceType().getCode());

				if (AppConstants.EMPTY_STRING.equals(sequenceTypeCode)) {
					sampleVariant.setSequenceType(null);
				} else {
					TbMSeqType sequenceType = masterDataService.getByCode(TbMSeqType.class, sequenceTypeCode);

					if (sequenceType == null) {
						throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "SequenceTypeCode does not exist.");
					} else {
						sampleVariant.setSequenceType(sequenceType);
					}
				}
			}
			
			boolean isDuplicate = sampleVcfService.findDuplicateUploadSampleVcf(sampleVariant.getSequenceType().getCode(), sampleVariant.getBiospecimen().getId());
			if (isDuplicate) {
				throw new ServiceException(MessageCode.ERROR_DUPLICATED.getCode(), "Sample VCF already exists in the system.");
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
					attachment.setCreateUser(requestedUser);
				}
					
				sampleVariant.setAttachment(attachment);
			}

			sampleVariant.setStatus(AppConstants.STATUS_DRAFT);
			sampleVariant.setCreateDate(DateUtil.getCurrentDate());
			sampleVcfService.uploadSampleVcf(sampleVariant);

			logger.info("O:--SUCCESS--:--Upload SampleVcf--");
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Upload SampleVcf--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Upload SampleVcf--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Upload SampleVcf--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
	
	@Override
	public List<SampleVariantDto> getSampleVcfListByBiospecimenIds(InquiryOmics input) {
		
		try {
			
			logger.info("I:--START--:--Get SampleVcf List by BiospecimenIds--");
			
			String seqTypeCode = StringUtil.nullSafeTrim(input.getSequenceType().getCode());

			List<BiospecimenDto> biospecimenDtoList = input.getBiospecimenList();
			Long[] ids = biospecimenDtoList.stream().map(BiospecimenDto::getId).toArray(Long[]::new);
			
			List<TbTSampleVariant> sampleVariantList = sampleVcfService.getSampleVcfListByBiospecimenIds(seqTypeCode, ids);
			
			if (sampleVariantList != null && sampleVariantList.size() > 0) {
				
				List<SampleVariantDto> sampleVariantDtoList = new ArrayList<>();

				for (TbTSampleVariant sampleVariant : sampleVariantList) {
					
					SampleVariantDto sampleVariantDto = new SampleVariantDto();
					sampleVariantDto.setId(sampleVariant.getId());
					
					if (sampleVariant.getSequenceType() != null) {
						sampleVariantDto.setSequenceType(new DataItem(sampleVariant.getSequenceType().getCode(), sampleVariant.getSequenceType().getName()));
					}
					
					TbTBiospecimen biospecimen = clinicalDataService.getBiospecimenById(sampleVariant.getBiospecimen().getId());

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
						sampleVariantDto.setBiospecimen(biospecimenDto);
					}
					
					if (sampleVariant.getAttachment() != null) {
						TbTAttachment attachment = sampleVariant.getAttachment();

						AttachmentDto attachmentDto = new AttachmentDto();
						attachmentDto.setAttachmentId(attachment.getId());
						attachmentDto.setContentType(attachment.getContentType());
						attachmentDto.setFileName(attachment.getFileName());
						attachmentDto.setFilePath(attachment.getFilePath());
						attachmentDto.setFileSize(attachment.getFileSize());
						attachmentDto.setUuid(attachment.getUuid());
						attachmentDto.setStatus(attachment.getStatus());
						sampleVariantDto.setAttachment(attachmentDto);
					}
					
					sampleVariantDtoList.add(sampleVariantDto);
				}

				return sampleVariantDtoList;
			}
			
			logger.info("O:--SUCCESS--:--Get SampleVcf List by BiospecimenIds--");
			return null;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get SampleVcf List by BiospecimenIds--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get SampleVcf List by BiospecimenIds--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get Variant Comparison--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
}
