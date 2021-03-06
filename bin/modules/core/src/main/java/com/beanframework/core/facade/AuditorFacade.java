package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;

public interface AuditorFacade {

  public static interface PreAuthorizeEnum {
    public static final String AUTHORITY_READ = "auditor_read";

    public static final String HAS_READ = "hasAuthority('" + AUTHORITY_READ + "')";
  }

  @PreAuthorize(PreAuthorizeEnum.HAS_READ)
  AuditorDto findOneByUuid(UUID uuid) throws BusinessException;

  @PreAuthorize(PreAuthorizeEnum.HAS_READ)
  AuditorDto findOneProperties(Map<String, Object> properties) throws BusinessException;

  @PreAuthorize(PreAuthorizeEnum.HAS_READ)
  Page<AuditorDto> findPage(DataTableRequest dataTableRequest) throws BusinessException;

  @PreAuthorize(PreAuthorizeEnum.HAS_READ)
  int count();

  @PreAuthorize(PreAuthorizeEnum.HAS_READ)
  List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException;

  @PreAuthorize(PreAuthorizeEnum.HAS_READ)
  int countHistory(DataTableRequest dataTableRequest);

}
