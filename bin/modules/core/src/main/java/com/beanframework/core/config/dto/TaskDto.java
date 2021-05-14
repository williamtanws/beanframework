package com.beanframework.core.config.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.flowable.identitylink.api.IdentityLinkInfo;

public class TaskDto {
  private String id;
  private String name;
  private String description;
  private int priority;
  private String owner;
  private String assignee;
  private String processInstanceId;
  private String executionId;
  private String taskDefinitionId;
  private String processDefinitionId;
  private String scopeId;
  private String subScopeId;
  private String scopeType;
  private String scopeDefinitionId;
  private String propagatedStageInstanceId;
  private Date createTime;
  private String taskDefinitionKey;
  private Date dueDate;
  private String category;
  private String parentTaskId;
  private String tenantId;
  private String formKey;
  private Map<String, Object> taskLocalVariables;
  private Map<String, Object> processVariables;
  private List<? extends IdentityLinkInfo> identityLinks;
  private Date claimTime;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getExecutionId() {
    return executionId;
  }

  public void setExecutionId(String executionId) {
    this.executionId = executionId;
  }

  public String getTaskDefinitionId() {
    return taskDefinitionId;
  }

  public void setTaskDefinitionId(String taskDefinitionId) {
    this.taskDefinitionId = taskDefinitionId;
  }

  public String getProcessDefinitionId() {
    return processDefinitionId;
  }

  public void setProcessDefinitionId(String processDefinitionId) {
    this.processDefinitionId = processDefinitionId;
  }

  public String getScopeId() {
    return scopeId;
  }

  public void setScopeId(String scopeId) {
    this.scopeId = scopeId;
  }

  public String getSubScopeId() {
    return subScopeId;
  }

  public void setSubScopeId(String subScopeId) {
    this.subScopeId = subScopeId;
  }

  public String getScopeType() {
    return scopeType;
  }

  public void setScopeType(String scopeType) {
    this.scopeType = scopeType;
  }

  public String getScopeDefinitionId() {
    return scopeDefinitionId;
  }

  public void setScopeDefinitionId(String scopeDefinitionId) {
    this.scopeDefinitionId = scopeDefinitionId;
  }

  public String getPropagatedStageInstanceId() {
    return propagatedStageInstanceId;
  }

  public void setPropagatedStageInstanceId(String propagatedStageInstanceId) {
    this.propagatedStageInstanceId = propagatedStageInstanceId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getTaskDefinitionKey() {
    return taskDefinitionKey;
  }

  public void setTaskDefinitionKey(String taskDefinitionKey) {
    this.taskDefinitionKey = taskDefinitionKey;
  }

  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    this.dueDate = dueDate;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getParentTaskId() {
    return parentTaskId;
  }

  public void setParentTaskId(String parentTaskId) {
    this.parentTaskId = parentTaskId;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getFormKey() {
    return formKey;
  }

  public void setFormKey(String formKey) {
    this.formKey = formKey;
  }

  public Map<String, Object> getTaskLocalVariables() {
    return taskLocalVariables;
  }

  public void setTaskLocalVariables(Map<String, Object> taskLocalVariables) {
    this.taskLocalVariables = taskLocalVariables;
  }

  public Map<String, Object> getProcessVariables() {
    return processVariables;
  }

  public void setProcessVariables(Map<String, Object> processVariables) {
    this.processVariables = processVariables;
  }

  public List<? extends IdentityLinkInfo> getIdentityLinks() {
    return identityLinks;
  }

  public void setIdentityLinks(List<? extends IdentityLinkInfo> identityLinks) {
    this.identityLinks = identityLinks;
  }

  public Date getClaimTime() {
    return claimTime;
  }

  public void setClaimTime(Date claimTime) {
    this.claimTime = claimTime;
  }



}
