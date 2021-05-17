package com.beanframework.core.converter.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.VendorDto;
import com.beanframework.user.domain.Vendor;

@Component
public class VendorEntityConverter implements EntityConverter<VendorDto, Vendor> {

  @Autowired
  private ModelService modelService;

  @Autowired
  private UserEntityConverter userEntityConverter;

  @Override
  public Vendor convert(VendorDto source) throws ConverterException {

    try {
      Vendor prototype;
      if (source.getUuid() != null) {
        prototype = modelService.findOneByUuid(source.getUuid(), Vendor.class);
        prototype = (Vendor) userEntityConverter.convertToEntity(source, prototype);

      } else {
        prototype =
            (Vendor) userEntityConverter.convertToEntity(source, modelService.create(Vendor.class));
      }

      // Additional property here if availably

      return prototype;

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }
}
