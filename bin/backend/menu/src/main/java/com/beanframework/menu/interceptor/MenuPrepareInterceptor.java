package com.beanframework.menu.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.common.service.ModelService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.domain.MenuField;

public class MenuPrepareInterceptor implements PrepareInterceptor<Menu> {

	@Autowired
	private ModelService modelService;

	@Override
	public void onPrepare(Menu model) throws InterceptorException {
		
		generateMenuField(model);

		if (StringUtils.isBlank(model.getPath())) {
			model.setPath(null);
		}
		if (StringUtils.isBlank(model.getIcon())) {
			model.setIcon(null);
		}
		for (int i = 0; i < model.getMenuFields().size(); i++) {
			if (StringUtils.isBlank(model.getMenuFields().get(i).getValue())) {
				model.getMenuFields().get(i).setValue(null);
			}
		}
	}

	private void generateMenuField(Menu model) throws InterceptorException {
		try {
			Map<String, Object> dynamicFieldProperties = new HashMap<String, Object>();
			dynamicFieldProperties.put(DynamicField.FIELD_GROUP, Menu.class.getSimpleName());
			List<DynamicField> dynamicFields = modelService.findEntityByProperties(dynamicFieldProperties, DynamicField.class);

			for (DynamicField dynamicField : dynamicFields) {

				boolean add = true;
				for (MenuField modelUserGroupField : model.getMenuFields()) {
					if (dynamicField.getUuid().equals(modelUserGroupField.getDynamicField().getUuid())) {
						add = false;
					}
				}

				if (add) {
					MenuField userGroupField = modelService.create(MenuField.class);
					userGroupField.setDynamicField(dynamicField);
					userGroupField.setId(model.getId() + "_" + dynamicField.getId());

					userGroupField.setMenu(model);
					model.getMenuFields().add(userGroupField);
				}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}
}
