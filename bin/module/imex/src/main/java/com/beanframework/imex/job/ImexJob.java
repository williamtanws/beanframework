package com.beanframework.imex.job;

import java.io.File;
import java.util.UUID;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.service.ModelService;
import com.beanframework.imex.ImexType;
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

		String uuid = (String) context.getMergedJobDataMap().get("uuid");

		Imex model;
		try {
			model = modelService.findOneByUuid(UUID.fromString(uuid), Imex.class);
			
			if (model.getType() == ImexType.EXPORT) {
				imexService.exportToCsv(model);
			} else if (model.getType() == ImexType.IMPORT) {
				imexService.importByFile(new File(model.getDirectory()));
			}

			context.setResult("Success");
		} catch (Exception e) {
			context.setResult("Failed: " + e.getMessage());
			LOGGER.error(e.getMessage(), e);
			throw new JobExecutionException(e.getMessage(), e);
		}

	}
}
