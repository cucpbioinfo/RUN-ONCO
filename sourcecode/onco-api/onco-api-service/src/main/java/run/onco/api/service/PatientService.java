package run.onco.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import run.onco.api.common.dto.Paging;
import run.onco.api.persist.entity.TbMPatient;
import run.onco.api.persist.entity.TbTSurvivalFollowup;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface PatientService {

	public TbMPatient getPatientByRef(final String ref, final String status);

	public TbMPatient getPatientById(final Long patientId, final String status);

	public List<TbMPatient> searchPatientByRef(final String refNo, final String status, final Paging paging);

	public int countPatientUsingQuery(Map<String, Object> criteria);

	public List<TbMPatient> listPaginatedPatientUsingQuery(final Map<String, Object> criteria);
	
	public void savePatient(HashMap<String, Object> map);

	public TbTSurvivalFollowup getSurvivalFollowupById(final Long survivalFollowupId);
	
	public void deletePatient(TbMPatient patient);
	
	public List<TbTSurvivalFollowup> getSurvivalFollowupListByPatientId(final Long patientId, final String status);
	
	public boolean findDuplicateHn(final Long patientId, final String hn);
}
