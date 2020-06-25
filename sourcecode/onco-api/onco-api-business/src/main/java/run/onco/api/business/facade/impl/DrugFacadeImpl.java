package run.onco.api.business.facade.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import run.onco.api.business.dto.ActionableVariantDto;
import run.onco.api.business.facade.DrugFacade;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.exception.ValidationException;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.connector.client.oncokb.OncoKbServiceClient;
import run.onco.api.persist.entity.TbMActionableVariant;
import run.onco.api.service.DrugService;
import run.onco.connector.oncokb.model.Alteration;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class DrugFacadeImpl implements DrugFacade {

	private final static Logger logger = Logger.getLogger(DrugFacadeImpl.class);

	@Autowired
	private Environment env;
	
	@Autowired
	private OncoKbServiceClient oncoKbServiceClient;
	
	@Autowired
	private DrugService drugService;

	@Override
	public void test() {
		Map<String, Object> criteria = new HashMap<>();
		criteria.put("entrezGeneId", 4763);
		List<Alteration> alterationList = oncoKbServiceClient.getVariantsLookup(criteria);
		logger.info(String.format("--->%s", AppUtil.isNotEmpty(alterationList) ? alterationList.size() : 0));
		
		if (alterationList != null && alterationList.size() > 0) {
			for (Alteration alt: alterationList) {
				logger.info(String.format("Alt:%s\nGene:%s", alt.getAlteration(), alt.getGene().getName()));
			}
		}
	}

	@Override
	public List<ActionableVariantDto> getActionableVariantByGeneName(String geneName) {
		
		try {
			logger.info(String.format("I:--START--:--Get ActionableVariantList by GeneName--:geneName/%s", geneName));
			
			List<TbMActionableVariant> annotatedVariantList = drugService.getActionableVariantListByGeneName(geneName);
			if (annotatedVariantList == null || annotatedVariantList.size() == 0) {
				throw new ServiceException(MessageCode.ERROR_DATA_NOT_FOUND.getCode(), MessageCode.ERROR_DATA_NOT_FOUND.getDesc());
			}
			
			List<ActionableVariantDto> actionableVariantDtoList = new ArrayList<ActionableVariantDto>();
			
			for (TbMActionableVariant annotatedVariant : annotatedVariantList) {
				ActionableVariantDto actionableVariantDto = this.prepareActionableVariantDto(annotatedVariant);
				actionableVariantDtoList.add(actionableVariantDto);
			}
			
			logger.info("O:--SUCCESS--:--Get ActionableVariantList by GeneName--");
			return actionableVariantDtoList;
		} catch (ValidationException ex) {
			logger.debug(String.format("O:--FAIL--:--Get ActionableVariantList by GeneName--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (ServiceException ex) {
			logger.debug(String.format("O:--FAIL--:--Get ActionableVariantList by GeneName--:errorMsg/%s", ex.getMessage()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.debug(String.format("O:--FAIL--:--Get ActionableVariantList by GeneName--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}
	
	private ActionableVariantDto prepareActionableVariantDto(TbMActionableVariant annotatedVariant) {
		ActionableVariantDto actionableVariantDto = new ActionableVariantDto();
		actionableVariantDto.setGene(annotatedVariant.getGene());
		actionableVariantDto.setAlteration(annotatedVariant.getAlteration());
		actionableVariantDto.setCancerType(annotatedVariant.getCancerType());
		actionableVariantDto.setDrugs(annotatedVariant.getDrugs());
		actionableVariantDto.setIsoform(annotatedVariant.getIsoform());
		actionableVariantDto.setLevel(annotatedVariant.getLevel());
		return actionableVariantDto;
	}
}
