package com.beanframework.console.csv;

import java.util.Date;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.Token;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

public class CronjobCsv extends AbstractCsv {

	private String jobClass;
	private String jobGroup;
	private String jobName;
	private String description;
	private String cronExpression;
	private Boolean startup;
	private String jobTrigger;
	private Date triggerStartDate;
	private String cronjobData;

	public static CellProcessor[] getUpdateProcessors() {
		final CellProcessor[] processors = new CellProcessor[] { //
				new Optional(new Trim()), // ModeType
				new NotNull(new Trim()), // id
				new NotNull(new Trim()), // jobClass
				new NotNull(new Trim()), // jobGroup
				new NotNull(new Trim()), // jobName
				new Optional(new Trim()), // description
				new NotNull(new Trim()), // cronExpression
				new Optional(new Trim(new ParseBool())), // startup
				new NotNull(new Trim()), // jobTrigger
				new Optional(new Trim(new Token(" ", null, new ParseDate("dd/MM/yyyy h:mm a", true)))), // triggerStartDate
				new Optional(new Trim()) // cronjobData
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

	public Boolean getStartup() {
		return startup;
	}

	public void setStartup(Boolean startup) {
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

	@Override
	public String toString() {
		return "CronjobCsv [id=" + id + ", jobClass=" + jobClass + ", jobGroup=" + jobGroup + ", jobName=" + jobName + ", description=" + description + ", cronExpression=" + cronExpression + ", startup=" + startup
				+ ", jobTrigger=" + jobTrigger + ", triggerStartDate=" + triggerStartDate + ", cronjobData=" + cronjobData + "]";
	}

	
}
