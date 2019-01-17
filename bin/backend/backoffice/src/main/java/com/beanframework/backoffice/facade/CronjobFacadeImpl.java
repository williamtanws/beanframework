package com.beanframework.backoffice.facade;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.beanframework.backoffice.data.CronjobDataDto;
import com.beanframework.backoffice.data.CronjobDto;
import com.beanframework.backoffice.data.CronjobSearch;
import com.beanframework.backoffice.data.CronjobSpecification;
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
	public Page<CronjobDto> findPage(CronjobSearch search, PageRequest pageable) throws Exception {
		Page<Cronjob> page = cronjobService.findEntityPage(search.toString(), CronjobSpecification.findByCriteria(search), pageable);
		List<CronjobDto> dtos = modelService.getDto(page.getContent(), CronjobDto.class);
		return new PageImpl<CronjobDto>(dtos, page.getPageable(), page.getTotalElements());
	}

	@Override
	public CronjobDto findOneByUuid(UUID uuid) throws Exception {
		Cronjob entity = cronjobService.findOneEntityByUuid(uuid);
		return modelService.getDto(entity, CronjobDto.class);
	}

	@Override
	public CronjobDto findOneProperties(Map<String, Object> properties) throws Exception {
		Cronjob entity = cronjobService.findOneEntityByProperties(properties);
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

	public CronjobDto save(CronjobDto dto) throws BusinessException {
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
		cronjobService.deleteByUuid(uuid);
	}

	@Override
	public List<Object[]> findHistoryByUuid(UUID uuid, Integer firstResult, Integer maxResults) throws Exception {
		List<Object[]> revisions = cronjobService.findHistoryByUuid(uuid, firstResult, maxResults);
//		for (int i = 0; i < revisions.size(); i++) {
//			revisions.get(i)[0] = modelService.getDto(revisions.get(i)[0], CronjobDto.class);
//		}

		return revisions;
	}

	@Override
	public void trigger(CronjobDto cronjob) throws BusinessException {
		try {
			Cronjob updateCronjob = cronjobService.findOneEntityByUuid(cronjob.getUuid());

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
			Cronjob updateCronjob = cronjobService.findOneEntityByUuid(uuid);

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

			updateCronjob = (Cronjob) cronjobService.saveEntity(updateCronjob);

			return modelService.getDto(updateCronjob, Cronjob.class);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void updateCronjobData(UUID cronjobUuid, CronjobDataDto dto) throws BusinessException {

		try {

			Cronjob entityCronjob = cronjobService.findOneEntityByUuid(cronjobUuid);

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

			Cronjob entityCronjob = cronjobService.findOneEntityByUuid(cronjobUuid);

			Iterator<CronjobData> cronjobDatas = entityCronjob.getCronjobDatas().iterator();
			while (cronjobDatas.hasNext()) {
				if (cronjobDatas.next().getUuid().equals(cronjobDataUuid)) {
					cronjobDatas.remove();
				}
			}

			cronjobService.saveEntity(entityCronjob);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

}
