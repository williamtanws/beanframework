package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.SiteDto;

public interface SiteFacade {

  SiteDto findOneByUuid(UUID uuid) throws BusinessException;

  SiteDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  SiteDto save(SiteDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<SiteDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  SiteDto createDto() throws BusinessException;
}
