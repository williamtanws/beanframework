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
import com.beanframework.core.data.ImexDto;
import com.beanframework.imex.domain.Imex;
import com.beanframework.imex.service.ImexService;
import com.beanframework.imex.specification.ImexSpecification;

@Component
public class ImexFacadeImpl implements ImexFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private ImexService imexService;

	@Override
	public ImexDto findOneByUuid(UUID uuid) throws Exception {
		Imex entity = modelService.findOneByUuid(uuid, Imex.class);
		return modelService.getDto(entity, ImexDto.class, new DtoConverterContext(ConvertRelationType.ALL));
	}

	@Override
	public ImexDto findOneProperties(Map<String, Object> properties) throws Exception {
		Imex entity = modelService.findOneByProperties(properties, Imex.class);
		return modelService.getDto(entity, ImexDto.class);
	}

	@Override
	public ImexDto create(ImexDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public ImexDto update(ImexDto model) throws BusinessException {
		return save(model);
	}

	public ImexDto save(ImexDto dto) throws BusinessException {
		try {
			Imex entity = modelService.getEntity(dto, Imex.class);
			entity = modelService.saveEntity(entity, Imex.class);
			
			return modelService.getDto(entity, ImexDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Imex.class);
	}

	@Override
	public Page<ImexDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		Page<Imex> page = modelService.findPage(ImexSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Imex.class);

		List<ImexDto> dtos = modelService.getDto(page.getContent(), ImexDto.class, new DtoConverterContext(ConvertRelationType.BASIC));
		return new PageImpl<ImexDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public int count() throws Exception {
		return modelService.countAll(Imex.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<Object[]> revisions = imexService.findHistory(dataTableRequest);
		for (int i = 0; i < revisions.size(); i++) {
			Object[] entityObject = revisions.get(i);
			if (entityObject[0] instanceof Imex) {

				entityObject[0] = modelService.getDto(entityObject[0], ImexDto.class);
			}
			revisions.set(i, entityObject);
		}

		return revisions;
	}

	@Override
	public int countHistory(DataTableRequest dataTableRequest) throws Exception {
		return imexService.findCountHistory(dataTableRequest);
	}

	@Override
	public ImexDto createDto() throws Exception {
		Imex Imex = modelService.create(Imex.class);
		return modelService.getDto(Imex, ImexDto.class, new DtoConverterContext(ConvertRelationType.ALL));
	}
}
