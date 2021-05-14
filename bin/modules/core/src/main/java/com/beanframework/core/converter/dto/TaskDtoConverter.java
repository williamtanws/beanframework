package com.beanframework.core.converter.dto;

import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.core.config.dto.TaskDto;

@Component
public class TaskDtoConverter implements DtoConverter<Task, TaskDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(TaskDtoConverter.class);

  @Override
  public TaskDto convert(Task source) {
    TaskDto target = new TaskDto();
    target.setId(source.getId());
    target.setName(source.getName());
    target.setDescription(source.getDescription());
    target.setPriority(source.getPriority());
    target.setOwner(source.getOwner());
    target.setAssignee(source.getAssignee());
    target.setProcessInstanceId(source.getProcessDefinitionId());
    target.setExecutionId(source.getExecutionId());
    target.setTaskDefinitionId(source.getTaskDefinitionId());
    target.setProcessDefinitionId(source.getProcessDefinitionId());
    target.setScopeId(source.getScopeId());
    target.setSubScopeId(source.getSubScopeId());
    target.setScopeType(source.getScopeType());
    target.setScopeDefinitionId(source.getScopeDefinitionId());
    target.setPropagatedStageInstanceId(source.getPropagatedStageInstanceId());
    target.setCreateTime(source.getCreateTime());
    target.setTaskDefinitionKey(source.getTaskDefinitionKey());
    target.setDueDate(source.getDueDate());
    target.setCategory(source.getCategory());
    target.setParentTaskId(source.getParentTaskId());
    target.setTenantId(source.getTenantId());
    target.setFormKey(source.getFormKey());
    target.setTaskLocalVariables(source.getTaskLocalVariables());
    target.setProcessVariables(source.getProcessVariables());
    target.setIdentityLinks(source.getIdentityLinks());
    target.setClaimTime(source.getClaimTime());
    return target;
  }
}
