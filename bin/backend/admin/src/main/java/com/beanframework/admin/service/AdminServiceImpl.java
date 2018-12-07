package com.beanframework.admin.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.admin.AdminConstants;
import com.beanframework.admin.converter.DtoAdminConverter;
import com.beanframework.admin.converter.EntityAdminConverter;
import com.beanframework.admin.domain.Admin;
import com.beanframework.admin.domain.AdminSpecification;
import com.beanframework.admin.repository.AdminRepository;
import com.beanframework.user.utils.PasswordUtils;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private EntityAdminConverter entityAdminConverter;

	@Autowired
	private DtoAdminConverter dtoAdminConverter;

	@Value(AdminConstants.Admin.DEFAULT_ID)
	private String defaultAdminId;

	@Value(AdminConstants.Admin.DEFAULT_PASSWORD)
	private String defaultAdminPassword;

	@Override
	public Admin create() {
		return initDefaults(new Admin());
	}

	@Override
	public Admin initDefaults(Admin admin) {
		admin.setEnabled(true);
		admin.setAccountNonExpired(true);
		admin.setAccountNonLocked(true);
		admin.setCredentialsNonExpired(true);
		
		return admin;
	}

	@Transactional(readOnly = false)
	@Override
	public Admin save(Admin admin) {
		admin = entityAdminConverter.convert(admin);
		admin = adminRepository.save(admin);
		admin = dtoAdminConverter.convert(admin);

		return admin;
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(UUID uuid) {
		adminRepository.deleteById(uuid);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteAll() {
		adminRepository.deleteAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Admin> findEntityByUuid(UUID uuid) {
		return adminRepository.findByUuid(uuid);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Admin> findEntityById(String id) {
		return adminRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Admin findByUuid(UUID uuid) {
		Optional<Admin> admin = adminRepository.findByUuid(uuid);

		if (admin.isPresent()) {
			return dtoAdminConverter.convert(admin.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Admin findById(String id) {
		Optional<Admin> admin = adminRepository.findById(id);

		if (admin.isPresent()) {
			return dtoAdminConverter.convert(admin.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Admin> page(Admin admin, Pageable pageable) {
		Page<Admin> page = adminRepository.findAll(AdminSpecification.findByCriteria(admin), pageable);
		List<Admin> content = dtoAdminConverter.convert(page.getContent());
		return new PageImpl<Admin>(content, page.getPageable(), page.getTotalElements());
	}

	@Transactional(readOnly = false)
	@Override
	public Admin authenticate(String id, String password) {

		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(password)) {
			return null;
		}

		Optional<Admin> adminEntity = adminRepository.findById(id);

		if (adminEntity.isPresent()) {
			if (PasswordUtils.isMatch(password, adminEntity.get().getPassword()) == false) {
				return null;
			} else {
				return dtoAdminConverter.convert(adminEntity.get());
			}
		} else {
			if (StringUtils.compare(password, defaultAdminPassword) != 0) {
				return null;
			} else {
				Admin admin = create();
				admin.setId(defaultAdminId);

				return admin;
			}
		}
	}

	public AdminRepository getAdminRepository() {
		return adminRepository;
	}

	public void setAdminRepository(AdminRepository adminRepository) {
		this.adminRepository = adminRepository;
	}

	public EntityAdminConverter getEntityAdminConverter() {
		return entityAdminConverter;
	}

	public void setEntityAdminConverter(EntityAdminConverter entityAdminConverter) {
		this.entityAdminConverter = entityAdminConverter;
	}

	public DtoAdminConverter getDtoAdminConverter() {
		return dtoAdminConverter;
	}

	public void setDtoAdminConverter(DtoAdminConverter dtoAdminConverter) {
		this.dtoAdminConverter = dtoAdminConverter;
	}

}
