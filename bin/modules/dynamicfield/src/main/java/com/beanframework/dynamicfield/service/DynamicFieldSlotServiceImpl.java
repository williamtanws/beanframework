package com.beanframework.dynamicfield.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

@Service
public class DynamicFieldSlotServiceImpl implements DynamicFieldSlotService {
	
	@Autowired
	private ModelService modelService;

	@Override
	public void removeDynamicFieldRel(DynamicField model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(DynamicFieldSlot.DYNAMIC_FIELD, model.getUuid());
		List<DynamicFieldSlot> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, DynamicFieldSlot.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getDynamicField() != null)
					if (entities.get(i).getDynamicField().equals(model.getUuid())) {
						entities.get(i).setDynamicField(null);
						modelService.saveEntityByLegacyMode(entities.get(i), DynamicFieldSlot.class);
					}
			}
	}
}
