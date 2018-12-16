package com.beanframework.cronjob.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

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
	private CronjobService cronjobService;
	
	@Autowired
	private CronjobManagerService cronjobManagerService;

	@Autowired
	private LocaleMessageService localeMessageService;

	@Override
	public void trigger(Cronjob cronjob) {
		cronjobManagerService.trigger(cronjob);
	}

	@Override
	public Cronjob addCronjobData(UUID uuid, String name, String value) throws Exception {
		
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
		
		modelService.save(updateCronjob);

		return updateCronjob;
	}

	@Override
	public Page<Cronjob> page(Cronjob cronjob, int page, int size, Direction direction, String... properties) {

		// Change page to index's page
		page = page <= 0 ? 0 : page - 1;
		size = size <= 0 ? 1 : size;

		PageRequest pageRequest = PageRequest.of(page, size, direction, properties);

		Page<Cronjob> cronjobPage = cronjobService.page(cronjob, pageRequest);
		
		List<Cronjob> content = modelService.getDto(cronjobPage.getContent());
		return new PageImpl<Cronjob>(content, cronjobPage.getPageable(), cronjobPage.getTotalElements());
	}
}
