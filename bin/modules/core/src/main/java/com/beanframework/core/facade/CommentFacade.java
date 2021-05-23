package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CommentDto;

public interface CommentFacade {

  CommentDto findOneByUuid(UUID uuid) throws BusinessException;

  CommentDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  CommentDto save(CommentDto model) throws BusinessException;

  void delete(UUID uuid) throws BusinessException;

  Page<CommentDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  int count();

  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  int countHistory(DataTableRequest dataTableRequest);

  CommentDto createDto() throws BusinessException;

}
