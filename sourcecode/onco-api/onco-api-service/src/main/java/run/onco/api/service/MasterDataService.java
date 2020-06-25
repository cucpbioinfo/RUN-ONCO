package run.onco.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import run.onco.api.common.dto.DataItem;
import run.onco.api.common.dto.Paging;
import run.onco.api.persist.entity.TbCDataVersion;
import run.onco.api.persist.entity.TbMCancerGeneGroup;
import run.onco.api.persist.entity.TbMIcd;
import run.onco.api.persist.entity.TbMIcdO;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface MasterDataService {

	public <T> List<DataItem> getList(Class<T> clazz, Map<String, Object> criteria, String[] fields);

	public <T> T getByCode(Class<T> clazz, String code);

	public <T> T getByCode(Class<T> clazz, String code, String type);

	public <T> T getById(Class<T> clazz, Integer id);

	public <T> T getActiveById(Class<T> clazz, Integer id);
	
	public <T> List<T> search(Class<T> clazz, Map<String, Object> criteria, Paging paging);
	
	public <T> void saveOrUpdate(T entity);

	public List<TbMIcd> searchIcd(final Map<String, Object> criteria, Paging paging);

	public List<TbMIcdO> searchIcdO(final Map<String, Object> criteria, Paging paging);
	
	public List<TbMCancerGeneGroup> getActiveCancerGeneGroups();
	
	public List<TbCDataVersion> listPaginatedDataVersionUsingQuery(final Map<String, Object> criteria, Paging paging);
	
	public int countDataVersionUsingQuery(Map<String, Object> criteria, Paging paging);
	
	public List<TbMIcd> listPaginatedIcdUsingQuery(final Map<String, Object> criteria, Paging paging);
	
	public int countIcdUsingQuery(Map<String, Object> criteria, Paging paging);
	
	public boolean findDuplicateDataVersion(final String type, final String version, final Integer id);
	
	public void saveIcdList(HashMap<String, Object> map);
	
	public void saveIcdOList(HashMap<String, Object> map);
	
	public void deleteIcdListByDataVersion(TbCDataVersion dataVersion);
	
	public void deleteIcdOListByDataVersion(TbCDataVersion dataVersion);
	
	public List<TbMIcdO> listPaginatedIcdOUsingQuery(final Map<String, Object> criteria, Paging paging);
	
	public int countIcdOUsingQuery(Map<String, Object> criteria, Paging paging);
	
}
