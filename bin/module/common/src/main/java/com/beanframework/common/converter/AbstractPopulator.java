package com.beanframework.common.converter;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.common.service.ModelService;

public abstract class AbstractPopulator<T extends GenericEntity, E extends GenericDto> {

	@Autowired
	protected ModelService modelService;

	protected void convertCommonProperties(T source, E prototype) throws PopulatorException {
		try {			
			prototype.setUuid(source.getUuid());
			prototype.setId(source.getId());
			prototype.setCreatedDate(source.getCreatedDate());
			prototype.setLastModifiedDate(source.getLastModifiedDate());
			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), AuditorDto.class));
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}
}
