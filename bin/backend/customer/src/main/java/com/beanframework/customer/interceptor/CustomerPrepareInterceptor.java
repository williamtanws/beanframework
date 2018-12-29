package com.beanframework.customer.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.exception.InterceptorException;
import com.beanframework.common.interceptor.PrepareInterceptor;
import com.beanframework.common.service.ModelService;
import com.beanframework.customer.domain.Customer;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.user.domain.UserField;

public class CustomerPrepareInterceptor implements PrepareInterceptor<Customer> {
	
	@Autowired
	private ModelService modelService;

	@Override
	public void onPrepare(Customer model) throws InterceptorException {
		generateCustomerField(model);
		
		for (int i = 0; i < model.getFields().size(); i++) {
			if (StringUtils.isBlank(model.getFields().get(i).getValue())) {
				model.getFields().get(i).setValue(null);
			}
		}
	}
	
	private void generateCustomerField(Customer model) throws InterceptorException {
		try {
			Map<String, Object> dynamicFieldProperties = new HashMap<String, Object>();
			dynamicFieldProperties.put(DynamicField.FIELD_GROUP, Customer.class.getSimpleName());
			List<DynamicField> dynamicFields = modelService.findEntityByProperties(dynamicFieldProperties, DynamicField.class);

			for (DynamicField dynamicField : dynamicFields) {
				
				boolean add = true;
				for (UserField modelUserGroupField : model.getFields()) {
					if (dynamicField.getUuid().equals(modelUserGroupField.getDynamicField().getUuid())) {
						add = false;
					}
				}

				if (add) {
					UserField userGroupField = modelService.create(UserField.class);
					userGroupField.setDynamicField(dynamicField);
					userGroupField.setId(model.getId() + "_" + dynamicField.getId());

					userGroupField.setUser(model);
					model.getFields().add(userGroupField);
				}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}
}
