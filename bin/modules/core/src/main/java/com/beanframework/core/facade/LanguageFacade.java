package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.LanguageDto;

public interface LanguageFacade {

  LanguageDto findOneByUuid(UUID uuid) throws Exception;

  LanguageDto findOneProperties(Map<String, Object> properties) throws Exception;

  LanguageDto create(LanguageDto model) throws BusinessException;

  LanguageDto update(LanguageDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<LanguageDto> findPage(DataTableRequest dataTableRequest) throws Exception;

  int count() throws Exception;

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception;

  int countHistory(DataTableRequest dataTableRequest) throws Exception;

  LanguageDto createDto() throws Exception;
}
