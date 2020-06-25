package run.onco.api.common.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import run.onco.api.common.constants.AppConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class AppUtil {

	private final static Logger logger = Logger.getLogger(AppUtil.class);
	
	private static volatile SecureRandom numberGenerator = null;
    private static final long MSB = 0x8000000000000000L;

	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	@SuppressWarnings("rawtypes")
	public static boolean isEmpty(Object obj) {
		boolean rtn = true;

		if (obj instanceof String) {
			String strObj = (String) obj;
			if (strObj.length() > 0) {
				return false;
			}
		} else if (obj instanceof Collection) {
			Collection collectionObj = (Collection) obj;
			if (collectionObj.size() > 0) {
				return false;
			}
		} else {
			if (obj != null) {
				return false;
			}
		}

		return rtn;
	}

	public static BigDecimal randomNumber(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max);
		return new BigDecimal(randomNum).divide(new BigDecimal(100)).setScale(2);
	}

	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	public static String randomTokenUuid() {
		Random r = new Random();
		char[] c = new char[10];

		String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwyxz-";
		for (int i = 0; i < c.length; i++) {
			c[i] = alphabet.charAt(r.nextInt(alphabet.length()));
		}

		return String.valueOf(c);
	}

	public static String randomReferenceNo() {
		Random r = new Random();
		char[] c = new char[5];

		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < c.length; i++) {
			c[i] = alphabet.charAt(r.nextInt(alphabet.length()));
		}

		return String.valueOf(c);
	}

	public static String getClientIp(HttpServletRequest request) {

		for (String header : AppConstants.IP_HEADER_CANDIDATES) {
			String ip = request.getHeader(header);
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}

		return request.getRemoteAddr();
	}
	
	/**
     * Encode a string using algorithm specified in web.xml and return the
     * resulting encrypted password. If exception, the plain credentials
     * string is returned
     *
     * @param password Password or other credentials to use in authenticating this username
     * @param algorithm Algorithm used to do the digest
     *
     * @return encypted password based on the algorithm.
     */
    public static String encodePassword(String password, String algorithm) {
        byte[] unencodedPassword = password.getBytes();

        MessageDigest md = null;

        try {
            // first create an instance, given the provider
            md = MessageDigest.getInstance(algorithm);
        } catch (Exception ex) {
            logger.error("Exception occur:\n", ex);

            return password;
        }

        md.reset();

        // call the update method one or more times
        // (useful when you don't know the size of your data, eg. stream)
        md.update(unencodedPassword);

        // now calculate the hash
        byte[] encodedPassword = md.digest();

        StringBuffer buf = new StringBuffer();

        for (byte anEncodedPassword : encodedPassword) {
            if ((anEncodedPassword & 0xff) < 0x10) {
                buf.append("0");
            }

            buf.append(Long.toString(anEncodedPassword & 0xff, 16));
        }

        return buf.toString();
    }
    
	public static String unique() {
        SecureRandom ng = numberGenerator;
        if (ng == null) {
            numberGenerator = ng = new SecureRandom();
        }

        return Long.toHexString(MSB | ng.nextLong()) + Long.toHexString(MSB | ng.nextLong());
    }  
	
	public static <T> Collector<T, ?, T> toSingleton() {
	    return Collectors.collectingAndThen(
	            Collectors.toList(),
	            list -> {
	                if (list.size() != 1) {
	                    throw new IllegalStateException();
	                }
	                return list.get(0);
	            }
	    );
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getMapValue(Map<?, ?> map, String key) {
		
		if (map.containsKey(key)) {
			return (T) map.get(key);
		}
		
		return null;
	}
	
	public static Part getPartByName(HttpServletRequest request, String key) throws IOException, ServletException {
		Part part = request.getPart(key);
		return part;
	}
	
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
	
    public static void main(String[] args) {
		String encoded = encodePassword("password", "MD5");
		System.out.println("---> encoded : " + encoded);
	}
    
    public static String getReadableFileSizeString(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
