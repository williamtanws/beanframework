package com.beanframework.core.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.CsvUtils;
import com.beanframework.cronjob.service.QuartzManager;
import com.beanframework.media.domain.Media;
import com.beanframework.media.service.MediaService;

@Component
@DisallowConcurrentExecution
public class CreateNativeQueryJob implements Job {

	protected static final Logger LOGGER = LoggerFactory.getLogger(CreateNativeQueryJob.class);

	public static final String QUERY = "query";

	public static final String MEDIA_FOLDER = "\\createnativequery";

	private SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");

	@Autowired
	private ModelService modelService;

	@Autowired
	private MediaService mediaService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {

		try {
			String query = (String) context.getMergedJobDataMap().get(QUERY);

			Query nativeQuery = modelService.createNativeQuery(query);

			if (query.toLowerCase().startsWith("select")) {

				List<?> resultList = nativeQuery.getResultList();

				StringBuilder resultBuilder = CsvUtils.List2Csv(resultList);

				String name = "createnativequery_" + format.format(new Date());

				Media media = modelService.create(Media.class);
				media.setId(name);
				media.setTitle(name);
				media.setFileName(name + ".csv");
				media.setFileType("text/csv");
				media.setFolder(MEDIA_FOLDER);
				media = modelService.saveEntity(media);
				media = mediaService.storeData(media, resultBuilder.toString());

				context.setResult("Created media: " + media.getId());
			} else {
				int count = nativeQuery.executeUpdate();
				context.setResult("Updated " + count + " entity(s)");
			}

			context.put(QuartzManager.CRONJOB_NOTIFICATION, Boolean.TRUE);

		} catch (Exception e) {
			context.put(QuartzManager.CRONJOB_NOTIFICATION, Boolean.TRUE);
			LOGGER.error(e.getMessage(), e);
			throw new JobExecutionException(e.getMessage(), e);
		}
	}

}
