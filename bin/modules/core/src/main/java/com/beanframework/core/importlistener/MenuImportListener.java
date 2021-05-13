package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class MenuImportListener extends ImportListener {

  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.MenuImport.TYPE);
    setName(CoreImportListenerConstants.MenuImport.NAME);
    setSort(CoreImportListenerConstants.MenuImport.SORT);
    setDescription(CoreImportListenerConstants.MenuImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.MenuImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.MenuImport.CLASS_ENTITY);
  }
}
