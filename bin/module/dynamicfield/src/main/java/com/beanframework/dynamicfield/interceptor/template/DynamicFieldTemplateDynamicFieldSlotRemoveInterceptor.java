package com.beanframework.dynamicfield.interceptor.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.dynamicfield.domain.DynamicFieldTemplate;

public class DynamicFieldTemplateDynamicFieldSlotRemoveInterceptor extends AbstractRemoveInterceptor<DynamicFieldSlot> {

	@Autowired
	private ModelService modelService;

	@Override
	public void onRemove(DynamicFieldSlot model, InterceptorContext context) throws InterceptorException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(DynamicFieldTemplate.DYNAMIC_FIELD_SLOTS + "." + DynamicFieldSlot.UUID, model.getUuid());
			List<DynamicFieldTemplate> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, DynamicFieldTemplate.class);

			for (int i = 0; i < entities.size(); i++) {

				boolean removed = false;
				for (int j = 0; j < entities.get(i).getDynamicFieldSlots().size(); j++) {
					if (entities.get(i).getDynamicFieldSlots().get(j).getUuid().equals(model.getUuid())) {
						entities.get(i).getDynamicFieldSlots().remove(j);
						removed = true;
						break;
					}
				}

				if (removed)
					modelService.saveEntity(entities.get(i), DynamicFieldTemplate.class);
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}

	}
}
