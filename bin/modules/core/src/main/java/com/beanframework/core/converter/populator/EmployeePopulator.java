package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.core.data.UserFieldDto;
import com.beanframework.user.domain.Employee;
import com.beanframework.user.domain.UserField;

public class EmployeePopulator extends AbstractPopulator<Employee, EmployeeDto> implements Populator<Employee, EmployeeDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EmployeePopulator.class);

	@Override
	public void populate(Employee source, EmployeeDto target) throws PopulatorException {
		try {
			populateGeneric(source, target);
			target.setName(source.getName());
			target.setAccountNonExpired(source.getAccountNonExpired());
			target.setAccountNonLocked(source.getAccountNonLocked());
			target.setCredentialsNonExpired(source.getCredentialsNonExpired());
			target.setEnabled(source.getEnabled());
			target.setParameters(source.getParameters());

			for (UUID uuid : source.getUserGroups()) {
				target.getUserGroups().add(populateUserGroup(uuid));
			}
			for (UUID uuid : source.getCompanies()) {
				target.getCompanies().add(populateCompany(uuid));
			}
			for (UUID uuid : source.getAddresses()) {
				target.getAddresses().add(populateAddress(uuid));
			}
			for (UserField field : source.getFields()) {
				target.getFields().add(populateUserField(field));
			}

			Collections.sort(target.getFields(), new Comparator<UserFieldDto>() {
				@Override
				public int compare(UserFieldDto o1, UserFieldDto o2) {
					if (o1.getDynamicFieldSlot().getSort() == null)
						return o2.getDynamicFieldSlot().getSort() == null ? 0 : 1;

					if (o2.getDynamicFieldSlot().getSort() == null)
						return -1;

					return o1.getDynamicFieldSlot().getSort() - o2.getDynamicFieldSlot().getSort();
				}
			});
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public UserFieldDto populateUserField(UserField source) throws PopulatorException {
		if (source == null)
			return null;

		try {
			UserFieldDto target = new UserFieldDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

			target.setValue(source.getValue());
			target.setDynamicFieldSlot(populateDynamicFieldSlot(source.getDynamicFieldSlot()));

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}
}
