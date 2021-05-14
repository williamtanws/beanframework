package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class EnumerationImportListener extends ImportListener {

  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.EnumerationImport.TYPE);
    setName(CoreImportListenerConstants.EnumerationImport.NAME);
    setSort(CoreImportListenerConstants.EnumerationImport.SORT);
    setDescription(CoreImportListenerConstants.EnumerationImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.EnumerationImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.EnumerationImport.CLASS_ENTITY);
  }
}
