package com.beanframework.cronjob.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.beanframework.common.domain.AbstractDomain;
import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.cronjob.domain.CronjobEnum.Status;
import com.beanframework.cronjob.domain.CronjobEnum.JobTrigger;
import com.beanframework.cronjob.domain.CronjobEnum.Result;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = CronjobConstants.Table.CRONJOB)
public class Cronjob extends AbstractDomain {

	public static final String MODEL = "cronjob";

	/**
	 * 
	 */
	private static final long serialVersionUID = 8939913795649500178L;
	public static final String JOB_NAME = "jobName";
	public static final String JOB_GROUP = "jobGroup";
	public static final String DESCRIPTION = "description";
	public static final String CRONJOB_DATAS = "cronjobDatas";

	private String jobClass;

	/** Task Group */
	private String jobGroup;

	/** Task Name */
	private String jobName;

	private String description;

	/** Task run time expression */
	private String cronExpression;

	private Boolean startup;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Enumerated(EnumType.STRING)
	private Result result;

	@Lob
	@Column(length = 100000)
	private String message;

	@Enumerated(EnumType.STRING)
	private JobTrigger jobTrigger;

	private Date triggerStartDate;

	private Date lastTriggeredDate;

	private Date lastStartExecutedDate;

	private Date lastFinishExecutedDate;

	@Cascade({ CascadeType.ALL })
	@OneToMany(mappedBy = CronjobData.CRONJOB, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderBy("createdDate DESC")
	private List<CronjobData> cronjobDatas = new ArrayList<CronjobData>(0);

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

	public List<CronjobData> getCronjobDatas() {
		return cronjobDatas;
	}

	public void setCronjobDatas(List<CronjobData> cronjobDatas) {
		this.cronjobDatas = cronjobDatas;
	}

}
