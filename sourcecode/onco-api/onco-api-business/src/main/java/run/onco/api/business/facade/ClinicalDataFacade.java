package run.onco.api.business.facade;

import run.onco.api.business.dto.ClinicalDataDto;
import run.onco.api.business.message.pagination.DataTableRequest;
import run.onco.api.business.message.pagination.DataTableResults;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface ClinicalDataFacade {

	public ClinicalDataDto saveClinicalData(ClinicalDataDto clinicalDataDto);

	public DataTableResults<ClinicalDataDto> searchClinicalData(DataTableRequest<ClinicalDataDto> req);

	public ClinicalDataDto getClinicalDataById(ClinicalDataDto clinicalDataDto);

	public void deleteClinicalData(ClinicalDataDto clinicalDataDto);
}
