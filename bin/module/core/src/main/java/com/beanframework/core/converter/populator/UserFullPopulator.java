package com.beanframework.core.converter.populator;

import java.util.Collections;
import java.util.Comparator;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beanframework.common.context.DtoConverterContext;
import com.beanframework.common.converter.Populator;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.core.data.AddressDto;
import com.beanframework.core.data.CompanyDto;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.data.UserFieldDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.Company;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserGroup;

@Component
public class UserFullPopulator extends AbstractPopulator<User, UserDto> implements Populator<User, UserDto> {

	protected static Logger LOGGER = LoggerFactory.getLogger(UserFullPopulator.class);
	
	@Autowired
	private UserFieldFullPopulator userFieldFullPopulator;
	
	@Autowired
	private UserGroupBasicPopulator userGroupBasicPopulator;
	
	@Autowired
	private CompanyBasicPopulator companyBasicPopulator;
	
	@Autowired
	private AddressBasicPopulator addressBasicPopulator;

	@Override
	public void populate(User source, UserDto target) throws PopulatorException {
		try {
			convertCommonProperties(source, target);
			target.setName(source.getName());
			target.setType(source.getType());
			target.setAccountNonExpired(source.getAccountNonExpired());
			target.setAccountNonLocked(source.getAccountNonLocked());
			target.setCredentialsNonExpired(source.getCredentialsNonExpired());
			target.setEnabled(source.getEnabled());
			
			if (source.getUserGroups() != null && source.getUserGroups().isEmpty() == false) {
				for (UUID uuid : source.getUserGroups()) {
					UserGroup entity = modelService.findOneByUuid(uuid, UserGroup.class);
					target.getUserGroups().add(modelService.getDto(entity, UserGroupDto.class, new DtoConverterContext(userGroupBasicPopulator)));
				}
			}
			if (source.getCompanies() != null && source.getCompanies().isEmpty() == false) {
				for (UUID uuid : source.getCompanies()) {
					Company entity = modelService.findOneByUuid(uuid, Company.class);
					target.getCompanies().add(modelService.getDto(entity, CompanyDto.class, new DtoConverterContext(companyBasicPopulator)));
				}
			}
			if (source.getAddresses() != null && source.getAddresses().isEmpty() == false) {
				for (UUID uuid : source.getAddresses()) {
					Address entity = modelService.findOneByUuid(uuid, Address.class);
					target.getAddresses().add(modelService.getDto(entity, AddressDto.class, new DtoConverterContext(addressBasicPopulator)));
				}
			}

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
