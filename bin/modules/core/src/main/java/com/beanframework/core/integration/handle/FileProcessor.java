package com.beanframework.core.integration.handle;

import org.springframework.messaging.Message;

public interface FileProcessor {

  void process(Message<String> msg) throws Exception;

}
