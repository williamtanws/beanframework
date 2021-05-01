package com.beanframework.core.job;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.beanframework.cronjob.service.QuartzManager;

@Component
@DisallowConcurrentExecution
public class DumpDbJob implements Job {

	protected static final Logger LOGGER = LoggerFactory.getLogger(DumpDbJob.class);

	@Value("${database.backup.dir}")
	private String DATABASE_BACKUP_DIR;

	@Value("${database.backup.executecommand}")
	private String DB_EXECUTE_COMMAND;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			LOGGER.info("Executing " + DumpDbJob.class.getName());

			LOGGER.info("Backup Started at " + new Date());

			Date backupDate = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			String backupDateStr = format.format(backupDate);
			//String dbNameList = "client_1 client_2";

			String fileName = "mysqldump"; // default file name
			File f1 = new File(DATABASE_BACKUP_DIR);
			f1.mkdir(); // create folder if not exist

			String saveFileName = fileName + "_" + backupDateStr + ".sql";
			String savePath = f1.getAbsolutePath() + File.separator + saveFileName;

			String executeCmd = DB_EXECUTE_COMMAND + " " + savePath;

			Process runtimeProcess = null;
			try {
				runtimeProcess = Runtime.getRuntime().exec(executeCmd);
			} catch (IOException e) {
				e.printStackTrace();
			}
			int processComplete = 0;
			try {
				processComplete = runtimeProcess.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (processComplete == 0) {
				String message = "Backup Complete at " + new Date();
				LOGGER.info(message);
				context.setResult(message);
			} else {
				String message = "Backup Failure";
				LOGGER.info(message);
				context.setResult(message);
			}
			
			context.put(QuartzManager.CRONJOB_NOTIFICATION, Boolean.TRUE);
		} catch (Exception e) {
			context.put(QuartzManager.CRONJOB_NOTIFICATION, Boolean.TRUE);
			LOGGER.error(e.getMessage(), e);
			throw new JobExecutionException(e.getMessage(), e);
		}
	}
}
