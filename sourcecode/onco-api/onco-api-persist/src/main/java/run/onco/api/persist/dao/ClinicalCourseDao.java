package run.onco.api.persist.dao;

import java.util.List;

import run.onco.api.persist.entity.TbTClinicalCourse;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface ClinicalCourseDao {

	public List<TbTClinicalCourse> getClinicalCourseListByDiagnosisId(final Long diagnosisId, final String status);
}
