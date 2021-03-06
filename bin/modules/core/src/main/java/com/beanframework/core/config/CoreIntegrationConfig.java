package com.beanframework.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
public class CoreIntegrationConfig {

  public static final String READ_CHANNEL = "readChannel";
  public static final String PROCESSED_CHANNEL = "processedChannel";
  public static final String FAILED_CHANNEL = "failedChannel";

  @Bean(name = READ_CHANNEL)
  public MessageChannel readChannel() {
    return MessageChannels.direct().get();
  }

  @Bean(name = PROCESSED_CHANNEL)
  public MessageChannel processedChannel() {
    return MessageChannels.direct().get();
  }

  @Bean(name = FAILED_CHANNEL)
  public MessageChannel failedChannel() {
    return MessageChannels.direct().get();
  }
}
