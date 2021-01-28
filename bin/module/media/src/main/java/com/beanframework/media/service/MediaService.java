package com.beanframework.media.service;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.beanframework.media.domain.Media;

public interface MediaService {

	Media storeMultipartFile(Media media, MultipartFile file) throws Exception;

	Media storeFile(Media media, File file) throws Exception;

	Media storeData(Media media, String data) throws Exception;
	
	void removeFile(Media media);

}
