package com.beanframework.core.converter.populator;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.domain.Auditor;
import com.beanframework.common.domain.GenericEntity;
import com.beanframework.common.exception.PopulatorException;
import com.beanframework.common.service.ModelService;
import com.beanframework.core.data.AddressDto;
import com.beanframework.core.data.CompanyDto;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.data.DynamicFieldSlotDto;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.core.data.UserDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.dynamicfield.domain.DynamicField;
import com.beanframework.dynamicfield.domain.DynamicFieldSlot;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.internationalization.domain.Language;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.Company;
import com.beanframework.user.domain.User;
import com.beanframework.user.domain.UserGroup;

public abstract class AbstractPopulator<T extends GenericEntity, E extends GenericDto> {
	
	@Autowired
	protected ModelService modelService;

	protected void populateGeneric(T source, E target) throws PopulatorException {
		try {
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	protected AuditorDto populateAuditor(Auditor source) {
		if(source == null)
			return null;
		
		AuditorDto target = new AuditorDto();
		target.setUuid(source.getUuid());
		target.setId(source.getId());
		target.setName(source.getName());

		return target;
	}
	
	public UserDto populateUser(UUID uuid) throws PopulatorException {
		if(uuid == null)
			return null;
		
		try {
			User source = modelService.findOneByUuid(uuid, User.class);
			UserDto target = new UserDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setName(source.getName());

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}
	
	public UserGroupDto populateUserGroup(UUID uuid) throws PopulatorException {
		if (uuid == null)
			return null;

		try {
			UserGroup source = modelService.findOneByUuid(uuid, UserGroup.class);
			UserGroupDto target = new UserGroupDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));
			target.setName(source.getName());

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}
	
	public UserGroupDto populateUserGroup(UserGroup source) throws PopulatorException {
		if (source == null)
			return null;

		try {
			UserGroupDto target = new UserGroupDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

			target.setName(source.getName());
			
			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public CompanyDto populateCompany(UUID uuid) throws PopulatorException {
		if (uuid == null)
			return null;

		try {
			Company source = modelService.findOneByUuid(uuid, Company.class);
			CompanyDto target = new CompanyDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));
			target.setName(source.getName());

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public AddressDto populateAddress(UUID uuid) throws PopulatorException {
		if (uuid == null)
			return null;

		try {
			Address source = modelService.findOneByUuid(uuid, Address.class);
			AddressDto target = new AddressDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));
			target.setStreetName(source.getStreetName());

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}
	
	public DynamicFieldSlotDto populateDynamicFieldSlot(UUID uuid) throws PopulatorException {
		if (uuid == null)
			return null;

		try {
			DynamicFieldSlot source = modelService.findOneByUuid(uuid, DynamicFieldSlot.class);
			DynamicFieldSlotDto target = new DynamicFieldSlotDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

			target.setName(source.getName());
			target.setSort(source.getSort());
			target.setDynamicField(populateDynamicField(source.getDynamicField()));

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public DynamicFieldDto populateDynamicField(UUID uuid) throws PopulatorException {
		if (uuid == null)
			return null;

		try {
			DynamicField source = modelService.findOneByUuid(uuid, DynamicField.class);
			DynamicFieldDto target = new DynamicFieldDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

			target.setName(source.getName());
			target.setRequired(source.getRequired());
			target.setRule(source.getRule());
			target.setType(source.getType());
			target.setLabel(source.getLabel());
			target.setGrid(source.getGrid());

			target.setLanguage(populateLanguage(source.getLanguage()));

			for (UUID uuid1 : source.getEnumerations()) {
				target.getEnumerations().add(populateEnumeration(uuid1));
			}

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public LanguageDto populateLanguage(UUID uuid) throws PopulatorException {
		if (uuid == null)
			return null;

		try {
			Language source = modelService.findOneByUuid(uuid, Language.class);
			LanguageDto target = new LanguageDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

			target.setName(source.getName());
			target.setSort(source.getSort());
			target.setActive(source.getActive());

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}

	public EnumerationDto populateEnumeration(UUID uuid) throws PopulatorException {
		if (uuid == null)
			return null;

		try {
			Enumeration source = modelService.findOneByUuid(uuid, Enumeration.class);
			EnumerationDto target = new EnumerationDto();
			target.setUuid(source.getUuid());
			target.setId(source.getId());
			target.setCreatedDate(source.getCreatedDate());
			target.setLastModifiedDate(source.getLastModifiedDate());
			target.setCreatedBy(populateAuditor(source.getCreatedBy()));
			target.setLastModifiedBy(populateAuditor(source.getLastModifiedBy()));

			target.setName(source.getName());
			target.setSort(source.getSort());

			return target;
		} catch (Exception e) {
			throw new PopulatorException(e.getMessage(), e);
		}
	}
}
