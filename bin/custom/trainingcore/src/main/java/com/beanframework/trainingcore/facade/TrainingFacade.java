package com.beanframework.trainingcore.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.trainingcore.data.TrainingDto;

public interface TrainingFacade {

	TrainingDto findOneByUuid(UUID uuid) throws Exception;

	TrainingDto findOneProperties(Map<String, Object> properties) throws Exception;

	TrainingDto create(TrainingDto model) throws BusinessException;

	TrainingDto update(TrainingDto model) throws BusinessException;

	void delete(UUID uuid) throws BusinessException;

	Page<TrainingDto> findPage(DataTableRequest dataTableRequest) throws Exception;

	int count() throws Exception;

	List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

	int countHistory(DataTableRequest dataTableRequest) throws Exception;

	TrainingDto createDto() throws Exception;
}
