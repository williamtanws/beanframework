package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.cronjob.domain.Cronjob;

@Component
public class CronjobEntityConverter implements EntityConverter<CronjobDto, Cronjob> {

	@Autowired
	private ModelService modelService;

	@Override
	public Cronjob convert(CronjobDto source) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Cronjob prototype = modelService.findOneByUuid(source.getUuid(), Cronjob.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(Cronjob.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

	}

	private Cronjob convertToEntity(CronjobDto source, Cronjob prototype) {

		Date lastModifiedDate = new Date();

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getJobClass()), prototype.getJobClass()) == Boolean.FALSE) {
			prototype.setJobClass(StringUtils.stripToNull(source.getJobClass()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getJobGroup()), prototype.getJobGroup()) == Boolean.FALSE) {
			prototype.setJobGroup(StringUtils.stripToNull(source.getJobGroup()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getDescription()), prototype.getDescription()) == Boolean.FALSE) {
			prototype.setDescription(StringUtils.stripToNull(source.getDescription()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getCronExpression()), prototype.getCronExpression()) == Boolean.FALSE) {
			prototype.setCronExpression(StringUtils.stripToNull(source.getCronExpression()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (source.getStartup() == null) {
			if (prototype.getStartup() != null) {
				prototype.setStartup(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		} else {
			if (prototype.getStartup() == null || prototype.getStartup().equals(source.getStartup()) == Boolean.FALSE) {
				prototype.setStartup(source.getStartup());
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		}

		if (source.getJobTrigger() != prototype.getJobTrigger()) {
			prototype.setJobTrigger(source.getJobTrigger());
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (source.getJobTrigger() != null) {
			prototype.setLastTriggeredDate(new Date());
		}

		if (source.getTriggerStartDate() != null) {
			prototype.setTriggerStartDate(source.getTriggerStartDate());
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (source.getLastFinishExecutedDate() != null) {
			prototype.setLastFinishExecutedDate(source.getLastFinishExecutedDate());
			prototype.setLastModifiedDate(lastModifiedDate);

		}

		if (source.getLastStartExecutedDate() != null) {
			prototype.setLastStartExecutedDate(source.getLastStartExecutedDate());
			prototype.setLastModifiedDate(lastModifiedDate);

		}

		if (source.getLastTriggeredDate() != null) {
			prototype.setLastTriggeredDate(source.getLastTriggeredDate());
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (source.getStatus() != null) {
			prototype.setStatus(source.getStatus());
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		// Parameters
		Map<String, String> prototypeParameters = new HashMap<String, String>();
		if (source.getSelectedParameterKeys() != null && source.getSelectedParameterValues() != null && source.getSelectedParameterKeys().length == source.getSelectedParameterValues().length) {
			for (int i = 0; i < source.getSelectedParameterKeys().length; i++) {
				prototypeParameters.put(source.getSelectedParameterKeys()[i], source.getSelectedParameterValues()[i]);
			}
		}

		if (prototypeParameters.isEmpty()) {
			prototype.getParameters().clear();
		} else {

			for (Map.Entry<String, String> entry : prototypeParameters.entrySet()) {
				prototype.getParameters().put(entry.getKey(), entry.getValue());
			}
		}

		if (source.getParameters().equals(prototypeParameters) == false) {
			prototype.setLastModifiedDate(lastModifiedDate);
		}
		return prototype;
	}

}
