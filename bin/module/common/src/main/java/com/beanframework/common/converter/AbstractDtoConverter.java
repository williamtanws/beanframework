package com.beanframework.common.converter;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.common.service.ModelService;

public abstract class AbstractDtoConverter<T extends GenericEntity, E extends GenericDto> {

	@Autowired
	protected ModelService modelService;

	protected void populate(T source, E prototype, DtoConverterContext context) throws PopulatorException {
		for (Populator<?, ?> populatorMapping : context.getPopulatorMappings()) {
			@SuppressWarnings("unchecked")
			Populator<T, E> populator = (Populator<T, E>) populatorMapping;
			populator.populate(source, prototype);
		}
	}
}
