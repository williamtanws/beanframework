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
  public VendorDto findOneByUuid(UUID uuid) throws BusinessException {
    return findOneByUuid(uuid, entityClass, dtoClass);
  }

  @Override
  public VendorDto findOneProperties(Map<String, Object> properties) throws BusinessException {
    return findOneProperties(properties, entityClass, dtoClass);
  }

  @Override
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
  public Page<VendorDto> findPage(DataTableRequest dataTableRequest) throws BusinessException {
    return findPage(dataTableRequest, VendorSpecification.getPageSpecification(dataTableRequest),
        entityClass, dtoClass);
  }

  @Override
  public int count() {
    return count(entityClass);
  }

  @Override
  public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws BusinessException {
    return findHistory(dataTableRequest, entityClass, dtoClass);
  }

  @Override
  public int countHistory(DataTableRequest dataTableRequest) {
    return findCountHistory(dataTableRequest, entityClass);
  }

  @Override
  public VendorDto createDto() throws BusinessException {
    return createDto(entityClass, dtoClass);
  }
}
