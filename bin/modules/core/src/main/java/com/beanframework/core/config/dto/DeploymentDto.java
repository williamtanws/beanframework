package com.beanframework.core.config.dto;

import java.util.Date;
import java.util.Map;
import org.flowable.common.engine.api.repository.EngineResource;

public class DeploymentDto {
  private String id;
  private String name;
  private Date deploymentTime;
  private String category;
  private String key;
  private String derivedFrom;
  private String derivedFromRoot;
  private String tenantId;
  private String engineVersion;
  private boolean isNew;
  private Map<String, EngineResource> resources;
  private String parentDeploymentId;

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

  public Date getDeploymentTime() {
    return deploymentTime;
  }

  public void setDeploymentTime(Date deploymentTime) {
    this.deploymentTime = deploymentTime;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getDerivedFrom() {
    return derivedFrom;
  }

  public void setDerivedFrom(String derivedFrom) {
    this.derivedFrom = derivedFrom;
  }

  public String getDerivedFromRoot() {
    return derivedFromRoot;
  }

  public void setDerivedFromRoot(String derivedFromRoot) {
    this.derivedFromRoot = derivedFromRoot;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getEngineVersion() {
    return engineVersion;
  }

  public void setEngineVersion(String engineVersion) {
    this.engineVersion = engineVersion;
  }

  public boolean getIsNew() {
    return isNew;
  }

  public void setIsNew(boolean isNew) {
    this.isNew = isNew;
  }

  public Map<String, EngineResource> getResources() {
    return resources;
  }

  public void setResources(Map<String, EngineResource> resources) {
    this.resources = resources;
  }

  public String getParentDeploymentId() {
    return parentDeploymentId;
  }

  public void setParentDeploymentId(String parentDeploymentId) {
    this.parentDeploymentId = parentDeploymentId;
  }


}
