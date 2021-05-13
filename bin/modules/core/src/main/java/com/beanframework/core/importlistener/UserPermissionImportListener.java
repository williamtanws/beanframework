package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class UserPermissionImportListener extends ImportListener {

  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.UserPermissionImport.TYPE);
    setName(CoreImportListenerConstants.UserPermissionImport.NAME);
    setSort(CoreImportListenerConstants.UserPermissionImport.SORT);
    setDescription(CoreImportListenerConstants.UserPermissionImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.UserPermissionImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.UserPermissionImport.CLASS_ENTITY);
  }
}
