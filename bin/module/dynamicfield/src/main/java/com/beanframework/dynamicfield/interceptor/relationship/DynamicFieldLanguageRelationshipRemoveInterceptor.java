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
import com.beanframework.language.domain.Language;

public class DynamicFieldLanguageRelationshipRemoveInterceptor extends AbstractRemoveInterceptor<Language> {

	@Autowired
	private ModelService modelService;

	@Override
	public void onRemove(Language model, InterceptorContext context) throws InterceptorException {

		try {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(DynamicField.LANGUAGE + "." + Language.UUID, model.getUuid());
			List<DynamicField> entities = modelService.findEntityByPropertiesAndSorts(properties, null, null, null, DynamicField.class);

			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getLanguage() != null)
					if (entities.get(i).getLanguage().getUuid().equals(model.getUuid())) {
						entities.get(i).setLanguage(null);
						modelService.saveEntity(entities.get(i), DynamicField.class);
					}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}

	}
}
