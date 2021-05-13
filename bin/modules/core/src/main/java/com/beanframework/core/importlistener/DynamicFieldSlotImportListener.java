package com.beanframework.core.importlistener;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.imex.registry.ImportListener;

@Component
public class DynamicFieldSlotImportListener extends ImportListener {

  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.DynamicFieldSlotImport.TYPE);
    setName(CoreImportListenerConstants.DynamicFieldSlotImport.NAME);
    setSort(CoreImportListenerConstants.DynamicFieldSlotImport.SORT);
    setDescription(CoreImportListenerConstants.DynamicFieldSlotImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.DynamicFieldSlotImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.DynamicFieldSlotImport.CLASS_ENTITY);
  }

}
