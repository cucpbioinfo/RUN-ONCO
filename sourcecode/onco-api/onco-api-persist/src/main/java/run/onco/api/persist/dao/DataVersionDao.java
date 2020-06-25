package run.onco.api.persist.dao;

import java.util.List;
import java.util.Map;

import run.onco.api.common.dto.Paging;
import run.onco.api.persist.entity.TbCDataVersion;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface DataVersionDao {

	public List<TbCDataVersion> listPaginatedDataVersionUsingQuery(final Map<String, Object> criteria, Paging paging);
	
	public int countDataVersionUsingQuery(Map<String, Object> criteria, Paging paging);
	
	public boolean findDuplicateDataVersion(final String type, final String version, final Integer id);
	
	public List<TbCDataVersion> getMarkAsDefaultDataVersionsByType(final String type, final Integer id);
}
