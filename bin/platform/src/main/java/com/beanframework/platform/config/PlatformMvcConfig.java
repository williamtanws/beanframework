package com.beanframework.platform.config;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableWebMvc
// @ComponentScan(basePackages = { "com.beanframework.platform" })
public class PlatformMvcConfig implements WebMvcConfigurer {

  @Value("${i18n.resources}")
  private String I18N_RESOURCES;

  @Value("${i18n.resources.exclude}")
  private String I18N_RESOURCES_EXCLUDE;

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
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();

    Resource[] resources = loadResources(I18N_RESOURCES);

    Resource[] resources_exclude = null;
    if (StringUtils.isNoneEmpty(I18N_RESOURCES_EXCLUDE)) {
      resources_exclude = loadResources(I18N_RESOURCES_EXCLUDE);
    }

    if (resources != null) {
      for (Resource resource : resources) {

        boolean exclude = false;
        if (resources_exclude != null) {

          for (Resource resourceExclude : resources_exclude) {
            if (resource.getFilename().equals(resourceExclude.getFilename())) {
              exclude = true;
            }
          }
        }

        if (exclude == false) {
          String fileName = resource.getFilename();
          String basename;
          if (fileName.contains("_")) {
            basename = fileName.substring(0, fileName.indexOf('_'));
          } else {
            basename = fileName;
          }
          messageSource.addBasenames(I18N_BASENAMES + basename);
        }
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
    return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(
        new HttpSessionEventPublisher());
  }

  @Bean
  public static SessionRegistry sessionRegistry() {
    return new SessionRegistryImpl();
  }

  @Bean
  public AuditingEntityListener createAuditingListener() {
    return new AuditingEntityListener();
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor()).addPathPatterns(LANGUAGE_PATHPATTERNS);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler(RESOURCE_HANDLER).addResourceLocations(RESOURCE_LOCATIONS);
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    // // Hibernate for Json
    // Hibernate5Module module = new Hibernate5Module();
    // module.disable(Feature.USE_TRANSIENT_ANNOTATION);
    // module.disable(Feature.FORCE_LAZY_LOADING);
    //
    // // Simple Module
    /// * SimpleModule simpleModule = new SimpleModule();
    // simpleModule.addSerializer(new NullAsEmptyStringSerializer());*/
    //
    // Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
    // builder.modulesToInstall(module);
    // builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    //
    // converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    //
    // // Comment
    // final ByteArrayHttpMessageConverter arrayHttpMessageConverter = new
    // ByteArrayHttpMessageConverter();
    // final List<MediaType> list = new ArrayList<>();
    // list.add(MediaType.ALL);
    // arrayHttpMessageConverter.setSupportedMediaTypes(list);
    // converters.add(arrayHttpMessageConverter);
  }

  @Bean
  public VelocityEngine velocityEngine() throws Exception {
    Properties properties = new Properties();
    properties.setProperty("input.encoding", "UTF-8");
    properties.setProperty("output.encoding", "UTF-8");
    properties.setProperty("resource.loader", "class");
    properties.setProperty("class.resource.loader.class",
        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    VelocityEngine velocityEngine = new VelocityEngine(properties);
    return velocityEngine;
  }
}
