package com.beanframework.cronjob.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.GenericEntity;
import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.cronjob.domain.CronjobEnum.JobTrigger;
import com.beanframework.cronjob.domain.CronjobEnum.Result;
import com.beanframework.cronjob.domain.CronjobEnum.Status;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Audited
@Table(name = CronjobConstants.Table.CRONJOB)
public class Cronjob extends GenericEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8939913795649500178L;
	public static final String JOB_CLASS = "jobClass";
	public static final String NAME = "name";
	public static final String JOB_GROUP = "jobGroup";
	public static final String DESCRIPTION = "description";
	public static final String CRON_EXPRESSION = "cronExpression";
	public static final String STARTUP = "startup";
	public static final String RESULT = "result";
	public static final String MESSAGE = "message";
	public static final String JOB_TRIGGER = "jobTrigger";
	public static final String TRIGGER_START_DATE = "triggerStartDate";
	public static final String LAST_TRIGGERED_DATE = "lastTriggeredDate";
	public static final String LAST_START_EXECUTED_DATE = "lastStartExecutedDate";
	public static final String LAST_FINISH_EXECUTED_DATE = "lastFinishExecutedDate";
	public static final String PARAMETERS = "parameters";
	public static final String STATUS = "status";

	@Audited(withModifiedFlag = true)
	private String name;

	@Audited(withModifiedFlag = true)
	private String jobClass;

	@Audited(withModifiedFlag = true)
	private String jobGroup;

	@Audited(withModifiedFlag = true)
	private String description;

	@Audited(withModifiedFlag = true)
	private String cronExpression;

	@Audited(withModifiedFlag = true)
	private Boolean startup;

	@NotAudited
	@Enumerated(EnumType.STRING)
	private Status status;

	@NotAudited
	@Enumerated(EnumType.STRING)
	private Result result;

	@NotAudited
	@Lob
	@Column(length = 100000)
	private String message;

	@Audited(withModifiedFlag = true)
	@Enumerated(EnumType.STRING)
	private JobTrigger jobTrigger;

	@Audited(withModifiedFlag = true)
	private Date triggerStartDate;

	@Audited(withModifiedFlag = true)
	private Date lastTriggeredDate;

	@NotAudited
	private Date lastStartExecutedDate;

	@NotAudited
	private Date lastFinishExecutedDate;

	@Audited(withModifiedFlag = true)
	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "name")
	@Column(name = "value")
	@CollectionTable(name = CronjobConstants.Table.CRONJOB_PARAMETER, joinColumns = @JoinColumn(name = "cronjob_uuid"))
	Map<String, String> parameters = new HashMap<String, String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JobTrigger getJobTrigger() {
		return jobTrigger;
	}

	public void setJobTrigger(JobTrigger jobTrigger) {
		this.jobTrigger = jobTrigger;
	}

	public Date getTriggerStartDate() {
		return triggerStartDate;
	}

	public void setTriggerStartDate(Date triggerStartDate) {
		this.triggerStartDate = triggerStartDate;
	}

	public Date getLastTriggeredDate() {
		return lastTriggeredDate;
	}

	public void setLastTriggeredDate(Date lastTriggeredDate) {
		this.lastTriggeredDate = lastTriggeredDate;
	}

	public Date getLastStartExecutedDate() {
		return lastStartExecutedDate;
	}

	public void setLastStartExecutedDate(Date lastStartExecutedDate) {
		this.lastStartExecutedDate = lastStartExecutedDate;
	}

	public Date getLastFinishExecutedDate() {
		return lastFinishExecutedDate;
	}

	public void setLastFinishExecutedDate(Date lastFinishExecutedDate) {
		this.lastFinishExecutedDate = lastFinishExecutedDate;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

}
