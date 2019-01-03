package com.beanframework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication (scanBasePackages = { "${spring.scanBasePackages}" }, exclude = {SecurityAutoConfiguration.class})//
public class PlatformApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(PlatformApplication.class);

		// register PID write to spring boot. It will write PID to file
		springApplication.addListeners(new ApplicationPidFileWriter());
		springApplication.run(args);

//		SpringApplicationBuilder springApplication = new SpringApplicationBuilder();
////		springApplication.sources(PlatformConfig.class).web(WebApplicationType.SERVLET);
//		springApplication.parent(PlatformConfig.class).web(WebApplicationType.NONE);
//		springApplication.child(ConsoleConfig.class).web(WebApplicationType.SERVLET);
//		springApplication.sibling(BackofficeConfig.class).web(WebApplicationType.SERVLET);
//		springApplication.application().addListeners(new ApplicationPidFileWriter());
//		springApplication.run(args);
	}
	
//	@Bean
//    public DispatcherServlet dispatcherServlet() {
//        return new DispatcherServlet();
//    }
//
//	@Bean
//	public ServletRegistrationBean console() {
//		DispatcherServlet dispatcherServlet = new DispatcherServlet();
//
//        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
//        applicationContext.register(ConsoleConfig.class);
//        dispatcherServlet.setApplicationContext(applicationContext);
//
//		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet, "/console/*");
//		servletRegistrationBean.setName("console");
//
//		return servletRegistrationBean;
//	}
//
//	@Bean
//	public ServletRegistrationBean backoffice() {
//		DispatcherServlet dispatcherServlet = new DispatcherServlet();
//
//        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
//        applicationContext.register(BackofficeConfig.class);
//        dispatcherServlet.setApplicationContext(applicationContext);
//
//		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet, "/backoffice/*");
//		servletRegistrationBean.setName("backoffice");
//
//		return servletRegistrationBean;
//	}
}
