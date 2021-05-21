package com.beanframework.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import com.beanframework.api.ApiConstants;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  @Value(ApiConstants.Path.API)
  private String PATH_API;

  @Value(ApiConstants.RESOURCE_ID)
  private String RESOURCE_ID;

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.resourceId(RESOURCE_ID).stateless(false);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    .and()
    .antMatcher(PATH_API+"/users/**")
    .authorizeRequests()
    .antMatchers(PATH_API+"/users/**").authenticated()
    .antMatchers(PATH_API+"/users/**").hasAnyAuthority("backoffice_read")
    .anyRequest().authenticated()
    .and().csrf().disable()
    .anonymous().disable()
    .exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    // @formatter:on
  }

}
