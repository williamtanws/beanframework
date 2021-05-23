package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldTemplateDto;
import com.beanframework.core.specification.DynamicFieldTemplateSpecification;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

@Component
public class DynamicFieldTemplateFacadeImpl
    extends AbstractFacade<DynamicFieldTemplate, DynamicFieldTemplateDto>
    implements DynamicFieldTemplateFacade {

  private static final Class<DynamicFieldTemplate> entityClass = DynamicFieldTemplate.class;
  private static final Class<DynamicFieldTemplateDto> dtoClass = DynamicFieldTemplateDto.class;

  @Override
  public DynamicFieldTemplateDto findOneByUuid(UUID uuid) throws BusinessException {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public DynamicFieldTemplateDto findOneProperties(Map<String, Object> properties)
      throws BusinessException {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public DynamicFieldTemplateDto save(DynamicFieldTemplateDto model) throws BusinessException {
    return save(model, entityClass, dtoClass);
  }

  @Override
  public void delete(UUID uuid) throws BusinessException {
    delete(uuid, entityClass);
  }

  @Override
  public Page<DynamicFieldTemplateDto> findPage(DataTableRequest dataTableRequest)
      throws BusinessException {
    return findPage(dataTableRequest,
        DynamicFieldTemplateSpecification.getPageSpecification(dataTableRequest), entityClass,
        dtoClass);
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
  public DynamicFieldTemplateDto createDto() throws BusinessException {
    return createDto(entityClass, dtoClass);
  }
}
