package run.onco.api.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import run.onco.api.common.constants.AppConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class DateUtil {
	private final static Logger logger = Logger.getLogger(DateUtil.class);

	public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String ddMMMyyyyHHmmss = "dd-MMM-yyyy HH:mm:ss";
	public static final String yyMMddHHmmssS = "yyMMddHHmmssS";
	public static final String FORMAT_STORE_PROCEDURE_DATE = "yyyy-MM-dd"; // FORMAT_DATE_ATS_TRANSACTION_DATE = "yyyy-MM-dd";
	public static final String FORMAT_FULL_TIME = "HH:mm:ss"; //FORMAT_DATE_ATS_TRANSACTION_TIME = "HH:mm:ss";
	public static final String FORMAT_DATE_ATS_TRANSACTION_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String yyMMddHHmmssSSS ="yyMMddHHmmssSSS"; // FORMAT_DATE_ATS_GENERATE_TRANSACTION_ID = "yyMMddHHmmssSSS";
	public static final String FORMAT_DEFAULT_SHORT_DATE = "dd/MM/yyyy"; // FORMAT_DATE_NORMAL_DATE = "dd/MM/yyyy";
	public static final String FORMAT_DEFAULT_FULL_DATE_TIME = "dd/MM/yyyy HH:mm:ss"; // FORMAT_DATE_NORMAL_DATE_TIME = "dd/MM/yyyy HH:mm:ss";
	public static final String FORMAT_DEFAULT_FULL_DATE_TIME_WITHOUT_SECOND = "dd/MM/yyyy HH:mm"; // FORMAT_DATE_NORMAL_DATE_TIME_NO_SECOND = "dd/MM/yyyy HH:mm";
	public static final String FORMAT_DEFAULT_FULL_DATE_TIME_WITH_TIMEZONE = "dd/MM/yyyy HH:mm:ss Z"; // FORMAT_DATE_NORMAL_DATE_WITH_TIMEZONE = "dd/MM/yyyy HH:mm:ss Z";
	public static final String FORMAT_SHORT_DATE  = "dd MMM yyyy"; // FORMAT_DATE_DISPLAY_FOR_UI_DATE = "dd MMM yyyy";
	public static final String FORMAT_FULL_DATE = "dd MMM yyyy HH:mm"; // FORMAT_DATE_DISPLAY_FOR_UI_DATE_TIME = "dd MMM yyyy HH:mm";
	public static final String FORMAT_CALENDAR_FULL_DATE_TIME = "dd-MM-yyyy HH:mm:ss"; // FORMAT_RIB_SERVICE_SERVER_DATE = "dd-MM-yyyy HH:mm:ss";
	public static final String FORMAT_SHORT_TIME = "HH:mm"; // FORMAT_RIB_SERVICE_SERVER_TIME = "HH:mm";
	public static final String FORMAT_FCN_SERVICE_MAT_DATE = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String FORMAT_DATE_TIMESTAMP = "dd/MM/yyyy HH:mm:ss.SSS";
	public static final String FORMAT_DATE_LOOKUP_TRANSFER_TRANSACTION_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	
	private DateUtil() {
		throw new IllegalStateException("DateUtil class");
	}

	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.US);
		return df.format(date);
	}

	public static String formatDateTh(Date date, String pattern) {
		if (date == null) {
			return null;
		}

		SimpleDateFormat df = new SimpleDateFormat(pattern, new Locale("th", "TH"));
		return df.format(date);
	}

	public static String parseFormatDate(Date date, String language) {
		if (date == null) {
			return null;
		}
		
		Calendar calendar = Calendar.getInstance(Locale.US);
		calendar.setTime(date);
		SimpleDateFormat month = null;

		if (AppConstants.CULTURE_SHORTNAME_ENGLISH.equalsIgnoreCase(language)) {
			month = new SimpleDateFormat("MMM", Locale.US);
		} else {
			month = new SimpleDateFormat("MMM", new Locale("th", "TH"));
		}
		
		String result = calendar.get(Calendar.DAY_OF_MONTH) + " " + month.format(date) + " " + calendar.get(Calendar.YEAR);
		return result;
	}

	public static String parseFormatDateTime(Date date, String language) {
		if (date == null) {
			return null;
		}
		
		Calendar calendar = Calendar.getInstance(Locale.US);
		calendar.setTime(date);
		SimpleDateFormat month = null;
		SimpleDateFormat time = new SimpleDateFormat("HH:mm", Locale.US);

		if (AppConstants.CULTURE_SHORTNAME_ENGLISH.equalsIgnoreCase(language)) {
			month = new SimpleDateFormat("MMM", Locale.US);
		} else {
			month = new SimpleDateFormat("MMM", new Locale("th", "TH"));
		}
		
		String result = calendar.get(Calendar.DAY_OF_MONTH) + " " + month.format(date) + " " + calendar.get(Calendar.YEAR) + " " + time.format(date);
		return result;
	}

	public static Date parseDate(String dateText, String pattern) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.US);
			return df.parse(dateText);
		} catch (ParseException e) {
			logger.warn("[DateUtil] parseDate : " + e.toString());
			return null;
		}
	}

	public static Timestamp parseTimestamp(String dateText, String pattern) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.US);
			Date parsedDate = df.parse(dateText);
			Timestamp timestamp = new Timestamp(parsedDate.getTime());
			return timestamp;
		} catch (ParseException e) {
			logger.warn("[DateUtil] parseTimestamp : " + e.toString());
			return null;
		}
	}

	public static String formatCalendar(Calendar calendar, String pattern) {
		if (calendar == null) {
			return null;
		}
		
		SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.US);
		return df.format(calendar.getTime());
	}

	public static String formatCalendar(Calendar calendar, String pattern, TimeZone timeZone) {
		if (calendar == null) {
			return null;
		}
		
		SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.US);
		df.setTimeZone(timeZone);
		return df.format(calendar.getTime());
	}

	public static Calendar parseCalendar(String dateText, String pattern) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.US);
			Date date = df.parse(dateText);

			Calendar calendar = Calendar.getInstance(Locale.US);
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			logger.warn("[DateUtil] parseCalendar : " + e.toString());
			return null;
		}
	}

	public static Calendar parseCalendar(String dateText, String pattern, TimeZone timeZone) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.US);
			df.setTimeZone(timeZone);
			Date date = df.parse(dateText);

			Calendar calendar = Calendar.getInstance(Locale.US);
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			logger.warn("[DateUtil] parseCalendar : " + e.toString());
			return null;
		}
	}

	// strTime format is HH:mm:ss
	public static boolean checkCurrentDateBeforeTime(String strTime) {
		Calendar compareCalendar = Calendar.getInstance(Locale.US);
		compareCalendar.setTime(new Date());
		String[] strs = strTime.split(":");
		compareCalendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(strs[0]));
		compareCalendar.set(Calendar.MINUTE, Integer.valueOf(strs[1]));
		compareCalendar.set(Calendar.SECOND, Integer.valueOf(strs[2]));
		compareCalendar.set(Calendar.MILLISECOND, 0);
		Date compareDate = compareCalendar.getTime();

		if (new Date().compareTo(compareDate) < 0) {
			return true;
		}

		return false;
	}

	public static Date addDate(Date date, int add) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		cal.add(Calendar.DATE, add);
		return cal.getTime();
	}

	public static Date addMonth(Date date, int add) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		cal.add(Calendar.MONTH, add);
		return cal.getTime();
	}
	
	public static Date addMinute(Date date, int add) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		cal.add(Calendar.MINUTE, add);
		return cal.getTime();
	}

	public static boolean checkDifferenceDay(Date date1, Date date2) {
		return !checkSameSystemDate(date1, date2);
	}

	public static boolean checkSameSystemDate(Date date1, Date date2) {
		return (compareDate(date1, date2) == 0);
	}

	public static boolean checkBeforeSystemDate(Date checkDate) {
		return (compareDate(checkDate, new Date()) < 0);
	}

	public static boolean checkAfterSystemDate(Date checkDate) {
		return (compareDate(checkDate, new Date()) > 0);
	}

	public static int compareDate(Date date1, Date date2) {
		Calendar calendar = Calendar.getInstance(Locale.US);
		calendar.setTime(date1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date1 = calendar.getTime();

		calendar.setTime(date2);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		date2 = calendar.getTime();

		return date1.compareTo(date2);
	}

	public static String changeFormatDateFromStrDate(String fromFormatDate, String toFormatDate, String strDate) {
		Date date = parseDate(strDate, fromFormatDate);
		return formatDate(date, toFormatDate);
	}

	public static String changeFormatDateFromStrDateForEmail(String fromFormatDate, String toFormatDate, String strDate,
			String language) {
		Date date = parseDate(strDate, fromFormatDate);

		if (AppConstants.CULTURE_SHORTNAME_ENGLISH.equalsIgnoreCase(language)) {
			return formatDate(date, toFormatDate);
		}

		return formatDateTh(date, toFormatDate);
	}

	public static Date getStartDateOfMonth() {
		Calendar calendar = Calendar.getInstance(Locale.US);
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		setTimeToBeginningOfDay(calendar);
		return calendar.getTime();
	}

	public static Date getEndDateOfMonth() {
		Calendar calendar = Calendar.getInstance(Locale.US);
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		setTimeToEndofDay(calendar);
		return calendar.getTime();
	}

	private static void setTimeToBeginningOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}

	private static void setTimeToEndofDay(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	}

	public static String getServerDate() {
		return formatDate(new Date(), FORMAT_CALENDAR_FULL_DATE_TIME);
	}

	public static String getServerTime() {
		return formatDate(new Date(), FORMAT_SHORT_TIME);
	}

	public static XMLGregorianCalendar getTxnDateTime() {
		return XmlGregorianCalendarConversionUtil.asXMLGregorianCalendar(getCurrentDate());
	}
	
	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
        Date date =  calendar.getTime();
        return date;
	}
}
