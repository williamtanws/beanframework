package com.beanframework.user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.user.converter.DtoUserGroupConverter;
import com.beanframework.user.converter.EntityUserGroupConverter;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.domain.UserGroupSpecification;
import com.beanframework.user.repository.UserGroupRepository;

@Service
public class UserGroupServiceImpl implements UserGroupService {

	@Autowired
	private UserGroupRepository userGroupRepository;

	@Autowired
	private EntityUserGroupConverter entityUserGroupConverter;

	@Autowired
	private DtoUserGroupConverter dtoUserGroupConverter;

	@Override
	public UserGroup create() {
		return initDefaults(new UserGroup());
	}

	@Override
	public UserGroup initDefaults(UserGroup userGroup) {
		return userGroup;
	}

	@Transactional(readOnly = false)
	@Override
	public UserGroup save(UserGroup userGroup) {

		userGroup = entityUserGroupConverter.convert(userGroup);
		userGroup = userGroupRepository.save(userGroup);
		userGroup = dtoUserGroupConverter.convert(userGroup);

		return userGroup;
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(UUID uuid) {
		userGroupRepository.deleteById(uuid);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteAll() {
		userGroupRepository.deleteAll();
	}
	

	@Transactional(readOnly = false)
	@Override
	public void deleteLanguageByLanguageUuid(UUID uuid) {
		List<UserGroup> userGroups = userGroupRepository.findAll();
		for (UserGroup userGroup : userGroups) {
			for (int i = 0; i < userGroup.getUserGroupLangs().size(); i++) {
				if(userGroup.getUserGroupLangs().get(i).getLanguage().getUuid().equals(uuid)) {
					userGroup.getUserGroupLangs().remove(i);
					break;
				}
			}
		}
		userGroupRepository.saveAll(userGroups);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Optional<UserGroup> findEntityByUuid(UUID uuid) {
		return userGroupRepository.findByUuid(uuid);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Optional<UserGroup> findEntityById(String id) {
		return userGroupRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public UserGroup findByUuid(UUID uuid) {
		Optional<UserGroup> userGroup = userGroupRepository.findByUuid(uuid);

		if (userGroup.isPresent()) {
			return dtoUserGroupConverter.convert(userGroup.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public UserGroup findById(String id) {
		Optional<UserGroup> userGroup = userGroupRepository.findById(id);

		if (userGroup.isPresent()) {
			return dtoUserGroupConverter.convert(userGroup.get());
		} else {
			return null;
		}
	}

	@Override
	public boolean isIdExists(String id) {
		return userGroupRepository.isIdExists(id);
	}
	
	@Override
	public List<UserGroup> findByOrderByCreatedDate() {
		return dtoUserGroupConverter.convert(userGroupRepository.findByOrderByCreatedDate());
	}

	@Transactional(readOnly = true)
	@Override
	public Page<UserGroup> page(UserGroup userGroup, Pageable pageable) {
		Page<UserGroup> page = userGroupRepository.findAll(UserGroupSpecification.findByCriteria(userGroup), pageable);
		List<UserGroup> content = dtoUserGroupConverter.convert(page.getContent());
		return new PageImpl<UserGroup>(content, page.getPageable(), page.getTotalElements());
	}

	public UserGroupRepository getUserGroupRepository() {
		return userGroupRepository;
	}

	public void setUserGroupRepository(UserGroupRepository userGroupRepository) {
		this.userGroupRepository = userGroupRepository;
	}

	public EntityUserGroupConverter getEntityUserGroupConverter() {
		return entityUserGroupConverter;
	}

	public void setEntityUserGroupConverter(EntityUserGroupConverter entityUserGroupConverter) {
		this.entityUserGroupConverter = entityUserGroupConverter;
	}

	public DtoUserGroupConverter getDtoUserGroupConverter() {
		return dtoUserGroupConverter;
	}

	public void setDtoUserGroupConverter(DtoUserGroupConverter dtoUserGroupConverter) {
		this.dtoUserGroupConverter = dtoUserGroupConverter;
	}
}
