package com.beanframework.media.service;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.media.domain.Media;

public interface MediaService {

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int findCountHistory(DataTableRequest dataTableRequest) throws Exception;

	Media storeMultipartFile(Media media, MultipartFile file) throws Exception;

	Media storeFile(Media media, File file) throws Exception;

	Media storeData(Media media, String data) throws Exception;

}
