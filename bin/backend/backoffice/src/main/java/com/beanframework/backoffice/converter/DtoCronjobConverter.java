package com.beanframework.backoffice.converter;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CronjobDataDto;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.cronjob.domain.Cronjob;

public class DtoCronjobConverter implements DtoConverter<Cronjob, CronjobDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(DtoCronjobConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public CronjobDto convert(Cronjob source) throws ConverterException {
		return convert(source, new CronjobDto());
	}

	public List<CronjobDto> convert(List<Cronjob> sources) throws ConverterException {
		List<CronjobDto> convertedList = new ArrayList<CronjobDto>();
		try {
			for (Cronjob source : sources) {
				convertedList.add(convert(source));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

	private CronjobDto convert(Cronjob source, CronjobDto prototype) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setJobClass(source.getJobClass());
		prototype.setJobGroup(source.getJobGroup());
		prototype.setName(source.getName());

		prototype.setDescription(source.getDescription());
		prototype.setCronExpression(source.getCronExpression());
		prototype.setStartup(source.getStartup());
		prototype.setStatus(source.getStatus());
		prototype.setResult(source.getResult());
		prototype.setMessage(source.getMessage());

		prototype.setJobTrigger(source.getJobTrigger());
		prototype.setTriggerStartDate(source.getTriggerStartDate());
		prototype.setLastTriggeredDate(source.getLastTriggeredDate());
		prototype.setLastStartExecutedDate(source.getLastStartExecutedDate());
		prototype.setLastFinishExecutedDate(source.getLastFinishExecutedDate());

		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		try {
			if (Hibernate.isInitialized(source.getCronjobDatas()))
				prototype.setCronjobDatas(modelService.getDto(source.getCronjobDatas(), CronjobDataDto.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
