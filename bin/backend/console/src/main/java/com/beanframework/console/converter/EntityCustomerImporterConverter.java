package com.beanframework.console.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.console.csv.CustomerCsv;
import com.beanframework.console.registry.Importer;
import com.beanframework.customer.domain.Customer;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.utils.PasswordUtils;

@Component
public class EntityCustomerImporterConverter implements EntityConverter<CustomerCsv, Customer> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EntityCustomerImporterConverter.class);

	@Autowired
	private ModelService modelService;

	@Override
	public Customer convert(CustomerCsv source) throws ConverterException {

		try {

			if (source.getId() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Customer.ID, source.getId());

				Customer prototype = modelService.findOneEntityByProperties(properties, Customer.class);

				if (prototype != null) {
					Hibernate.initialize(prototype.getUserGroups());

					return convert(source, prototype);
				}
			}
			return convert(source, modelService.create(Customer.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}
	}

	private Customer convert(CustomerCsv source, Customer prototype) throws ConverterException {

		try {
			if (source.getId() != null)
				prototype.setId(source.getId());
			prototype.setLastModifiedDate(new Date());

			prototype.setAccountNonExpired(source.isAccountNonExpired());
			prototype.setAccountNonLocked(source.isAccountNonLocked());
			prototype.setCredentialsNonExpired(source.isCredentialsNonExpired());
			prototype.setEnabled(source.isEnabled());
			if (StringUtils.isNotBlank(source.getPassword()))
				prototype.setPassword(PasswordUtils.encode(source.getPassword()));
			
			// Dynamic Field
			if (source.getDynamicField() != null) {
				String[] dynamicFields = source.getDynamicField().split(Importer.SPLITTER);
				for (String dynamicField : dynamicFields) {
					String dynamicFieldId = dynamicField.split(Importer.EQUALS)[0];
					String value = dynamicField.split(Importer.EQUALS)[1];
					for (int i = 0; i < prototype.getFields().size(); i++) {
						if (prototype.getFields().get(i).getId().equals(prototype.getId() + Importer.UNDERSCORE + dynamicFieldId)) {
							prototype.getFields().get(i).setValue(value);
						}
					}
				}
			}
			
			// User Group
			String[] userGroupIds = source.getUserGroupIds().split(Importer.SPLITTER);
			for (int i = 0; i < userGroupIds.length; i++) {
				Map<String, Object> userGroupProperties = new HashMap<String, Object>();
				userGroupProperties.put(UserGroup.ID, userGroupIds[i]);
				UserGroup userGroup = modelService.findOneEntityByProperties(userGroupProperties, UserGroup.class);

				if (userGroup == null) {
					LOGGER.error("UserGroup not exists: " + userGroupIds[i]);
				} else {
					prototype.getUserGroups().add(userGroup);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
