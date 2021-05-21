package com.beanframework.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.beanframework.user.service.UserService;

@Component
public class ApiUserDetailsService implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserDetails user = userService.findUserDetails(username.trim());
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }

    System.out.println(user.getAuthorities());

    return new User(user.getUsername(), user.getPassword(), user.isEnabled(),
        user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(),
        user.getAuthorities());
  }
}
