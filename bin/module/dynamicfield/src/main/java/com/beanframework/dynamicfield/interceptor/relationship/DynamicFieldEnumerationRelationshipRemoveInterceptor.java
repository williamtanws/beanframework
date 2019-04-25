package com.beanframework.dynamicfield.interceptor.relationship;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.context.InterceptorContext;
import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.AbstractRemoveInterceptor;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.enumuration.domain.Enumeration;

public class DynamicFieldEnumerationRelationshipRemoveInterceptor extends AbstractRemoveInterceptor<Enumeration> {

	@Autowired
	private ModelService modelService;

	@Override
	public void onRemove(Enumeration model, InterceptorContext context) throws InterceptorException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(DynamicField.ENUMERATIONS + "." + Enumeration.UUID, model.getUuid());
			List<DynamicField> entities = modelService.findEntityByPropertiesAndSorts(properties, null, null, null, DynamicField.class);

			for (int i = 0; i < entities.size(); i++) {

				boolean removed = false;
				for (int j = 0; j < entities.get(i).getEnumerations().size(); j++) {
					if (entities.get(i).getEnumerations().get(j).getUuid().equals(model.getUuid())) {
						entities.get(i).getEnumerations().remove(j);
						removed = true;
						break;
					}
				}

				if (removed)
					modelService.saveEntity(entities.get(i), DynamicField.class);
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}

	}
}
