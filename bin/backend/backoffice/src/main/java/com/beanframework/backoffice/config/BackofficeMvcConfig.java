package com.beanframework.backoffice.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.beanframework.backoffice.interceptor.BackofficeSecurityInterceptor;
import com.beanframework.user.domain.User;

@Configuration
@EnableCaching
@EnableWebMvc
//@ComponentScan(basePackages = { "com.beanframework.backoffice" })
public class BackofficeMvcConfig implements WebMvcConfigurer{

	@Value("${backoffice.webroot}")
	private String BACKOFFICE_WEBROOT;

	@Bean
	public BackofficeSecurityInterceptor backofficeSecurityInterceptor() {
		return new BackofficeSecurityInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(backofficeSecurityInterceptor()).addPathPatterns(BACKOFFICE_WEBROOT+"/**");
	}
	
	@Bean
	public AuditorAware<String> createAuditorProvider() {
		return new SecurityAuditor();
	}

	public class SecurityAuditor implements AuditorAware<String> {
		@Override
		public Optional<String> getCurrentAuditor() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (auth != null) {
				User user = (User) auth.getPrincipal();
				if (user.getUuid() == null) {
					return Optional.empty();
				}
				return Optional.of(user.getUuid().toString());
			} else {
				return Optional.empty();
			}
		}
	}
}
