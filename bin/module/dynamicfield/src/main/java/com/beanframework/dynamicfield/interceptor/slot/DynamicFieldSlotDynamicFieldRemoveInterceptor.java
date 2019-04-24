package com.beanframework.dynamicfield.interceptor.slot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;

public class DynamicFieldSlotDynamicFieldRemoveInterceptor extends AbstractRemoveInterceptor<DynamicField> {

	@Autowired
	private ModelService modelService;

	@Override
	public void onRemove(DynamicField model, InterceptorContext context) throws InterceptorException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(DynamicFieldSlot.DYNAMIC_FIELD + "." + DynamicField.UUID, model.getUuid());
			List<DynamicFieldSlot> entities = modelService.findEntityByPropertiesAndSorts(properties, null, null, null, DynamicFieldSlot.class);

			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getDynamicField().getUuid().equals(model.getUuid())) {
					entities.get(i).setDynamicField(null);
					modelService.saveEntity(entities.get(i), DynamicFieldSlot.class);
				}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}

	}
}
