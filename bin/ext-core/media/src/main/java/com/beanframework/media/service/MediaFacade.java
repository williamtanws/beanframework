package com.beanframework.media.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.Errors;

import com.beanframework.media.domain.Media;

public interface MediaFacade {

	Media create();

	Media initDefaults(Media media);

	Media save(Media media, Errors bindingResult);

	void delete(UUID uuid, Errors bindingResult);

	void deleteAll();

	Media findByUuid(UUID uuid);

	Media findById(String id);
	
	boolean isIdExists(String id);

	Page<Media> page(Media media, int page, int size, Direction direction, String... properties);
}
