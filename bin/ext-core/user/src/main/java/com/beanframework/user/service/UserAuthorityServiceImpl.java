package com.beanframework.user.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.user.converter.DtoUserAuthorityConverter;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.repository.UserAuthorityRepository;

@Service
public class UserAuthorityServiceImpl implements UserAuthorityService {

	@Autowired
	private UserAuthorityRepository userAuthorityRepository;

	@Autowired
	private DtoUserAuthorityConverter dtoUserAuthorityConverter;

	@Override
	public UserAuthority create() {
		return new UserAuthority();
	}

	@Transactional(readOnly = true)
	@Override
	public List<UserAuthority> findByUserGroupUuid(UUID uuid) {
		List<UserAuthority> userAuthorities = userAuthorityRepository.findByUserGroupUuid(uuid);
		userAuthorities = dtoUserAuthorityConverter.convert(userAuthorities);

		return userAuthorities;
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteUserRightByUserRightUuid(UUID uuid) {
		userAuthorityRepository.deleteByUserRightUuid(uuid);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteUserRightByUserPermissionUuid(UUID uuid) {
		userAuthorityRepository.deleteByUserPermissionUuid(uuid);
	}

	public UserAuthorityRepository getUserAuthorityRepository() {
		return userAuthorityRepository;
	}

	public void setUserAuthorityRepository(UserAuthorityRepository userAuthorityRepository) {
		this.userAuthorityRepository = userAuthorityRepository;
	}

	public DtoUserAuthorityConverter getDtoUserAuthorityConverter() {
		return dtoUserAuthorityConverter;
	}

	public void setDtoUserAuthorityConverter(DtoUserAuthorityConverter dtoUserAuthorityConverter) {
		this.dtoUserAuthorityConverter = dtoUserAuthorityConverter;
	}

}
