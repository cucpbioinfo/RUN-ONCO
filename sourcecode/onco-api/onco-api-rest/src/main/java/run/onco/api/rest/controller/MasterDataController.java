package run.onco.api.rest.controller;

import java.util.HashMap;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import run.onco.api.business.dto.DataVersionDto;
import run.onco.api.business.dto.MasterDataDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.business.facade.MasterDataFacade;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;
import run.onco.api.common.aspect.ControllerLogging;
import run.onco.api.common.aspect.MandatoryField;
import run.onco.api.common.aspect.ValidCondition;
import run.onco.api.common.constants.AppConstants;
import run.onco.api.common.dto.DataItem;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.message.Header;
import run.onco.api.common.message.ServiceRequest;
import run.onco.api.common.message.ServiceResponse;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.common.utils.ServiceUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */
@RestController
@RequestMapping("/" + AppConstants.SECURITY_CONTEXT)
public class MasterDataController {

	private static final String[] fields = new String[] { "code", "name" };
	private static final HashMap<String, Object> criteria;
	static {
		criteria = new HashMap<String, Object>();
		criteria.put("status", AppConstants.STATUS_ACTIVE);
	}
	
	@Autowired
	private AuthFacade authFacade;
	
	@Autowired
	private MasterDataFacade masterDataFacade;
	
