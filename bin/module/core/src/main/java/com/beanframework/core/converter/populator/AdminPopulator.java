package com.beanframework.core.converter.populator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.AdminDto;
import com.beanframework.user.domain.Admin;

public class AdminPopulator extends AbstractPopulator<Admin, AdminDto> implements Populator<Admin, AdminDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(AdminPopulator.class);

	@Override
	public void populate(Admin source, AdminDto target) throws PopulatorException {
		populateCommon(source, target);
		target.setName(source.getName());
		target.setAccountNonExpired(source.getAccountNonExpired());
		target.setAccountNonLocked(source.getAccountNonLocked());
		target.setCredentialsNonExpired(source.getCredentialsNonExpired());
		target.setEnabled(source.getEnabled());
		target.setName(source.getName());
	}

}
