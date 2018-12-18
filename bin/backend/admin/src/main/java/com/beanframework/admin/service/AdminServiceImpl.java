package com.beanframework.admin.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.beanframework.admin.AdminConstants;
import com.beanframework.admin.domain.Admin;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.utils.PasswordUtils;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private ModelService modelService;

	@Value(AdminConstants.Admin.DEFAULT_ID)
	private String defaultAdminId;

	@Value(AdminConstants.Admin.DEFAULT_PASSWORD)
	private String defaultAdminPassword;

	@Override
	public Admin authenticate(String id, String password) throws Exception {

		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(password)) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Admin.ID, id);
		Admin admin = modelService.findOneEntityByProperties(map, Admin.class);

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
				return admin;
			}
		}
	}
}
