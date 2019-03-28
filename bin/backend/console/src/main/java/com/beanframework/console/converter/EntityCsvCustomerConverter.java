package com.beanframework.console.converter;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.CustomerCsv;
import com.beanframework.console.registry.ImportListener;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.service.CustomerService;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.domain.UserGroup;

@Component
public class EntityCsvCustomerConverter implements EntityConverter<CustomerCsv, Customer> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCsvCustomerConverter.class);

	@Autowired
	private ModelService modelService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Customer convert(CustomerCsv source, EntityConverterContext context) throws ConverterException {

		try {

			if (StringUtils.isNotBlank(source.getId())) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Customer.ID, source.getId());

				Customer prototype = customerService.findOneEntityByProperties(properties);

				if (prototype != null) {

					return convertToEntity(source, prototype);
				}
			}
			return convertToEntity(source, modelService.create(Customer.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}
	}

	public Customer convert(CustomerCsv source) throws ConverterException {
		return convert(source, new EntityConverterContext());
	}

	private Customer convertToEntity(CustomerCsv source, Customer prototype) throws ConverterException {

		try {
			prototype.setId(StringUtils.stripToNull(source.getId()));

			prototype.setAccountNonExpired(source.isAccountNonExpired());
			prototype.setAccountNonLocked(source.isAccountNonLocked());
			prototype.setCredentialsNonExpired(source.isCredentialsNonExpired());
			prototype.setEnabled(source.isEnabled());
			if (StringUtils.isNotBlank(source.getPassword()))
				prototype.setPassword(passwordEncoder.encode(source.getPassword()));

			prototype.setName(StringUtils.stripToNull(source.getName()));

			// Dynamic Field
			if (StringUtils.isNotBlank(source.getDynamicFieldSlotIds())) {
				String[] dynamicFields = source.getDynamicFieldSlotIds().split(ImportListener.SPLITTER);
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split(ImportListener.EQUALS)[0];
					String value = dynamicField.split(ImportListener.EQUALS)[1];

					boolean add = true;
					for (int i = 0; i < prototype.getFields().size(); i++) {
						if (StringUtils.equals(prototype.getFields().get(i).getId(), prototype.getId() + ImportListener.UNDERSCORE + dynamicFieldId)) {
							prototype.getFields().get(i).setValue(StringUtils.stripToNull(value));
							add = false;
						}
					}

					if (add) {
						Map<String, Object> dynamicFieldProperties = new HashMap<String, Object>();
						dynamicFieldProperties.put(DynamicField.ID, dynamicFieldId);
						DynamicField entityDynamicField = modelService.findOneEntityByProperties(dynamicFieldProperties, DynamicField.class);

						if (entityDynamicField == null) {
							LOGGER.error("DynamicField ID not exists: " + dynamicFieldId);
						} else {
							UserField field = new UserField();
							field.setValue(StringUtils.stripToNull(value));
							field.setDynamicField(entityDynamicField);
							field.setUser(prototype);
							prototype.getFields().add(field);
						}
					}
				}
			}

			// User Group
			if (StringUtils.isNotBlank(source.getUserGroupIds())) {
				String[] userGroupIds = source.getUserGroupIds().split(ImportListener.SPLITTER);
				for (int i = 0; i < userGroupIds.length; i++) {
					boolean add = true;
					for (UserGroup userGroup : prototype.getUserGroups()) {
						if (StringUtils.equals(userGroup.getId(), userGroupIds[i]))
							add = false;
					}

					if (add) {
						Map<String, Object> userGroupProperties = new HashMap<String, Object>();
						userGroupProperties.put(UserGroup.ID, userGroupIds[i]);
						UserGroup userGroup = modelService.findOneEntityByProperties(userGroupProperties, UserGroup.class);

						if (userGroup == null) {
							LOGGER.error("UserGroup ID not exists: " + userGroupIds[i]);
						} else {
							prototype.getUserGroups().add(userGroup);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
