package run.onco.api.common.utils;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class CheckDateValidator implements ConstraintValidator<CheckDateFormat, String> {
	private String pattern;

	@Override
	public void initialize(CheckDateFormat constraintAnnotation) {
		this.pattern = constraintAnnotation.pattern();
	}

	@Override
	public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
		if (object == null) {
			return true;
		}

		Date date = DateUtil.parseDate(object, pattern);
		return date == null ? false : true;
	}
}