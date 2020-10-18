package run.onco.api.business.facade.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import run.onco.api.business.dto.DataVersionDto;
import run.onco.api.business.dto.IcdDto;
import run.onco.api.business.dto.MasterDataDto;
import run.onco.api.business.facade.MasterDataFacade;
import run.onco.api.business.message.InquiryIcd;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;
import run.onco.api.common.dto.DataItem;
import run.onco.api.common.dto.Paging;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.exception.ValidationException;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.DateUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.entity.TbCDataVersion;
import run.onco.api.persist.entity.TbMCancerGeneGroup;
import run.onco.api.persist.entity.TbMIcd;
import run.onco.api.persist.entity.TbMIcdO;
import run.onco.api.persist.entity.TbMUser;
import run.onco.api.service.MasterDataService;
import run.onco.api.service.UserService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class MasterDataFacadeImpl implements MasterDataFacade {
	
	private final static Logger logger = Logger.getLogger(MasterDataFacadeImpl.class);

	@Autowired
	private MasterDataService masterDataService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public MasterDataDto<IcdDto> filterIcd(InquiryIcd searchCriteria) {
		
		IcdDto dataItem = searchCriteria.getIcd();
		Paging paging = searchCriteria.getPaging();
		
		HashMap<String, Object> criteria = new HashMap<>();
		criteria.put("name", dataItem.getName());
		criteria.put("version", dataItem.getVersion());
		criteria.put("status", dataItem.getStatus());
		criteria.put("isDefault", dataItem.getIsDefault());
		
		logger.info(String.format("I:--START--:--Search ICD--:name/%s:status/%s:version/%s:isDefault/%s", dataItem.getName(), dataItem.getStatus(), dataItem.getVersion(), dataItem.getIsDefault()));
		
		List<TbMIcd> icdList = masterDataService.searchIcd(criteria, paging);
		
		if (icdList == null || icdList.size() == 0) {
			logger.info(String.format("O:--FAIL--:--Search ICD--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc()));
			throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
		}
		
		List<IcdDto> dataList = new ArrayList<IcdDto>();
		
		for (TbMIcd icd : icdList) {
			IcdDto icdDto = new IcdDto();
			icdDto.setCode(icd.getCode());
			icdDto.setName(icd.getName());
			dataList.add(icdDto);
		}
		
		logger.info(String.format("O:--SUCCESS--:--Search ICD--:icdList size/%s", dataList.size()));
		
		MasterDataDto<IcdDto> masterDataDto = new MasterDataDto<IcdDto>(dataList);
		return masterDataDto;
	}

	@Override
	public MasterDataDto<IcdDto> filterIcdO(InquiryIcd searchCriteria) {
		IcdDto dataItem = searchCriteria.getIcd();
		Paging paging = searchCriteria.getPaging();
		
		HashMap<String, Object> criteria = new HashMap<>();
		criteria.put("name", dataItem.getName());
		criteria.put("version", dataItem.getVersion());
		criteria.put("status", dataItem.getStatus());
		criteria.put("isDefault", dataItem.getIsDefault());
		
		logger.info(String.format("I:--START--:--Search ICD-O--:name/%s:status/%s:version/%s:isDefault/%s", dataItem.getName(), dataItem.getStatus(), dataItem.getVersion(), dataItem.getIsDefault()));
		
		List<TbMIcdO> icdList = masterDataService.searchIcdO(criteria, paging);
		
		if (icdList == null || icdList.size() == 0) {
			logger.info(String.format("O:--FAIL--:--Search ICD-O--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc()));
			throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
		}
		
		List<IcdDto> dataList = new ArrayList<IcdDto>();
		
		for (TbMIcdO icd : icdList) {
			IcdDto icdDto = new IcdDto();
			icdDto.setCode(icd.getCode());
			icdDto.setName(icd.getName());
			dataList.add(icdDto);
		}
		
		logger.info(String.format("O:--SUCCESS--:--Search ICD-O--:icdList size/%s", dataList.size()));
		
		MasterDataDto<IcdDto> masterDataDto = new MasterDataDto<IcdDto>(dataList);
		return masterDataDto;
	}

	@Override
	public <T> MasterDataDto<DataItem> getList(String className, Map<String, Object> criteria, String[] fields) {
		
		String dataType = AppUtil.getMapValue(criteria, "type");
		
		logger.info(String.format("I:--START--:--Get List--:className/%s:type:/%s", className, dataType));
		
		try {
			Class<?> classType = Class.forName(String.format("run.onco.api.persist.entity.%s", className));
			List<DataItem> items = masterDataService.getList(classType, criteria, fields);
	
			if (items == null || items.size() == 0) {
				logger.info(String.format("O:--FAIL--:--Get List--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc()));
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			logger.info(String.format("O:--SUCCESS--:--Get List--:list size/%s", items.size()));
			
			MasterDataDto<DataItem> masterDataDto = new MasterDataDto<DataItem>(items);
			return masterDataDto;
		
		} catch (ClassNotFoundException ex) {
			throw new ServiceException(MessageCode.ERROR_CLASS_NOT_FOUND.getCode(), ex.getMessage());
		}
	}

	@Override
	public MasterDataDto<DataItem> getActiveCancerGeneGroups() {
		List<TbMCancerGeneGroup> cancerGeneGroups = masterDataService.getActiveCancerGeneGroups();
		
		if (cancerGeneGroups == null || cancerGeneGroups.size() == 0) {
			logger.info(String.format("O:--FAIL--:--Get CancerGeneGroup List--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc()));
			throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
		}
		
		List<DataItem> dataItems = new ArrayList<>();
		
		for(TbMCancerGeneGroup cancerGeneGroup : cancerGeneGroups) {
			DataItem dataItem = new DataItem(cancerGeneGroup.getId(), cancerGeneGroup.getName());
			dataItems.add(dataItem);
		}
		
		logger.info(String.format("O:--SUCCESS--:--Get CancerGeneGroup List--:dataItems size/%s", dataItems.size()));
		
		MasterDataDto<DataItem> masterDataDto = new MasterDataDto<DataItem>(dataItems);
		return masterDataDto;
	}

	@Override
	public DataTableResults<DataVersionDto> searchDataVersion(DataTableRequest<DataVersionDto> req) {
		
		try {
			DataVersionDto searchCriteria = req.getCriteria();
			Paging paging = req.getPaging();
			
			HashMap<String, Object> criteria = new HashMap<String, Object>();
			criteria.put("version", searchCriteria.getVersion());
			criteria.put("type", searchCriteria.getType());
			
			int paginatedCount = masterDataService.countDataVersionUsingQuery(criteria, paging);
			
			List<TbCDataVersion> dataVersions = masterDataService.listPaginatedDataVersionUsingQuery(criteria, paging);
			
			if (dataVersions == null || dataVersions.size() == 0) {
				logger.info(String.format("O:--FAIL--:--Search DataVersion--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc()));
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<DataVersionDto> dataList = new ArrayList<DataVersionDto>();
			
			for (TbCDataVersion dataVersion : dataVersions) {
				DataVersionDto dataVersionDto = new DataVersionDto();
				dataVersionDto.setId(dataVersion.getId());
				dataVersionDto.setVersion(dataVersion.getVersion());
				dataVersionDto.setDataDate(DateUtil.formatDate(dataVersion.getDataDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
				dataVersionDto.setType(dataVersion.getType());
				dataVersionDto.setIsDefault(dataVersion.getIsDefault());
				
				if (dataVersion.getUpdateUser() != null) {
					dataVersionDto.setUpdateUser(dataVersion.getUpdateUser().getUsername());
					dataVersionDto.setUpdateDate(DateUtil.formatDate(dataVersion.getUpdateDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
				} else {
					dataVersionDto.setUpdateUser(dataVersion.getCreateUser().getUsername());
					dataVersionDto.setUpdateDate(DateUtil.formatDate(dataVersion.getCreateDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
				}
				
				dataList.add(dataVersionDto);
			}
			
			logger.info(String.format("O:--SUCCESS--:--Search DataVersion--:paginatedCount/%s", paginatedCount));
			DataTableResults<DataVersionDto> results = new DataTableResults<DataVersionDto>(dataList, paginatedCount, 0, 15);
			return results;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Search DataVersion--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Search DataVersion--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Search DataVersion--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public DataTableResults<IcdDto> searchIcd(DataTableRequest<IcdDto> req) {
		
		try {
			IcdDto searchCriteria = req.getCriteria();
			Paging paging = req.getPaging();
			
			logger.info("I:--START--:--Search ICD--");
			
			HashMap<String, Object> criteria = new HashMap<>();
			criteria.put("dataVerId", searchCriteria.getDataVerId());
			
			int paginatedCount = masterDataService.countIcdUsingQuery(criteria, paging);
			
			List<TbMIcd> icdList = masterDataService.listPaginatedIcdUsingQuery(criteria, paging);
			
			if (icdList == null || icdList.size() == 0) {
				logger.info(String.format("O:--FAIL--:--Search ICD--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc()));
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<IcdDto> dataList = new ArrayList<IcdDto>();
			
			for(TbMIcd icd : icdList) {
				IcdDto icdDto = new IcdDto();
				icdDto.setCode(icd.getCode());
				icdDto.setName(icd.getName());
				dataList.add(icdDto);
			}
			
			logger.info(String.format("O:--SUCCESS--:--Search ICD--:paginatedCount/%s", paginatedCount));
			DataTableResults<IcdDto> results = new DataTableResults<IcdDto>(dataList, paginatedCount, 0, 15);
			return results;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Search ICD--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Search ICD--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Search ICD--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void uploadIcd(DataVersionDto dataVersionDto, List<IcdDto> icdDtoList) {
		
		try {
			logger.info("I:--START--:--Upload ICD--");
			
			TbMUser requestedUser = userService.getActiveUserById(dataVersionDto.getRequestedUserId());
			if (requestedUser == null) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Requested user does not exist.");
			}
			
			TbCDataVersion dataVersion = null;
			if (dataVersionDto.getId() != null) {
				dataVersion = masterDataService.getById(TbCDataVersion.class, dataVersionDto.getId());
				
				if (dataVersion == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "DataVersion does not exist.");
				}

				dataVersion.setUpdateUser(requestedUser);
				dataVersion.setUpdateDate(DateUtil.getCurrentDate());
			} else {
				dataVersion = new TbCDataVersion();
				dataVersion.setCreateUser(requestedUser);
				dataVersion.setCreateDate(DateUtil.getCurrentDate());
			}
			
			dataVersion.setVersion(dataVersionDto.getVersion());
			dataVersion.setDataDate(DateUtil.parseDate(dataVersionDto.getDataDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
			dataVersion.setType(dataVersionDto.getType());
			dataVersion.setIsDefault(dataVersionDto.getIsDefault());
			
			List<TbMIcd> icdList = null;
			if (icdDtoList != null) {
				icdList = new ArrayList<>();
			
				for (IcdDto icdDto : icdDtoList) {
					TbMIcd icd = new TbMIcd();
					icd.setCode(icdDto.getCode());
					icd.setName(icdDto.getName());
					icd.setDataVersion(dataVersion);
					icdList.add(icd);
				}
			}
			
			// Save ICD
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("DATA_VERSION", dataVersion);
			map.put("ICD_LIST", icdList);
			masterDataService.saveIcdList(map);
			
			logger.info("O:--SUCCESS--:--Upload ICD--");
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Upload ICD--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Upload ICD--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Upload ICD--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void findDuplicateDataVersion(DataVersionDto dataVersionDto) {
		
		try {
			String type = dataVersionDto.getType();
			String version = dataVersionDto.getVersion();
			Integer id = dataVersionDto.getId();
			
			logger.info(String.format("I:--START--:--Find Duplicate DataVersion--:type/%s:version/%s:id/%s", type, version, id));
			boolean isDuplicate = masterDataService.findDuplicateDataVersion(type, version, id);
			
			if (isDuplicate) {
				throw new ServiceException(MessageCode.ERROR_DUPLICATED.getCode(), "DataVersion already exists in the system.");
			}
			
			logger.info("O:--SUCCESS--:--Find Duplicate DataVersion--");
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Find Duplicate DataVersion--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Find Duplicate DataVersion--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Find Duplicate DataVersion--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void deleteIcdListByDataVersion(DataVersionDto dataVersionDto) {
		
		try {
			logger.info(String.format("I:--START--:--Delete DataVersion--:id/%s", dataVersionDto.getId()));
			
			TbCDataVersion dataVersion = masterDataService.getById(TbCDataVersion.class, dataVersionDto.getId());
			if (dataVersion == null) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "DataVersion does not exist.");
			}
			
			if ("ICD".equals(dataVersionDto.getType())) {
				masterDataService.deleteIcdListByDataVersion(dataVersion);
			} else if ("ICD-O".equals(dataVersionDto.getType())) {
				masterDataService.deleteIcdOListByDataVersion(dataVersion);
			}
			
			logger.info("O:--SUCCESS--:--Delete DataVersion--");
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Delete DataVersion--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Delete DataVersion--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Delete DataVersion--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void uploadIcdO(DataVersionDto dataVersionDto, List<IcdDto> icdDtoList) {
		
		try {
			logger.info("I:--START--:--Upload ICD-O--");
			
			TbMUser requestedUser = userService.getActiveUserById(dataVersionDto.getRequestedUserId());
			if (requestedUser == null) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Requested user does not exist.");
			}
			
			TbCDataVersion dataVersion = null;
			if (dataVersionDto.getId() != null) {
				dataVersion = masterDataService.getById(TbCDataVersion.class, dataVersionDto.getId());
				
				if (dataVersion == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "DataVersion does not exist.");
				}

				dataVersion.setUpdateUser(requestedUser);
				dataVersion.setUpdateDate(DateUtil.getCurrentDate());
			} else {
				dataVersion = new TbCDataVersion();
				dataVersion.setCreateUser(requestedUser);
				dataVersion.setCreateDate(DateUtil.getCurrentDate());
			}
			
			dataVersion.setVersion(dataVersionDto.getVersion());
			dataVersion.setDataDate(DateUtil.parseDate(dataVersionDto.getDataDate(), DateUtil.FORMAT_STORE_PROCEDURE_DATE));
			dataVersion.setType(dataVersionDto.getType());
			dataVersion.setIsDefault(dataVersionDto.getIsDefault());
			
			List<TbMIcdO> icdList = null;
			if (icdDtoList != null) {
				icdList = new ArrayList<>();
			
				for (IcdDto icdDto : icdDtoList) {
					TbMIcdO icd = new TbMIcdO();
					icd.setCode(icdDto.getCode());
					icd.setName(icdDto.getName());
					icd.setDataVersion(dataVersion);
					icdList.add(icd);
				}
			}
			
			// Save ICD
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("DATA_VERSION", dataVersion);
			map.put("ICD_LIST", icdList);
			masterDataService.saveIcdOList(map);
			
			logger.info("O:--SUCCESS--:--Upload ICD--");
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Upload ICD--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Upload ICD--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Upload ICD--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public DataTableResults<IcdDto> searchIcdO(DataTableRequest<IcdDto> req) {
		
		try {
			IcdDto searchCriteria = req.getCriteria();
			Paging paging = req.getPaging();
			
			logger.info("I:--START--:--Search ICD-O--");
			
			HashMap<String, Object> criteria = new HashMap<>();
			criteria.put("dataVerId", searchCriteria.getDataVerId());
			
			int paginatedCount = masterDataService.countIcdOUsingQuery(criteria, paging);
			
			List<TbMIcdO> icdList = masterDataService.listPaginatedIcdOUsingQuery(criteria, paging);
			
			if (icdList == null || icdList.size() == 0) {
				logger.info(String.format("O:--FAIL--:--Search ICD-O--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc()));
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<IcdDto> dataList = new ArrayList<IcdDto>();
			
			for(TbMIcdO icd : icdList) {
				IcdDto icdDto = new IcdDto();
				icdDto.setCode(icd.getCode());
				icdDto.setName(icd.getName());
				dataList.add(icdDto);
			}
			
			logger.info(String.format("O:--SUCCESS--:--Search ICD-O--:paginatedCount/%s", paginatedCount));
			DataTableResults<IcdDto> results = new DataTableResults<IcdDto>(dataList, paginatedCount, 0, 15);
			return results;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Search ICD-O--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Search ICD-O--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Search ICD-O--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
}
