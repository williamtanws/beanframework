package com.beanframework.console.service;

import java.io.File;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public interface PlatformService {

	String[] update(Set<String> keys);
	
	String[] importFiles(MultipartFile[] files);
	
	void importFile(File file) throws Exception;
}
