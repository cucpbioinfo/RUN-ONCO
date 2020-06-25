package run.onco.component.api.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import run.onco.component.api.constants.AppConstants;
import run.onco.component.api.constants.ConfigurationConstants;
import run.onco.component.api.dto.DynamicContentDto;
import run.onco.component.api.dto.UserDto;
import run.onco.component.api.exception.ServiceException;
import run.onco.component.api.message.ResponseStatus;
import run.onco.component.api.message.ServiceRequest;
import run.onco.component.api.message.ServiceResponse;
import run.onco.component.api.models.RequestDetails;
import run.onco.component.api.util.AppUtil;
import run.onco.component.api.util.CommandLineUtil;
import run.onco.component.api.util.FileUtil;
import run.onco.component.api.util.MessageCode;
import run.onco.component.api.util.ServiceUtil;
import run.onco.component.api.util.StringUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Service
@Configuration
public class DynamicContentServiceImpl implements DynamicContentService {
	
	@Autowired
	private Environment env;

	private static String analysisName;
	private static String componentClassName;
	
	@Override
	public void createDynamicContent(UserDto user, String component) {
		
		try {
			final String rootDir = AppUtil.getRootPath();
			final String dynamicContentDir = env.getProperty(ConfigurationConstants.DYNAMIC_CONTENT_DIRECTORY);
			final String dynaModuleFile = env.getProperty(ConfigurationConstants.DYNAMIC_CONTENT_MODULE_FILE);
			
			String dynaModuleDir = String.format("%s/%s", rootDir, dynamicContentDir);
			String contentDir = String.format("%s/%s", dynaModuleDir, component);
			
			if (FileUtil.isDirectoryExist(contentDir)) {
				throw new ServiceException(String.format("Unable to create component. Component %s already exists.", component));
			}
			
			String command = String.format("ng g c %s --entryComponent", component); 
			CommandLineUtil.executeCommand(command, dynaModuleDir);
			
			addDynaComponent(dynaModuleDir, dynaModuleFile, component);
			
			importScripts(user);
		} catch (ServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new ServiceException("Failed to create Dynamic Content");
		}
	}
	 
	private static void addDynaComponent(String moduleDir, String moduleFile, String component) throws Exception {
    		
    		String modulePath = String.format("%s/%s", moduleDir, moduleFile);
		String lines = FileUtil.readFileAsString(modulePath);
		String existComponents = AppConstants.EMPTY_STRING;
		    
		String pattern = "(?:mapping\\s*=\\s*[{]\\s*)([\\a-zA-Z0-9, ]*\\s*)(?:\\s*[}]\\s*)(?:[;])";
		Pattern x = Pattern.compile(pattern, Pattern.MULTILINE);
		Matcher m = x.matcher(lines);
		if (m.find()) {
//			System.out.println("m.group(0) ===> " + m.group(0));
//		    System.out.println("m.group(1) ===> " + m.group(1));
		    existComponents = m.group(1);
		}
		     
		List<String> list = StringUtil.convertStringToList(component, "-");
		analysisName = AppUtil.convertToAnalysisName(list);
		componentClassName = AppUtil.convertToComponentClassName(list);
		
		String str = AppConstants.EMPTY_STRING;
		    
		if (!StringUtil.isNullOrWhitespace(existComponents)) {
			String strLinebreaksAndSpaces = StringUtil.replaceLinebreaksAndSpaces(existComponents);
			List<String> results = StringUtil.convertStringToList(strLinebreaksAndSpaces, ",");
			String finalString = AppConstants.EMPTY_STRING;
			
			for (String s : results) {
				finalString += String.format("\t\t%s,\n", s);
			}
			str = lines.replaceAll(pattern, String.format("mapping = {\n%s\t\t%s\n\t};", finalString, componentClassName));
		} else {
			str = lines.replaceAll(pattern, String.format("mapping = {\n\t%s\n\t};", componentClassName));
		}
		    
		FileUtil.writeFile(str, modulePath);
	}
    
