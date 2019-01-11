package com.beanframework.employee.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.hibernate.Hibernate;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.service.ModelService;
import com.beanframework.employee.EmployeeConstants;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.utils.PasswordUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private ModelService modelService;

	@Value(EmployeeConstants.PROFILE_PICTURE_LOCATION)
	public String PROFILE_PICTURE_LOCATION;

	@Value("${module.employee.profile.picture.thumbnail.height:100}")
	public int PROFILE_PICTURE_THUMBNAIL_HEIGHT;

	@Value("${module.employee.profile.picture.thumbnail.weight:100}")
	public int PROFILE_PICTURE_THUMBNAIL_WEIGHT;

	@Override
	public void saveProfilePicture(Employee employee, MultipartFile picture) throws IOException {
		if (picture != null && picture.isEmpty() == false) {

			File profilePictureFolder = new File(PROFILE_PICTURE_LOCATION + File.separator + employee.getUuid());
			FileUtils.forceMkdir(profilePictureFolder);

//			String mimetype = picture.getContentType();
//			String extension = mimetype.split("/")[1];

			File original = new File(PROFILE_PICTURE_LOCATION + File.separator + employee.getUuid() + File.separator + "original.png");
			original = new File(original.getAbsolutePath());
			picture.transferTo(original);

			File thumbnail = new File(PROFILE_PICTURE_LOCATION + File.separator + employee.getUuid() + File.separator + "thumbnail.png");
			BufferedImage img = ImageIO.read(original);
			BufferedImage thumbImg = Scalr.resize(img, Method.ULTRA_QUALITY, Mode.AUTOMATIC, PROFILE_PICTURE_THUMBNAIL_WEIGHT, PROFILE_PICTURE_THUMBNAIL_HEIGHT, Scalr.OP_ANTIALIAS);
			ImageIO.write(thumbImg, "png", thumbnail);
		}
	}

	@Override
	public Employee updatePrincipal(Employee employee) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employeePrincipal = (Employee) auth.getPrincipal();

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(employeePrincipal, employeePrincipal.getPassword(), employeePrincipal.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);

		return employeePrincipal;
	}

	@Override
	public void deleteEmployeeProfilePictureByUuid(UUID uuid) {
		File employeeProfilePictureFolder = new File(PROFILE_PICTURE_LOCATION + File.separator + uuid);
		try {
			if (employeeProfilePictureFolder.exists()) {
				FileUtils.deleteDirectory(employeeProfilePictureFolder);
			}
		} catch (IOException e) {
			logger.error(e.toString(), e);
		}
	}

	@Override
	public void deleteAllEmployeeProfilePicture() {
		File employeeProfilePictureFolder = new File(PROFILE_PICTURE_LOCATION);
		try {
			if (employeeProfilePictureFolder.exists()) {
				FileUtils.deleteDirectory(employeeProfilePictureFolder);
			}
		} catch (IOException e) {
			logger.error(e.toString(), e);
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Employee getCurrentEmployee() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && auth.getPrincipal() instanceof Employee) {

			Employee employee = (Employee) auth.getPrincipal();
			return employee;
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Employee findDtoAuthenticate(String id, String password) throws Exception {

		if (StringUtils.isBlank(id) || StringUtils.isBlank(password)) {
			return null;
		}

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Employee.ID, id);
		Employee employee = modelService.findOneEntityByProperties(properties, Employee.class);

		if (employee != null) {
			if (PasswordUtils.isMatch(password, employee.getPassword()) == false) {
				return null;
			}
		} else {
			return null;
		}

		employee.setAuthorities(getAuthorities(employee.getUserGroups(), new HashSet<String>()));

		return modelService.getDto(employee, Employee.class);
	}

	//processedUserGroupUuids to prevent infinity loop
	private Set<GrantedAuthority> getAuthorities(List<UserGroup> userGroups, Set<String> processedUserGroupUuids) {

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		for (UserGroup userGroup : userGroups) {
			if (processedUserGroupUuids.contains(userGroup.getUuid().toString()) == false) {
				processedUserGroupUuids.add(userGroup.getUuid().toString());

				Hibernate.initialize(userGroup.getUserAuthorities());
				for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {

					if (Boolean.TRUE.equals(userAuthority.getEnabled())) {
						StringBuilder authority = new StringBuilder();

						Hibernate.initialize(userAuthority.getUserPermission());
						authority.append(userAuthority.getUserPermission().getId());
						authority.append("_");
						Hibernate.initialize(userAuthority.getUserRight());
						authority.append(userAuthority.getUserRight().getId());

						authorities.add(new SimpleGrantedAuthority(authority.toString()));
					}

				}

				Hibernate.initialize(userGroup.getUserGroups());
				if (userGroup.getUserGroups() != null && userGroup.getUserGroups().isEmpty() == false) {
					authorities.addAll(getAuthorities(userGroup.getUserGroups(), processedUserGroupUuids));
				}
			}
		}

		return authorities;
	}
}
