package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.AddressDto;

public interface AddressFacade {

  AddressDto findOneByUuid(UUID uuid) throws BusinessException;

  AddressDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  AddressDto save(AddressDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<AddressDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  AddressDto createDto() throws BusinessException;
}
