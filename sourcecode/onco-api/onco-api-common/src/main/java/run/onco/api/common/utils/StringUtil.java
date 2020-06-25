package run.onco.api.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import run.onco.api.common.constants.AppConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class StringUtil {

	private StringUtil() {
		throw new IllegalStateException("StringUtil class");
	}

	public static String replace(String content, String replacedStr, String newStr) {
		if (content == null) {
			return null;
		} // end if
		if ((replacedStr == null) || (replacedStr.length() == 0)) {
			return content;
		} // end if
		StringBuffer buffer = new StringBuffer();
		int contentLength = content.length();
		int lastIndex = 0;
		int currentIndex = 0;
		while ((lastIndex < contentLength) && ((currentIndex = content.indexOf(replacedStr, lastIndex)) >= 0)) {
			buffer.append(content.substring(lastIndex, currentIndex));
			if (newStr != null) {
				buffer.append(newStr);
			} // end if
			lastIndex = currentIndex + replacedStr.length();
		} // end while
		if (lastIndex < contentLength) {
			buffer.append(content.substring(lastIndex, contentLength));
		} // end if
		return buffer.toString();
	}

	public static boolean isNullOrEmpty(String s) {
		return s == null || s.length() == 0;
	}

	public static boolean isNullOrWhitespace(String s) {
		return s == null || s.length() == 0 || isWhitespace(s);
	}

	public static String nullSafeTrim(String s) {
		return isNullOrWhitespace(s) ? AppConstants.EMPTY_STRING : s.trim();
	}

	private static boolean isWhitespace(String s) {
		int length = s.length();
		if (length > 0) {
			for (int i = 0; i < length; i++) {
				if (!Character.isWhitespace(s.charAt(i))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static String padLeftZeros(String str, int n) {
		return String.format("%1$" + n + "s", str).replace(' ', '0');
	}

	public static byte[] convertBase64ToBytes(String base64String, String encoding) throws UnsupportedEncodingException {
		return Base64.getDecoder().decode(base64String.getBytes(encoding));
	}

	public static String convertBytesToBase64(byte[] bytes) {
		return Base64.getEncoder().encodeToString(bytes);
	}
	
	public static List<String> convertStringToList(final String s, final String delimiter) {
		String[] data = s.split(delimiter, -1);
        System.out.println(Arrays.toString(data));
        return Arrays.asList(data);
	}
	
	public static String join(Object[] arr) {
		
		if (arr != null && arr.length > 0) {
		    StringBuilder nameBuilder = new StringBuilder();

		    for (Object l : arr) {
		    		String s = l.toString();
		        nameBuilder.append("'").append(s.replace("'", "\\'")).append("',");
		        // can also do the following
		        // nameBuilder.append("'").append(n.replace("'", "''")).append("',");
		    }

		    nameBuilder.deleteCharAt(nameBuilder.length() - 1);
		    return nameBuilder.toString();
		}

		return "";
	}
}
