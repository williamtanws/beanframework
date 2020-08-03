package com.beanframework.core.converter.populator.history;

import java.util.Collections;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.converter.populator.AbstractPopulator;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.data.UserRightFieldDto;
import com.beanframework.user.domain.UserRight;

@Component
public class UserRightHistoryPopulator extends AbstractPopulator<UserRight, UserRightDto> implements Populator<UserRight, UserRightDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserRightHistoryPopulator.class);

	@Autowired
	private UserRightFieldHistoryPopulator userRightFieldHistoryPopulator;

	@Override
	public void populate(UserRight source, UserRightDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setSort(source.getSort());

			target.setFields(modelService.getDto(source.getFields(), UserRightFieldDto.class, new DtoConverterContext(userRightFieldHistoryPopulator)));
			if (target.getFields() != null)
				Collections.sort(target.getFields(), new Comparator<UserRightFieldDto>() {
					@Override
					public int compare(UserRightFieldDto o1, UserRightFieldDto o2) {
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

}
