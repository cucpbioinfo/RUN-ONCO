package run.onco.api.connector.client.oncokb.proxy;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import run.onco.api.common.utils.AppUtil;
import run.onco.api.connector.client.oncokb.util.EndpointUtil;
import run.onco.connector.oncokb.ApiException;
import run.onco.connector.oncokb.api.VariantsApi;
import run.onco.connector.oncokb.model.Alteration;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class OncoKbServiceProxy {
	
	public List<Alteration> getVariantsLookup(Map<String, Object> criteria) throws ApiException {
		Integer entrezGeneId = AppUtil.getMapValue(criteria, "entrezGeneId");
		String hugoSymbol = AppUtil.getMapValue(criteria, "hugoSymbol");
		String variant = AppUtil.getMapValue(criteria, "variant");
		String variantType = AppUtil.getMapValue(criteria, "variantType");
		String consequence = AppUtil.getMapValue(criteria, "consequence");
		Integer proteinStart = AppUtil.getMapValue(criteria, "proteinStart");
		Integer proteinEnd = AppUtil.getMapValue(criteria, "proteinEnd");
		String hgvs = AppUtil.getMapValue(criteria, "hgvs");
		String fields = AppUtil.getMapValue(criteria, "fields");
		
		return variantsApi().variantsLookupGetUsingGET(entrezGeneId, hugoSymbol, variant, variantType, consequence, proteinStart, proteinEnd, hgvs, fields);
	}

	
	private VariantsApi variantsApi() {
		VariantsApi api = new VariantsApi();
		api.getApiClient().setBasePath(EndpointUtil.getEndpoint().toString());
		api.getApiClient().setConnectTimeout(EndpointUtil.getConnectionTimeout());
		api.getApiClient().setReadTimeout(EndpointUtil.getReadTimeout());
		return api;
	}
}
