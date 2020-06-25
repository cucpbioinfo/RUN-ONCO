package run.onco.api.service;

import java.util.List;
import java.util.Map;

import run.onco.api.common.dto.Paging;
import run.onco.api.persist.entity.TbCDynamicContent;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface DynamicContentService {

	public List<TbCDynamicContent> getActiveDynamicContentList();
	
	public List<TbCDynamicContent> listPaginatedDynamicContentUsingQuery(final Map<String, Object> criteria, Paging paging);
	
	public int countDynamicContentUsingQuery(Map<String, Object> criteria, Paging paging);
	
	public boolean findDuplicateComponentName(final String componentName, final Integer id);
	
	public void saveDynamicContent(TbCDynamicContent dynamicContent);
	
	public TbCDynamicContent getDynamicContentById(Integer id);

}
