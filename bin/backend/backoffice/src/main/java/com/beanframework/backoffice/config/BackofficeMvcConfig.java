package com.beanframework.backoffice.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.beanframework.backoffice.interceptor.BackofficeSecurityInterceptor;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature;

@Configuration
@EnableCaching
@EnableWebMvc
//@ComponentScan(basePackages = { "com.beanframework.backoffice" })
public class BackofficeMvcConfig implements WebMvcConfigurer {

	@Value("${backoffice.webroot}")
	private String BACKOFFICE_WEBROOT;

	@Bean
	public BackofficeSecurityInterceptor backofficeSecurityInterceptor() {
		return new BackofficeSecurityInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(backofficeSecurityInterceptor()).addPathPatterns(BACKOFFICE_WEBROOT + "/**");
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		// Hibernate for Json
		Hibernate5Module module = new Hibernate5Module();
		module.disable(Feature.USE_TRANSIENT_ANNOTATION);
		module.enable(Feature.FORCE_LAZY_LOADING);

		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.modulesToInstall(module);

		converters.add(new MappingJackson2HttpMessageConverter(builder.build()));

		// Comment
		final ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
	    final List<MediaType> list = new ArrayList<>();
	    list.add(MediaType.ALL);
	    arrayHttpMessageConverter.setSupportedMediaTypes(list);
	    converters.add(arrayHttpMessageConverter);
	}
}
