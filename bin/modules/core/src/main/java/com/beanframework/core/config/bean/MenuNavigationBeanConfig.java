package com.beanframework.core.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.beanframework.core.bean.MenuNavigationBean;
import com.beanframework.core.bean.MenuNavigationBeanImpl;

@Configuration
public class MenuNavigationBeanConfig {

  @Bean(name = "MenuNavigation")
  public MenuNavigationBean MenuNavigationBean() throws Exception {
    return new MenuNavigationBeanImpl();
  }
}
