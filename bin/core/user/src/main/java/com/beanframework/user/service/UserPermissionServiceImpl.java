package com.beanframework.user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.user.converter.DtoUserPermissionConverter;
import com.beanframework.user.converter.EntityUserPermissionConverter;
import com.beanframework.user.domain.UserPermission;
import com.beanframework.user.domain.UserPermissionSpecification;
import com.beanframework.user.repository.UserPermissionRepository;

@Service
public class UserPermissionServiceImpl implements UserPermissionService {

	@Autowired
	private UserPermissionRepository userPermissionRepository;

	@Autowired
	private EntityUserPermissionConverter entityUserPermissionConverter;

	@Autowired
	private DtoUserPermissionConverter dtoUserPermissionConverter;

	@Override
	public UserPermission create() {
		return initDefaults(new UserPermission());
	}

	@Override
	public UserPermission initDefaults(UserPermission userPermission) {
		return userPermission;
	}

	@Transactional(readOnly = false)
	@Override
	public UserPermission save(UserPermission userPermission) {

		userPermission = entityUserPermissionConverter.convert(userPermission);
		userPermission = userPermissionRepository.save(userPermission);
		userPermission = dtoUserPermissionConverter.convert(userPermission);

		return userPermission;

	}

	@Transactional(readOnly = false)
	@Override
	public void delete(UUID uuid) {
		userPermissionRepository.deleteById(uuid);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteAll() {
		userPermissionRepository.deleteAll();
	}
	
	@Transactional(readOnly = false)
	@Override
	public void deleteLanguageByLanguageUuid(UUID uuid) {
		List<UserPermission> userPermissions = userPermissionRepository.findAll();
		for (UserPermission userPermission : userPermissions) {
			Hibernate.initialize(userPermission.getUserPermissionLangs());
			for (int i = 0; i < userPermission.getUserPermissionLangs().size(); i++) {
				Hibernate.initialize(userPermission.getUserPermissionLangs().get(i).getLanguage());
				if(userPermission.getUserPermissionLangs().get(i).getLanguage().getUuid().equals(uuid)) {
					userPermission.getUserPermissionLangs().remove(i);
					break;
				}
			}
		}
		userPermissionRepository.saveAll(userPermissions);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Optional<UserPermission> findEntityByUuid(UUID uuid) {
		return userPermissionRepository.findByUuid(uuid);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<UserPermission> findEntityById(String id) {
		return userPermissionRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public UserPermission findByUuid(UUID uuid) {
		Optional<UserPermission> userPermission = userPermissionRepository.findByUuid(uuid);

		if (userPermission.isPresent()) {
			return dtoUserPermissionConverter.convert(userPermission.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public UserPermission findById(String id) {
		Optional<UserPermission> userPermission = userPermissionRepository.findById(id);

		if (userPermission.isPresent()) {
			return dtoUserPermissionConverter.convert(userPermission.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public boolean isIdExists(String id) {
		return userPermissionRepository.isIdExists(id);
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<UserPermission> findEntityAllByOrderBySortAsc() {
		return userPermissionRepository.findByOrderBySortAsc();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<UserPermission> findByOrderByCreatedDateDesc() {
		return dtoUserPermissionConverter.convert(userPermissionRepository.findByOrderBySortAsc());
	}

	@Transactional(readOnly = true)
	@Override
	public Page<UserPermission> page(UserPermission userPermission, Pageable pageable) {
		Page<UserPermission> page = userPermissionRepository
				.findAll(UserPermissionSpecification.findByCriteria(userPermission), pageable);
		List<UserPermission> content = dtoUserPermissionConverter.convert(page.getContent());
		return new PageImpl<UserPermission>(content, page.getPageable(), page.getTotalElements());
	}

	public UserPermissionRepository getUserPermissionRepository() {
		return userPermissionRepository;
	}

	public void setUserPermissionRepository(UserPermissionRepository userPermissionRepository) {
		this.userPermissionRepository = userPermissionRepository;
	}

	public EntityUserPermissionConverter getEntityUserPermissionConverter() {
		return entityUserPermissionConverter;
	}

	public void setEntityUserPermissionConverter(EntityUserPermissionConverter entityUserPermissionConverter) {
		this.entityUserPermissionConverter = entityUserPermissionConverter;
	}

	public DtoUserPermissionConverter getDtoUserPermissionConverter() {
		return dtoUserPermissionConverter;
	}

	public void setDtoUserPermissionConverter(DtoUserPermissionConverter dtoUserPermissionConverter) {
		this.dtoUserPermissionConverter = dtoUserPermissionConverter;
	}

}
