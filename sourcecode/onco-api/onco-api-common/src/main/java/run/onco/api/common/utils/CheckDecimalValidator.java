package run.onco.api.common.utils;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import run.onco.api.common.constants.AppConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class CheckDecimalValidator implements ConstraintValidator<CheckDecimalFormat, BigDecimal> {
	private String pattern;

	@Override
	public void initialize(CheckDecimalFormat constraintAnnotation) {
		this.pattern = constraintAnnotation.pattern();
	}

	@Override
	public boolean isValid(BigDecimal n, ConstraintValidatorContext constraintContext) {
		String s = AppUtil.isEmpty(n) ? AppConstants.EMPTY_STRING : n.toString();
		
		if (!ValidationUtil.validateRegex(s, pattern)) {
			return false;
		}

		return true;
	}

}
