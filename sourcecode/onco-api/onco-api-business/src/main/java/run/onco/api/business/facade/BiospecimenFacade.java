package run.onco.api.business.facade;

import run.onco.api.business.dto.BiospecimenDto;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface BiospecimenFacade {

	public DataTableResults<BiospecimenDto> searchBiospecimen(DataTableRequest<BiospecimenDto> req);
	
	public BiospecimenDto getBiospecimenById(BiospecimenDto biospecimenDto);
	
	public void deleteBiospecimen(BiospecimenDto biospecimenDto);
	
	public BiospecimenDto saveBiospecimen(BiospecimenDto biospecimenDto);
	
	public void findDuplicateBioRef(BiospecimenDto biospecimenDto);
}
