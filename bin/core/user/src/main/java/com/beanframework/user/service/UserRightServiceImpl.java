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

import com.beanframework.user.converter.DtoUserRightConverter;
import com.beanframework.user.converter.EntityUserRightConverter;
import com.beanframework.user.domain.UserRight;
import com.beanframework.user.domain.UserRightSpecification;
import com.beanframework.user.repository.UserRightRepository;

@Service
public class UserRightServiceImpl implements UserRightService {

	@Autowired
	private UserRightRepository userRightRepository;

	@Autowired
	private EntityUserRightConverter entityUserRightConverter;

	@Autowired
	private DtoUserRightConverter dtoUserRightConverter;

	@Override
	public UserRight create() {
		return initDefaults(new UserRight());
	}

	@Override
	public UserRight initDefaults(UserRight userRight) {
		return userRight;
	}

	@Transactional(readOnly = false)
	@Override
	public UserRight save(UserRight userRight) {

		userRight = entityUserRightConverter.convert(userRight);
		userRight = userRightRepository.save(userRight);
		userRight = dtoUserRightConverter.convert(userRight);

		return userRight;
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(UUID uuid) {
		userRightRepository.deleteById(uuid);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteAll() {
		userRightRepository.deleteAll();
	}
	
	@Transactional(readOnly = false)
	@Override
	public void deleteLanguageByLanguageUuid(UUID uuid) {
		List<UserRight> userRights = userRightRepository.findAll();
		for (UserRight userRight : userRights) {
			for (int i = 0; i < userRight.getUserRightLangs().size(); i++) {
				if(userRight.getUserRightLangs().get(i).getLanguage().getUuid().equals(uuid)) {
					userRight.getUserRightLangs().remove(i);
					break;
				}
			}
		}
		userRightRepository.saveAll(userRights);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Optional<UserRight> findEntityByUuid(UUID uuid) {
		return userRightRepository.findByUuid(uuid);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<UserRight> findEntityById(String id) {
		return userRightRepository.findById(id);
	}


	@Transactional(readOnly = true)
	@Override
	public UserRight findByUuid(UUID uuid) {
		Optional<UserRight> userRight = userRightRepository.findByUuid(uuid);

		if (userRight.isPresent()) {
			return dtoUserRightConverter.convert(userRight.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public UserRight findById(String id) {
		Optional<UserRight> userRight = userRightRepository.findById(id);

		if (userRight.isPresent()) {
			return dtoUserRightConverter.convert(userRight.get());
		} else {
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<UserRight> findEntityAllByOrderBySortAsc() {
		return userRightRepository.findByOrderBySortAsc();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<UserRight> findByOrderByCreatedDateDesc() {
		return dtoUserRightConverter.convert(userRightRepository.findByOrderBySortAsc());
	}

	@Transactional(readOnly = true)
	@Override
	public Page<UserRight> page(UserRight userRight, Pageable pageable) {
		Page<UserRight> page = userRightRepository.findAll(UserRightSpecification.findByCriteria(userRight), pageable);
		List<UserRight> content = dtoUserRightConverter.convert(page.getContent());
		return new PageImpl<UserRight>(content, page.getPageable(), page.getTotalElements());
	}

	public UserRightRepository getUserRightRepository() {
		return userRightRepository;
	}

	public void setUserRightRepository(UserRightRepository userRightRepository) {
		this.userRightRepository = userRightRepository;
	}

	public EntityUserRightConverter getEntityUserRightConverter() {
		return entityUserRightConverter;
	}

	public void setEntityUserRightConverter(EntityUserRightConverter entityUserRightConverter) {
		this.entityUserRightConverter = entityUserRightConverter;
	}

	public DtoUserRightConverter getDtoUserRightConverter() {
		return dtoUserRightConverter;
	}

	public void setDtoUserRightConverter(DtoUserRightConverter dtoUserRightConverter) {
		this.dtoUserRightConverter = dtoUserRightConverter;
	}
}
