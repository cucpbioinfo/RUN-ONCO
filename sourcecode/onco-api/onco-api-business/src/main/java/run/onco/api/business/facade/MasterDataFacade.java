package run.onco.api.business.facade;

import java.util.List;
import java.util.Map;

import run.onco.api.business.dto.DataVersionDto;
import run.onco.api.business.dto.IcdDto;
import run.onco.api.business.dto.MasterDataDto;
import run.onco.api.business.message.InquiryIcd;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;
import run.onco.api.common.dto.DataItem;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface MasterDataFacade {

	public MasterDataDto<IcdDto> filterIcd(InquiryIcd searchCriteria);

	public MasterDataDto<IcdDto> filterIcdO(InquiryIcd searchCriteria);

	public <T> MasterDataDto<DataItem> getList(String className, Map<String, Object> criteria, String[] fields);

	public MasterDataDto<DataItem> getActiveCancerGeneGroups();
	
	public DataTableResults<DataVersionDto> searchDataVersion(DataTableRequest<DataVersionDto> req);
	
	public DataTableResults<IcdDto> searchIcd(DataTableRequest<IcdDto> req);
	
	public DataTableResults<IcdDto> searchIcdO(DataTableRequest<IcdDto> req);
	
	public void uploadIcd(DataVersionDto dataVersionDto, List<IcdDto> icdDtoList);
	
	public void findDuplicateDataVersion(DataVersionDto dataVersionDto);
	
	public void deleteIcdListByDataVersion(DataVersionDto dataVersionDto);
	
	public void uploadIcdO(DataVersionDto dataVersionDto, List<IcdDto> icdDtoList);
}
