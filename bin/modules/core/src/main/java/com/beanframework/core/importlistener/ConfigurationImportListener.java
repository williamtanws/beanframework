package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class ConfigurationImportListener extends ImportListener {

  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.ConfigurationImport.TYPE);
    setName(CoreImportListenerConstants.ConfigurationImport.NAME);
    setSort(CoreImportListenerConstants.ConfigurationImport.SORT);
    setDescription(CoreImportListenerConstants.ConfigurationImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.ConfigurationImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.ConfigurationImport.CLASS_ENTITY);
  }
}
