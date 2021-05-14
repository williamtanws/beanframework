package com.beanframework.core.importlistener;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.CoreImportListenerConstants;
import com.beanframework.core.csv.CronjobCsv;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.service.CronjobManagerService;
import com.beanframework.imex.registry.ImportListener;

@Component
public class CronjobImportListener extends ImportListener {

  @Autowired
  private ModelService modelService;

  @Autowired
  private CronjobManagerService cronjobManagerService;

  @PostConstruct
  public void importer() {
    setType(CoreImportListenerConstants.CronjobImport.TYPE);
    setName(CoreImportListenerConstants.CronjobImport.NAME);
    setSort(CoreImportListenerConstants.CronjobImport.SORT);
    setDescription(CoreImportListenerConstants.CronjobImport.DESCRIPTION);
    setClassCsv(CoreImportListenerConstants.CronjobImport.CLASS_CSV);
    setClassEntity(CoreImportListenerConstants.CronjobImport.CLASS_ENTITY);
  }

  @Override
  public void afterImport(Object objectCsv) throws Exception {
    CronjobCsv csv = (CronjobCsv) objectCsv;

    if (StringUtils.isNotBlank(csv.getJobTrigger())) {

      Map<String, Object> properties = new HashMap<String, Object>();
      properties.put(GenericEntity.ID, csv.getId());
      Cronjob cronjob = modelService.findOneByProperties(properties, Cronjob.class);

      cronjobManagerService.updateJobAndSaveTrigger(cronjob);
    }
  }
}
