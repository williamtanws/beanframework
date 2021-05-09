package com.beanframework.core.integration.handle;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.beanframework.imex.service.ImexService;

@Component
public class ImportFileProcessor implements FileProcessor {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ImportFileProcessor.class);

	private static final String MSG = "%s received. Path: %s";

	@Autowired
	private ImexService platformService;

	@Autowired
	public File inboundProcessedDirectory;

	@Override
	public void process(Message<String> msg) throws Exception {
		String fileName = (String) msg.getHeaders().get(FileHeaders.FILENAME);
		File fileOriginalFile = (File) msg.getHeaders().get(FileHeaders.ORIGINAL_FILE);

		LOGGER.info(String.format(MSG, fileName, fileOriginalFile.getAbsolutePath()));

		String[] result = platformService.importByFileSystem(fileOriginalFile);
		// Result
		File resultFile = new File(inboundProcessedDirectory.getPath() + File.separator + fileOriginalFile.getName() + ".result.txt");
		FileUtils.write(resultFile, result[0]+result[1], StandardCharsets.UTF_8.name());
	}
}
