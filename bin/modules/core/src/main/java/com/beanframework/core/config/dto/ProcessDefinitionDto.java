package com.beanframework.core.config.dto;

public class ProcessDefinitionDto {
  private String id;
  private String category;
  private String name;
  private String key;
  private String description;
  private int version;
  private String resourceName;
  private String deploymentId;
  private String diagramResourceName;
  private boolean hasStartFormKey;
  private boolean hasGraphicalNotation;
  private boolean isSuspended;
  private String tenantId;
  private String derivedFrom;
  private String derivedFromRoot;
  private int derivedVersion;
  private String engineVersion;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public String getDeploymentId() {
    return deploymentId;
  }

  public void setDeploymentId(String deploymentId) {
    this.deploymentId = deploymentId;
  }

  public String getDiagramResourceName() {
    return diagramResourceName;
  }

  public void setDiagramResourceName(String diagramResourceName) {
    this.diagramResourceName = diagramResourceName;
  }

  public boolean isHasStartFormKey() {
    return hasStartFormKey;
  }

  public void setHasStartFormKey(boolean hasStartFormKey) {
    this.hasStartFormKey = hasStartFormKey;
  }

  public boolean isHasGraphicalNotation() {
    return hasGraphicalNotation;
  }

  public void setHasGraphicalNotation(boolean hasGraphicalNotation) {
    this.hasGraphicalNotation = hasGraphicalNotation;
  }

  public boolean isSuspended() {
    return isSuspended;
  }

  public void setSuspended(boolean isSuspended) {
    this.isSuspended = isSuspended;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
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

  public int getDerivedVersion() {
    return derivedVersion;
  }

  public void setDerivedVersion(int derivedVersion) {
    this.derivedVersion = derivedVersion;
  }

  public String getEngineVersion() {
    return engineVersion;
  }

  public void setEngineVersion(String engineVersion) {
    this.engineVersion = engineVersion;
  }


}
