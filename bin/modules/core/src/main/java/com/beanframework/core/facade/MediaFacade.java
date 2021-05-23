package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.MediaDto;

public interface MediaFacade {

  MediaDto findOneByUuid(UUID uuid) throws BusinessException;

  MediaDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  MediaDto save(MediaDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<MediaDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  MediaDto createDto() throws BusinessException;

}
