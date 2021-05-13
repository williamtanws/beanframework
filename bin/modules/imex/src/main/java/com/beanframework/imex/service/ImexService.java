package com.beanframework.imex.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import org.springframework.web.multipart.MultipartFile;
import com.beanframework.imex.domain.Imex;

public interface ImexService {

  String[] importByMultipartFiles(MultipartFile[] files) throws IOException;

  String[] importByQuery(String importName, String query);

  String[] importByFileSystem(File file) throws Exception;

  String[] importByClasspathLocations(List<String> locations);

  String[] importByFoldersByClasspathLocations(TreeMap<String, Set<String>> locationAndFolders);

  void importExportMedia(Imex imex) throws Exception;
}
