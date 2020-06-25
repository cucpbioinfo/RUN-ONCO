package run.onco.api.persist.dao;

import java.util.List;

import run.onco.api.persist.entity.TbTSurvivalFollowup;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface SurvivalFollowupDao {

	public TbTSurvivalFollowup getSurvivalFollowupById(final Long survivalFollowupId);
	
	public List<TbTSurvivalFollowup> getSurvivalFollowupListByPatientId(final Long patientId, final String status);
}
