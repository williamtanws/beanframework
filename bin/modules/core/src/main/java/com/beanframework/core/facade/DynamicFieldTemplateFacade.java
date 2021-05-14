package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldTemplateDto;

public interface DynamicFieldTemplateFacade {

  DynamicFieldTemplateDto findOneByUuid(UUID uuid) throws Exception;

  DynamicFieldTemplateDto findOneProperties(Map<String, Object> properties) throws Exception;

  DynamicFieldTemplateDto create(DynamicFieldTemplateDto model) throws BusinessException;

  DynamicFieldTemplateDto update(DynamicFieldTemplateDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<DynamicFieldTemplateDto> findPage(DataTableRequest dataTableRequest) throws Exception;

  int count() throws Exception;

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

  int countHistory(DataTableRequest dataTableRequest) throws Exception;

  DynamicFieldTemplateDto createDto() throws Exception;

}
