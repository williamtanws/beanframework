package com.beanframework.backoffice.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@Configuration
public class BackofficeConfig implements WebApplicationInitializer {
  @Override
  public void onStartup(ServletContext container) {
    XmlWebApplicationContext context = new XmlWebApplicationContext();
    context.setConfigLocation("/WEB-INF/spring/dispatcher-config.xml");

    ServletRegistration.Dynamic dispatcher =
        container.addServlet("dispatcher", new DispatcherServlet(context));

    dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/");
  }
}
