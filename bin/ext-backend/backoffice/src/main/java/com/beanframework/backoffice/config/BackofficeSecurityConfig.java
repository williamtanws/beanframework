package com.beanframework.backoffice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.beanframework.backoffice.WebBackofficeConstants;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(1)
public class BackofficeSecurityConfig extends WebSecurityConfigurerAdapter {
		
	@Value(WebBackofficeConstants.Path.BACKOFFICE)
	private String PATH_BACKOFFICE;
	
	@Value(WebBackofficeConstants.Path.BACKOFFICE_API)
	private String PATH_BACKOFFICE_API;
	
	@Value(WebBackofficeConstants.Http.USERNAME_PARAM)
	private String HTTP_USERNAME_PARAM;
	
	@Value(WebBackofficeConstants.Http.PASSWORD_PARAM)
	private String HTTP_PASSWORD_PARAM;
	
	@Value(WebBackofficeConstants.Http.ANTPATTERNS_PERMITALL)
	private String[] HTTP_ANTPATTERNS_PERMITALL;
	
	@Value(WebBackofficeConstants.Http.REMEMBERME_PARAM)
	private String HTTP_REMEMBERME_PARAM;
	
	@Value(WebBackofficeConstants.Http.REMEMBERME_COOKIENAME)
	private String HTTP_REMEMBERME_COOKIENAME;
	
	@Value(WebBackofficeConstants.Http.REMEMBERME_TOKENVALIDITYSECONDS)
	private int HTTP_REMEMBERME_TOKENVALIDITYSECONDS;
	
	@Value(WebBackofficeConstants.Path.LOGIN)
	private String PATH_BACKOFFICE_LOGIN;
	
	@Value(WebBackofficeConstants.Path.LOGOUT)
	private String PATH_BACKOFFICE_LOGOUT;
	
	@Value(WebBackofficeConstants.Authority.BACKOFFICE)
	private String BACKOFFICE_ACCESS;
	
	@Value("${module.backoffice.session.max:-1}")
	private int SESSION_MAX;
	
	@Value("${module.backoffice.session.login.prevent:false}")
	private boolean SESSION_LOGIN_PREVENT;
	
	@Autowired
	private BackofficeAuthProvider authProvider;
	
	@Autowired
	private BackofficeSuccessHandler successHandler;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.requestMatchers()
    			.antMatchers(PATH_BACKOFFICE+"/**", PATH_BACKOFFICE_API+"/**")
    			.and()
	        .authorizeRequests()
	        	.antMatchers(HTTP_ANTPATTERNS_PERMITALL).permitAll()
	        	.antMatchers(PATH_BACKOFFICE+"/**").authenticated()
	        	.antMatchers(PATH_BACKOFFICE+"/**").hasAnyAuthority(BACKOFFICE_ACCESS)
	        	.and()
	        .addFilterAfter(csrfHeaderFilter(PATH_BACKOFFICE), CsrfFilter.class)
	        .csrf().csrfTokenRepository(csrfTokenRepository())
	        	.and()
	        .formLogin()
                .loginPage(PATH_BACKOFFICE_LOGIN)
                .successHandler(successHandler)
                .failureUrl(PATH_BACKOFFICE_LOGIN+"?error")
                .usernameParameter(HTTP_USERNAME_PARAM)
                .passwordParameter(HTTP_PASSWORD_PARAM)
                .permitAll()
                .and()
            .logout()
            	.logoutUrl(PATH_BACKOFFICE_LOGOUT)
            	.logoutSuccessUrl(PATH_BACKOFFICE_LOGIN+"?logout") 
            	.invalidateHttpSession(true)
            	.deleteCookies(WebBackofficeConstants.Cookie.REMEMBER_ME)
               	.permitAll()
        		.and()
        	.exceptionHandling()
        		.accessDeniedPage(PATH_BACKOFFICE_LOGIN+"?denied")
        		.authenticationEntryPoint(backofficeAuthenticationEntryPoint())
        		.and()
        	.rememberMe()
        		.rememberMeParameter(HTTP_REMEMBERME_PARAM)
        		.rememberMeCookieName(HTTP_REMEMBERME_COOKIENAME)
        		.tokenValiditySeconds(HTTP_REMEMBERME_TOKENVALIDITYSECONDS)
        		.and()
        	.sessionManagement()
        		.maximumSessions(SESSION_MAX)
        		.sessionRegistry(sessionRegistry)
        		.maxSessionsPreventsLogin(SESSION_LOGIN_PREVENT)
        		.expiredUrl(PATH_BACKOFFICE_LOGIN+"?expired");
    	http.headers().frameOptions().sameOrigin();
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(authProvider);
    }
    
	public CsrfHeaderFilter csrfHeaderFilter(String path){
    	CsrfHeaderFilter csrfHeaderFilter = new CsrfHeaderFilter();
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
        return new SessionExpiredDetectingLoginUrlAuthenticationEntryPoint(PATH_BACKOFFICE_LOGIN);
    }
}