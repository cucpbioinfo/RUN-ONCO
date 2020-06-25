package run.onco.component.api.util;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import run.onco.component.api.DynamicContent;
import run.onco.component.api.constants.AppConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class AppUtil {

	/**
	 * Checks if is object empty.
	 *
	 * @param object the object
	 * @return true, if is object empty
	 */
	public static boolean isObjectEmpty(Object object) {
		if(object == null) return true;
		else if(object instanceof String) {
			if (((String)object).trim().length() == 0) {
				return true;
			}
		} else if(object instanceof Collection) {
			return isCollectionEmpty((Collection<?>)object);
		}
		return false;
	}
	
	/**
	 * Checks if is collection empty.
	 *
	 * @param collection the collection
	 * @return true, if is collection empty
	 */
	private static boolean isCollectionEmpty(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}
	
    public static String getRefNo() {
    		Date currentDate = DateUtil.getCurrentDate();
    		String str  = DateUtil.formatDate(currentDate, DateUtil.yyyyMMddHHmmssSSSS);
    		String refNo = String.format("ONC%s", str);
    		return refNo;
    }
    
    public static String getTxnDate() {
    		Date currentDate = DateUtil.getCurrentDate();
    		String str = DateUtil.formatDate(currentDate, DateUtil.yyyyMMddHHmmss);
    		return str;
    }
    
    public static String getRootPath() {
    		final File jarFile = new File(DynamicContent.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    		final File jarDir = jarFile.getParentFile();
//    		System.out.println(jarDir.getAbsolutePath());
    		return jarDir.getAbsolutePath();
    }
    
    public static String getEndpointUrl(String rootDir, String configFile) throws Exception {
    		String angularJsonPath = String.format("%s/%s", rootDir, configFile);
		String jsonString = FileUtil.readFileAsString(angularJsonPath);
		JSONObject rootJSON = (JSONObject) new JSONParser().parse(jsonString);
		JSONObject api = (JSONObject) rootJSON.get("/api/*");
    		String endpointUrl =  (String) api.get("target");
    		return endpointUrl;
    }
    
    public static String convertToAnalysisName(List<String> list) {
		String str = AppConstants.EMPTY_STRING;
		
		if (!AppUtil.isObjectEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				String result = StringUtil.capitalizeFirstLetter(list.get(i));
				list.set(i, result);
			}
			
			str = String.join(" ", list);
		}

		return str;
    }

	public static String convertToComponentClassName(List<String> list) {
		String str = AppConstants.EMPTY_STRING;
		
		if (!AppUtil.isObjectEmpty(list)) {
			for (String result : list) {
				str += StringUtil.capitalizeFirstLetter(result);
			}
		}
		
		return String.format("%sComponent", str);
	}
}
