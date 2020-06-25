package run.onco.api.persist.dao;

import java.util.List;
import java.util.Map;

import run.onco.api.persist.entity.TbMPatient;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface PatientDao {

	public TbMPatient getPatientByRef(final String refNo, final String status);

	public List<TbMPatient> searchPatientByRef(final String refNo, final String status, final int pageNumber, final int pageSize);

	public int countPatientUsingQuery(Map<String, Object> criteria);

	public List<TbMPatient> listPaginatedPatientUsingQuery(Map<String, Object> criteria);
	
	public TbMPatient getPatientById(final Long patientId, final String status);
	
	public boolean findDuplicateHn(final Long patientId, final String hn);
}
