package com.beanframework.core.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.common.specification.AbstractSpecification;
import com.beanframework.core.converter.dto.RevisionsDtoConverter;
import com.beanframework.user.domain.RevisionsEntity;

public abstract class AbstractFacade<ENTITY extends GenericEntity, DTO extends GenericDto> {

  @Autowired
  protected ModelService modelService;

  @Autowired
  private RevisionsDtoConverter dtoRevisionsConverter;

  public DTO findOneByUuid(UUID uuid, Class<ENTITY> entityClass, Class<DTO> dtoClass)
      throws BusinessException {
    return modelService.getDto(modelService.findOneByUuid(uuid, entityClass), dtoClass);
  }

  public DTO findOneProperties(Map<String, Object> properties, Class<ENTITY> entityClass,
      Class<DTO> dtoClass) throws BusinessException {
    return modelService.getDto(modelService.findOneByProperties(properties, entityClass), dtoClass);
  }

  public Page<DTO> findPage(DataTableRequest dataTableRequest,
      AbstractSpecification<ENTITY> specification, Class<ENTITY> entityClass, Class<DTO> dtoClass)
      throws BusinessException {
    Page<ENTITY> page =
        modelService.findPage(specification, dataTableRequest.getPageable(), entityClass);

    List<DTO> dtos = modelService.getDtoList(page.getContent(), dtoClass);
    return new PageImpl<DTO>(dtos, page.getPageable(), page.getTotalElements());
  }

  public int count(Class<ENTITY> entityClass) {
    return modelService.countAll(entityClass);
  }

  @SuppressWarnings("unchecked")
  public List<Object[]> findHistory(DataTableRequest dataTableRequest, Class<ENTITY> entityClass,
      Class<DTO> dtoClass) throws BusinessException {

    List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
    if (dataTableRequest.getAuditCriterion() != null)
      auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

    List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
    if (dataTableRequest.getAuditOrder() != null)
      auditOrders.add(dataTableRequest.getAuditOrder());

    List<Object[]> entities = modelService.findHistory(false, auditCriterions, auditOrders,
        dataTableRequest.getStart(), dataTableRequest.getLength(), entityClass);

    List<Object[]> dtos = new ArrayList<Object[]>();
    for (Object[] entity : entities) {

      Object object = entity[0];
      RevisionsEntity revisionEntity = (RevisionsEntity) entity[1];
      RevisionType revisionType = (RevisionType) entity[2];
      Set<String> propertiesChanged = (Set<String>) entity[3];

      Object[] dto = new Object[4];
      dto[0] = modelService.getDto(object, dtoClass);
      dto[1] = dtoRevisionsConverter.convert(revisionEntity);
      dto[2] = revisionType;
      dto[3] = propertiesChanged;
      dtos.add(dto);
    }
    return dtos;
  }

  public int findCountHistory(DataTableRequest dataTableRequest, Class<ENTITY> entityClass) {

    List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
    if (dataTableRequest.getAuditCriterion() != null)
      auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

    List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
    if (dataTableRequest.getAuditOrder() != null)
      auditOrders.add(dataTableRequest.getAuditOrder());

    return modelService.countHistory(false, auditCriterions, auditOrders, entityClass);
  }

  public DTO save(DTO dto, Class<ENTITY> entityClass, Class<DTO> dtoClass)
      throws BusinessException {
    ENTITY entity = modelService.getEntity(dto, entityClass);
    entity = modelService.saveEntity(entity);

    return modelService.getDto(entity, dtoClass);
  }

  public void delete(UUID uuid, Class<ENTITY> entityClass) throws BusinessException {
    modelService.deleteByUuid(uuid, entityClass);
  }

  public DTO createDto(Class<ENTITY> entityClass, Class<DTO> dtoClass) throws BusinessException {
    ENTITY entity;
    try {
      entity = modelService.create(entityClass);
      return modelService.getDto(entity, dtoClass);
    } catch (Exception e) {
      throw new BusinessException(e.getMessage(), e);
    }
  }
}
