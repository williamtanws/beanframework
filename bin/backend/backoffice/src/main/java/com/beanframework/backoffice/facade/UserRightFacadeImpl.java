package com.beanframework.backoffice.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.beanframework.backoffice.data.UserRightDto;
import com.beanframework.backoffice.data.UserRightFieldDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightField;
import com.beanframework.user.service.UserRightService;

@Component
public class UserRightFacadeImpl implements UserRightFacade {

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private UserRightService userRightService;

	@Override
	public Page<UserRightDto> findPage(Specification<UserRightDto> specification, PageRequest pageRequest) throws Exception {
		Page<UserRight> page = modelService.findEntityPage(specification, pageRequest, UserRight.class);
		List<UserRightDto> dtos = modelService.getDto(page.getContent(), UserRightDto.class);
		return new PageImpl<UserRightDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public UserRightDto findOneByUuid(UUID uuid) throws Exception {
		UserRight entity = modelService.findOneEntityByUuid(uuid, UserRight.class);
		return modelService.getDto(entity, UserRightDto.class);
	}

	@Override
	public UserRightDto findOneByProperties(Map<String, Object> properties) throws Exception {
		UserRight entity = modelService.findOneEntityByProperties(properties, UserRight.class);
		return modelService.getDto(entity, UserRightDto.class);
	}

	@Override
	public UserRightDto create(UserRightDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public UserRightDto update(UserRightDto model) throws BusinessException {
		return save(model);
	}
	
	public UserRightDto save(UserRightDto dto) throws BusinessException {
		try {
			UserRight entity = modelService.getEntity(dto, UserRight.class);
			entity = (UserRight) userRightService.saveEntity(entity);

			return modelService.getDto(entity, UserRightDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		try {
			modelService.deleteByUuid(uuid, UserRight.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, UserRight.class);
		for (int i = 0; i < revisions.size(); i++) {
			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], UserRightDto.class);
		}
		
		return revisions;
	}

	@Override
	public List<Object[]> findFieldHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.relatedId(UserRightField.USER_RIGHT).eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, UserRightField.class);
		for (int i = 0; i < revisions.size(); i++) {
			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], UserRightFieldDto.class);
		}


		return revisions;
	}

	@Override
	public List<UserRightDto> findAllDtoUserRights() throws Exception {
		Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
		sorts.put(UserRightDto.SORT, Sort.Direction.ASC);
		return modelService.getDto(userRightService.findEntityBySorts(sorts), UserRightDto.class);	}

}
