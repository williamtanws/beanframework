package com.beanframework.console.csv;

import java.util.Date;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.Token;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class CronjobCsv extends AbstractCsv {

	private String jobClass;
	private String jobGroup;
	private String jobName;
	private String description;
	private String cronExpression;
	private boolean startup;
	private String jobTrigger;
	private Date triggerStartDate;
	private String cronjobData;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new UniqueHashCode(), // id
				new NotNull(), // jobClass
				new NotNull(), // jobGroup
				new NotNull(), // jobName
				new Optional(), // description
				new NotNull(), // cronExpression
				new ParseBool(), // startup
				new NotNull(), // jobTrigger
				new Optional(new Token(" ", null, new ParseDate("dd/MM/yyyy h:mm a", true))), // triggerStartDate
				new Optional() // cronjobData
		};

		return processors;
	}

	public String getJobClass() {
		return jobClass;
	}

	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public boolean isStartup() {
		return startup;
	}

	public void setStartup(boolean startup) {
		this.startup = startup;
	}

	public String getJobTrigger() {
		return jobTrigger;
	}

	public void setJobTrigger(String jobTrigger) {
		this.jobTrigger = jobTrigger;
	}

	public Date getTriggerStartDate() {
		return triggerStartDate;
	}

	public void setTriggerStartDate(Date triggerStartDate) {
		this.triggerStartDate = triggerStartDate;
	}

	public String getCronjobData() {
		return cronjobData;
	}

	public void setCronjobData(String cronjobData) {
		this.cronjobData = cronjobData;
	}

}
