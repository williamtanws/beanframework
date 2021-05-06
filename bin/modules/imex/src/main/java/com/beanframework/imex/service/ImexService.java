package com.beanframework.imex.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.beanframework.imex.domain.Imex;

public interface ImexService {

	String[] importByListenerKeys(Set<String> keys);

	String[] importByMultipartFiles(MultipartFile[] files);

	String[] importByQuery(String importName, String query);

	void importByFile(File file) throws Exception;

	void importExportMedia(Imex imex) throws Exception;

	String[] importByKeysAndReader(Set<String> keys, List<String> locations);
}
