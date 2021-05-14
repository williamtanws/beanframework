package com.beanframework.core.config;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreWorkflowConfig {

  protected static final Logger LOGGER = LoggerFactory.getLogger(CoreWorkflowConfig.class);

  @Bean
  public CommandLineRunner init(final RepositoryService repositoryService,
      final RuntimeService runtimeService, final TaskService taskService) {

    return new CommandLineRunner() {
      @Override
      public void run(String... strings) throws Exception {
        LOGGER.info("Number of process definitions : "
            + repositoryService.createProcessDefinitionQuery().count());
        LOGGER.info("Number of tasks : " + taskService.createTaskQuery().count());
      }
    };
  }
}
