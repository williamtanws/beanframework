package com.beanframework.core.csv;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.Trim;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import com.beanframework.common.data.GenericCsv;

public class SiteCsv extends GenericCsv {

  private String name;
  private String url;

  public static CellProcessor[] getUpdateProcessors() {
    final CellProcessor[] processors = new CellProcessor[] { //
        new Optional(new Trim()), // ModeType
        new NotNull(new Trim()), // id
        new Optional(new Trim()), // name
        new Optional(new Trim()) // url
    };

    return processors;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  @Override
  public String toString() {
    return "SiteCsv [id=" + id + ", name=" + name + ", url=" + url + "]";
  }

}
