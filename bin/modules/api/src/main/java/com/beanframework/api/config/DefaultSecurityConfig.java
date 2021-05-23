package com.beanframework.api.config;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import com.beanframework.api.ApiConstants;
import com.beanframework.api.service.ApiUserDetailsService;

@EnableWebSecurity
public class DefaultSecurityConfig {

  @Value(ApiConstants.Path.API)
  private String PATH_API;

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
// @formatter:off
        http.authorizeRequests(authorizeRequests ->
          authorizeRequests.antMatchers(PATH_API + "/**").authenticated()
        )
        .oauth2Login(withDefaults());
        return http.build();
// @formatter:on
  }

  @Bean
  UserDetailsService users() {
    return new ApiUserDetailsService();
  }
}
