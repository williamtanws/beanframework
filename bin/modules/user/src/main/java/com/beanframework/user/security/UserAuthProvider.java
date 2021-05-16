package com.beanframework.user.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.stereotype.Component;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.user.UserConstants;
import com.beanframework.user.service.UserService;

@Component
public class UserAuthProvider implements AuthenticationProvider {

  @Autowired
  private UserService userService;

  @Autowired
  private LocaleMessageService localeMessageService;

  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String id = authentication.getName();
    String password = (String) authentication.getCredentials();

    if (StringUtils.isBlank(id.trim())) {
      throw new BadCredentialsException(
          localeMessageService.getMessage(UserConstants.Locale.LOGIN_WRONG_USERNAME_PASSWORD));
    } else {
      id = id.trim();
    }

    if (StringUtils.isBlank(password)) {
      throw new BadCredentialsException(
          localeMessageService.getMessage(UserConstants.Locale.LOGIN_WRONG_USERNAME_PASSWORD));
    }

    try {
      return userService.findAuthenticate(id, password);
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException(
          localeMessageService.getMessage(UserConstants.Locale.LOGIN_WRONG_USERNAME_PASSWORD));
    } catch (DisabledException e) {
      throw new DisabledException(
          localeMessageService.getMessage(UserConstants.Locale.ACCOUNT_DISABLED));
    } catch (AccountExpiredException e) {
      throw new AccountExpiredException(
          localeMessageService.getMessage(UserConstants.Locale.ACCOUNT_EXPIRED));
    } catch (LockedException e) {
      throw new LockedException(
          localeMessageService.getMessage(UserConstants.Locale.ACCOUNT_LOCKED));
    } catch (CredentialsExpiredException e) {
      throw new CredentialsExpiredException(
          localeMessageService.getMessage(UserConstants.Locale.ACCOUNT_PASSWORD_EXPIRED));
    } catch (Exception e) {
      throw new AuthenticationServiceException(e.getMessage());
    }
  }

  public boolean supports(Class<? extends Object> authentication) {
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }
}
