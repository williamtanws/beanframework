package com.beanframework.media.service;

import java.io.File;
import java.util.List;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;
import com.beanframework.media.domain.Media;

public interface MediaService {

  File getFile(Media media) throws Exception;

  List<File> getFiles(UUID... uuids) throws Exception;

  List<UUID> saveMedia(String folder, MultipartFile[] file) throws Exception;

  void removeMedia(UUID uuid) throws Exception;

  Media storeMultipartFile(Media media, MultipartFile file) throws Exception;

  Media storeFile(Media media, File file) throws Exception;

  Media storeData(Media media, String data) throws Exception;

  void removeFile(Media media);

}
