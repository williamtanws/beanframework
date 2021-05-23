package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.core.specification.CronjobSpecification;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.service.CronjobManagerService;

@Component
public class CronjobFacadeImpl extends AbstractFacade<Cronjob, CronjobDto>
    implements CronjobFacade {

  private static final Class<Cronjob> entityClass = Cronjob.class;
  private static final Class<CronjobDto> dtoClass = CronjobDto.class;

  @Autowired
  private ModelService modelService;

  @Autowired
  private CronjobManagerService cronjobManagerService;

  @Override
  public CronjobDto findOneByUuid(UUID uuid) throws BusinessException {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public CronjobDto findOneProperties(Map<String, Object> properties) throws BusinessException {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public CronjobDto save(CronjobDto model) throws BusinessException {
    model = save(model, entityClass, dtoClass);

    if (model.getJobTrigger() != null) {
      trigger(model);
    }

    return model;
  }

  @Override
  public void delete(UUID uuid) throws BusinessException {
    delete(uuid, entityClass);
  }

  @Override
  public Page<CronjobDto> findPage(DataTableRequest dataTableRequest) throws BusinessException {
    return findPage(dataTableRequest, CronjobSpecification.getPageSpecification(dataTableRequest),
        entityClass, dtoClass);
  }

  @Override
  public int count() {
    return count(entityClass);
  }

  @Override
  public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException {
    return findHistory(dataTableRequest, entityClass, dtoClass);
  }

  @Override
  public int countHistory(DataTableRequest dataTableRequest) {
    return findCountHistory(dataTableRequest, entityClass);
  }

  @Override
  public CronjobDto createDto() throws BusinessException {
    return createDto(entityClass, dtoClass);
  }

  public void trigger(CronjobDto cronjob) throws BusinessException {
    try {
      Cronjob updateCronjob = modelService.findOneByUuid(cronjob.getUuid(), entityClass);

      cronjobManagerService.updateJobAndSaveTrigger(updateCronjob);
    } catch (Exception e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }
}
