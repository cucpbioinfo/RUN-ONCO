package run.onco.api.business.facade;

import run.onco.api.business.dto.DynamicContentDto;
import run.onco.api.business.dto.MasterDataDto;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface DynamicContentFacade {

	public MasterDataDto<DynamicContentDto> getActiveDynamicContentList();
	
	public DataTableResults<DynamicContentDto> searchDynamicContent(DataTableRequest<DynamicContentDto> req);
	
	public void findDuplicateComponentName(DynamicContentDto dynamicContentDto);
	
	public void saveDynamicContent(DynamicContentDto dynamicContentDto);
	
	public void deleteDynamicContent(DynamicContentDto dynamicContentDto);
}
