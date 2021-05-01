package com.beanframework.core.job;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.service.ModelService;
import com.beanframework.cronjob.service.QuartzManager;
import com.beanframework.imex.domain.Imex;
import com.beanframework.imex.service.ImexService;

@Component
@DisallowConcurrentExecution
public class ImexJob implements Job {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ImexJob.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private ImexService imexService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		String uuid = (String) context.get("uuid");

		try {
			if (StringUtils.isNotBlank(uuid)) {
				Imex model = modelService.findOneByUuid(UUID.fromString(uuid), Imex.class);

				if (model != null) {
					imexService.importExportMedia(model);
					context.setResult("Generated 1 imex");
				} else {
					context.setResult("UUID not found in database");
				}

			} else {
				List<Imex> models = modelService.findAll(Imex.class);
				for (Imex model : models) {
					imexService.importExportMedia(model);
				}

				context.setResult("Executed " + models.size() + " imex");
			}
			context.put(QuartzManager.CRONJOB_NOTIFICATION, Boolean.TRUE);

		} catch (Exception e) {
			context.put(QuartzManager.CRONJOB_NOTIFICATION, Boolean.TRUE);
			LOGGER.error(e.getMessage(), e);
			throw new JobExecutionException(e.getMessage(), e);
		}
	}
}
