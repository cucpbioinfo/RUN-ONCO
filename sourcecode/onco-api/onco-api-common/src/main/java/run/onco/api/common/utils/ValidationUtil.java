package run.onco.api.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.log4j.Logger;

import run.onco.api.common.aspect.MandatoryField;
import run.onco.api.common.aspect.ValidCondition;
import run.onco.api.common.exception.ValidationException;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class ValidationUtil {

	private final static Logger logger = Logger.getLogger(ValidationUtil.class);
	
	private ValidationUtil() {
		throw new IllegalStateException("ValidationUtil class");
	}
	
	/**
	 * check target is valid for condition specify by validCondition
	 * 
	 * @param target
	 * @param validCondition
	 * @return
	 * @throws ValidationException
	 */
	public static boolean checkValid(Object target, ValidCondition validCondition) throws ValidationException {

		for (MandatoryField mandatory : validCondition.value()) {
			try {
				checkMandatory(target, mandatory);
			} catch (ValidationException ve) {
				throw ve;
			} catch (Exception e) {
//				throw new ValidationException(MessageCode.ERROR_INVALID_PARAM.getCode(), mandatory.value() + " has invalid with exception : " + e.getMessage() + ".", e);
				throw new ValidationException(MessageCode.ERROR_INVALID_PARAM.getCode(), String.format("Invalid %s.", mandatory.value()), e);
			}
		}
		return true;
	}

	private static void checkMandatory(Object argument, MandatoryField mandatory) {
		String fieldName = mandatory.value();
		try {
			checkMandatory("", argument, fieldName, mandatory);
		} catch (ValidationException e) {
			throw e;
		} catch (Exception e) {
//			throw new ValidationException(MessageCode.ERROR_INVALID_PARAM.getCode(), mandatory.value() + " invalid with field " + fieldName + " exception : " + e.getMessage() + ".", e);
			throw new ValidationException(MessageCode.ERROR_INVALID_PARAM.getCode(), String.format("Invalid %s.", mandatory.value()), e);
		}
	}

	@SuppressWarnings("unchecked")
	private static void checkMandatory(String parentName, Object argument, String fieldName, MandatoryField mandatory)
			throws NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		if (argument instanceof Map) {
			if (!fieldName.equals(MandatoryField.ALL_FIELD)) {
				Map<String, ?> map = (Map<String, ?>) argument;
				Object fieldValue = map.get(fieldName);
				validateField(parentName, fieldValue, fieldName, mandatory);
			}
		} else {
			int pointIndex = fieldName.indexOf(".");
			
			if (pointIndex != -1) {
				String rootFieldName = fieldName.substring(0, pointIndex);
				String nestedFieldName = fieldName.substring(pointIndex + 1);

				Field field = ReflectionUtil.getField(argument.getClass(), rootFieldName);
				Method method = ReflectionUtil.getGetterMethod(field, argument);
				
				if (method != null) {
					Object fieldValue = method.invoke(argument);
					if (fieldValue == null || fieldValue.toString().length() == 0) {
						throw new ValidationException(MessageCode.ERROR_INVALID_PARAM.getCode(), String.format("Invalid %s.", rootFieldName));
					}
					checkMandatory(rootFieldName, fieldValue, nestedFieldName, mandatory);
				}
			} else {
				if (fieldName.equals(MandatoryField.ALL_FIELD)) {
					for (Field field : ReflectionUtil.getAllField(argument.getClass())) {
						Method method = ReflectionUtil.getGetterMethod(field, argument);
						if (method != null) {
							Object fieldValue = method.invoke(argument);

							if (ReflectionUtil.isNoNested(fieldValue)) {
								validateField(parentName, fieldValue, field.getName(), mandatory);
							} else {
								checkMandatory(parentName, fieldValue, fieldName, mandatory);
							}
						}
					}

				} else {
					Field field = ReflectionUtil.getField(argument.getClass(), fieldName);
					Method method = ReflectionUtil.getGetterMethod(field, argument);
					if (method != null) {
						Object fieldValue = method.invoke(argument);
						validateField(parentName, fieldValue, fieldName, mandatory);
					}
				}
			}
		}
	}

	private static void validateField(String parentName, Object fieldValue, String fieldName, MandatoryField mandatory)
			throws NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		String displayName = fieldName;
		
		//if (parentName.trim().length() > 0) {
		//	displayName = parentName + "." + fieldName;
		//}

		if (fieldValue == null || fieldValue.toString().length() == 0) {
			throw new ValidationException(MessageCode.ERROR_INVALID_PARAM.getCode(), String.format("Invalid %s.", displayName));
		} else if (mandatory.length() > 0 && fieldValue.toString().length() > mandatory.length()) {
			throw new ValidationException(MessageCode.ERROR_INVALID_PARAM.getCode(), displayName + " size > " + mandatory.length() + ".");
		} else if (fieldValue instanceof String && mandatory.datePattern() != null && mandatory.datePattern().trim().length() > 0) {
			try {
				new SimpleDateFormat(mandatory.datePattern()).parse(fieldValue.toString());
			} catch (ParseException e) {
				throw new ValidationException(MessageCode.ERROR_INVALID_PARAM.getCode(), displayName + " invalid date pattern with " + mandatory.datePattern() + ".");
			}
		}
	}
	
	public static boolean validateRegex(String s, String pattern) {
		if(Pattern.compile(pattern).matcher(s).matches()) {
			return true;
		}
		
		return false;
	}
	
	public static <T> void validateOpts(T object) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<T>> violations = validator.validate(object);

		if (violations.size() > 0) {
			logger.debug(String.format("O:--Validate Input--:size/%s", violations.size()));

			for (ConstraintViolation<T> violation : violations) {
				//String errorMsg = "Configuration file invalid: property [" + violation.getPropertyPath() + "] error [" + violation.getMessage() + "]";
				String errorMsg = String.format("Invalid %s.", violation.getPropertyPath());
				throw new ValidationException(MessageCode.ERROR_INVALID_PARAM.getCode(), errorMsg);
			}
		}
	}
}
