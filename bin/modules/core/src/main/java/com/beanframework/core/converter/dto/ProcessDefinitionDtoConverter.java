package com.beanframework.core.converter.dto;

import org.flowable.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.core.config.dto.ProcessDefinitionDto;

@Component
public class ProcessDefinitionDtoConverter
    implements DtoConverter<ProcessDefinition, ProcessDefinitionDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(ProcessDefinitionDtoConverter.class);

  @Override
  public ProcessDefinitionDto convert(ProcessDefinition source) {
    ProcessDefinitionDto target = new ProcessDefinitionDto();
    target.setId(source.getId());
    target.setCategory(source.getCategory());
    target.setName(source.getName());
    target.setKey(source.getKey());
    target.setDescription(source.getDescription());
    target.setVersion(source.getVersion());
    target.setResourceName(source.getResourceName());
    target.setDeploymentId(source.getDeploymentId());
    target.setDiagramResourceName(source.getDiagramResourceName());
    target.setHasGraphicalNotation(source.hasGraphicalNotation());
    target.setHasStartFormKey(source.hasStartFormKey());
    target.setSuspended(source.isSuspended());
    target.setTenantId(source.getTenantId());
    target.setDerivedFrom(source.getDerivedFrom());
    target.setDerivedFromRoot(source.getDerivedFromRoot());
    target.setDerivedVersion(source.getDerivedVersion());
    target.setEngineVersion(source.getEngineVersion());
    return target;
  }
}
