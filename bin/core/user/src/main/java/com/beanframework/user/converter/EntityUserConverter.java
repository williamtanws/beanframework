package com.beanframework.user.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserField;
import com.beanframework.user.utils.PasswordUtils;

public class EntityUserConverter implements EntityConverter<User, User> {

	@Autowired
	private ModelService modelService;

	@Override
	public User convert(User source) throws ConverterException {

		User prototype;
		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(User.UUID, source.getUuid());
				User exists = modelService.findOneEntityByProperties(properties, User.class);

				if (exists != null) {
					prototype = exists;
				} else {
					prototype = modelService.create(User.class);
				}
			} else {
				prototype = modelService.create(User.class);
			}
		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), this);
		}

		return convert(source, prototype);
	}

	private User convert(User source, User prototype) throws ConverterException {

		if (source.getId() != null)
			prototype.setId(source.getId());
		prototype.setLastModifiedDate(new Date());

		prototype.setAccountNonExpired(source.getAccountNonExpired());
		prototype.setAccountNonLocked(source.getAccountNonLocked());
		prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
		prototype.setEnabled(source.getEnabled());
		if (StringUtils.isNotBlank(source.getPassword()))
			prototype.setPassword(PasswordUtils.encode(source.getPassword()));
		if (source.getUserFields() != null && source.getUserFields().isEmpty() == false) {
			for (int i = 0; i < prototype.getUserFields().size(); i++) {
				for (UserField sourceUserField : source.getUserFields()) {
					if (prototype.getUserFields().get(i).getUuid().equals(sourceUserField.getUuid())) {
						prototype.getUserFields().get(i).setValue(sourceUserField.getValue());
					}
				}
			}
		}

		return prototype;
	}

}