	@SuppressWarnings("unchecked")
	public void importScripts(UserDto user) throws Exception {

		final String rootDir = AppUtil.getRootPath();
		final String dropinsDir = env.getProperty(ConfigurationConstants.DROPINS_DIRECTORY);
		final String assetsJsDir = env.getProperty(ConfigurationConstants.ASSETS_JS_DIRECTORY);
		final String angularJsonFile = env.getProperty(ConfigurationConstants.ANGULAR_JSON_FILE);
		
		String dropinsPath = String.format("%s/%s", rootDir, dropinsDir);
//		String assetsJsPath = String.format("%s", assetsJsDir);
		
    		List<File> files = new ArrayList<>();
    		FileUtil.listFilesForFolder(dropinsPath, files);
    		
    		if (!AppUtil.isObjectEmpty(files)) {
    		
	    		String angularJsonPath = String.format("%s/%s", rootDir, angularJsonFile);
	    		String jsonString = FileUtil.readFileAsString(angularJsonPath);
	    		
	    		JSONObject rootJSON = (JSONObject) new JSONParser().parse(jsonString);
	    		JSONObject project = (JSONObject) rootJSON.get("projects");
	    		JSONObject app = (JSONObject) project.get("onco-web");
	    		JSONObject architect = (JSONObject) app.get("architect");
	    		JSONObject build =  (JSONObject) architect.get("build");
	    		JSONObject options = (JSONObject) build.get("options");
	    		JSONArray scripts = (JSONArray) options.get("scripts");
	    		
	    		for (File fileEntry : files) {
	    			String lStr = fileEntry.getParent();
					String directory = lStr.substring(lStr.lastIndexOf("/") + 1);

					String fileName = fileEntry.getName();

//			        System.out.println(directory);
	//		        System.out.println(fileName);

					if (!scripts.contains(fileName)) {
						String scriptPath = "";

						if(!"dropins".equalsIgnoreCase(directory)) {
							scriptPath = String.format("%s/%s/%s", directory, assetsJsDir, fileName);
						} else {
							scriptPath = String.format("%s/%s", assetsJsDir, fileName);
						}

						scripts.add(scriptPath);
					} else {
						throw new ServiceException(String.format("File %s already exists.", fileName));
					}
	    		}
	    		
	    		options.put("scripts", scripts);
	    		build.put("options", options);
	    		architect.put("build", build);
	    		app.put("architect", architect);
	    		project.put("onco-web", app);
	    		rootJSON.put("projects", project);
	    		
	    		ObjectMapper mapper = new ObjectMapper();
	    		String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootJSON);

	    		FileUtil.writeFile(indented, angularJsonPath);

	    		String assetJsPath = String.format("%s/%s", rootDir, assetsJsDir);
	    		File srcDir = new File(dropinsDir);
	    		File destDir = new File(assetJsPath);
	    		FileUtil.copy(srcDir, destDir);

	    		FileUtil.cleanDirectory(srcDir);
    		}
    		
