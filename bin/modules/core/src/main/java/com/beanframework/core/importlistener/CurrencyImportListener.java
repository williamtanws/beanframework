package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class CurrencyImportListener extends ImportListener {

  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.CurrencyImport.TYPE);
    setName(CoreImportListenerConstants.CurrencyImport.NAME);
    setSort(CoreImportListenerConstants.CurrencyImport.SORT);
    setDescription(CoreImportListenerConstants.CurrencyImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.CurrencyImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.CurrencyImport.CLASS_ENTITY);
  }

}
