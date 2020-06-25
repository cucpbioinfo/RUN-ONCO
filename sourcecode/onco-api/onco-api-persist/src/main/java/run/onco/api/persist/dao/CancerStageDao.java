package run.onco.api.persist.dao;

import run.onco.api.persist.entity.TbTCancerStage;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface CancerStageDao {

	public TbTCancerStage getCancerStageByDiagnosisId(final Long diagnosisId);
}
