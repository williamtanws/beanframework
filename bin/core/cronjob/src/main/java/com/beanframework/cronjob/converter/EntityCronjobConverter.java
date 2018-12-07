package com.beanframework.cronjob.converter;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.service.CronjobService;

@Component
public class EntityCronjobConverter implements Converter<Cronjob, Cronjob> {

	@Autowired
	private CronjobService cronjobService;

	@Override
	public Cronjob convert(Cronjob source) {

		Optional<Cronjob> prototype = Optional.of(cronjobService.create());
		if (source.getUuid() != null) {
			Optional<Cronjob> exists = cronjobService.findEntityByUuid(source.getUuid());
			if(exists.isPresent()) {
				prototype = exists;
			}
		}
		else if (StringUtils.isNotEmpty(source.getId())) {
			Optional<Cronjob> exists = cronjobService.findEntityById(source.getId());
			if(exists.isPresent()) {
				prototype = exists;
			}
		}

		return convert(source, prototype.get());
	}

	private Cronjob convert(Cronjob source, Cronjob prototype) {

		prototype.setId(source.getId());
		prototype.setJobClass(source.getJobClass());
		prototype.setJobGroup(source.getJobGroup());
		prototype.setJobName(source.getJobName());
		prototype.setDescription(source.getDescription());
		prototype.setCronExpression(source.getCronExpression());
		prototype.setStartup(source.getStartup());
		prototype.setLastModifiedDate(new Date());

		prototype.getCronjobDatas().clear();
		for (int i = 0; i < source.getCronjobDatas().size(); i++) {
			source.getCronjobDatas().get(i).setCronjob(prototype);
		}
		prototype.getCronjobDatas().addAll(source.getCronjobDatas());

		return prototype;
	}

}
