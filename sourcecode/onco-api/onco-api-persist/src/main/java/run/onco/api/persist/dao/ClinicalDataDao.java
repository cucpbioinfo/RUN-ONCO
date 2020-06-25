package run.onco.api.persist.dao;

import java.util.List;
import java.util.Map;

import run.onco.api.common.dto.Paging;
import run.onco.api.persist.entity.TbTClinicalData;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface ClinicalDataDao {

	public TbTClinicalData getClinicalDataByRef(final String ref, final String status);
	
	public List<TbTClinicalData> listPaginatedClinicalDataUsingQuery(final Map<String, Object> criteria, Paging paging);
	
	public int countClinicalDataUsingQuery(Map<String, Object> criteria, Paging paging);
	
	public TbTClinicalData getClinicalDataById(final Long clinicalDataId, final String status);
}
