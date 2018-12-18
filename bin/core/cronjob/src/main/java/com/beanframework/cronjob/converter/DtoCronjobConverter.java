package com.beanframework.cronjob.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.DtoConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.cronjob.domain.Cronjob;

public class DtoCronjobConverter implements DtoConverter<Cronjob, Cronjob> {

	@Autowired
	private DtoCronjobDataConverter dtoCronjobDataConverter;

	@Override
	public Cronjob convert(Cronjob source) throws ConverterException {
		return convert(source, new Cronjob());
	}

	public List<Cronjob> convert(List<Cronjob> sources) throws ConverterException {
		List<Cronjob> convertedList = new ArrayList<Cronjob>();
		try {
			for (Cronjob source : sources) {
				convertedList.add(convert(source));
			}
		} catch (ConverterException e) {
			throw new ConverterException(e.getMessage(), this);
		}
		return convertedList;
	}

	private Cronjob convert(Cronjob source, Cronjob prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setJobClass(source.getJobClass());
		prototype.setJobGroup(source.getJobGroup());
		prototype.setJobName(source.getJobName());

		prototype.setDescription(source.getDescription());
		prototype.setCronExpression(source.getCronExpression());
		prototype.setStartup(source.isStartup());
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

		prototype.setCronjobDatas(dtoCronjobDataConverter.convert(source.getCronjobDatas()));

		return prototype;
	}

}
