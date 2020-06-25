package run.onco.api.business.facade;

import run.onco.api.business.dto.UserDto;
import run.onco.api.common.message.Header;
import run.onco.api.common.message.ServiceRequest;

/**
 * 
 * @author Neda Peyrone
 *
 */
public interface AuthFacade {

	public <T> void verifyServiceRequest(ServiceRequest<T> request, String serviceName);
	
	public UserDto login(Header header, UserDto data);
}