	    	saveDynamicContent(user);
    }
    
    ResponseErrorHandler responseHandler = new ResponseErrorHandler() {
		
		@Override
		public boolean hasError(ClientHttpResponse response) throws IOException {
			
			if (response.getStatusCode() != HttpStatus.OK) {
				System.out.println(response.getStatusText());
			}
			return response.getStatusCode() == HttpStatus.OK ? false : true;
		}
		
		@Override
		public void handleError(ClientHttpResponse response) throws IOException {
//			StringBuilder sb = new StringBuilder();
//		    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
//		    String line = bufferedReader.readLine();
//		    while (line != null) {
//		    		sb.append(line);
//		    		sb.append('\n');
//		        line = bufferedReader.readLine();
//		    }
		    
		    throw new ServiceException(MessageCode.ERROR_CALL_EXTERNAL_API.getDesc());
		}
	};
	
    @Override
	public UserDto login(String username, String password) {
    	
    		try {
    	
	    		final String rootDir = AppUtil.getRootPath();
	    		final String proxyConfigFile = env.getProperty(ConfigurationConstants.PROXY_CONFIG_FILE);
	    		final String serviceName = AppConstants.ACT_CODE_LOGIN;
	    		final String endpointUrl = AppUtil.getEndpointUrl(rootDir, proxyConfigFile);
	    		String serviceUrl = String.format("%s/%s", endpointUrl, serviceName);
	    		
	    		UserDto input = new UserDto();
	    		input.setUsername(username);
	    		input.setPassword(password);
	    		
	    		ServiceRequest<UserDto> req = new ServiceRequest<>(ServiceUtil.getHeader(serviceName));
	    		req.setData(input);
	
	    		ParameterizedTypeReference<ServiceResponse<UserDto>> typeRef = new ParameterizedTypeReference<ServiceResponse<UserDto>>() {};
	    		
	    		ServiceResponse<UserDto> responseBody = new GenericRestClient<ServiceRequest<UserDto>, ServiceResponse<UserDto>>()
	    				.execute(new RequestDetails(serviceUrl, HttpMethod.POST), req, responseHandler, typeRef);
	    		
	    		ResponseStatus responseStatus = (ResponseStatus) responseBody.getResponseStatus();
	    		
	    		if (!MessageCode.SUCCESS.getCode().equals(responseStatus.getResponseCode())) {
	    			throw new SecurityException(String.format("Login failed. Error Details: %s.", responseStatus.getResponseMessage()));
	    		}
	    		
	    		return responseBody.getData();
			
		} catch (ResourceAccessException ex) {
			throw new ServiceException("Failed to connect to ONCO API");
		} catch (Exception ex) {
			throw new ServiceException(String.format("%s", ex.getMessage()));
		}
    }
   
	private void saveDynamicContent(UserDto user) throws ResourceAccessException, Exception {
    	
		try {
			final String ctx  = AppConstants.SECURITY_CONTEXT;
	    		final String serviceName = AppConstants.ACT_CODE_SAVE_DYNAMIC_CONTENT;
	    		String serviceUrl = getServiceUrl(ctx, serviceName);
	    		
	    		String dynaModuleClass = env.getProperty(ConfigurationConstants.DYNAMIC_CONTENT_MODULE_CLASS); 
	    		String dynamicComponentDir = env.getProperty(ConfigurationConstants.DYNAMIC_CONTENT_DIRECTORY);
	    		String dynaModuleFile = env.getProperty(ConfigurationConstants.DYNAMIC_CONTENT_MODULE_FILE);
	    		String dynaModuleFilePath = String.format("%s/%s", dynamicComponentDir, dynaModuleFile);
	    		
	    		DynamicContentDto input = new DynamicContentDto();
	    		input.setAnalysisName(analysisName);
	    		input.setComponentName(componentClassName);
	    		input.setModuleName(dynaModuleClass);
	    		input.setModulePath(dynaModuleFilePath.replace(".ts", AppConstants.EMPTY_STRING));
	    		input.setStatus(AppConstants.STATUS_ACTIVE);
	    		input.setUserId(user.getUserId());
	    		
	    		RequestDetails requestDetails = new RequestDetails(serviceUrl, HttpMethod.POST, user.getAccessToken());
	    		
	    		ServiceRequest<DynamicContentDto> req = new ServiceRequest<>(ServiceUtil.getHeader(serviceName));
	    		req.setData(input);
	    		
	    		ParameterizedTypeReference<ServiceResponse<DynamicContentDto>> typeRef = new ParameterizedTypeReference<ServiceResponse<DynamicContentDto>>() {};
	    		
	    		ServiceResponse<DynamicContentDto> responseBody = new GenericRestClient<ServiceRequest<DynamicContentDto>, ServiceResponse<DynamicContentDto>>()
	    				.execute(requestDetails, req, responseHandler, typeRef);
	    		
	    		ResponseStatus responseStatus = (ResponseStatus) responseBody.getResponseStatus();
	    		
	    		if (!MessageCode.SUCCESS.getCode().equals(responseStatus.getResponseCode())) {
	    			throw new SecurityException(String.format("Failed to save data. Error Details: %s.", responseStatus.getResponseMessage()));
	    		}
	    		
		} catch (ResourceAccessException ex) {
			throw new ServiceException("Failed to connect to ONCO API");
		} catch (Exception ex) {
			throw new ServiceException(String.format("Error: Unknown error(Details: %s).", ex.getMessage()));
		}
    }
	
	private String getServiceUrl(String ctx, String serviceName) throws Exception {
		final String rootDir = AppUtil.getRootPath();
		final String proxyConfigFile = env.getProperty(ConfigurationConstants.PROXY_CONFIG_FILE);
		final String endpointUrl = AppUtil.getEndpointUrl(rootDir, proxyConfigFile);
		
		String serviceUri = serviceName;
		if (!StringUtil.isNullOrWhitespace(ctx)) {
			serviceUri = String.format("%s/%s", "sec", serviceName);
		}
		
		String serviceUrl = String.format("%s/%s", endpointUrl, serviceUri);
		return serviceUrl;	
	}
}
