package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class LanguageImportListener extends ImportListener {

  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.LanguageImport.TYPE);
    setName(CoreImportListenerConstants.LanguageImport.NAME);
    setSort(CoreImportListenerConstants.LanguageImport.SORT);
    setDescription(CoreImportListenerConstants.LanguageImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.LanguageImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.LanguageImport.CLASS_ENTITY);
  }

}
