package com.beanframework.core.converter.populator.history;

import java.util.Collections;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.data.CronjobDataDto;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.cronjob.domain.Cronjob;

@Component
public class CronjobHistoryPopulator extends AbstractPopulator<Cronjob, CronjobDto> implements Populator<Cronjob, CronjobDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CronjobHistoryPopulator.class);

	@Autowired
	private CronjobDataHistoryPopulator cronjobDataHistoryPopulator;

	@Override
	public void populate(Cronjob source, CronjobDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
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
			target.setCronjobDatas(modelService.getDto(source.getCronjobDatas(), CronjobDataDto.class, new DtoConverterContext(cronjobDataHistoryPopulator)));
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

}
