package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.AddressDto;
import com.beanframework.core.data.CompanyDto;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.core.data.UserFieldDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.user.domain.Employee;

@Component
public class EmployeeFullPopulator extends AbstractPopulator<Employee, EmployeeDto> implements Populator<Employee, EmployeeDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(EmployeeFullPopulator.class);

	@Autowired
	private UserFieldFullPopulator userFieldFullPopulator;
	
	@Autowired
	private UserGroupBasicPopulator userGroupBasicPopulator;
	
	@Autowired
	private CompanyBasicPopulator companyBasicPopulator;
	
	@Autowired
	private AddressBasicPopulator addressBasicPopulator;

	@Override
	public void populate(Employee source, EmployeeDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setPassword(source.getPassword());
			target.setAccountNonExpired(source.getAccountNonExpired());
			target.setAccountNonLocked(source.getAccountNonLocked());
			target.setCredentialsNonExpired(source.getCredentialsNonExpired());
			target.setEnabled(source.getEnabled());
			target.setUserGroups(modelService.getDto(source.getUserGroups(), UserGroupDto.class, new DtoConverterContext(userGroupBasicPopulator)));
			target.setCompanies(modelService.getDto(source.getCompanies(), CompanyDto.class, new DtoConverterContext(companyBasicPopulator)));
			target.setAddresses(modelService.getDto(source.getAddresses(), AddressDto.class, new DtoConverterContext(addressBasicPopulator)));

			target.setFields(modelService.getDto(source.getFields(), UserFieldDto.class, new DtoConverterContext(userFieldFullPopulator)));
			if (target.getFields() != null)
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

}
