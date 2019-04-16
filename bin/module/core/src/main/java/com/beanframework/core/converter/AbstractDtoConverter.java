package com.beanframework.core.converter;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.AuditorDto;
import com.beanframework.core.data.GenericDto;

public abstract class AbstractDtoConverter<T extends GenericEntity, E extends GenericDto> {

	@Autowired
	protected ModelService modelService;

	@SuppressWarnings("unchecked")
	protected void convertCommonProperties(T source, E prototype, DtoConverterContext context) throws ConverterException {

		try {			
			source = (T) Hibernate.unproxy(source);
			prototype.setUuid(source.getUuid());
			prototype.setId(source.getId());
			prototype.setCreatedDate(source.getCreatedDate());
			prototype.setLastModifiedDate(source.getLastModifiedDate());
			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), AuditorDto.class));
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

}
