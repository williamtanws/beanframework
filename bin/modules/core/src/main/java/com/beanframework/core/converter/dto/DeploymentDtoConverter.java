package com.beanframework.core.converter.dto;

import org.flowable.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.DtoConverter;
import com.beanframework.core.config.dto.DeploymentDto;

@Component
public class DeploymentDtoConverter implements DtoConverter<Deployment, DeploymentDto> {

  protected static Logger LOGGER = LoggerFactory.getLogger(DeploymentDtoConverter.class);

  @Override
  public DeploymentDto convert(Deployment source) {
    DeploymentDto target = new DeploymentDto();
    target.setId(source.getId());
    target.setName(source.getName());
    target.setDeploymentTime(source.getDeploymentTime());
    target.setCategory(source.getCategory());
    target.setKey(source.getKey());
    target.setDerivedFrom(source.getDerivedFrom());
    target.setDerivedFromRoot(source.getDerivedFromRoot());
    target.setTenantId(source.getTenantId());
    target.setEngineVersion(source.getEngineVersion());
    target.setIsNew(source.isNew());
    // target.setResources(source.getResources());
    target.setParentDeploymentId(source.getParentDeploymentId());
    return target;
  }
}
