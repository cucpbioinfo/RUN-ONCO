package run.onco.api.rest.config;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import run.onco.api.common.constants.AppConstants;

/**
 * 
 * @author Neda Peyrone
 *
 */
public class JwtFilter extends GenericFilterBean {
	
	private final static Logger logger = Logger.getLogger(JwtFilter.class);
	
	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {
		
		final HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String path = ((HttpServletRequest) request).getRequestURI();
		
		logger.info(String.format("I:--START--:--JWT Filter--:path/%s", path));
		
		boolean contains = Arrays.stream(AppConstants.IGNORE_ACTION_LIST).anyMatch(path::contains);
		
		if(!contains) {
			final String authHeader = request.getHeader("Authorization");
			
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer error=\"missing_token\"");
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter();
			} else {
				final String token = authHeader.substring(7); // The part after "Bearer "
		
				try {
					final Claims claims = Jwts.parser().setSigningKey("run0nc0").parseClaimsJws(token).getBody();
					request.setAttribute("claims", claims);
					chain.doFilter(request, response);
				} catch (final ExpiredJwtException ex) {
					response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer error=\"expired_token\"");
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					response.getWriter();
				} catch (final SignatureException ex) {
					response.setHeader(HttpHeaders.WWW_AUTHENTICATE, "Bearer error=\"invalid_token\"");
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					logger.debug("AuthenticationManager rejected JWT Authentication.", ex);
					response.getWriter();
				}
			}
		} else {
			chain.doFilter(request, response);
		}
	}

}