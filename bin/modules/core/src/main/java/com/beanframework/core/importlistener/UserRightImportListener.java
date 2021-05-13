package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class UserRightImportListener extends ImportListener {

  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.UserRightImport.TYPE);
    setName(CoreImportListenerConstants.UserRightImport.NAME);
    setSort(CoreImportListenerConstants.UserRightImport.SORT);
    setDescription(CoreImportListenerConstants.UserRightImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.UserRightImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.UserRightImport.CLASS_ENTITY);
  }
}
