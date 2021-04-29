package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.MyAccountDto;
import com.beanframework.core.data.UserFieldDto;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserField;

public class MyAccountPopulator extends AbstractPopulator<User, MyAccountDto> implements Populator<User, MyAccountDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(MyAccountPopulator.class);

	@Override
	public void populate(User source, MyAccountDto target) throws PopulatorException {
		try {
			populateGeneric(source, target);
			target.setName(source.getName());
			target.setPassword(source.getPassword());

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
