package com.beanframework.console.config;

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

import com.beanframework.console.WebConsoleConstants;
import com.beanframework.console.security.ConsoleAuthProvider;
import com.beanframework.console.security.ConsoleSuccessHandler;
import com.beanframework.console.security.CsrfHeaderFilter;
import com.beanframework.console.security.SessionExpiredDetectingLoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@Order(1)
public class ConsoleConfig extends WebSecurityConfigurerAdapter {
	
	@Value(WebConsoleConstants.Path.CONSOLE)
	private String MODULE_CONSOLE_PATH;
	
	@Value(WebConsoleConstants.Path.CONSOLE_API)
	private String MODULE_CONSOLE_PATH_API;
	
	@Value(WebConsoleConstants.Http.USERNAME_PARAM)
	private String HTTP_USERNAME_PARAM;
	
	@Value(WebConsoleConstants.Http.PASSWORD_PARAM)
	private String HTTP_PASSWORD_PARAM;
	
	@Value(WebConsoleConstants.Http.ANTPATTERNS_PERMITALL)
	private String[] HTTP_ANTPATTERNS_PERMITALL;
	
	@Value(WebConsoleConstants.Http.REMEMBERME_PARAM)
	private String HTTP_REMEMBERME_PARAM;
	
	@Value(WebConsoleConstants.Http.REMEMBERME_COOKIENAME)
	private String HTTP_REMEMBERME_COOKIENAME;
	
	@Value(WebConsoleConstants.Http.REMEMBERME_TOKENVALIDITYSECONDS)
	private int HTTP_REMEMBERME_TOKENVALIDITYSECONDS;
	
	@Value(WebConsoleConstants.Path.LOGIN)
	private String PATH_CONSOLE_LOGIN;
	
	@Value(WebConsoleConstants.Path.LOGOUT)
	private String PATH_CONSOLE_LOGOUT;
	
	@Value(WebConsoleConstants.Authority.CONSOLE)
	private String CONSOLE_ACCESS;
	
	@Autowired
	private ConsoleAuthProvider authProvider;
	
	@Autowired
	private ConsoleSuccessHandler successHandler;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.requestMatchers()
    			.antMatchers(MODULE_CONSOLE_PATH+"/**", MODULE_CONSOLE_PATH_API+"/**")
    			.and()
	        .authorizeRequests()
	        	.antMatchers(HTTP_ANTPATTERNS_PERMITALL).permitAll()
	        	.antMatchers(MODULE_CONSOLE_PATH+"/**", MODULE_CONSOLE_PATH_API+"/**").authenticated()
	        	.antMatchers(MODULE_CONSOLE_PATH+"/**").hasAnyAuthority(CONSOLE_ACCESS)
	        	.and()
	        .addFilterAfter(csrfHeaderFilter(MODULE_CONSOLE_PATH), CsrfFilter.class)
	        .csrf().csrfTokenRepository(csrfTokenRepository())
	        	.and()
	        .formLogin()
                .loginPage(PATH_CONSOLE_LOGIN)
                .successHandler(successHandler)
                .failureUrl(PATH_CONSOLE_LOGIN+"?error")
                .usernameParameter(HTTP_USERNAME_PARAM)
                .passwordParameter(HTTP_PASSWORD_PARAM)
                .permitAll()
                .and()
            .logout()
            	.logoutUrl(PATH_CONSOLE_LOGOUT)
            	.logoutSuccessUrl(PATH_CONSOLE_LOGIN+"?logout") 
            	.invalidateHttpSession(true)
            	.deleteCookies(WebConsoleConstants.Cookie.REMEMBER_ME)
               	.permitAll()
        		.and()
        	.exceptionHandling()
        		.accessDeniedPage(PATH_CONSOLE_LOGIN+"?denied")
        		.authenticationEntryPoint(consoleAuthenticationEntryPoint())
        		.and()
        	.rememberMe()
        		.rememberMeParameter(HTTP_REMEMBERME_PARAM)
        		.rememberMeCookieName(HTTP_REMEMBERME_COOKIENAME)
        		.tokenValiditySeconds(HTTP_REMEMBERME_TOKENVALIDITYSECONDS)
        		.and()
        	.sessionManagement()
        		.maximumSessions(-1)
        		.sessionRegistry(sessionRegistry)
        		.maxSessionsPreventsLogin(true)
        		.expiredUrl(PATH_CONSOLE_LOGIN+"?expired");
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
    public AuthenticationEntryPoint consoleAuthenticationEntryPoint() {
        return new SessionExpiredDetectingLoginUrlAuthenticationEntryPoint(PATH_CONSOLE_LOGIN);
    }
}