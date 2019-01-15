package com.beanframework.backoffice.facade;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.beanframework.backoffice.data.CronjobDataDto;
import com.beanframework.backoffice.data.CronjobDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobData;
import com.beanframework.cronjob.service.CronjobManagerService;
import com.beanframework.cronjob.service.CronjobService;

@Component
public class CronjobFacadeImpl implements CronjobFacade {

	@Autowired
	private ModelService modelService;

	@Autowired
	private CronjobManagerService cronjobManagerService;

	@Autowired
	private LocaleMessageService localeMessageService;
	
	@Autowired
	private CronjobService cronjobService;

	@Override
	public Page<CronjobDto> findPage(Specification<CronjobDto> specification, PageRequest pageable) throws Exception {
		Page<Cronjob> page = modelService.findEntityPage(specification, pageable, Cronjob.class);
		List<CronjobDto> dtos = modelService.getDto(page.getContent(), CronjobDto.class);
		return new PageImpl<CronjobDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public CronjobDto findOneByUuid(UUID uuid) throws Exception {
		Cronjob entity = modelService.findOneEntityByUuid(uuid, Cronjob.class);
		return modelService.getDto(entity, CronjobDto.class);
	}

	@Override
	public CronjobDto findOneByProperties(Map<String, Object> properties) throws Exception {
		Cronjob entity = modelService.findOneEntityByProperties(properties, Cronjob.class);
		return modelService.getDto(entity, CronjobDto.class);
	}
	

	@Override
	public CronjobDto create(CronjobDto model) throws BusinessException {
		return save(model);
	}

	@Override
	public CronjobDto update(CronjobDto model) throws BusinessException {
		return save(model);
	}

	private CronjobDto save(CronjobDto dto) throws BusinessException {
		try {
			Cronjob entity = modelService.getEntity(dto, Cronjob.class);
			entity = (Cronjob) cronjobService.saveEntity(entity);

			return modelService.getDto(entity, CronjobDto.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		try {
			modelService.deleteByUuid(uuid, Cronjob.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void trigger(CronjobDto cronjob) throws BusinessException {
		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Cronjob.UUID, cronjob.getUuid());

			Cronjob updateCronjob = modelService.findOneEntityByProperties(properties, Cronjob.class);

			updateCronjob.setJobTrigger(cronjob.getJobTrigger());
			updateCronjob.setTriggerStartDate(cronjob.getTriggerStartDate());
			updateCronjob.setLastTriggeredDate(new Date());

			cronjobManagerService.updateJobAndSaveTrigger(updateCronjob);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public CronjobDto addCronjobData(UUID uuid, String name, String value) throws BusinessException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Cronjob.UUID, uuid);

			Cronjob updateCronjob = modelService.findOneEntityByProperties(properties, Cronjob.class);

			List<CronjobData> datas = updateCronjob.getCronjobDatas();

			for (int i = 0; i < datas.size(); i++) {
				if (datas.get(i).getName().equals(name)) {
					throw new Exception(localeMessageService.getMessage(CronjobConstants.Locale.CRONJOB_DATA_NAME_EXISTS));
				}
			}

			CronjobData data = new CronjobData();
			data.setName(name);
			data.setValue(value);
			data.setCronjob(updateCronjob);

			updateCronjob.getCronjobDatas().add(data);

			updateCronjob = (Cronjob) modelService.saveEntity(updateCronjob, Cronjob.class);

			return modelService.getDto(updateCronjob, Cronjob.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void updateCronjobData(UUID cronjobUuid, CronjobDataDto dto) throws BusinessException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Cronjob.UUID, cronjobUuid);

			Cronjob entityCronjob = modelService.findOneEntityByProperties(properties, Cronjob.class);
			
			CronjobData entityCronjobData = modelService.getEntity(dto, CronjobData.class);
			entityCronjobData.setCronjob(entityCronjob);

			entityCronjob.getCronjobDatas().add(entityCronjobData);

			modelService.saveEntity(entityCronjobData, CronjobData.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void removeCronjobData(UUID cronjobUuid, UUID cronjobDataUuid) throws BusinessException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Cronjob.UUID, cronjobUuid);

			Cronjob cronjob = modelService.findOneEntityByProperties(properties, Cronjob.class);

			Iterator<CronjobData> cronjobDatas = cronjob.getCronjobDatas().iterator();
			while (cronjobDatas.hasNext()) {
				if (cronjobDatas.next().getUuid().equals(cronjobDataUuid)) {
					cronjobDatas.remove();
				}
			}

			modelService.saveEntity(cronjob, Cronjob.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		AuditCriterion criterion = AuditEntity.conjunction().add(AuditEntity.id().eq(uuid)).add(AuditEntity.revisionType().ne(RevisionType.DEL));
		AuditOrder order = AuditEntity.revisionNumber().desc();
		List<Object[]> revisions = modelService.findHistory(false, criterion, order, null, null, Cronjob.class);
		for (int i = 0; i < revisions.size(); i++) {
			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], CronjobDto.class);
		}

		return revisions;
	}

}
