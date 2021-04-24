package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.MediaDto;
import com.beanframework.core.specification.MediaSpecification;
import com.beanframework.media.domain.Media;
import com.beanframework.media.service.MediaService;

@Component
public class MediaFacadeImpl extends AbstractFacade<Media, MediaDto> implements MediaFacade {
	
	private static final Class<Media> entityClass = Media.class;
	private static final Class<MediaDto> dtoClass = MediaDto.class;

	@Autowired
	private MediaService mediaService;

	@Override
	public MediaDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public MediaDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
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
			Media entity = modelService.getEntity(dto, entityClass);
			entity = modelService.saveEntity(entity);

			if(dto.getFile() != null) {
				mediaService.storeMultipartFile(entity, dto.getFile());
			}
			
			return modelService.getDto(entity, dtoClass);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		try {
			delete(uuid, entityClass);
			mediaService.removeFile(modelService.findOneByUuid(uuid, entityClass));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<MediaDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, MediaSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
	}

	@Override
	public int count() throws Exception {
		return count(entityClass);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {
		return findHistory(dataTableRequest, entityClass, dtoClass);
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return findCountHistory(dataTableRequest, entityClass);
	}

	@Override
	public MediaDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}
}
