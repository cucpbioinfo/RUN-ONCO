package run.onco.api.service;

import java.util.List;

import run.onco.api.common.dto.Paging;
import run.onco.api.persist.entity.TbTPathological;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface PathoService {

	public List<TbTPathological> getPathoListByDiagnosisId(final Long diagnosisId, final String status);

	public List<TbTPathological> searchPatientPathoByPathoNo(final Long patientId, final String pathoNo, final String status, Paging paging);
	
	public boolean findDuplicatePathoNo(final Long pathoId, final String pathoNo);
}
