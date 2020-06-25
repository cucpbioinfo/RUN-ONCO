package run.onco.api.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import run.onco.api.common.dto.Paging;
import run.onco.api.persist.entity.TbTBiospecimen;
import run.onco.api.persist.entity.TbTCancerStage;
import run.onco.api.persist.entity.TbTClinicalCourse;
import run.onco.api.persist.entity.TbTClinicalData;
import run.onco.api.persist.entity.TbTDiagnosis;
import run.onco.api.persist.entity.TbTPathological;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface ClinicalDataService {

	public void saveClinicalData(HashMap<String, Object> map);

	public TbTClinicalData getClinicalDataByRef(final String ref, final String status);

	public TbTCancerStage getCancerStageById(final Long cancerStageId);

	public List<TbTClinicalData> listPaginatedClinicalDataUsingQuery(final Map<String, Object> criteria, Paging paging);
	
	public int countClinicalDataUsingQuery(final Map<String, Object> criteria, Paging paging);

	public List<TbTDiagnosis> getDiagnosisListByClinicalDataId(final Long clinicalDataId);
	
	public void deleteClinicalData(TbTClinicalData clinicalData);
	
	public TbTBiospecimen getBiospecimenById(final Long biospecimenId);
	
	public TbTPathological getPathologicalById(final Long pathoId);
	
	public TbTClinicalCourse getClinicalCourseById(final Long clinicalCourseId);
	
	public TbTClinicalData getClinicalDataById(final Long clinicalDataId, final String status);
	
	public TbTDiagnosis getDiagnosisById(final Long diagnosisId);
	
	public List<TbTClinicalCourse> getClinicalCourseListByDiagnosisId(final Long diagnosisId, final String status);

	public List<TbTBiospecimen> getActiveฺBiospecimenListByPatientId(final Long patientId);
}
