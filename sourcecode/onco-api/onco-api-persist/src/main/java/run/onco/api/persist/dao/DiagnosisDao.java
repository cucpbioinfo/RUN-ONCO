package run.onco.api.persist.dao;

import java.util.List;

import run.onco.api.persist.entity.TbTDiagnosis;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface DiagnosisDao {

	public List<TbTDiagnosis> getDiagonoseListByClinicalDataId(final Long clinicalDataId);
	
}
