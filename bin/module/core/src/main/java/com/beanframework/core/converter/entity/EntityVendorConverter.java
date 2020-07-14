package com.beanframework.core.converter.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.beanframework.common.context.EntityConverterContext;
import com.beanframework.common.converter.EntityConverter;
import com.beanframework.common.exception.ConverterException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.UserFieldDto;
import com.beanframework.core.data.VendorDto;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.vendor.domain.Vendor;

public class EntityVendorConverter implements EntityConverter<VendorDto, Vendor> {

	@Autowired
	private ModelService modelService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Vendor convert(VendorDto source, EntityConverterContext context) throws ConverterException {

		try {

			if (source.getUuid() != null) {
				Map<String, Object> properties = new HashMap<String, Object>();
				properties.put(Vendor.UUID, source.getUuid());
				Vendor prototype = modelService.findOneByProperties(properties, Vendor.class);

				if (prototype != null) {
					return convertToEntity(source, prototype);
				}
			}

			return convertToEntity(source, modelService.create(Vendor.class));

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

	}

	private Vendor convertToEntity(VendorDto source, Vendor prototype) throws ConverterException {

		try {
			Date lastModifiedDate = new Date();

			if (StringUtils.equals(StringUtils.stripToNull(source.getId()), prototype.getId()) == Boolean.FALSE) {
				prototype.setId(StringUtils.stripToNull(source.getId()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (source.getAccountNonExpired() == null) {
				if (prototype.getAccountNonExpired() != null) {
					prototype.setAccountNonExpired(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getAccountNonExpired() == null || prototype.getAccountNonExpired().equals(source.getAccountNonExpired()) == Boolean.FALSE) {
					prototype.setAccountNonExpired(source.getAccountNonExpired());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			if (source.getAccountNonLocked() == null) {
				if (prototype.getAccountNonLocked() != null) {
					prototype.setAccountNonLocked(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getAccountNonLocked() == null || prototype.getAccountNonLocked().equals(source.getAccountNonLocked()) == Boolean.FALSE) {
					prototype.setAccountNonLocked(source.getAccountNonLocked());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			if (source.getCredentialsNonExpired() == null) {
				if (prototype.getCredentialsNonExpired() != null) {
					prototype.setCredentialsNonExpired(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getCredentialsNonExpired() == null || prototype.getCredentialsNonExpired().equals(source.getCredentialsNonExpired()) == Boolean.FALSE) {
					prototype.setCredentialsNonExpired(source.getCredentialsNonExpired());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			if (source.getEnabled() == null) {
				if (prototype.getEnabled() != null) {
					prototype.setEnabled(null);
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			} else {
				if (prototype.getEnabled() == null || prototype.getEnabled().equals(source.getEnabled()) == Boolean.FALSE) {
					prototype.setEnabled(source.getEnabled());
					prototype.setLastModifiedDate(lastModifiedDate);
				}
			}

			if (StringUtils.isNotBlank(source.getPassword())) {
				prototype.setPassword(passwordEncoder.encode(source.getPassword()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			if (StringUtils.equals(StringUtils.stripToNull(source.getName()), prototype.getName()) == Boolean.FALSE) {
				prototype.setName(StringUtils.stripToNull(source.getName()));
				prototype.setLastModifiedDate(lastModifiedDate);
			}

			// Field
			if (source.getFields() != null && source.getFields().isEmpty() == Boolean.FALSE) {
				for (int i = 0; i < prototype.getFields().size(); i++) {
					for (UserFieldDto sourceField : source.getFields()) {

						if (prototype.getFields().get(i).getDynamicFieldSlot().getUuid().equals(sourceField.getDynamicFieldSlot().getUuid())) {
							if (StringUtils.equals(StringUtils.stripToNull(sourceField.getValue()), prototype.getFields().get(i).getValue()) == Boolean.FALSE) {
								prototype.getFields().get(i).setValue(StringUtils.stripToNull(sourceField.getValue()));

								prototype.getFields().get(i).setLastModifiedDate(lastModifiedDate);
								prototype.setLastModifiedDate(lastModifiedDate);
							}
						}
					}
				}
			}

			// User Groups
			if (source.getSelectedUserGroups() != null) {

				Iterator<UserGroup> userGroupsIterator = prototype.getUserGroups().iterator();
				while (userGroupsIterator.hasNext()) {
					UserGroup userGroup = userGroupsIterator.next();

					boolean remove = true;
					for (int i = 0; i < source.getSelectedUserGroups().length; i++) {
						if (userGroup.getUuid().equals(UUID.fromString(source.getSelectedUserGroups()[i]))) {
							remove = false;
						}
					}
					if (remove) {
						userGroupsIterator.remove();
						prototype.setLastModifiedDate(lastModifiedDate);
					}
				}

				for (int i = 0; i < source.getSelectedUserGroups().length; i++) {

					boolean add = true;
					userGroupsIterator = prototype.getUserGroups().iterator();
					while (userGroupsIterator.hasNext()) {
						UserGroup userGroup = userGroupsIterator.next();

						if (userGroup.getUuid().equals(UUID.fromString(source.getSelectedUserGroups()[i]))) {
							add = false;
						}
					}

					if (add) {
						UserGroup userGroup = modelService.findOneByUuid(UUID.fromString(source.getSelectedUserGroups()[i]), UserGroup.class);
						if (userGroup != null) {
							prototype.getUserGroups().add(userGroup);
							prototype.setLastModifiedDate(lastModifiedDate);
						}
					}
				}
			}

		} catch (Exception e) {
			throw new ConverterException(e.getMessage(), e);
		}

		return prototype;
	}

}
