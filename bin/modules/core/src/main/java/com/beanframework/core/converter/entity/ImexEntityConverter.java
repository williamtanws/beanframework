package com.beanframework.core.converter.entity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.ImexDto;
import com.beanframework.imex.domain.Imex;

@Component
public class ImexEntityConverter implements EntityConverter<ImexDto, Imex> {

  @Autowired
  private ModelService modelService;

  @Override
  public Imex convert(ImexDto source) throws ConverterException {

    try {

      if (source.getUuid() != null) {
        Imex prototype = modelService.findOneByUuid(source.getUuid(), Imex.class);
        return convertToEntity(source, prototype);
      }

      return convertToEntity(source, modelService.create(Imex.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Imex convertToEntity(ImexDto source, Imex prototype) {

    if (StringUtils.equals(StringUtils.stripToNull(source.getId()),
        prototype.getId()) == Boolean.FALSE) {
      prototype.setId(StringUtils.stripToNull(source.getId()));
    }

    if (prototype.getType() == source.getType() == Boolean.FALSE) {
      prototype.setType(source.getType());
    }

    if (StringUtils.equals(StringUtils.stripToNull(source.getDirectory()),
        prototype.getDirectory()) == Boolean.FALSE) {
      prototype.setDirectory(StringUtils.stripToNull(source.getDirectory()));
    }

    if (StringUtils.equals(StringUtils.stripToNull(source.getFileName()),
        prototype.getFileName()) == Boolean.FALSE) {
      prototype.setFileName(StringUtils.stripToNull(source.getFileName()));
    }

    if (StringUtils.equals(StringUtils.stripToNull(source.getQuery()),
        prototype.getQuery()) == Boolean.FALSE) {
      prototype.setQuery(StringUtils.stripToNull(source.getQuery()));
    }

    if (StringUtils.equals(StringUtils.stripToNull(source.getHeader()),
        prototype.getHeader()) == Boolean.FALSE) {
      prototype.setHeader(StringUtils.stripToNull(source.getHeader()));
    }

    if (StringUtils.equals(StringUtils.stripToNull(source.getSeperator()),
        prototype.getSeperator()) == Boolean.FALSE) {
      prototype.setSeperator(StringUtils.stripToNull(source.getSeperator()));
    }

    return prototype;
  }

}
