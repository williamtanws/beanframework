package com.beanframework.core.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.cronjob.domain.Cronjob;

public class EntityCronjobConverter implements EntityConverter<CronjobDto, Cronjob> {

	@Autowired
	private ModelService modelService;

	@Override
	public Cronjob convert(CronjobDto source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getUuid() != null) {

				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Cronjob.UUID, source.getUuid());

				Cronjob prototype = modelService.findByProperties(properties, Cronjob.class);

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

		if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == false) {
			prototype.setId(StringUtils.stripToNull(source.getId()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getJobClass()), prototype.getJobClass()) == false) {
			prototype.setJobClass(StringUtils.stripToNull(source.getJobClass()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getJobGroup()), prototype.getJobGroup()) == false) {
			prototype.setJobGroup(StringUtils.stripToNull(source.getJobGroup()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == false) {
			prototype.setName(StringUtils.stripToNull(source.getName()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getDescription()), prototype.getDescription()) == false) {
			prototype.setDescription(StringUtils.stripToNull(source.getDescription()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (StringUtils.equals(StringUtils.stripToNull(source.getCronExpression()), prototype.getCronExpression()) == false) {
			prototype.setCronExpression(StringUtils.stripToNull(source.getCronExpression()));
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (source.getStartup() == null) {
			if (prototype.getStartup() != null) {
				prototype.setStartup(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		} else {
			if (prototype.getStartup() == null || prototype.getStartup().equals(source.getStartup()) == false) {
				prototype.setStartup(source.getStartup());
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		}

		if (source.getStatus() != null) {
			prototype.setStatus(source.getStatus());
			prototype.setLastModifiedDate(lastModifiedDate);
		}

		if (source.getTriggerStartDate() != null && prototype.getTriggerStartDate() != null && source.getTriggerStartDate().compareTo(prototype.getTriggerStartDate()) != 0) {
			prototype.setTriggerStartDate(source.getTriggerStartDate());
			prototype.setLastModifiedDate(lastModifiedDate);

		} else if (source.getTriggerStartDate() == null) {
			if (prototype.getTriggerStartDate() != null) {
				prototype.setTriggerStartDate(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		}

		if (source.getLastFinishExecutedDate() != null && prototype.getLastFinishExecutedDate() != null && source.getLastFinishExecutedDate().compareTo(prototype.getLastFinishExecutedDate()) != 0) {
			prototype.setLastFinishExecutedDate(source.getLastFinishExecutedDate());
			prototype.setLastModifiedDate(lastModifiedDate);

		} else if (source.getLastFinishExecutedDate() == null) {
			if (prototype.getLastFinishExecutedDate() != null) {
				prototype.setLastFinishExecutedDate(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		}

		if (source.getLastStartExecutedDate() != null && prototype.getLastStartExecutedDate() != null && source.getLastStartExecutedDate().compareTo(prototype.getLastStartExecutedDate()) != 0) {
			prototype.setLastStartExecutedDate(source.getLastStartExecutedDate());
			prototype.setLastModifiedDate(lastModifiedDate);

		} else if (source.getLastStartExecutedDate() == null) {
			if (prototype.getLastStartExecutedDate() != null) {
				prototype.setLastStartExecutedDate(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		}

		if (source.getLastTriggeredDate() != null && prototype.getLastTriggeredDate() != null && source.getLastTriggeredDate().compareTo(prototype.getLastTriggeredDate()) != 0) {
			prototype.setLastTriggeredDate(source.getLastTriggeredDate());
			prototype.setLastModifiedDate(lastModifiedDate);

		} else if (source.getLastTriggeredDate() == null) {
			if (prototype.getLastTriggeredDate() != null) {
				prototype.setLastTriggeredDate(null);
				prototype.setLastModifiedDate(lastModifiedDate);
			}
		}

		return prototype;
	}

}
