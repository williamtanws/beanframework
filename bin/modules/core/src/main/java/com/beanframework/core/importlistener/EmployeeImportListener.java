package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class EmployeeImportListener extends ImportListener {

  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.EmployeeImport.TYPE);
    setName(CoreImportListenerConstants.EmployeeImport.NAME);
    setSort(CoreImportListenerConstants.EmployeeImport.SORT);
    setDescription(CoreImportListenerConstants.EmployeeImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.EmployeeImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.EmployeeImport.CLASS_ENTITY);
  }
}
