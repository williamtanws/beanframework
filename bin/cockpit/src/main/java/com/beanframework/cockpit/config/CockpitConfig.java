package com.beanframework.cockpit.config;

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

import com.beanframework.cockpit.WebCockpitConstants;

@Configuration
@EnableWebSecurity
@Order(3)
public class CockpitConfig extends WebSecurityConfigurerAdapter {
		
	@Value(WebCockpitConstants.Path.COCKPIT)
	private String PATH_COCKPIT;
	
	@Value(WebCockpitConstants.Path.COCKPIT_API)
	private String PATH_COCKPIT_API;
	
	@Value(WebCockpitConstants.Http.USERNAME_PARAM)
	private String HTTP_USERNAME_PARAM;
	
	@Value(WebCockpitConstants.Http.PASSWORD_PARAM)
	private String HTTP_PASSWORD_PARAM;
	
	@Value(WebCockpitConstants.Http.ANTPATTERNS_PERMITALL)
	private String[] HTTP_ANTPATTERNS_PERMITALL;
	
	@Value(WebCockpitConstants.Http.REMEMBERME_PARAM)
	private String HTTP_REMEMBERME_PARAM;
	
	@Value(WebCockpitConstants.Http.REMEMBERME_COOKIENAME)
	private String HTTP_REMEMBERME_COOKIENAME;
	
	@Value(WebCockpitConstants.Http.REMEMBERME_TOKENVALIDITYSECONDS)
	private int HTTP_REMEMBERME_TOKENVALIDITYSECONDS;
	
	@Value(WebCockpitConstants.Path.LOGIN)
	private String PATH_COCKPIT_LOGIN;
	
	@Value(WebCockpitConstants.Path.LOGOUT)
	private String PATH_COCKPIT_LOGOUT;
	
	@Value(WebCockpitConstants.Authority.COCKPIT)
	private String COCKPIT_ACCESS;
	
	@Value("${module.cockpit.session.max:-1}")
	private int SESSION_MAX;
	
	@Value("${module.cockpit.session.login.prevent:false}")
	private boolean SESSION_LOGIN_PREVENT;
	
	@Autowired
	private CockpitAuthProvider authProvider;
	
	@Autowired
	private CockpitSuccessHandler successHandler;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
    protected void configure(HttpSecurity http) throws Exception {
    	http
    		.requestMatchers()
    			.antMatchers(PATH_COCKPIT+"/**", PATH_COCKPIT_API+"/**")
    			.and()
	        .authorizeRequests()
	        	.antMatchers(HTTP_ANTPATTERNS_PERMITALL).permitAll()
	        	.antMatchers(PATH_COCKPIT+"/**").authenticated()
	        	.antMatchers(PATH_COCKPIT+"/**").hasAnyAuthority(COCKPIT_ACCESS)
	        	.and()
	        .addFilterAfter(csrfHeaderFilter(PATH_COCKPIT), CsrfFilter.class)
	        .csrf().csrfTokenRepository(csrfTokenRepository())
	        	.and()
	        .formLogin()
                .loginPage(PATH_COCKPIT_LOGIN)
                .successHandler(successHandler)
                .failureUrl(PATH_COCKPIT_LOGIN+"?error")
                .usernameParameter(HTTP_USERNAME_PARAM)
                .passwordParameter(HTTP_PASSWORD_PARAM)
                .permitAll()
                .and()
            .logout()
            	.logoutUrl(PATH_COCKPIT_LOGOUT)
            	.logoutSuccessUrl(PATH_COCKPIT_LOGIN+"?logout") 
            	.invalidateHttpSession(true)
            	.deleteCookies(WebCockpitConstants.Cookie.REMEMBER_ME)
               	.permitAll()
        		.and()
        	.exceptionHandling()
        		.accessDeniedPage(PATH_COCKPIT_LOGIN+"?denied")
        		.authenticationEntryPoint(cockpitAuthenticationEntryPoint())
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
        		.expiredUrl(PATH_COCKPIT_LOGIN+"?expired");
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
    public AuthenticationEntryPoint cockpitAuthenticationEntryPoint() {
        return new SessionExpiredDetectingLoginUrlAuthenticationEntryPoint(PATH_COCKPIT_LOGIN);
    }
}