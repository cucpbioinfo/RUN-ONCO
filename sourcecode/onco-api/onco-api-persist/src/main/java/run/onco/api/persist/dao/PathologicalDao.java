package run.onco.api.persist.dao;

import java.util.List;

import run.onco.api.persist.entity.TbTPathological;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface PathologicalDao {

	public List<TbTPathological> getPathoListByDiagnosisId(final Long diagnosisId, final String status);
	
	public List<TbTPathological> searchPatientPathoByPathoNo(final Long patientId, final String pathoNo, final String status, final int pageNumber, final int pageSize);
	
	public boolean findDuplicatePathoNo(final Long pathoId, final String pathoNo);
}
