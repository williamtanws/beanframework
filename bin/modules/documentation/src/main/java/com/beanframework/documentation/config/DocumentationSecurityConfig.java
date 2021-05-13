package com.beanframework.documentation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import com.beanframework.documentation.DocumentationConstants;

@Configuration
@EnableWebSecurity
@Order(3)
public class DocumentationSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value(DocumentationConstants.Path.DOCUMENTATION)
  private String PATH_DOCUMENTATION;

  @Value(DocumentationConstants.Access.DOCUMENTATION)
  private String ACCESS_DOCUMENTATION;

  protected void configure(HttpSecurity http) throws Exception {
    http.requestMatchers().antMatchers(PATH_DOCUMENTATION + "/**").and().authorizeRequests()
        .antMatchers(PATH_DOCUMENTATION + "/**").authenticated()
        .antMatchers(PATH_DOCUMENTATION + "/**").hasAnyAuthority(ACCESS_DOCUMENTATION);
    http.headers().frameOptions().sameOrigin();
  }
}
