package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.converter.populator.EnumerationBasicPopulator;
import com.beanframework.core.converter.populator.EnumerationFullPopulator;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.enumuration.service.EnumerationService;
import com.beanframework.enumuration.specification.EnumerationSpecification;

@Component
public class EnumerationFacadeImpl implements EnumerationFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private EnumerationService enumerationService;

	@Autowired
	private EnumerationFullPopulator enumerationFullPopulator;

	@Autowired
	private EnumerationBasicPopulator enumerationBasicPopulator;
	
	@Override
	public EnumerationDto findOneByUuid(UUID uuid) throws Exception {
		Enumeration entity = modelService.findOneByUuid(uuid, Enumeration.class);

		return modelService.getDto(entity, EnumerationDto.class, new DtoConverterContext(enumerationFullPopulator));
	}

	@Override
	public EnumerationDto findOneProperties(Map<String, Object> properties) throws Exception {
		Enumeration entity = modelService.findOneByProperties(properties, Enumeration.class);

		return modelService.getDto(entity, EnumerationDto.class, new DtoConverterContext(enumerationFullPopulator));
	}

	@Override
	public EnumerationDto create(EnumerationDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public EnumerationDto update(EnumerationDto model) throws BusinessException {
		return save(model);
	}

	public EnumerationDto save(EnumerationDto dto) throws BusinessException {
		try {
			Enumeration entity = modelService.getEntity(dto, Enumeration.class);
			entity = modelService.saveEntity(entity, Enumeration.class);

			return modelService.getDto(entity, EnumerationDto.class, new DtoConverterContext(enumerationFullPopulator));
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Enumeration.class);
	}

	@Override
	public Page<EnumerationDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Enumeration> page = modelService.findPage(EnumerationSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Enumeration.class);

		List<EnumerationDto> dtos = modelService.getDto(page.getContent(), EnumerationDto.class, new DtoConverterContext(enumerationBasicPopulator));
		return new PageImpl<EnumerationDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Enumeration.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = enumerationService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Enumeration) {

				entityObject[0] = modelService.getDto(entityObject[0], EnumerationDto.class, new DtoConverterContext(enumerationFullPopulator));
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return enumerationService.findCountHistory(dataTableRequest);
	}

	@Override
	public EnumerationDto createDto() throws Exception {
		Enumeration enumeration = modelService.create(Enumeration.class);
		return modelService.getDto(enumeration, EnumerationDto.class, new DtoConverterContext(enumerationFullPopulator));
	}

}
