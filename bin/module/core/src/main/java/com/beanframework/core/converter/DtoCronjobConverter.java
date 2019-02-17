package com.beanframework.core.converter;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.data.AuditorDto;
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
	public CronjobDto convert(Cronjob source, DtoConverterContext context) throws ConverterException {
		return convert(source, new CronjobDto(), context);
	}

	public List<CronjobDto> convert(List<Cronjob> sources, DtoConverterContext context) throws ConverterException {
		List<CronjobDto> convertedList = new ArrayList<CronjobDto>();
		try {
			for (Cronjob source : sources) {
				convertedList.add(convert(source, context));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), e);
		}
		return convertedList;
	}

	private CronjobDto convert(Cronjob source, CronjobDto prototype, DtoConverterContext context) throws ConverterException {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

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

		try {
			prototype.setCreatedBy(modelService.getDto(source.getCreatedBy(), AuditorDto.class));
			prototype.setLastModifiedBy(modelService.getDto(source.getLastModifiedBy(), AuditorDto.class));

			prototype.setCronjobDatas(modelService.getDto(source.getCronjobDatas(), CronjobDataDto.class));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw new ConverterException(e.getMessage(), e);
		}
		return prototype;
	}

}
