package com.beanframework.core.facade;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.CronjobDataDto;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobData;
import com.beanframework.cronjob.service.CronjobManagerService;
import com.beanframework.cronjob.specification.CronjobSpecification;

@Component
public class CronjobFacadeImpl extends AbstractFacade<Cronjob, CronjobDto> implements CronjobFacade {

	private static final Class<Cronjob> entityClass = Cronjob.class;
	private static final Class<CronjobDto> dtoClass = CronjobDto.class;

	@Autowired
	private ModelService modelService;

	@Autowired
	private CronjobManagerService cronjobManagerService;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Override
	public CronjobDto findOneByUuid(UUID uuid) throws Exception {
		return findOneByUuid(uuid, entityClass, dtoClass);
	}
	
	@Override
	public CronjobDto findOneProperties(Map<String, Object> properties) throws Exception {
		return findOneProperties(properties, entityClass, dtoClass);
	}

	@Override
	public CronjobDto create(CronjobDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public CronjobDto update(CronjobDto model) throws BusinessException {
		return save(model, entityClass, dtoClass);
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		delete(uuid, entityClass);
	}

	@Override
	public Page<CronjobDto> findPage(DataTableRequest dataTableRequest) throws Exception {
		return findPage(dataTableRequest, CronjobSpecification.getSpecification(dataTableRequest), entityClass, dtoClass);
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
	public CronjobDto createDto() throws Exception {
		return createDto(entityClass, dtoClass);
	}

	@Override
	public void trigger(CronjobDto cronjob) throws BusinessException {
		try {
			Cronjob updateCronjob = modelService.findOneByUuid(cronjob.getUuid(), entityClass);

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
			Cronjob updateCronjob = modelService.findOneByUuid(uuid, entityClass);

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

			updateCronjob = modelService.saveEntity(updateCronjob);

			return modelService.getDto(updateCronjob, dtoClass);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void createCronjobData(UUID cronjobUuid, CronjobDataDto dto) throws BusinessException {

		try {

			Cronjob entityCronjob = modelService.findOneByUuid(cronjobUuid, entityClass);

			CronjobData entityCronjobData = modelService.getEntity(dto, CronjobDataDto.class);
			entityCronjobData.setCronjob(entityCronjob);

			entityCronjob.getCronjobDatas().add(entityCronjobData);

			modelService.saveEntity(entityCronjobData);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public void removeCronjobData(UUID cronjobUuid, UUID cronjobDataUuid) throws BusinessException {

		try {

			Cronjob entityCronjob = modelService.findOneByUuid(cronjobUuid, entityClass);

			Iterator<CronjobData> cronjobDatas = entityCronjob.getCronjobDatas().iterator();
			while (cronjobDatas.hasNext()) {
				if (cronjobDatas.next().getUuid().equals(cronjobDataUuid)) {
					cronjobDatas.remove();
				}
			}

			modelService.saveEntity(entityCronjob);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

}
