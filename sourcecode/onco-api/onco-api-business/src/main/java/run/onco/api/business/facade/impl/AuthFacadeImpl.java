package run.onco.api.business.facade.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import run.onco.api.business.dto.MenuDto;
import run.onco.api.business.dto.PermissionDto;
import run.onco.api.business.dto.UserDto;
import run.onco.api.business.facade.AuthFacade;
import run.onco.api.common.exception.ServiceException;
import run.onco.api.common.exception.ValidationException;
import run.onco.api.common.message.Header;
import run.onco.api.common.message.ServiceRequest;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.JwtUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.common.utils.ValidationUtil;
import run.onco.api.persist.entity.TbCDynamicContent;
import run.onco.api.persist.entity.TbCMenu;
import run.onco.api.persist.entity.TbMUser;
import run.onco.api.service.ConfigService;
import run.onco.api.service.PermissionService;
import run.onco.api.service.UserService;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class AuthFacadeImpl implements AuthFacade {
	
	private final static Logger logger = Logger.getLogger(AuthFacadeImpl.class);
	
	@Autowired
	private ConfigService configService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private PermissionService permissionService;
	
	@Override
	public <T> void verifyServiceRequest(ServiceRequest<T> request, String serviceName) {
		
		try {
			Header header = request.getHeader();
			logger.info(String.format("I:--START--:--Verify ServiceRequest--:systemCode/%s:serviceName/%s:referenceNo/%s:transactionDate/%s", header.getSystemCode(), header.getServiceName(), header.getReferenceNo(), header.getTransactionDate()));
			
			ValidationUtil.validateOpts(header);
			
			boolean authorized = configService.checkServiceAuth(header.getSystemCode(), header.getChannelId());
			
			if(!authorized) {
				throw new ServiceException(MessageCode.ERROR_INVALID_PARAM.getCode(), "Invalid ServiceCode or ChannelID.");
			}

			if(!serviceName.toUpperCase().equals(header.getServiceName().toUpperCase())) {
				throw new ServiceException(MessageCode.ERROR_INVALID_PARAM.getCode(), "Invalid ServiceName.");
			}
			
			ValidationUtil.validateOpts(request.getData());
			
			logger.info("O:--SUCCESS--:--Verify ServiceRequest--");
		} catch (ValidationException ex) {
			logger.info(String.format("O:--FAIL--:--Verify ServiceRequest--:errorCode/%s:errorDesc/%s", ex.getErrorCode(), ex.getErrorDesc()));
			throw ex;
		} catch (ServiceException ex) {
			logger.info(String.format("O:--FAIL--:--Verify ServiceRequest--:errorCode/%s:errorDesc/%s", ex.getErrorCode(), ex.getErrorDesc()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.info(String.format("O:--FAIL--:--Verify ServiceRequest--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

	@Override
	public UserDto login(Header header, UserDto input) {
		
		try {
			logger.info(String.format("I:--START--:--Login--:username/%s", input.getUsername()));
			
			String encrypted = AppUtil.encodePassword(input.getPassword(), "MD5");
			TbMUser user = userService.login(input.getUsername(), encrypted);
			
			if(user != null) {
				String accessToken = jwtUtil.createJWT(user.getId().toString(), user.getUsername());
				
				UserDto output = new UserDto();
				output.setId(user.getId());
				output.setFirstName(user.getFirstName());
				output.setLastName(user.getLastName());
				output.setAccessToken(accessToken);
				output.setPermission(new PermissionDto());
				
				List<TbCMenu> menus  = permissionService.getMenusByUserId(user.getId());
				if (!AppUtil.isObjectEmpty(menus)) {
					Stream<MenuDto> grp = menus.stream().map(obj -> {
						MenuDto menuGroupDto = new MenuDto();
						menuGroupDto.setKey(obj.getCode());
						menuGroupDto.setTitle(obj.getName());
						
						if (!AppUtil.isObjectEmpty(obj.getScreens())) {
							Stream<MenuDto> st = obj.getScreens().stream().map(x -> {
								MenuDto menuDto = new MenuDto();
								menuDto.setKey(x.getCode());
								menuDto.setTitle(x.getName());
								menuDto.setRouterLink(x.getRouterLink());
								return menuDto;
							});
							
							List<MenuDto> menuDtoList = st.collect(Collectors.toList());
							menuGroupDto.setMenus(menuDtoList);
						}
						
						return menuGroupDto;
					});
					
					List<MenuDto> menuGroups = grp.collect(Collectors.toList());
					output.getPermission().setMenuGroups(menuGroups);
				}
				
				List<TbCDynamicContent> dynamicContents = permissionService.getDynamicConntentsByUserId(user.getId());
				if (!AppUtil.isObjectEmpty(dynamicContents)) {
					Stream<String> s = dynamicContents.stream().map(obj -> {
						return obj.getComponentName();
					});
					
					List<String> componentNames = s.collect(Collectors.toList());
					output.getPermission().setDynamicContents(componentNames);
				}

				logger.info("O:--SUCCESS--:--Login--");
				return output;
			}
			
			throw new ServiceException( MessageCode.ERROR_INVALID_LOGIN.getCode(), MessageCode.ERROR_INVALID_LOGIN.getDesc());
		} catch (ValidationException ex) {
			logger.info(String.format("O:--FAIL--:--Login--:errorCode/%s:errorDesc/%s", ex.getErrorCode(), ex.getErrorDesc()));
			throw ex;
		} catch (ServiceException ex) {
			logger.info(String.format("O:--FAIL--:--Login--:errorCode/%s:errorDesc/%s", ex.getErrorCode(), ex.getErrorDesc()));
			throw ex;
		} catch (Exception ex) {
			logger.error("Exception occur:\n", ex);
			logger.info(String.format("O:--FAIL--:--Login--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), ex.getMessage()));
			throw new ServiceException(MessageCode.ERROR_SERVICE_UNAVAIL.getCode(), MessageCode.ERROR_SERVICE_UNAVAIL.getDesc(), "", ex.getMessage());
		}
	}

}
