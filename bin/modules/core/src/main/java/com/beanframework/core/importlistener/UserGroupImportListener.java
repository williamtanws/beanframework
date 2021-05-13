package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class UserGroupImportListener extends ImportListener {

  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.UserGroupImport.TYPE);
    setName(CoreImportListenerConstants.UserGroupImport.NAME);
    setSort(CoreImportListenerConstants.UserGroupImport.SORT);
    setDescription(CoreImportListenerConstants.UserGroupImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.UserGroupImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.UserGroupImport.CLASS_ENTITY);
  }
}
