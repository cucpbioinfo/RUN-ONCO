package run.onco.api.business.facade.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import run.onco.api.business.dto.DynamicContentDto;
import run.onco.api.business.dto.MasterDataDto;
import run.onco.api.business.facade.DynamicContentFacade;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;
import run.onco.api.common.dto.Paging;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.exception.ValidationException;
import run.onco.api.common.utils.DateUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.persist.entity.TbCDynamicContent;
import run.onco.api.persist.entity.TbCRole;
import run.onco.api.persist.entity.TbMUser;
import run.onco.api.service.DynamicContentService;
import run.onco.api.service.UserService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class DynamicContentFacadeImpl implements DynamicContentFacade {
	
	private final static Logger logger = Logger.getLogger(DynamicContentFacadeImpl.class);
	
	@Autowired
	private DynamicContentService dynamicContentService;

	@Autowired
	private UserService userService;
	
	@Override
	public MasterDataDto<DynamicContentDto> getActiveDynamicContentList() {
		
		try {
			List<TbCDynamicContent> componentList = dynamicContentService.getActiveDynamicContentList();
			
			if (componentList == null || componentList.size() == 0) {
				logger.info(String.format("O:--FAIL--:--Get Component List--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc()));
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<DynamicContentDto> componentDtoList = new ArrayList<>();
			
			for(TbCDynamicContent component : componentList) {
				DynamicContentDto componentDto = new DynamicContentDto();
				componentDto.setComponentName(component.getComponentName());
				componentDto.setModuleName(component.getModuleName());
				componentDto.setModulePath(component.getModulePath());
				componentDto.setInputField(component.getInputField());
				componentDto.setAnalysisName(component.getAnalysisName());
				componentDto.setDataTypeAnalysis(component.getDataTypeAnalysis());
				componentDtoList.add(componentDto);
			}
			
			logger.info(String.format("O:--SUCCESS--:--Get Component List--:componentList size/%s", componentDtoList.size()));
			
			MasterDataDto<DynamicContentDto> masterDataDto = new MasterDataDto<DynamicContentDto>(componentDtoList);
			return masterDataDto;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get Component List--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get Component List--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public DataTableResults<DynamicContentDto> searchDynamicContent(DataTableRequest<DynamicContentDto> req) {
		
		try {
			DynamicContentDto searchCriteria = req.getCriteria();
			Paging paging = req.getPaging();
			
			HashMap<String, Object> criteria = new HashMap<String, Object>();
			criteria.put("analysisName", searchCriteria.getAnalysisName());
			criteria.put("componentName", searchCriteria.getComponentName());
			
			int paginatedCount = dynamicContentService.countDynamicContentUsingQuery(criteria, paging);
			
			List<TbCDynamicContent> dynamicContentList = dynamicContentService.listPaginatedDynamicContentUsingQuery(criteria, paging);
			
			if (dynamicContentList == null || dynamicContentList.size() == 0) {
				logger.info(String.format("O:--FAIL--:--Search DynamicComponent--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc()));
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<DynamicContentDto> dataList = new ArrayList<>();
			
			for (TbCDynamicContent dynamicComponent : dynamicContentList) {
				DynamicContentDto dynamicContentDto = new DynamicContentDto();
				dynamicContentDto.setId(dynamicComponent.getId());
				dynamicContentDto.setAnalysisName(dynamicComponent.getAnalysisName());
				dynamicContentDto.setComponentName(dynamicComponent.getComponentName());
				dynamicContentDto.setModuleName(dynamicComponent.getModuleName());
				dynamicContentDto.setModulePath(dynamicComponent.getModulePath());
				dynamicContentDto.setInputField(dynamicComponent.getInputField());
				dynamicContentDto.setStatus(dynamicComponent.getStatus());
				dataList.add(dynamicContentDto);
			}
			
			logger.info(String.format("O:--SUCCESS--:--Search DynamicComponent--:paginatedCount/%s", paginatedCount));
			DataTableResults<DynamicContentDto> results = new DataTableResults<DynamicContentDto>(dataList, paginatedCount, 0, 15);
			return results;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Search DynamicComponent--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Search DynamicComponent--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void findDuplicateComponentName(DynamicContentDto dynamicContentDto) {

		try {
			Integer id = dynamicContentDto.getId();
			String componentName = dynamicContentDto.getComponentName();
			
			logger.info(String.format("I:--START--:--Find Duplicate ComponentName--:componentName/%s:id/%s", componentName, id));
			boolean isDuplicate = dynamicContentService.findDuplicateComponentName(componentName, id);
			
			if (isDuplicate) {
				throw new ServiceException(MessageCode.ERROR_DUPLICATED.getCode(), "DynamicComponent already exists in the system.");
			}
			
			logger.info("O:--SUCCESS--:--Find Duplicate ComponentName--");
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Find Duplicate ComponentName--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Find Duplicate ComponentName--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void saveDynamicContent(DynamicContentDto dynamicContentDto) {
		
		try {
			logger.info("I:--START--:--Save DynamicContent--");
			
			TbMUser requestedUser = userService.getActiveUserById(dynamicContentDto.getRequestedUserId());
			if (requestedUser == null) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "Requested user does not exist.");
			}
			
			List<TbCRole> roles = requestedUser.getRoles();
			
			findDuplicateComponentName(dynamicContentDto);
			
			TbCDynamicContent dynamicContent = null;
			if (dynamicContentDto.getId() != null) {
				dynamicContent = dynamicContentService.getDynamicContentById(dynamicContentDto.getId());
				
				if (dynamicContent == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "DynamicContent does not exist.");
				}
				
				dynamicContent.setUpdateUser(requestedUser);
				dynamicContent.setUpdateDate(DateUtil.getCurrentDate());
			} else {
				dynamicContent = new TbCDynamicContent();
				dynamicContent.setCreateUser(requestedUser);
				dynamicContent.setCreateDate(DateUtil.getCurrentDate());
			}
			
			if (roles != null) {
				for(TbCRole role: roles) {
					dynamicContent.getRoles().add(role);
				}
			}
			
			dynamicContent.setAnalysisName(dynamicContentDto.getAnalysisName());
			dynamicContent.setComponentName(dynamicContentDto.getComponentName());
			dynamicContent.setModuleName(dynamicContentDto.getModuleName());
			dynamicContent.setModulePath(dynamicContentDto.getModulePath());
			dynamicContent.setInputField(dynamicContentDto.getInputField());
			dynamicContent.setStatus(dynamicContentDto.getStatus());
			dynamicContentService.saveDynamicContent(dynamicContent);
			
			logger.info("O:--SUCCESS--:--Save DynamicContent--");
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Save DynamicContent--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Save DynamicContent--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public void deleteDynamicContent(DynamicContentDto dynamicContentDto) {
		
		try {
			logger.info(String.format("I:--START--:--Delete DynamicContent--:id/%s", dynamicContentDto.getId()));
			
			if (dynamicContentDto.getId() != null) {
				TbCDynamicContent dynamicContent = dynamicContentService.getDynamicContentById(dynamicContentDto.getId());
				
				if (dynamicContent == null) {
					throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), "DynamicContent does not exist.");
				}
				
				dynamicContentService.deleteDynamicContent(dynamicContent);
			}
			
			logger.info("O:--SUCCESS--:--Delete DynamicContent--");
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Delete DynamicContent--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Delete DynamicContent--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Delete DynamicContent--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
}