	@ControllerLogging(AppConstants.ACT_CODE_GET_CANCER_GENE_GROUPS)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_GET_CANCER_GENE_GROUPS, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<MasterDataDto<DataItem>> getCancerGeneGroups(@RequestBody
			@ValidCondition({
			}) ServiceRequest<?> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_GET_CANCER_GENE_GROUPS);
			MasterDataDto<DataItem> cancerGeneGroups = masterDataFacade.getActiveCancerGeneGroups();
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, cancerGeneGroups);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_SEARCH_DATA_VERSION)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_SEARCH_DATA_VERSION, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<DataTableResults<DataVersionDto>> searchDataVersion(@RequestBody
			@ValidCondition({
				@MandatoryField("data.criteria"),
				@MandatoryField("data.paging")
			}) ServiceRequest<DataTableRequest<DataVersionDto>> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_SEARCH_DATA_VERSION);
			DataTableResults<DataVersionDto> output = masterDataFacade.searchDataVersion(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, output);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_FIND_DUPLICATE_DATA_VERSION)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_FIND_DUPLICATE_DATA_VERSION, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<?> findDuplicateDataVersion(@RequestBody
			@ValidCondition({
				@MandatoryField("data.type"),
				@MandatoryField("data.version")
			}) ServiceRequest<DataVersionDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_FIND_DUPLICATE_DATA_VERSION);
			masterDataFacade.findDuplicateDataVersion(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.ACT_CODE_DELETE_DATA_VERSION)
	@RequestMapping(value = "/" + AppConstants.ACT_CODE_DELETE_DATA_VERSION, method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<Boolean> deleteDataVersion(@RequestBody
			@ValidCondition({
				@MandatoryField("data.id"),
				@MandatoryField("data.type")
			}) ServiceRequest<DataVersionDto> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, AppConstants.ACT_CODE_DELETE_DATA_VERSION);
			masterDataFacade.deleteIcdListByDataVersion(request.getData());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS);
		} catch (ServiceException | ValidationException ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	@ControllerLogging(AppConstants.MASTER_DATA_MANAGEMENT_CONTEXT)
	@RequestMapping(value = "/" + AppConstants.MASTER_DATA_MANAGEMENT_CONTEXT + "/{action}", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ServiceResponse<MasterDataDto<DataItem>> get(
			@PathVariable("action") String action, 
			@RequestBody
				@ValidCondition({
			}) 
			ServiceRequest<MasterDataDto<?>> request) {
		
		Header header = request.getHeader();
		
		try {
			authFacade.verifyServiceRequest(request, action);
			MasterDataDto<DataItem> dataList = retrieveData(action, request.getData().getType());
			return ServiceUtil.buildResponse(header, MessageCode.SUCCESS, dataList);
		} catch (ServiceException | ValidationException  ex) {
			return ServiceUtil.buildResponse(header, ((ServiceException)ex).getErrorCode(), ((ServiceException)ex).getErrorDesc());
		}
	}
	
	private MasterDataDto<DataItem> retrieveData(String action, String dataType) {
		
		switch(action) {
			case AppConstants.ACT_CODE_GET_GENDER_LIST : 
				return masterDataFacade.getList("TbMGender", criteria, fields);
			case AppConstants.ACT_CODE_GET_BLOOD_TYPE_LIST :
				return masterDataFacade.getList("TbMBloodType", criteria, fields);
			case AppConstants.ACT_CODE_GET_CELL_TYPE_LIST :
				return masterDataFacade.getList("TbMCellType", criteria, fields);
			case AppConstants.ACT_CODE_GET_CELL_LINE_TYPE_LIST :
				return masterDataFacade.getList("TbMCellLineType", criteria, fields);
			case AppConstants.ACT_CODE_GET_FREEZE_METHOD_LIST : 
				return masterDataFacade.getList("TbMFreezeMethod", criteria, fields);
			case AppConstants.ACT_CODE_GET_PRESERVE_METHOD_LIST :
				return masterDataFacade.getList("TbMPreserveMethod", criteria, fields);
			case AppConstants.ACT_CODE_GET_RACE_LIST :
				return masterDataFacade.getList("TbMRace", criteria, fields);
			case AppConstants.ACT_CODE_GET_SAMPLE_TYPE_LIST :
				return masterDataFacade.getList("TbMSampleType", criteria, fields);
			case AppConstants.ACT_CODE_GET_STAGE_TYPE_LIST :
				return getParameters("TbCParameter", AppConstants.Parameter.STAGE_TYPE.getValue(), fields);
			case AppConstants.ACT_CODE_GET_TISSUE_TYPE_LIST :
				return getParameters("TbMTissueType", dataType, fields);
			case AppConstants.ACT_CODE_GET_VITAL_STATUS_LIST :
				return masterDataFacade.getList("TbMVitalStatus", criteria, fields);
			case AppConstants.ACT_CODE_GET_TISSUE_PROCEDURE_LIST :
				return masterDataFacade.getList("TbMTissueProcedure", criteria, fields);
			case AppConstants.ACT_CODE_GET_HISTOLOGIC_GRADE_LIST :
				return getParameters("TbCParameter", AppConstants.Parameter.HISTOLOGIC_GRADE.getValue(), fields);
			case AppConstants.ACT_CODE_GET_AJCC_LIST :
				return getParameters("TbCParameter", AppConstants.Parameter.AJCC.getValue(), fields);
			case AppConstants.ACT_CODE_GET_CT_STAGE_LIST :
				return getParameters("TbCParameter", AppConstants.Parameter.T_STAGE.getValue(), fields);
			case AppConstants.ACT_CODE_GET_CN_STAGE_LIST :
				return getParameters("TbCParameter", AppConstants.Parameter.N_STAGE.getValue(), fields);
			case AppConstants.ACT_CODE_GET_PT_STAGE_LIST : 
				return getParameters("TbCParameter", AppConstants.Parameter.T_STAGE.getValue(), fields);
			case AppConstants.ACT_CODE_GET_PN_STAGE_LIST :
				return getParameters("TbCParameter", AppConstants.Parameter.N_STAGE.getValue(), fields);
			case AppConstants.ACT_CODE_GET_YPT_STAGE_LIST :
				return getParameters("TbCParameter", AppConstants.Parameter.T_STAGE.getValue(), fields);
			case AppConstants.ACT_CODE_GET_YPN_STAGE_LIST :
				return getParameters("TbCParameter", AppConstants.Parameter.N_STAGE.getValue(), fields);
			case AppConstants.ACT_CODE_GET_M_STAGE_LIST :
				return getParameters("TbCParameter", AppConstants.Parameter.M_STAGE.getValue(), fields);
			case AppConstants.ACT_CODE_GET_CLINICAL_STAGE_LIST :
				return getParameters("TbCParameter", AppConstants.Parameter.CLINICAL_STAGE.getValue(), fields);
			case AppConstants.ACT_CODE_GET_PATHOLOGICAL_STAGE_LIST :
				return getParameters("TbCParameter", AppConstants.Parameter.PATHO_STAGE.getValue(), fields);
			case AppConstants.ACT_CODE_GET_RECURRENCE_STATUS_LIST :
				return getParameters("TbCParameter", AppConstants.Parameter.RECUR_STATUS.getValue(), fields);
			case AppConstants.ACT_CODE_GET_SEQ_TYPE_LIST :
				return masterDataFacade.getList("TbMSeqType", criteria, fields);
			case AppConstants.ACT_CODE_GET_CANCER_SUBSTAGE_LIST : 
				return getParameters("TbCParameter", AppConstants.Parameter.SUBSTAGE.getValue(), fields);
			case AppConstants.ACT_CODE_GET_CLUSTER_ALGORITHMS :
				return masterDataFacade.getList("TbCDataAnalysis", criteria, fields);
			default:
				throw new ServiceException(MessageCode.ERROR_INVALID_PARAM.getCode(), "Service name does not exist.");
		}
	}
	
	@SuppressWarnings("unchecked")
	private MasterDataDto<DataItem> getParameters(String tableName, String type, String[] fields) {
		HashMap<String, Object> newCriteria = (HashMap<String, Object>) criteria.clone();
		newCriteria.put("type",type);
		return masterDataFacade.getList(tableName, newCriteria, fields);
	}
}
