package run.onco.api.persist.dao;

import java.util.List;
import java.util.Map;

import run.onco.api.persist.entity.TbTBiospecimen;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface BiospecimenDao {

	public List<TbTBiospecimen> getActiveฺBiospecimenListByPatientId(final Long patientId);

	public TbTBiospecimen getBiospecimenById(final Long biospecimenId, final String status);

	public List<TbTBiospecimen> listPaginatedBiospecimenUsingQuery(final Map<String, Object> criteria);

	public int countBiospecimenUsingQuery(final Map<String, Object> criteria);
	
	public boolean findDuplicateBioRef(final Long biospecimenId, final String refNo);
}
