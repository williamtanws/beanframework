package com.beanframework.media.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.beanframework.media.domain.Media;

public interface MediaService {

	Media create();

	Media initDefaults(Media media);

	Media save(Media media);

	void delete(UUID uuid);

	void deleteAll();

	Optional<Media> findEntityByUuid(UUID uuid);

	Optional<Media> findEntityById(String id);

	Media findByUuid(UUID uuid);

	Media findById(String id);
	
	boolean isIdExists(String id);

	Page<Media> page(Media media, Pageable pageable);

}
