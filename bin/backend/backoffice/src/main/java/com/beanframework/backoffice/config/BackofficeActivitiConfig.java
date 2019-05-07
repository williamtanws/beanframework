package com.beanframework.backoffice.config;

import java.util.List;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BackofficeActivitiConfig {

	@Bean
	public InitializingBean initializingBean(RepositoryService repositoryService) {
		return () -> {
			List<Deployment> list = repositoryService.createDeploymentQuery().list();
			System.err.println("Deployment size: " + list.size());// where '1' is expected
		};
	}
}
