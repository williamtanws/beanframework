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
import com.beanframework.configuration.domain.Configuration;
import com.beanframework.configuration.service.ConfigurationService;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.service.EmployeeService;
import com.beanframework.user.domain.UserGroup;

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
	
	@Autowired
	private ConfigurationService configurationService;
	
	@Value(BackofficeWebConstants.Configuration.USERGROUP)
	private String BACKOFFICE_CONFIGURATION_USERGROUP;

	@Value(BackofficeWebConstants.Configuration.USERGROUP_SPLITTER)
	private String BACKOFFICE_CONFIGURATION_SPLITTER;

	@Value(BackofficeWebConstants.Authority.BACKOFFICE)
	private String BACKOFFICE_ACCESS;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
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
			
			if (isAuthorized(employee) == false) {
				throw new BadCredentialsException(localeMessageService.getMessage(BackofficeWebConstants.Locale.LOGIN_WRONG_USERNAME_PASSWORD));
			}
			
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
		
		Set<GrantedAuthority> authorities = employeeService.getAuthorities(employee.getUserGroups(), new HashSet<String>());
		authorities.add(new SimpleGrantedAuthority(BACKOFFICE_ACCESS));

		return new UsernamePasswordAuthenticationToken(employee, employee.getPassword(), authorities);
	}

	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	boolean isAuthorized(Employee employee) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Configuration.ID, BACKOFFICE_CONFIGURATION_USERGROUP);
		Configuration usergroupConfiguration = configurationService.findOneEntityByProperties(properties);
		if (usergroupConfiguration != null) {
			Set<String> usergroups = new HashSet<String>(Arrays.asList(usergroupConfiguration.getValue().split(BACKOFFICE_CONFIGURATION_SPLITTER)));

			for (UserGroup userGroup : employee.getUserGroups()) {
				if (usergroups.contains(userGroup.getId())) {
					return true;
				}
				else if (userGroup.getUserGroups() != null && userGroup.getUserGroups().isEmpty() == false){
					return isAuthorized(userGroup, usergroups);
				}
			}
		}
		
		return false;
	}
	
	boolean isAuthorized(UserGroup model, Set<String> usergroups) {
		for (UserGroup userGroup : model.getUserGroups()) {
			if (usergroups.contains(userGroup.getId())) {
				return true;
			}
			else if (userGroup.getUserGroups() != null && userGroup.getUserGroups().isEmpty() == false){
				return isAuthorized(userGroup, usergroups);
			}
		}
		
		return false;
	}

}
