package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class ImexImportListener extends ImportListener {
  protected static Logger LOGGER = LoggerFactory.getLogger(ImexImportListener.class);

  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.ImexImport.TYPE);
    setName(CoreImportListenerConstants.ImexImport.NAME);
    setSort(CoreImportListenerConstants.ImexImport.SORT);
    setDescription(CoreImportListenerConstants.ImexImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.ImexImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.ImexImport.CLASS_ENTITY);
  }
}
