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
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.employee.EmployeeConstants;
import com.beanframework.employee.domain.Employee;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.AuditorService;
import com.beanframework.user.utils.PasswordUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	public static final String CACHE_EMPLOYEE_PROFILE = "EmployeeProfile";

	protected static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private ModelService modelService;

	@Autowired
	private AuditorService auditorService;

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
	public void deleteEmployeeProfilePictureByUuid(UUID uuid) {
		File employeeProfilePictureFolder = new File(PROFILE_PICTURE_LOCATION + File.separator + uuid);
		try {
			if (employeeProfilePictureFolder.exists()) {
				FileUtils.deleteDirectory(employeeProfilePictureFolder);
			}
		} catch (IOException e) {
			LOGGER.error(e.toString(), e);
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
			LOGGER.error(e.toString(), e);
		}
	}

	@Override
	public List<Employee> findEntityBySorts(Map<String, Direction> employeeSorts) throws Exception {
		return modelService.findCachedEntityByPropertiesAndSorts(null, employeeSorts, null, null, Employee.class);
	}

	@Override
	public Employee create() throws Exception {
		return modelService.create(Employee.class);
	}

	@CacheEvict(value=CACHE_EMPLOYEE_PROFILE, key="#employee.uuid")
	@Override
	public Employee saveEntity(Employee employee) throws BusinessException {
		Employee entity = (Employee) modelService.saveEntity(employee, Employee.class);
		auditorService.saveUser(entity);
		return entity;
	}
	
	@Override
	public Employee findAuthenticate(String id, String password) throws Exception {

		if (StringUtils.isBlank(id) || StringUtils.isBlank(password)) {
			throw new BadCredentialsException("Bad Credentials");
		}

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Employee.ID, id);
		Employee entity = modelService.findOneEntityByProperties(properties, Employee.class);

		if (entity == null) {
			throw new BadCredentialsException("Bad Credentials");
		} else {
			if (PasswordUtils.isMatch(password, entity.getPassword()) == false) {
				throw new BadCredentialsException("Bad Credentials");
			}
		}

		if (entity.getEnabled() == false) {
			throw new DisabledException("Account Disabled");
		}

		if (entity.getAccountNonExpired() == false) {
			throw new AccountExpiredException("Account Expired");
		}

		if (entity.getAccountNonLocked() == false) {
			throw new LockedException("Account Locked");
		}

		if (entity.getCredentialsNonExpired() == false) {
			throw new CredentialsExpiredException("Password Expired");
		}

		return entity;
	}

	
	@Override
	public Employee getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {

			Employee employee = (Employee) auth.getPrincipal();
			return employee;
		} else {
			return null;
		}
	}

	@Override
	public Employee updatePrincipal(Employee employee) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employeePrincipal = (Employee) auth.getPrincipal();
		employeePrincipal.setId(employee.getId());
		employeePrincipal.setName(employee.getName());
		employeePrincipal.setPassword(employee.getPassword());
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(employeePrincipal, employeePrincipal.getPassword(), auth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);

		return employeePrincipal;
	}
	
	@Override
	public Set<GrantedAuthority> getAuthorities(List<UserGroup> userGroups, Set<String> processedUserGroupUuids) {

		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		for (UserGroup userGroup : userGroups) {
			if (processedUserGroupUuids.contains(userGroup.getUuid().toString()) == false) {
				processedUserGroupUuids.add(userGroup.getUuid().toString());

				for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {

					if (Boolean.TRUE.equals(userAuthority.getEnabled())) {
						StringBuilder authority = new StringBuilder();

						authority.append(userAuthority.getUserPermission().getId());
						authority.append("_");
						authority.append(userAuthority.getUserRight().getId());

						authorities.add(new SimpleGrantedAuthority(authority.toString()));
					}

				}

				if (userGroup.getUserGroups() != null && userGroup.getUserGroups().isEmpty() == false) {
					authorities.addAll(getAuthorities(userGroup.getUserGroups(), processedUserGroupUuids));
				}
			}
		}

		return authorities;
	}
	
	@Cacheable(value=CACHE_EMPLOYEE_PROFILE, key="#uuid")
	@Override
	public Employee findCachedOneEntityByUuid(UUID uuid) throws Exception {
		Employee entity = modelService.findOneEntityByUuid(uuid, Employee.class);
		return entity;
	}

	@CacheEvict(value=CACHE_EMPLOYEE_PROFILE, key="#employee.uuid")
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {
		modelService.deleteByUuid(uuid, Employee.class);
	}
}
