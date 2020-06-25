package run.onco.api.common.utils;

import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class ValidBirthDateValidator implements ConstraintValidator<ValidBirthDate, String> {
	private String pattern;

	@Override
	public void initialize(ValidBirthDate constraintAnnotation) {
		this.pattern = constraintAnnotation.pattern();
	}

	@Override
	public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
		if (object == null) {
			return true;
		}

		Date date = DateUtil.parseDate(object, pattern);
		return DateUtil.compareDate(date, DateUtil.getCurrentDate()) >= 0 ? false : true;
	}
}