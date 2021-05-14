package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class RegionImportListener extends ImportListener {

  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.RegionImport.TYPE);
    setName(CoreImportListenerConstants.RegionImport.NAME);
    setSort(CoreImportListenerConstants.RegionImport.SORT);
    setDescription(CoreImportListenerConstants.RegionImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.RegionImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.RegionImport.CLASS_ENTITY);
  }

}
