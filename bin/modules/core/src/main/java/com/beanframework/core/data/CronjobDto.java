package com.beanframework.core.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.beanframework.common.data.GenericDto;
import com.beanframework.cronjob.domain.CronjobEnum.JobTrigger;
import com.beanframework.cronjob.domain.CronjobEnum.Result;
import com.beanframework.cronjob.domain.CronjobEnum.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CronjobDto extends GenericDto {
  /**
   * 
   */
  private static final long serialVersionUID = -6298142854359675081L;
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
  public static final String CRONJOB_DATAS = "cronjobDatas";
  public static final String STATUS = "status";

  private String name;

  private String jobClass;

  private String jobGroup;

  private String description;

  private String cronExpression;

  private Boolean startup;

  private Status status;

  private Result result;

  private String message;

  private JobTrigger jobTrigger;

  private Date triggerStartDate;

  private Date lastTriggeredDate;

  private Date lastStartExecutedDate;

  private Date lastFinishExecutedDate;

  private Map<String, String> parameters = new HashMap<String, String>();

  private UserDto user;

  @JsonIgnore
  private String[] selectedParameterKeys;
  @JsonIgnore
  private String[] selectedParameterValues;
  @JsonIgnore
  private String selectedUserUuid;

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

  public String[] getSelectedParameterKeys() {
    return selectedParameterKeys;
  }

  public void setSelectedParameterKeys(String[] selectedParameterKeys) {
    this.selectedParameterKeys = selectedParameterKeys;
  }

  public String[] getSelectedParameterValues() {
    return selectedParameterValues;
  }

  public void setSelectedParameterValues(String[] selectedParameterValues) {
    this.selectedParameterValues = selectedParameterValues;
  }

  public UserDto getUser() {
    return user;
  }

  public void setUser(UserDto user) {
    this.user = user;
  }

  public String getSelectedUserUuid() {
    return selectedUserUuid;
  }

  public void setSelectedUserUuid(String selectedUserUuid) {
    this.selectedUserUuid = selectedUserUuid;
  }



}
