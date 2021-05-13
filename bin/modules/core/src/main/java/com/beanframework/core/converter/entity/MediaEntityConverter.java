package com.beanframework.core.converter.entity;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.MediaDto;
import com.beanframework.media.domain.Media;

@Component
public class MediaEntityConverter implements EntityConverter<MediaDto, Media> {

  @Autowired
  private ModelService modelService;

  @Override
  public Media convert(MediaDto source) throws ConverterException {

    try {

      if (source.getUuid() != null) {
        Media prototype = modelService.findOneByUuid(source.getUuid(), Media.class);

        if (prototype != null) {
          return convertToEntity(source, prototype);
        }
      }

      return convertToEntity(source, modelService.create(Media.class));

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }
  }

  private Media convertToEntity(MediaDto source, Media prototype) throws ConverterException {

    try {
      Date lastModifiedDate = new Date();

      if (StringUtils.equals(StringUtils.stripToNull(source.getId()),
          prototype.getId()) == Boolean.FALSE) {
        prototype.setId(StringUtils.stripToNull(source.getId()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getFileName()),
          prototype.getFileName()) == Boolean.FALSE) {
        prototype.setFileName(StringUtils.stripToNull(source.getFileName()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getFileType()),
          prototype.getFileType()) == Boolean.FALSE) {
        prototype.setFileType(StringUtils.stripToNull(source.getFileType()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getTitle()),
          prototype.getTitle()) == Boolean.FALSE) {
        prototype.setTitle(StringUtils.stripToNull(source.getTitle()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getCaption()),
          prototype.getCaption()) == Boolean.FALSE) {
        prototype.setCaption(StringUtils.stripToNull(source.getCaption()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getAltText()),
          prototype.getAltText()) == Boolean.FALSE) {
        prototype.setAltText(StringUtils.stripToNull(source.getAltText()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getDescription()),
          prototype.getDescription()) == Boolean.FALSE) {
        prototype.setDescription(StringUtils.stripToNull(source.getDescription()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getUrl()),
          prototype.getUrl()) == Boolean.FALSE) {
        prototype.setUrl(StringUtils.stripToNull(source.getUrl()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

      if (StringUtils.equals(StringUtils.stripToNull(source.getFolder()),
          prototype.getFolder()) == Boolean.FALSE) {
        prototype.setFolder(StringUtils.stripToNull(source.getFolder()));
        prototype.setLastModifiedDate(lastModifiedDate);
      }

    } catch (Exception e) {
      throw new ConverterException(e.getMessage(), e);
    }

    return prototype;
  }

}
