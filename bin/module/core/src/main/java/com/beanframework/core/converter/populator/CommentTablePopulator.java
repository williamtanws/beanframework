package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.admin.domain.Admin;
import com.beanframework.common.converter.AbstractPopulator;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.AdminDto;

public class CommentTablePopulator extends AbstractPopulator<Admin, AdminDto> implements Populator<Admin, AdminDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(CommentTablePopulator.class);

	@Override
	public void populate(Admin source, AdminDto target) throws PopulatorException {
		convertCommonProperties(source, target);
		target.setName(source.getName());
	}

}
