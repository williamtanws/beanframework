package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class CountryImportListener extends ImportListener {

  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.CountryImport.TYPE);
    setName(CoreImportListenerConstants.CountryImport.NAME);
    setSort(CoreImportListenerConstants.CountryImport.SORT);
    setDescription(CoreImportListenerConstants.CountryImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.CountryImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.CountryImport.CLASS_ENTITY);
  }

}
