package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class AddressImportListener extends ImportListener {

  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.AddressImport.TYPE);
    setName(CoreImportListenerConstants.AddressImport.NAME);
    setSort(CoreImportListenerConstants.AddressImport.SORT);
    setDescription(CoreImportListenerConstants.AddressImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.AddressImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.AddressImport.CLASS_ENTITY);
  }

}
