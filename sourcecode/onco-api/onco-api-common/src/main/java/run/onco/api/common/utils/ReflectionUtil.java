package run.onco.api.common.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import run.onco.api.common.constants.AppConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class ReflectionUtil {

	private static Logger log = Logger.getLogger(ReflectionUtil.class.getName());

	private static final String IGNORE_GETTER_SETTER_ATTRIBUTE = "serialVersionUID";

	public static Method getGetterMethod(Field field, Object ownerObject)
			throws NoSuchMethodException, SecurityException {
		Method method = null;
		try {
			if (field.getType().equals(Boolean.class)) {
				method = ownerObject.getClass()
						.getMethod("is" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
			}
			if (method == null) {
				if (!field.getType().equals(Boolean.class)) {
					if (!IGNORE_GETTER_SETTER_ATTRIBUTE.equalsIgnoreCase(field.getName())) {
						method = ownerObject.getClass().getMethod(
								"get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
					}
				}
			}
		} catch (Exception e) {
			// don't print error for object not declare getter method
			log.warning("" + e);
		}
		return method;
	}

	public static Method getSetterMethod(Field field, Object ownerObject)
			throws NoSuchMethodException, SecurityException {
		Method method = null;
		try {
			method = ownerObject.getClass().getMethod(
					"set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1),
					field.getType());
		} catch (Exception e) {
			log.warning("" + e);
		}
		return method;
	}

	@SuppressWarnings("rawtypes")
	public static List<Field> getAllField(Class type) {
		List<Field> fields = new ArrayList<Field>();

		fields.addAll(Arrays.asList(type.getDeclaredFields()));

		// get supper class field
		Class superClass = type.getSuperclass();
		if (superClass.getPackage().getName().indexOf(AppConstants.ROOT_PACKAGE) != -1) {
			fields.addAll(getAllField(superClass));
		}

		return fields;
	}

	@SuppressWarnings("rawtypes")
	public static Field getField(Class type, String fieldName) throws NoSuchFieldException, SecurityException {

		List<Field> fieldList = getAllField(type);

		for (Field field : fieldList) {
			if (field.getName().equals(fieldName)) {
				return field;
			}
		}

		throw new NoSuchFieldException("Field " + fieldName + " not found for type " + type + ".");

	}

	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T getParameterAnnotation(Class<T> annotationClass, Method method,
			int argIndex) {
		Annotation[] paramAnnotations = method.getParameterAnnotations()[argIndex];
		if (paramAnnotations != null) {
			for (Annotation annotation : paramAnnotations) {
				if (annotation.annotationType().equals(annotationClass)) {
					return (T) annotation;
				}
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static int getArgumentIndexByAnnotation(Method method, Class specifyAnnotation) {

		for (int argumentIndex = 0; argumentIndex < method.getParameterAnnotations().length; argumentIndex++) {
			if (method.getParameterAnnotations()[argumentIndex] != null) {
				for (Annotation argumentAnnotation : method.getParameterAnnotations()[argumentIndex]) {
					if (argumentAnnotation.annotationType().equals(specifyAnnotation)) {
						return argumentIndex;
					}
				}
			}
		}
		return -1;
	}

	public static boolean isArray(Object fieldValue, Field field) {
		if (fieldValue == null) {
			return field.getType().isArray();
		}
		return fieldValue.getClass().isArray();
	}

	public static boolean isCollection(Object fieldValue, Field field) {
		if (fieldValue == null) {
			return field.getType().equals(Collection.class) || field.getType().equals(List.class);
		}

		return fieldValue instanceof Collection || fieldValue instanceof List || fieldValue instanceof ArrayList;
	}

	public static boolean isNoNested(Object fieldValue) {
		try {
			return fieldValue == null || fieldValue.getClass().isPrimitive()
					|| fieldValue.getClass().getPackage().getName().equals("java.lang")
					|| fieldValue.getClass().getPackage().getName().equals("java.math");
		} catch (Exception e) {
			log.warning("" + e);
			return false;
		}
	}

	public static void setValueByMap(Object targetObject, Map<String, Object> mapValue, String datePattern) {
		if (targetObject != null) {
			List<Field> allFields = getAllField(targetObject.getClass());
			for (Field field : allFields) {
				try {
					Method method = getSetterMethod(field, targetObject);
					Object setValue = castValue(field.getType(), mapValue.get(field.getName()), datePattern);
					method.invoke(targetObject, setValue);
				} catch (NoSuchMethodException e) {
					log.warning("" + e);
				} catch (SecurityException se) {
					log.warning("" + se);
				} catch (IllegalAccessException iare) {
					log.warning("" + iare);
				} catch (IllegalArgumentException iae) {
					log.warning("" + iae);
				} catch (InvocationTargetException ite) {
					log.warning("" + ite);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T castValue(Class<T> type, Object value, String datePattern) {
		if (value == null) {
			return null;
		} else if (type.equals(value.getClass())) {
			return (T) value;
		} else {

			if (type.equals(Long.class)) {
				return (T) new Long(value + "");
			} else if (type.equals(Integer.class)) {
				return (T) new Integer(value + "");
			} else if (type.equals(BigDecimal.class)) {
				return (T) new BigDecimal(value + "");
			} else if (type.equals(Date.class)) {
				return (T) DateUtil.parseDate(value + "", datePattern);
			} else if (type.equals(String.class)) {
				return (T) value;
			} else {
				return (T) value;
			}
		}
	}
}
