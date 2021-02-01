package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.CronjobDataDto;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobData;

public class CronjobPopulator extends AbstractPopulator<Cronjob, CronjobDto> implements Populator<Cronjob, CronjobDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CronjobPopulator.class);

	@Override
	public void populate(Cronjob source, CronjobDto target) throws PopulatorException {
		try {
			populateGeneric(source, target);
			target.setJobClass(source.getJobClass());
			target.setJobGroup(source.getJobGroup());
			target.setName(source.getName());
			target.setDescription(source.getDescription());
			target.setCronExpression(source.getCronExpression());
			target.setStartup(source.getStartup());
			target.setStatus(source.getStatus());
			target.setResult(source.getResult());
			target.setMessage(source.getMessage());
			target.setJobTrigger(source.getJobTrigger());
			target.setTriggerStartDate(source.getTriggerStartDate());
			target.setLastTriggeredDate(source.getLastTriggeredDate());
			target.setLastStartExecutedDate(source.getLastStartExecutedDate());
			target.setLastFinishExecutedDate(source.getLastFinishExecutedDate());

			for (CronjobData cronjobData : source.getCronjobDatas()) {
				target.getCronjobDatas().add(populateCronjobData(cronjobData));
			}

			if (target.getCronjobDatas() != null)
				Collections.sort(target.getCronjobDatas(), new Comparator<CronjobDataDto>() {
					@Override
					public int compare(CronjobDataDto o1, CronjobDataDto o2) {
						if (o1.getCreatedDate() == null)
							return o2.getCreatedDate() == null ? 0 : 1;

						if (o2.getCreatedDate() == null)
							return -1;

						return o2.getCreatedDate().compareTo(o1.getCreatedDate());
					}
				});
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public CronjobDataDto populateCronjobData(CronjobData source) throws PopulatorException {
		try {
			CronjobDataDto target = new CronjobDataDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));
			target.setName(source.getName());
			target.setValue(source.getValue());

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

}
