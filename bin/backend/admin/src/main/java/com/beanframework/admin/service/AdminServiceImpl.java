package com.beanframework.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.admin.AdminConstants;
import com.beanframework.admin.AdminSpecification;
import com.beanframework.admin.converter.DtoAdminConverter;
import com.beanframework.admin.domain.Admin;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.utils.PasswordUtils;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private DtoAdminConverter dtoAdminConverter;

	@Autowired
	private ModelService modelService;

	@Value(AdminConstants.Admin.DEFAULT_ID)
	private String defaultAdminId;

	@Value(AdminConstants.Admin.DEFAULT_PASSWORD)
	private String defaultAdminPassword;

	@Transactional(readOnly = true)
	@Override
	public Page<Admin> page(Admin admin, Pageable pageable) {
		Page<Admin> page = modelService.findPage(AdminSpecification.findByCriteria(admin), pageable, Admin.class);
		List<Admin> content = dtoAdminConverter.convert(page.getContent());
		return new PageImpl<Admin>(content, page.getPageable(), page.getTotalElements());
	}

	@Transactional(readOnly = false)
	@Override
	public Admin authenticate(String id, String password) {

		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(password)) {
			return null;
		}

		Map<String, String> map = new HashMap<String, String>();
		map.put(Admin.ID, id);
		Admin admin = modelService.findOneByFields(map, Admin.class);

		if (admin == null) {
			if (StringUtils.compare(password, defaultAdminPassword) != 0) {
				return null;
			} else {
				admin = modelService.create(Admin.class);
				admin.setId(defaultAdminId);

				return admin;
			}
		} else {
			if (PasswordUtils.isMatch(password, admin.getPassword()) == false) {
				return null;
			} else {
				return dtoAdminConverter.convert(admin);
			}
		}
	}
}
