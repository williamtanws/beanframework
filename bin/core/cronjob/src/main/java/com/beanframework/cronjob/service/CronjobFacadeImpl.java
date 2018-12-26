package com.beanframework.cronjob.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.service.ModelService;
import com.beanframework.cronjob.CronjobConstants;
import com.beanframework.cronjob.domain.Cronjob;
import com.beanframework.cronjob.domain.CronjobData;

@Component
public class CronjobFacadeImpl implements CronjobFacade {
	
	@Autowired
	private ModelService modelService;
	
	@Autowired
	private CronjobManagerService cronjobManagerService;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Override
	public void trigger(Cronjob cronjob) throws BusinessException {
		try {
			cronjobManagerService.trigger(cronjob);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Cronjob addCronjobData(UUID uuid, String name, String value) throws BusinessException {
		
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
			
			modelService.saveEntity(updateCronjob, Cronjob.class);

			return updateCronjob;
		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<Cronjob> findPage(Specification<Cronjob> specification, PageRequest pageable) throws Exception {
		return modelService.findDtoPage(specification, pageable, Cronjob.class);
	}

	@Override
	public Cronjob create() throws Exception {
		return modelService.create(Cronjob.class);
	}

	@Override
	public Cronjob findOneDtoByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneDtoByProperties(properties, Cronjob.class);
	}

	@Override
	public void createDto(Cronjob model) throws BusinessException {
		modelService.saveDto(model, Cronjob.class);
	}

	@Override
	public void updateDto(Cronjob model) throws BusinessException {
		modelService.saveDto(model, Cronjob.class);
		
	}

	@Override
	public void delete(UUID uuid) throws BusinessException {
		modelService.delete(uuid, Cronjob.class);
	}
}
