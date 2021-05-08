package com.beanframework.imex.service;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.web.multipart.MultipartFile;

import com.beanframework.imex.domain.Imex;

public interface ImexService {

	String[] importByFolders(List<String> folders);

	String[] importByMultipartFiles(MultipartFile[] files);

	String[] importByQuery(String importName, String query);

	void importByFile(File file) throws Exception;

	void importExportMedia(Imex imex) throws Exception;

	String[] importByFoldersByLocations(List<String> folders, List<String> locations);

	String[] importByFoldersByLocations(TreeMap<String, Set<String>> locationAndFolders);
}
