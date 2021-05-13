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
  public DynamicFieldTemplateDto findOneByUuid(UUID uuid) throws Exception {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public DynamicFieldTemplateDto findOneProperties(Map<String, Object> properties)
      throws Exception {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public DynamicFieldTemplateDto create(DynamicFieldTemplateDto model) throws BusinessException {
    return save(model, entityClass, dtoClass);
  }

  @Override
  public DynamicFieldTemplateDto update(DynamicFieldTemplateDto model) throws BusinessException {
    return save(model, entityClass, dtoClass);
  }

  @Override
  public void delete(UUID uuid) throws BusinessException {
    delete(uuid, entityClass);
  }

  @Override
  public Page<DynamicFieldTemplateDto> findPage(DataTableRequest dataTableRequest)
      throws Exception {
    return findPage(dataTableRequest,
        DynamicFieldTemplateSpecification.getPageSpecification(dataTableRequest), entityClass,
        dtoClass);
  }

  @Override
  public int count() throws Exception {
    return count(entityClass);
  }

  @Override
  public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {
    return findHistory(dataTableRequest, entityClass, dtoClass);
  }

  @Override
  public int countHistory(DataTableRequest dataTableRequest) throws Exception {
    return findCountHistory(dataTableRequest, entityClass);
  }

  @Override
  public DynamicFieldTemplateDto createDto() throws Exception {
    return createDto(entityClass, dtoClass);
  }
}
