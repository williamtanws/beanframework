package com.beanframework.platform.config;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.beanframework.user.domain.User;

@Configuration
@EnableCaching
@EnableAsync
@EnableJpaAuditing
public class PlatformMvcConfig implements WebMvcConfigurer {

	@Value("${i18n.resources}")
	private String I18N_RESOURCES;

	@Value("${i18n.basenames}")
	private String I18N_BASENAMES;

	@Value("${i18n.cache.seconds}")
	private int I18N_CACHE_SECONDS;

	@Value("${i18n.default.encoding}")
	private String I18N_DEFAULT_ENCODING;

	@Value("${resource.handler}")
	private String[] RESOURCE_HANDLER;

	@Value("${resource.locations}")
	private String[] RESOURCE_LOCATIONS;

	@Value("${language.pathpatterns}")
	private String[] LANGUAGE_PATHPATTERNS;

	@Autowired
	public ResourceLoader resourceLoader;

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		return new LocaleChangeInterceptor();
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver resolver = new CookieLocaleResolver();
		resolver.setDefaultLocale(new Locale("en")); // your default locale
		return resolver;
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		Resource[] resources = loadResources(I18N_RESOURCES);

		if (resources != null) {
			for (Resource resource : resources) {
				String fileName = resource.getFilename();
				String basename = fileName.substring(0, fileName.indexOf('_'));
				messageSource.addBasenames(I18N_BASENAMES + basename);
			}
		}

		messageSource.setDefaultEncoding(I18N_DEFAULT_ENCODING);
		messageSource.setCacheSeconds(I18N_CACHE_SECONDS);
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}

	public Resource[] loadResources(String pattern) {
		try {
			return ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResources(pattern);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Bean
	public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
		return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}

	@Bean
	public static SessionRegistry sessionRegistry() {
		return new SessionRegistryImpl();
	}

	@Bean
	public AuditingEntityListener createAuditingListener() {
		return new AuditingEntityListener();
	}

	@Bean
	public AuditorAware<String> createAuditorProvider() {
		return new SecurityAuditor();
	}

	public class SecurityAuditor implements AuditorAware<String> {
		@Override
		public Optional<String> getCurrentAuditor() {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			if(auth != null) {
				User user = (User) auth.getPrincipal();
				if(user.getUuid() == null) {
					return Optional.empty(); 
				}
				return Optional.of(user.getUuid().toString());
			}
			else {
				return Optional.empty(); 
			}
		}
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor()).addPathPatterns(LANGUAGE_PATHPATTERNS);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(RESOURCE_HANDLER).addResourceLocations(RESOURCE_LOCATIONS);
	}

}
