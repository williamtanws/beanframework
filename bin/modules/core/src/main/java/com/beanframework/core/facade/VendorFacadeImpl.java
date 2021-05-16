package com.beanframework.core.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.VendorDto;
import com.beanframework.core.specification.VendorSpecification;
import com.beanframework.user.domain.Vendor;

@Component
public class VendorFacadeImpl extends AbstractFacade<Vendor, VendorDto> implements VendorFacade {

  private static final Class<Vendor> entityClass = Vendor.class;
  private static final Class<VendorDto> dtoClass = VendorDto.class;

  @Override
  public VendorDto findOneByUuid(UUID uuid) throws Exception {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public VendorDto findOneProperties(Map<String, Object> properties) throws Exception {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
  public VendorDto create(VendorDto model) throws BusinessException {
    return save(model);
  }

  @Override
  public VendorDto update(VendorDto model) throws BusinessException {
    return save(model);
  }

  public VendorDto save(VendorDto dto) throws BusinessException {
    try {
      if (dto.getProfilePicture() != null && dto.getProfilePicture().isEmpty() == Boolean.FALSE) {
        String mimetype = dto.getProfilePicture().getContentType();
        String type = mimetype.split("/")[0];
        if (type.equals("image") == Boolean.FALSE) {
          throw new Exception("Wrong picture format");
        }
      }

      Vendor entity = modelService.getEntity(dto, entityClass);
      entity = modelService.saveEntity(entity);

      return modelService.getDto(entity, dtoClass);
    } catch (Exception e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }

  @Override
  public void delete(UUID uuid) throws BusinessException {
    delete(uuid, entityClass);
  }

  @Override
  public Page<VendorDto> findPage(DataTableRequest dataTableRequest) throws Exception {
    return findPage(dataTableRequest, VendorSpecification.getPageSpecification(dataTableRequest),
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
  public VendorDto createDto() throws Exception {
    return createDto(entityClass, dtoClass);
  }
}
