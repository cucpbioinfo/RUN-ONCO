package run.onco.api.common.aspect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface MandatoryField {
	String value() ;
	
	String datePattern() default "";
	
	int length() default 0;

	public static final String ALL_FIELD = "*";
}
