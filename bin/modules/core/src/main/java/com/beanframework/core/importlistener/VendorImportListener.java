package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class VendorImportListener extends ImportListener {
  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.VendorImport.TYPE);
    setName(CoreImportListenerConstants.VendorImport.NAME);
    setSort(CoreImportListenerConstants.VendorImport.SORT);
    setDescription(CoreImportListenerConstants.VendorImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.VendorImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.VendorImport.CLASS_ENTITY);
  }
}
