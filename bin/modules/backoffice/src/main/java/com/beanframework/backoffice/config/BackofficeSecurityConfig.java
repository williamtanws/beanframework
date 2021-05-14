package com.beanframework.backoffice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.security.BackofficeCsrfHeaderFilter;
import com.beanframework.backoffice.security.BackofficeLoginSuccessHandler;
import com.beanframework.backoffice.security.BackofficeLogoutHandler;
import com.beanframework.backoffice.security.BackofficeSessionExpiredDetectingLoginUrlAuthenticationEntryPoint;
import com.beanframework.user.UserConstants;
import com.beanframework.user.security.UserAuthProvider;

@Configuration
@EnableWebSecurity
@Order(2)
public class BackofficeSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value(BackofficeWebConstants.Path.BACKOFFICE)
  private String PATH_BACKOFFICE;

  @Value(BackofficeWebConstants.Path.BACKOFFICE_API)
  private String PATH_BACKOFFICE_API;

  @Value(BackofficeWebConstants.Http.USERNAME_PARAM)
  private String HTTP_USERNAME_PARAM;

  @Value(BackofficeWebConstants.Http.PASSWORD_PARAM)
  private String HTTP_PASSWORD_PARAM;

  @Value(BackofficeWebConstants.Http.ANTPATTERNS_PERMITALL)
  private String[] HTTP_ANTPATTERNS_PERMITALL;

  @Value(BackofficeWebConstants.Http.REMEMBERME_PARAM)
  private String HTTP_REMEMBERME_PARAM;

  @Value(BackofficeWebConstants.Http.REMEMBERME_COOKIENAME)
  private String HTTP_REMEMBERME_COOKIENAME;

  @Value(BackofficeWebConstants.Http.REMEMBERME_TOKENVALIDITYSECONDS)
  private int HTTP_REMEMBERME_TOKENVALIDITYSECONDS;

  @Value(BackofficeWebConstants.Path.LOGIN)
  private String PATH_BACKOFFICE_LOGIN;

  @Value(BackofficeWebConstants.Path.LOGOUT)
  private String PATH_BACKOFFICE_LOGOUT;

  @Value(UserConstants.MAX_SESSION_USER)
  private int SESSION_MAX;

  @Value(UserConstants.MAX_SESSION_PREVENTS_LOGIN)
  private boolean SESSION_LOGIN_PREVENT;

  @Autowired
  private UserAuthProvider authProvider;

  @Autowired
  private BackofficeLoginSuccessHandler loginSuccessHandler;

  @Autowired
  private BackofficeLogoutHandler logoutHandler;

  @Autowired
  private SessionRegistry sessionRegistry;

  @Value(UserConstants.Access.BACKOFFICE)
  private String ACCESS_BACKOFFICE;

  protected void configure(HttpSecurity http) throws Exception {
    http.requestMatchers().antMatchers(PATH_BACKOFFICE + "/**", PATH_BACKOFFICE_API + "/**").and()
        .authorizeRequests().antMatchers(HTTP_ANTPATTERNS_PERMITALL).permitAll()
        .antMatchers(PATH_BACKOFFICE + "/**").authenticated().antMatchers(PATH_BACKOFFICE + "/**")
        .hasAnyAuthority(ACCESS_BACKOFFICE).and()
        .addFilterAfter(csrfHeaderFilter(PATH_BACKOFFICE), CsrfFilter.class).csrf()
        .csrfTokenRepository(csrfTokenRepository()).and().formLogin()
        .loginPage(PATH_BACKOFFICE_LOGIN).successHandler(loginSuccessHandler)
        .failureUrl(PATH_BACKOFFICE_LOGIN + "?error").usernameParameter(HTTP_USERNAME_PARAM)
        .passwordParameter(HTTP_PASSWORD_PARAM).permitAll().and().logout()
        .logoutUrl(PATH_BACKOFFICE_LOGOUT).logoutSuccessUrl(PATH_BACKOFFICE_LOGIN + "?logout")
        .addLogoutHandler(logoutHandler).invalidateHttpSession(true)
        .deleteCookies(BackofficeWebConstants.Cookie.REMEMBER_ME).permitAll().and()
        .exceptionHandling().accessDeniedPage(PATH_BACKOFFICE_LOGIN + "?denied")
        .authenticationEntryPoint(backofficeAuthenticationEntryPoint()).and().rememberMe()
        .rememberMeParameter(HTTP_REMEMBERME_PARAM).rememberMeCookieName(HTTP_REMEMBERME_COOKIENAME)
        .tokenValiditySeconds(HTTP_REMEMBERME_TOKENVALIDITYSECONDS).and().sessionManagement()
        .maximumSessions(SESSION_MAX).sessionRegistry(sessionRegistry)
        .maxSessionsPreventsLogin(SESSION_LOGIN_PREVENT)
        .expiredUrl(PATH_BACKOFFICE_LOGIN + "?expired");
    http.headers().frameOptions().sameOrigin();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authProvider);
  }

  public BackofficeCsrfHeaderFilter csrfHeaderFilter(String path) {
    BackofficeCsrfHeaderFilter csrfHeaderFilter = new BackofficeCsrfHeaderFilter();
    csrfHeaderFilter.setPath(path);
    return csrfHeaderFilter;
  }

  public CsrfTokenRepository csrfTokenRepository() {
    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
    repository.setHeaderName("X-XSRF-TOKEN");
    return repository;
  }

  @Bean
  public AuthenticationEntryPoint backofficeAuthenticationEntryPoint() {
    return new BackofficeSessionExpiredDetectingLoginUrlAuthenticationEntryPoint(
        PATH_BACKOFFICE_LOGIN);
  }
}
