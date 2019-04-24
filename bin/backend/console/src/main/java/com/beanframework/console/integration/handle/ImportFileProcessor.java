package com.beanframework.console.integration.handle;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.beanframework.console.service.PlatformService;
import com.beanframework.core.data.FileProcessor;

@Component
public class ImportFileProcessor implements FileProcessor {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ImportFileProcessor.class);

	private static final String MSG = "%s received. Path: %s";

	@Autowired
	private PlatformService platformService;

	@Override
	public void process(Message<String> msg) throws Exception {
		String fileName = (String) msg.getHeaders().get(FileHeaders.FILENAME);
		File fileOriginalFile = (File) msg.getHeaders().get(FileHeaders.ORIGINAL_FILE);

		LOGGER.info(String.format(MSG, fileName, fileOriginalFile.getAbsolutePath()));

		platformService.importFile(fileOriginalFile);
	}
}
