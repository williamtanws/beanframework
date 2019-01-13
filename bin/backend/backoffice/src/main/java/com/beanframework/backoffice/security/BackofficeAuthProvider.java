package com.beanframework.backoffice.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.EmployeeWebConstants;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeService;

@Component
public class BackofficeAuthProvider implements AuthenticationProvider {

	Logger logger = LoggerFactory.getLogger(BackofficeAuthProvider.class);

	public static final String LOGIN_WRONG_USERNAME_PASSWORD = "form.login.error.wrongusernameorpassword";
	public static final String LOGIN_ACCOUNT_DISABLED = "form.login.account.disabled";
	public static final String LOGIN_ACCOUNT_EXPIRED = "form.login.account.expired";
	public static final String LOGIN_ACCOUNT_LOCKED = "form.login.account.locked";
	public static final String LOGIN_ACCOUNT_CREDENTIALS_LOCKED = "form.login.account.credentials.expired";

	@Autowired
	private LocaleMessageService localeMessageService;

	@Autowired
	private EmployeeService employeeService;

	@Value(BackofficeWebConstants.Authority.BACKOFFICE)
	private String BACKOFFICE_ACCESS;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String id = authentication.getName();
		String password = (String) authentication.getCredentials();
		
		if(StringUtils.stripToNull(id) == null || StringUtils.stripToNull(id) == "" ) {
			throw new BadCredentialsException(localeMessageService.getMessage(BackofficeWebConstants.Locale.LOGIN_WRONG_USERNAME_PASSWORD));
		}
		if(StringUtils.stripToNull(password) == null || StringUtils.stripToNull(password) == "" ) {
			throw new BadCredentialsException(localeMessageService.getMessage(BackofficeWebConstants.Locale.LOGIN_WRONG_USERNAME_PASSWORD));
		}

		Employee employee;
		try {
			employee = employeeService.findDtoAuthenticate(id, password);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(localeMessageService.getMessage(BackofficeWebConstants.Locale.LOGIN_WRONG_USERNAME_PASSWORD));
		} catch (DisabledException e) {
			throw new DisabledException(localeMessageService.getMessage(EmployeeWebConstants.Locale.ACCOUNT_DISABLED));
		} catch (AccountExpiredException e) {
			throw new AccountExpiredException(localeMessageService.getMessage(EmployeeWebConstants.Locale.ACCOUNT_EXPIRED));
		} catch (LockedException e) {
			throw new LockedException(localeMessageService.getMessage(EmployeeWebConstants.Locale.ACCOUNT_LOCKED));
		} catch (CredentialsExpiredException e) {
			throw new CredentialsExpiredException(localeMessageService.getMessage(EmployeeWebConstants.Locale.ACCOUNT_PASSWORD_EXPIRED));
		} catch (Exception e) {
			e.printStackTrace();
			throw new AuthenticationServiceException(e.getMessage(), e);
		}

		employee.getAuthorities().add(new SimpleGrantedAuthority(BACKOFFICE_ACCESS));

		logger.debug("Authenticated User: " + id);
		StringBuilder authorized = new StringBuilder();
		for (GrantedAuthority grantedAuthority : employee.getAuthorities()) {
			if (authorized.length() != 0) {
				authorized.append(",");
			}
			authorized.append(grantedAuthority.getAuthority());
		}
		logger.debug("Authorized: " + authorized.toString());

		return new UsernamePasswordAuthenticationToken(employee, employee.getPassword(), employee.getAuthorities());

	}

	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
