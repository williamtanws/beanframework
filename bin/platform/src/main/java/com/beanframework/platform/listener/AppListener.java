package com.beanframework.platform.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

@SuppressWarnings("rawtypes")
@Component
public class AppListener implements ApplicationListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppListener.class);

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// Here you can listen to the Spring Boot life cycle
		if (event instanceof ApplicationEnvironmentPreparedEvent) {
			// Initialize the environment variable
			LOGGER.info("**************onApplicationEvent initializes environment variables **************");

		} else if (event instanceof ApplicationPreparedEvent) {
			// loading finished
			LOGGER.info("**************onApplicationEvent initialization completed **************");
		} else if (event instanceof ContextRefreshedEvent) {
			// Apply refresh
//            init((ContextRefreshedEvent) event);
			LOGGER.info("**************onApplicationEvent application refresh completed **************");

		} else if (event instanceof ApplicationReadyEvent) {
//            / / Initialize the operation
//            User user= new User ();
//            user.setName("Super Administrator");
//            user.setPassWord("123");
//            userService.insert(user);
			// The app has started to finish
			LOGGER.info("**************onApplicationEvent application startup completed **************");
		} else if (event instanceof ContextStartedEvent) {
			// Application launch, you need to dynamically add a listener in the code to
			// capture
			LOGGER.info("**************onApplicationEvent ContextStartedEvent completed **************");
		} else if (event instanceof ContextStoppedEvent) {

			LOGGER.info("**************onApplicationEvent application stopped completing **************");

		} else if (event instanceof ContextClosedEvent) {
			// app close
			LOGGER.info("**************onApplicationEvent application close completed **************");
		} else {
			//LOGGER.info("**************onApplicationEvent Other events not processed **************");
		}
	}
}