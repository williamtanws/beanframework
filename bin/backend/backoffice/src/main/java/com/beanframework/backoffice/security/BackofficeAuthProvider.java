package com.beanframework.backoffice.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
import com.beanframework.common.service.ModelService;
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeService;
import com.beanframework.user.service.UserService;

@Component
public class BackofficeAuthProvider implements AuthenticationProvider {

	protected static final Logger LOGGER = LoggerFactory.getLogger(BackofficeAuthProvider.class);

	public static final String LOGIN_WRONG_USERNAME_PASSWORD = "form.login.error.wrongusernameorpassword";
	public static final String LOGIN_ACCOUNT_DISABLED = "form.login.account.disabled";
	public static final String LOGIN_ACCOUNT_EXPIRED = "form.login.account.expired";
	public static final String LOGIN_ACCOUNT_LOCKED = "form.login.account.locked";
	public static final String LOGIN_ACCOUNT_CREDENTIALS_LOCKED = "form.login.account.credentials.expired";

	@Autowired
	private LocaleMessageService localeMessageService;

	@Autowired
	private UserService userService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private ModelService modelService;

	@Value(BackofficeWebConstants.Configuration.USERGROUP)
	private String BACKOFFICE_CONFIGURATION_USERGROUP;

	@Value(BackofficeWebConstants.Configuration.USERGROUP_SPLITTER)
	private String BACKOFFICE_CONFIGURATION_SPLITTER;

	@Value(BackofficeWebConstants.Authority.BACKOFFICE)
	private String BACKOFFICE_ACCESS;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			String id = authentication.getName();
			String password = (String) authentication.getCredentials();

			if (StringUtils.stripToNull(id) == null) {
				throw new BadCredentialsException(localeMessageService.getMessage(BackofficeWebConstants.Locale.LOGIN_WRONG_USERNAME_PASSWORD));
			}
			if (StringUtils.stripToNull(password) == null) {
				throw new BadCredentialsException(localeMessageService.getMessage(BackofficeWebConstants.Locale.LOGIN_WRONG_USERNAME_PASSWORD));
			}

			Employee employee;
			try {
				employee = employeeService.findAuthenticate(id, password);

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

			Set<GrantedAuthority> authorities = getAuthorities(employee);
			if (authorities.isEmpty()) {
				throw new BadCredentialsException(localeMessageService.getMessage(BackofficeWebConstants.Locale.LOGIN_WRONG_USERNAME_PASSWORD));
			}
			authorities.add(new SimpleGrantedAuthority(BACKOFFICE_ACCESS));

			return new UsernamePasswordAuthenticationToken(employee, employee.getPassword(), authorities);
		} catch (Exception e) {
			throw new BadCredentialsException(e.getMessage(), e);
		}
	}

	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

	private Set<GrantedAuthority> getAuthorities(Employee employee) throws Exception {
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, BACKOFFICE_CONFIGURATION_USERGROUP);
		Configuration usergroupConfiguration = modelService.findOneByProperties(properties, Configuration.class);
		if (usergroupConfiguration != null) {
			Set<String> usergroupIds = new HashSet<String>(Arrays.asList(usergroupConfiguration.getValue().split(BACKOFFICE_CONFIGURATION_SPLITTER)));

			if (usergroupIds != null)
				for (String userGroupId : usergroupIds) {
					authorities.addAll(userService.getAuthorities(employee.getUuid(), userGroupId));
				}
		}

		return authorities;
	}

}
