package com.beanframework.imex.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.imex.domain.Imex;
import com.beanframework.media.domain.Media;

public interface ImexService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

	String[] importByListenerKeys(Set<String> keys);

	String[] importByMultipartFiles(MultipartFile[] files);

	String[] importByQuery(String query);

	void importByFile(File file) throws Exception;

	Media exportToCsv(Imex imex) throws Exception;
}
