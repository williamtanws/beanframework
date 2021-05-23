package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.EmailDto;

public interface EmailFacade {

  EmailDto findOneByUuid(UUID uuid) throws BusinessException;

  EmailDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  EmailDto save(EmailDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<EmailDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  EmailDto createDto() throws BusinessException;
}
