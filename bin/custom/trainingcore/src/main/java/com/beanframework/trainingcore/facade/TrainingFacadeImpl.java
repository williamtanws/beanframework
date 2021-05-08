package com.beanframework.trainingcore.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.facade.AbstractFacade;
import com.beanframework.training.domain.Training;
import com.beanframework.trainingcore.data.TrainingDto;
import com.beanframework.trainingcore.specification.TrainingSpecification;

@Component
public class TrainingFacadeImpl extends AbstractFacade<Training, TrainingDto> implements TrainingFacade {
	
	private static final Class<Training> entityClass = Training.class;
	private static final Class<TrainingDto> dtoClass = TrainingDto.class;

	@Override
	public TrainingDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}

	@Override
	public TrainingDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public TrainingDto create(TrainingDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public TrainingDto update(TrainingDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<TrainingDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, TrainingSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
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
	public TrainingDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}
}
