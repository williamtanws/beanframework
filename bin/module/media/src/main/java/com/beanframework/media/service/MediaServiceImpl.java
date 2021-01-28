package com.beanframework.media.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.service.ModelService;
import com.beanframework.media.MediaConstants;
import com.beanframework.media.domain.Media;

@Service
public class MediaServiceImpl implements MediaService {

	protected static final Logger LOGGER = LoggerFactory.getLogger(MediaServiceImpl.class);

	@Autowired
	private ModelService modelService;

	@Value(MediaConstants.MEDIA_LOCATION)
	public String MEDIA_LOCATION;

	@Value(MediaConstants.MEDIA_URL)
	private String MEDIA_URL;

	@Override
	public Media storeMultipartFile(Media media, MultipartFile file) throws Exception {
		File mediaFolder;
		if (StringUtils.isBlank(media.getFolder())) {
			mediaFolder = new File(MEDIA_LOCATION + File.separator + media.getUuid().toString());
		} else {
			mediaFolder = new File(MEDIA_LOCATION + File.separator + media.getFolder() + File.separator + media.getUuid().toString());
		}

		FileUtils.forceMkdir(mediaFolder);

		File original = new File(mediaFolder.getAbsolutePath(), media.getFileName());
		file.transferTo(original);

		media.setFileSize(original.length());
		media.setUrl(MEDIA_URL + "/" + media.getUuid() + "/" + media.getFileName());

		return modelService.saveEntity(media);
	}

	@Override
	public Media storeFile(Media media, File srcFile) throws Exception {
		File mediaFolder;
		if (StringUtils.isBlank(media.getFolder())) {
			mediaFolder = new File(MEDIA_LOCATION + File.separator + media.getUuid().toString());
		} else {
			mediaFolder = new File(MEDIA_LOCATION + File.separator + media.getFolder() + File.separator + media.getUuid().toString());
		}

		FileUtils.forceMkdir(mediaFolder);

		File destFile = new File(mediaFolder.getAbsolutePath(), media.getFileName());
		FileUtils.copyFile(srcFile.getAbsoluteFile(), destFile.getAbsoluteFile());

		media.setFileSize(destFile.length());
		media.setUrl(MEDIA_URL + "/" + media.getUuid() + "/" + media.getFileName());

		return modelService.saveEntity(media);
	}

	@Override
	public Media storeData(Media media, String data) throws Exception {
		File mediaFolder;
		if (StringUtils.isBlank(media.getFolder())) {
			mediaFolder = new File(MEDIA_LOCATION + File.separator + media.getUuid().toString());
		} else {
			mediaFolder = new File(MEDIA_LOCATION + File.separator + media.getFolder() + File.separator + media.getUuid().toString());
		}

		FileUtils.forceMkdir(mediaFolder);

		File destFile = new File(mediaFolder.getAbsolutePath(), media.getFileName());
		FileUtils.write(destFile.getAbsoluteFile(), data, "UTF-8", false);

		media.setFileSize(destFile.length());
		media.setUrl(MEDIA_URL + "/" + media.getUuid() + "/" + media.getFileName());

		return modelService.saveEntity(media);
	}

	@Override
	public void removeFile(Media media) {
		File mediaFolder = new File(MEDIA_LOCATION + File.separator + media.getFolder() + File.separator + media.getUuid().toString());
		try {
			if (mediaFolder.exists()) {
				FileUtils.deleteDirectory(mediaFolder);
			}
		} catch (IOException e) {
			LOGGER.error(e.toString(), e);
		}
	}
}
