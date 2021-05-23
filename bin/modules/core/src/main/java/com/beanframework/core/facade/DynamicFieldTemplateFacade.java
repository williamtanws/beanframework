package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldTemplateDto;

public interface DynamicFieldTemplateFacade {

  DynamicFieldTemplateDto findOneByUuid(UUID uuid) throws BusinessException;

  DynamicFieldTemplateDto findOneProperties(Map<String, Object> properties)
      throws BusinessException;

  DynamicFieldTemplateDto save(DynamicFieldTemplateDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<DynamicFieldTemplateDto> findPage(DataTableRequest dataTableRequest)
      throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  DynamicFieldTemplateDto createDto() throws BusinessException;

}
