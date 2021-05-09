package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.data.UserRightAttributeDto;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightAttribute;

@Component
public class UserRightPopulator extends AbstractPopulator<UserRight, UserRightDto> implements Populator<UserRight, UserRightDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserRightPopulator.class);

	@Override
	public void populate(UserRight source, UserRightDto target) throws PopulatorException {
		try {
			populateGeneric(source, target);
			target.setName(source.getName());
			target.setSort(source.getSort());

			for (UserRightAttribute field : source.getAttributes()) {
				target.getAttributes().add(populateUserRightField(field));
			}

			Collections.sort(target.getAttributes(), new Comparator<UserRightAttributeDto>() {
				@Override
				public int compare(UserRightAttributeDto o1, UserRightAttributeDto o2) {
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
	
	public UserRightAttributeDto populateUserRightField(UserRightAttribute source) throws PopulatorException {
		if (source == null)
			return null;

		try {
			UserRightAttributeDto target = new UserRightAttributeDto();
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
