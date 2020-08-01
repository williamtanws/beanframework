package com.beanframework.core.converter.populator;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.common.service.ModelService;

public abstract class AbstractPopulator<T extends GenericEntity, E extends GenericDto> {

	@Autowired
	protected ModelService modelService;
	
	@Autowired
	private AuditorFullPopulator auditorFullPopulator;

	protected void convertCommonProperties(T source, E target) throws PopulatorException {
		try {			
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(modelService.getDto(source.getCreatedBy(), AuditorDto.class, new DtoConverterContext(auditorFullPopulator)));
			target.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), AuditorDto.class, new DtoConverterContext(auditorFullPopulator)));
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}
}
