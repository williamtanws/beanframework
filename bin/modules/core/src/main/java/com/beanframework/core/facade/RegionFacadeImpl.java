package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.RegionDto;
import com.beanframework.core.specification.RegionSpecification;
import com.beanframework.internationalization.domain.Region;

@Component
public class RegionFacadeImpl extends AbstractFacade<Region, RegionDto> implements RegionFacade {

  private static final Class<Region> entityClass = Region.class;
  private static final Class<RegionDto> dtoClass = RegionDto.class;

  @Override
  public RegionDto findOneByUuid(UUID uuid) throws Exception {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public RegionDto findOneProperties(Map<String, Object> properties) throws Exception {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public RegionDto create(RegionDto model) throws BusinessException {
    return save(model, entityClass, dtoClass);
  }

  @Override
  public RegionDto update(RegionDto model) throws BusinessException {
    return save(model, entityClass, dtoClass);
  }

  @Override
  public void delete(UUID uuid) throws BusinessException {
    delete(uuid, entityClass);
  }

  @Override
  public Page<RegionDto> findPage(DataTableRequest dataTableRequest) throws Exception {
    return findPage(dataTableRequest, RegionSpecification.getPageSpecification(dataTableRequest),
        entityClass, dtoClass);
  }

  @Override
  public int count() throws Exception {
    return count(entityClass);
  }

  @Override
  public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {
    return findHistory(dataTableRequest, entityClass, dtoClass);
  }

  @Override
  public int countHistory(DataTableRequest dataTableRequest) throws Exception {
    return findCountHistory(dataTableRequest, entityClass);
  }

  @Override
  public RegionDto createDto() throws Exception {
    return createDto(entityClass, dtoClass);
  }

}
