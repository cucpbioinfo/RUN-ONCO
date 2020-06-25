package run.onco.api.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import org.apache.log4j.Logger;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class NumberUtil {

	private final static Logger logger = Logger.getLogger(NumberUtil.class);
	
	private NumberUtil() {
		throw new IllegalStateException("NumberUtil class");
	}

	public static BigDecimal parseDecimal(String s) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);

		// Parse the string
		try {
			BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse(s);
			return bigDecimal;
		} catch (ParseException ex) {
			logger.warn("[NumberUtil] parseDecimal : " + ex.getMessage() + ex);
		}

		return null;
	}

	public static String formatDecimal(BigDecimal num) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,##0.0#";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);

		// Format the number
		try {
			return decimalFormat.format(num);
		} catch (Exception ex) {
			logger.warn("[NumberUtil] parseDecimal : " + ex.getMessage() + ex);
		}

		return null;
	}

	public static BigDecimal parseNumber(String s) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,###";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);

		// Parse the string
		try {
			BigDecimal bigDecimal = (BigDecimal) decimalFormat.parse(s);
			return bigDecimal;
		} catch (ParseException ex) {
			logger.warn("[NumberUtil] parseDecimal : " + ex.getMessage() + ex);
		}

		return null;
	}

	public static String formatNumber(BigDecimal n) {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setGroupingSeparator(',');
		symbols.setDecimalSeparator('.');
		String pattern = "#,###";
		DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
		decimalFormat.setParseBigDecimal(true);

		// Format the number
		try {
			return decimalFormat.format(n);
		} catch (Exception ex) {
			logger.warn("[NumberUtil] parseDecimal : " + ex.getMessage() + ex);
		}

		return null;
	}

	public static Integer tryParseInt(String value) {
		
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			logger.warn("[NumberUtil] tryParseInt : " + ex.getMessage() + ex);
			return null;
		}
	}
	
	public static boolean isNumeric(String str) {
		return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
	}
}
