package com.beanframework.media.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import com.beanframework.media.domain.Media;
import com.beanframework.media.validator.DeleteMediaValidator;
import com.beanframework.media.validator.SaveMediaValidator;

@Component
public class MediaFacadeImpl implements MediaFacade {

	@Autowired
	private MediaService mediaService;

	@Autowired
	private SaveMediaValidator saveMediaValidator;

	@Autowired
	private DeleteMediaValidator deleteMediaValidator;

	@Override
	public Media create() {
		return mediaService.create();
	}

	@Override
	public Media initDefaults(Media media) {
		return mediaService.initDefaults(media);
	}

	@Override
	public Media save(Media media, Errors bindingResult) {
		saveMediaValidator.validate(media, bindingResult);

		if (bindingResult.hasErrors()) {
			return media;
		}

		return mediaService.save(media);
	}

	@Override
	public void delete(UUID uuid, Errors bindingResult) {
		deleteMediaValidator.validate(uuid, bindingResult);

		if (bindingResult.hasErrors() == false) {
			mediaService.delete(uuid);
		}
	}

	@Override
	public void deleteAll() {
		mediaService.deleteAll();
	}

	@Override
	public Media findByUuid(UUID uuid) {
		return mediaService.findByUuid(uuid);
	}

	@Override
	public Media findById(String id) {
		return mediaService.findById(id);
	}

	@Override
	public boolean isIdExists(String id) {
		return mediaService.isIdExists(id);
	}

	@Override
	public Page<Media> page(Media media, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		return mediaService.page(media, pageRequest);
	}

	public MediaService getMediaService() {
		return mediaService;
	}

	public void setMediaService(MediaService mediaService) {
		this.mediaService = mediaService;
	}

	public SaveMediaValidator getSaveMediaValidator() {
		return saveMediaValidator;
	}

	public void setSaveMediaValidator(SaveMediaValidator saveMediaValidator) {
		this.saveMediaValidator = saveMediaValidator;
	}

	public DeleteMediaValidator getDeleteMediaValidator() {
		return deleteMediaValidator;
	}

	public void setDeleteMediaValidator(DeleteMediaValidator deleteMediaValidator) {
		this.deleteMediaValidator = deleteMediaValidator;
	}

}
