package run.onco.api.rest.aspect;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import run.onco.api.common.aspect.ValidCondition;
import run.onco.api.common.exception.ValidationException;
import run.onco.api.common.message.Header;
import run.onco.api.common.message.ServiceRequest;
import run.onco.api.common.utils.AppUtil;
import run.onco.api.common.utils.MessageCode;
import run.onco.api.common.utils.ReflectionUtil;
import run.onco.api.common.utils.ServiceUtil;
import run.onco.api.common.utils.ValidationUtil;

/**
 * 
 * @author Neda Peyrone
 *
 */
@Aspect
@Component
public class ControllerLoggingAspect {

	// private static final String logPattern = "%s|%s|%s millis.";
	final static Logger logger = Logger.getLogger(ControllerLoggingAspect.class);

	// @Autowired
	// private Environment env;

	@Autowired
	private HttpServletRequest context;

	@Pointcut("@annotation(run.onco.api.common.aspect.ControllerLogging)")
	public void controller() {
	}

	@SuppressWarnings("rawtypes")
	@Around("controller()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("I:--START--:--Trace (Before Call)--");

		long start = System.currentTimeMillis();

		Header header = null;
		Object[] args = joinPoint.getArgs();

		if (args != null) {
			Stream<Object> stream = Arrays.stream(args);
			Optional<ServiceRequest> optional = stream.filter(x -> x instanceof ServiceRequest).map(x -> (ServiceRequest) x).findFirst();

			if (optional.isPresent()) {
				header = optional.get().getHeader();
				header.setClientIp(AppUtil.getClientIp(context));
			}
		}

		try {
			MethodSignature signature = (MethodSignature) joinPoint.getSignature();
			Method method = signature.getMethod();

			String className = signature.getDeclaringTypeName();
			String methodName = method.getName();

			validateArgument(method, args);

			Object result = joinPoint.proceed();

			long elapsedTime = System.currentTimeMillis() - start;
			logger.info(String.format("O:--SUCCESS--:--Method %s.%s()--:executionTime/%s ms", className, methodName, elapsedTime));

			return result;
		} catch (ValidationException re) {
			logger.error(re);
			logger.info(String.format("O:--FAIL--:--Validation--:errorCode/%s:errorDesc/%s", MessageCode.ERROR_INVALID_PARAM.getCode(), re.getMessage()));
			return ServiceUtil.buildResponse(header, MessageCode.ERROR_INVALID_PARAM.getCode(), re.getMessage());
		} catch (IllegalArgumentException e) {
			logger.info(String.format("O:--FAIL--:--Illegal argument %s in %s()--", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getName()));
			throw e;
		} finally {
			logger.info("O:--FINISH--:--Trace (After Call)--");
		}
	}

	private void validateArgument(Method method, Object[] args) throws ValidationException {
		if (method == null || args == null) {
			return;
		}

		for (int argIndex = 0; argIndex < args.length; argIndex++) {
			ValidCondition validCondition = ReflectionUtil.getParameterAnnotation(ValidCondition.class, method, argIndex);

			if (validCondition != null) {
				ValidationUtil.checkValid(args[argIndex], validCondition);
			}
		}
	}
}
