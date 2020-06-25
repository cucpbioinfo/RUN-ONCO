package run.onco.api.connector.client.oncokb;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import run.onco.api.connector.client.oncokb.proxy.OncoKbServiceProxy;
import run.onco.connector.oncokb.ApiException;
import run.onco.connector.oncokb.model.Alteration;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Repository
public class OncoKbServiceClient {

	@Autowired
	private OncoKbServiceProxy proxy;

	public List<Alteration> getVariantsLookup(Map<String, Object> criteria) {
		try {
			return proxy.getVariantsLookup(criteria);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return null;
	}
}
