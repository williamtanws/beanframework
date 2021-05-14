package com.beanframework.imex.registry;

public abstract class ImportListener {

  public boolean customImport(Object objectCsv) throws Exception {
    return false;
  }

  public void beforeImport(Object objectCsv) throws Exception {

  }

  public void afterImport(Object objectCsv) throws Exception {

  }

  private String type;
  private String name;
  private int sort;
  private String description;
  private Class<?> classCsv;
  private Class<?> classEntity;
  private boolean customImport = false;
  public static final String SPLITTER = ";";
  public static final String EQUALS = "=";
  public static final String COLON = ":";
  public static final String POSITIVE = "+";
  public static final String NEGATIVE = "-";
  public static final String UNDERSCORE = "_";

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getSort() {
    return sort;
  }

  public void setSort(int sort) {
    this.sort = sort;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Class<?> getClassCsv() {
    return classCsv;
  }

  public void setClassCsv(Class<?> classCsv) {
    this.classCsv = classCsv;
  }

  public Class<?> getClassEntity() {
    return classEntity;
  }

  public void setClassEntity(Class<?> classEntity) {
    this.classEntity = classEntity;
  }

  public boolean isCustomImport() {
    return customImport;
  }

  public void setCustomImport(boolean customImport) {
    this.customImport = customImport;
  }

}
