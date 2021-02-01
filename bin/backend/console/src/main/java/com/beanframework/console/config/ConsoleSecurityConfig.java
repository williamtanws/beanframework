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

import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.console.security.ConsoleCsrfHeaderFilter;
import com.beanframework.console.security.ConsoleSessionExpiredDetectingLoginUrlAuthenticationEntryPoint;
import com.beanframework.console.security.ConsoleSuccessHandler;
import com.beanframework.user.UserConstants;
import com.beanframework.user.security.UserAuthProvider;

@Configuration
@EnableWebSecurity
@Order(1)
public class ConsoleSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value(ConsoleWebConstants.Path.CONSOLE)
	private String MODULE_CONSOLE_PATH;
	
	@Value(ConsoleWebConstants.Path.CONSOLE_API)
	private String MODULE_CONSOLE_PATH_API;
	
	@Value(ConsoleWebConstants.Http.USERNAME_PARAM)
	private String HTTP_USERNAME_PARAM;
	
	@Value(ConsoleWebConstants.Http.PASSWORD_PARAM)
	private String HTTP_PASSWORD_PARAM;
	
	@Value(ConsoleWebConstants.Http.ANTPATTERNS_PERMITALL)
	private String[] HTTP_ANTPATTERNS_PERMITALL;
	
	@Value(ConsoleWebConstants.Http.REMEMBERME_PARAM)
	private String HTTP_REMEMBERME_PARAM;
	
	@Value(ConsoleWebConstants.Http.REMEMBERME_COOKIENAME)
	private String HTTP_REMEMBERME_COOKIENAME;
	
	@Value(ConsoleWebConstants.Http.REMEMBERME_TOKENVALIDITYSECONDS)
	private int HTTP_REMEMBERME_TOKENVALIDITYSECONDS;
	
	@Value(ConsoleWebConstants.Path.LOGIN)
	private String PATH_CONSOLE_LOGIN;
	
	@Value(ConsoleWebConstants.Path.LOGOUT)
	private String PATH_CONSOLE_LOGOUT;
	
	@Autowired
	private UserAuthProvider authProvider;
	
	@Autowired
	private ConsoleSuccessHandler successHandler;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@Value(UserConstants.Admin.DEFAULT_GROUP)
	private String defaultAdminGroup;
	
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.requestMatchers()
    			.antMatchers(MODULE_CONSOLE_PATH+"/**", MODULE_CONSOLE_PATH_API+"/**")
    			.and()
	        .authorizeRequests()
	        	.antMatchers(HTTP_ANTPATTERNS_PERMITALL).permitAll()
	        	.antMatchers(MODULE_CONSOLE_PATH+"/**", MODULE_CONSOLE_PATH_API+"/**").authenticated()
	        	.antMatchers(MODULE_CONSOLE_PATH+"/**").hasAnyAuthority(defaultAdminGroup)
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
            	.deleteCookies(ConsoleWebConstants.Cookie.REMEMBER_ME)
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
    
	public ConsoleCsrfHeaderFilter csrfHeaderFilter(String path){
    	ConsoleCsrfHeaderFilter csrfHeaderFilter = new ConsoleCsrfHeaderFilter();
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
        return new ConsoleSessionExpiredDetectingLoginUrlAuthenticationEntryPoint(PATH_CONSOLE_LOGIN);
    }
}