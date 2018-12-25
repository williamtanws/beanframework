package com.beanframework.customer.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	}
	
	private void generateCustomerField(Customer model) throws InterceptorException {
		try {
			Map<String, Object> dynamicFieldProperties = new HashMap<String, Object>();
			dynamicFieldProperties.put(DynamicField.FIELD_GROUP, Customer.class.getSimpleName());
			List<DynamicField> dynamicFields = modelService.findEntityByProperties(dynamicFieldProperties, DynamicField.class);

			for (DynamicField dynamicField : dynamicFields) {
				
				boolean add = true;
				for (UserField modelUserGroupField : model.getUserFields()) {
					if (dynamicField.getUuid().equals(modelUserGroupField.getDynamicField().getUuid())) {
						add = false;
					}
				}

				if (add) {
					UserField userGroupField = modelService.create(UserField.class);
					userGroupField.setDynamicField(dynamicField);
					userGroupField.setId(model.getId() + "_" + dynamicField.getId());

					userGroupField.setUser(model);
					model.getUserFields().add(userGroupField);
				}
			}
		} catch (Exception e) {
			throw new InterceptorException(e.getMessage(), e);
		}
	}
}
