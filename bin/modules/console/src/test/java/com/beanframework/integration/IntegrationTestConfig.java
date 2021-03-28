package com.beanframework.integration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource({ "classpath*:integration/import-update.xml" })
public class IntegrationTestConfig {

}
