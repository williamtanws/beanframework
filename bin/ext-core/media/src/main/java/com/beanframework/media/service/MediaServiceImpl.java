package com.beanframework.media.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.media.MediaConstants;
import com.beanframework.media.converter.DtoMediaConverter;
import com.beanframework.media.converter.EntityMediaConverter;
import com.beanframework.media.domain.Media;
import com.beanframework.media.domain.MediaSpecification;
import com.beanframework.media.repository.MediaRepository;

@Service
public class MediaServiceImpl implements MediaService {

	@Autowired
	private MediaRepository mediaRepository;

	@Autowired
	private EntityMediaConverter entityMediaConverter;

	@Autowired
	private DtoMediaConverter dtoMediaConverter;

	@Override
	public Media create() {
		return initDefaults(new Media());
	}

	@Override
	public Media initDefaults(Media Media) {
		return Media;
	}

	@CacheEvict(value = { MediaConstants.Cache.MEDIA,  MediaConstants.Cache.MEDIAS}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public Media save(Media media) {

		media = entityMediaConverter.convert(media);
		media = mediaRepository.save(media);
		media = dtoMediaConverter.convert(media);

		return media;
	}

	@CacheEvict(value = { MediaConstants.Cache.MEDIA,  MediaConstants.Cache.MEDIAS}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void delete(UUID uuid) {
		mediaRepository.deleteById(uuid);
	}

	@CacheEvict(value = { MediaConstants.Cache.MEDIA,  MediaConstants.Cache.MEDIAS}, allEntries = true)
	@Transactional(readOnly = false)
	@Override
	public void deleteAll() {
		mediaRepository.deleteAll();
	}
	
	@Transactional(readOnly = true)
	@Override
	public Optional<Media> findEntityByUuid(UUID uuid) {
		return mediaRepository.findByUuid(uuid);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Media> findEntityById(String id) {
		return mediaRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Media findByUuid(UUID uuid) {
		Optional<Media> Media = mediaRepository.findByUuid(uuid);

		if (Media.isPresent()) {
			return dtoMediaConverter.convert(Media.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Media findById(String id) {
		Optional<Media> Media = mediaRepository.findById(id);

		if (Media.isPresent()) {
			return dtoMediaConverter.convert(Media.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public boolean isIdExists(String id) {
		return mediaRepository.isIdExists(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Media> page(Media media, Pageable pageable) {
		Page<Media> page = mediaRepository.findAll(MediaSpecification.findByCriteria(media), pageable);
		List<Media> content = dtoMediaConverter.convert(page.getContent());
		return new PageImpl<Media>(content, page.getPageable(), page.getTotalElements());
	}

	public MediaRepository getMediaRepository() {
		return mediaRepository;
	}

	public void setMediaRepository(MediaRepository mediaRepository) {
		this.mediaRepository = mediaRepository;
	}

	public EntityMediaConverter getEntityMediaConverter() {
		return entityMediaConverter;
	}

	public void setEntityMediaConverter(EntityMediaConverter entityMediaConverter) {
		this.entityMediaConverter = entityMediaConverter;
	}

	public DtoMediaConverter getDtoMediaConverter() {
		return dtoMediaConverter;
	}

	public void setDtoMediaConverter(DtoMediaConverter dtoMediaConverter) {
		this.dtoMediaConverter = dtoMediaConverter;
	}

	
}
