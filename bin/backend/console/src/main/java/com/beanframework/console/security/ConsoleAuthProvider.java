package com.beanframework.console.security;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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

import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.console.AdminWebConstants;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.core.facade.AdminFacade.AdminPreAuthorizeEnum;
import com.beanframework.core.facade.ConfigurationFacade.ConfigurationPreAuthorizeEnum;
import com.beanframework.user.domain.Admin;
import com.beanframework.user.service.AdminService;

@Component
public class ConsoleAuthProvider implements AuthenticationProvider {

	@Autowired
	private AdminService adminService;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Value(ConsoleWebConstants.Authority.CONSOLE)
	private String CONSOLE_ACCESS;

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String id = authentication.getName();
		String password = (String) authentication.getCredentials();
		
		if (id == null || StringUtils.isBlank(id.trim())) {
			throw new BadCredentialsException(localeMessageService.getMessage(ConsoleWebConstants.Locale.LOGIN_WRONG_USERNAME_PASSWORD));
		}
		else {
			id = id.trim();
		}
		
		if (password == null || StringUtils.isBlank(password)) {
			throw new BadCredentialsException(localeMessageService.getMessage(ConsoleWebConstants.Locale.LOGIN_WRONG_USERNAME_PASSWORD));
		}

		Admin admin;
		try {
			admin = adminService.findAuthenticate(id, password);
		} catch (BadCredentialsException e) {
			throw new BadCredentialsException(localeMessageService.getMessage(ConsoleWebConstants.Locale.LOGIN_WRONG_USERNAME_PASSWORD));
		} catch (DisabledException e) {
			throw new DisabledException(localeMessageService.getMessage(AdminWebConstants.Locale.ACCOUNT_DISABLED));
		} catch (AccountExpiredException e) {
			throw new AccountExpiredException(localeMessageService.getMessage(AdminWebConstants.Locale.ACCOUNT_EXPIRED));
		} catch (LockedException e) {
			throw new LockedException(localeMessageService.getMessage(AdminWebConstants.Locale.ACCOUNT_LOCKED));
		} catch (CredentialsExpiredException e) {
			throw new CredentialsExpiredException(localeMessageService.getMessage(AdminWebConstants.Locale.ACCOUNT_PASSWORD_EXPIRED));
		} catch (Exception e) {
			throw new AuthenticationServiceException(e.getMessage());
		}

		// Console
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(CONSOLE_ACCESS));
		authorities.add(new SimpleGrantedAuthority(AdminPreAuthorizeEnum.AUTHORITY_READ));
		authorities.add(new SimpleGrantedAuthority(AdminPreAuthorizeEnum.AUTHORITY_CREATE));
		authorities.add(new SimpleGrantedAuthority(AdminPreAuthorizeEnum.AUTHORITY_UPDATE));
		authorities.add(new SimpleGrantedAuthority(AdminPreAuthorizeEnum.AUTHORITY_DELETE));
		authorities.add(new SimpleGrantedAuthority(ConfigurationPreAuthorizeEnum.AUTHORITY_READ));
		authorities.add(new SimpleGrantedAuthority(ConfigurationPreAuthorizeEnum.AUTHORITY_CREATE));
		authorities.add(new SimpleGrantedAuthority(ConfigurationPreAuthorizeEnum.AUTHORITY_UPDATE));
		authorities.add(new SimpleGrantedAuthority(ConfigurationPreAuthorizeEnum.AUTHORITY_DELETE));

		return new UsernamePasswordAuthenticationToken(admin, admin.getPassword(), authorities);
	}

	public boolean supports(Class<? extends Object> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
}
