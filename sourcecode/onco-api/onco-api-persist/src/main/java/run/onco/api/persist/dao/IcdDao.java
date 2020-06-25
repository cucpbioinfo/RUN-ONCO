package run.onco.api.persist.dao;

import java.util.List;
import java.util.Map;

import run.onco.api.common.dto.Paging;
import run.onco.api.persist.entity.TbMIcd;
import run.onco.api.persist.entity.TbMIcdO;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface IcdDao {

	public List<TbMIcd> searchIcd(final Map<String, Object> criteria, Paging paging);

	public List<TbMIcdO> searchIcdO(final Map<String, Object> criteria, Paging paging);
	
	public List<TbMIcd> listPaginatedIcdUsingQuery(final Map<String, Object> criteria, Paging paging);
	
	public int countIcdUsingQuery(Map<String, Object> criteria, Paging paging);
	
	public void deleteIcdList(final Integer dataVerId);
	
	public void deleteIcdOList(final Integer dataVerId);
	
	public List<TbMIcdO> listPaginatedIcdOUsingQuery(final Map<String, Object> criteria, Paging paging);
	
	public int countIcdOUsingQuery(Map<String, Object> criteria, Paging paging);
}
