package com.beanframework.core.converter.dto;

import java.util.Date;
import com.beanframework.common.data.AuditorDto;

public class RevisionsDto {

  private int id;

  private long timestamp;

  private AuditorDto lastModifiedBy;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Date getRevisionDate() {
    return new Date(timestamp);
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  public AuditorDto getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(AuditorDto lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

}
