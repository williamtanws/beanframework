package com.beanframework.core.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.beanframework.core.bean.NullAsEmptyStringSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;

@Configuration
public class CoreObjectMapperConfig implements InitializingBean {

  @Autowired
  private NullAsEmptyStringSerializer nullSerializer;

  @Autowired
  private ObjectMapper objectMapper;

  // will be called by Spring after all the beans are created
  // and the proper `objectMapper` instance is available here.
  @Override
  public void afterPropertiesSet() throws Exception {
    DefaultSerializerProvider serializerProvider = new DefaultSerializerProvider.Impl();
    serializerProvider.setNullValueSerializer(nullSerializer);
    objectMapper.setSerializerProvider(serializerProvider);
  }

}
