package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.ConvertRelationType;
import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.MediaDto;
import com.beanframework.media.domain.Media;
import com.beanframework.media.service.MediaService;
import com.beanframework.media.specification.MediaSpecification;

@Component
public class MediaFacadeImpl implements MediaFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private MediaService mediaService;

	@Override
	public MediaDto findOneByUuid(UUID uuid) throws Exception {
		Media entity = modelService.findOneEntityByUuid(uuid, Media.class);
		return modelService.getDto(entity, MediaDto.class, new DtoConverterContext(ConvertRelationType.ALL));
	}

	@Override
	public MediaDto findOneProperties(Map<String, Object> properties) throws Exception {
		Media entity = modelService.findOneEntityByProperties(properties, Media.class);
		return modelService.getDto(entity, MediaDto.class);
	}

	@Override
	public MediaDto create(MediaDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public MediaDto update(MediaDto model) throws BusinessException {
		return save(model);
	}

	public MediaDto save(MediaDto dto) throws BusinessException {
		try {
			Media entity = modelService.getEntity(dto, Media.class);
			entity = modelService.saveEntity(entity, Media.class);

			return modelService.getDto(entity, MediaDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Media.class);
	}

	@Override
	public Page<MediaDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Media> page = modelService.findEntityPage(MediaSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Media.class);

		List<MediaDto> dtos = modelService.getDto(page.getContent(), MediaDto.class, new DtoConverterContext(ConvertRelationType.RELATION));
		return new PageImpl<MediaDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.count(Media.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = mediaService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Media) {

				entityObject[0] = modelService.getDto(entityObject[0], MediaDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return mediaService.findCountHistory(dataTableRequest);
	}

	@Override
	public MediaDto createDto() throws Exception {

		return modelService.getDto(modelService.create(Media.class), MediaDto.class);
	}
}
