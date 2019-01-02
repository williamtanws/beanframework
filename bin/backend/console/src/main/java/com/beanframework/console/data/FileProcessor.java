package com.beanframework.console.data;

import org.springframework.messaging.Message;

public interface FileProcessor {

	void process(Message<String> msg) throws Exception;

}
