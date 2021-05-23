package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.VendorDto;

public interface VendorFacade {

  VendorDto findOneByUuid(UUID uuid) throws BusinessException;

  VendorDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  VendorDto save(VendorDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<VendorDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  VendorDto createDto() throws BusinessException;
}
