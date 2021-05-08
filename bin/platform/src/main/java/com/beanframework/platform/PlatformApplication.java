package com.beanframework.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication(scanBasePackages = { "${spring.scanBasePackages}" }, exclude = { SecurityAutoConfiguration.class })
public class PlatformApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(PlatformApplication.class);

		// register PID write to spring boot. It will write PID to file
		springApplication.addListeners(new ApplicationPidFileWriter());
		springApplication.run(args);

		// TODO: Build multiple web contexts in future
//		SpringApplicationBuilder springApplication = new SpringApplicationBuilder(PlatformApplication.class);
//		springApplication.parent(PlatformConfig.class);
//		springApplication.child(ConsoleMvcConfig.class).web(WebApplicationType.SERVLET);
//		springApplication.child(BackofficeMvcConfig.class).web(WebApplicationType.SERVLET);
////		springApplication.application().addListeners(new ApplicationPidFileWriter());
//		springApplication.run(args);

	}

//	@Bean
//    public ServletRegistrationBean console(ApplicationContext parent) {
//        return createChildContextServlet(parent, "/console/*", "console");
//    }
//
//    @Bean
//    public ServletRegistrationBean backoffice(ApplicationContext parent) {
//        return createChildContextServlet(parent, "/backoffice/*", "backoffice");
//    }
//	
//	private ServletRegistrationBean createChildContextServlet(ApplicationContext parent, String path, String name) {
//        DispatcherServlet dispatcherServlet = new DispatcherServlet();
//
//        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
//        ctx.setParent(parent);
//        ctx.register(PropertyPlaceholderAutoConfiguration.class,
//                DispatcherServletAutoConfiguration.class);
//        dispatcherServlet.setApplicationContext(ctx);
//
//        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(dispatcherServlet, path);
//        servletRegistrationBean.setName(name);
//
//        return servletRegistrationBean;
//    }
}
