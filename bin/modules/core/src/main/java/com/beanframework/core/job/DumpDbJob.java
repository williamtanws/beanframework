package com.beanframework.core.job;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
public class DumpDbJob implements Job {

	protected static final Logger LOGGER = LoggerFactory.getLogger(DumpDbJob.class);

	@Value("${database.backup.dir}")
	private String DATABASE_BACKUP_DIR;

	@Value("${datasource.platform.database}")
	private String DB;

	@Value("${datasource.platform.username}")
	private String DB_USER_NAME;

	@Value("${datasource.platform.password}")
	private String DB_USER_PASSWORD;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		LOGGER.info("Executing " + DumpDbJob.class.getName());

		StringBuilder result = new StringBuilder();
		result.append("This is sample job result. ");

		JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		for (Entry<String, Object> entry : dataMap.entrySet()) {
			result.append("Job Data Name: " + entry.getKey() + ", Job Data Value" + entry.getValue() + ". ");
		}

		context.setResult(result.toString());

	}

	public void exportData() {
		LOGGER.info("Backup Started at " + new Date());

		Date backupDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String backupDateStr = format.format(backupDate);
//		String dbNameList = "client_1 client_2";

		String fileName = "mysqldump"; // default file name
		File f1 = new File(DATABASE_BACKUP_DIR);
		f1.mkdir(); // create folder if not exist

		String saveFileName = fileName + "_" + backupDateStr + ".sql";
		String savePath = f1.getAbsolutePath() + File.separator + saveFileName;

		String executeCmd = "C:\\xampp\\mysql\\bin\\mysqldump -u " + DB_USER_NAME + " -p" + DB_USER_PASSWORD + "  --databases " + DB + " -r " + savePath;

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
			LOGGER.info("Backup Complete at " + new Date());
		} else {
			LOGGER.info("Backup Failure");
		}

	}
}
