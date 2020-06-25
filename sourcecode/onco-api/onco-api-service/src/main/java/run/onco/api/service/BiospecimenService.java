package run.onco.api.service;

import java.util.List;
import java.util.Map;

import run.onco.api.persist.entity.TbTBiospecimen;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface BiospecimenService {

	public List<TbTBiospecimen> listPaginatedBiospecimenUsingQuery(final Map<String, Object> criteria);

	public int countBiospecimenUsingQuery(final Map<String, Object> criteria);
	
	public TbTBiospecimen getBiospecimenById(final Long biospecimenId, final String status);
	
	public void deleteBiospecimen(TbTBiospecimen biospecimen);
	
	public void saveBiospecimen(TbTBiospecimen biospecimen);
	
	public boolean findDuplicateBioRef(final Long biospecimenId, final String refNo);
}
