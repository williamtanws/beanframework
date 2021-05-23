package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.LanguageDto;

public interface LanguageFacade {

  LanguageDto findOneByUuid(UUID uuid) throws BusinessException;

  LanguageDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  LanguageDto save(LanguageDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<LanguageDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  LanguageDto createDto() throws BusinessException;
}
