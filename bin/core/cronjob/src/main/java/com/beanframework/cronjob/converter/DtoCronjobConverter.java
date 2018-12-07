package com.beanframework.cronjob.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.service.CronjobService;

@Component
public class DtoCronjobConverter implements Converter<Cronjob, Cronjob> {

	@Autowired
	private CronjobService cronjobService;
	
	@Autowired
	private DtoCronjobDataConverter dtoCronjobDataConverter;

	@Override
	public Cronjob convert(Cronjob source) {
		return convert(source, cronjobService.create());
	}

	public List<Cronjob> convert(List<Cronjob> sources) {
		List<Cronjob> convertedList = new ArrayList<Cronjob>();
		for (Cronjob source : sources) {
			convertedList.add(convert(source));
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
		
		prototype.setCronjobDatas(dtoCronjobDataConverter.convert(source.getCronjobDatas()));

		return prototype;
	}

}
