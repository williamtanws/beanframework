package com.beanframework.employee.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.employee.EmployeeConstants;
import com.beanframework.employee.converter.DtoEmployeeConverter;
import com.beanframework.employee.converter.DtoEmployeePrincipalConverter;
import com.beanframework.employee.converter.DtoEmployeeProfileConverter;
import com.beanframework.employee.converter.EntityEmployeeConverter;
import com.beanframework.employee.converter.EntityEmployeeProfileConverter;
import com.beanframework.employee.domain.Employee;
import com.beanframework.employee.domain.EmployeeSpecification;
import com.beanframework.employee.repository.EmployeeRepository;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.utils.PasswordUtils;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EntityEmployeeConverter entityEmployeeConverter;

	@Autowired
	private EntityEmployeeProfileConverter entityEmployeeProfileConverter;

	@Autowired
	private DtoEmployeeConverter dtoEmployeeConverter;

	@Autowired
	private DtoEmployeeProfileConverter dtoEmployeeProfileConverter;

	@Autowired
	private DtoEmployeePrincipalConverter dtoEmployeePrincipalConverter;

	@Value(EmployeeConstants.PROFILE_PICTURE_LOCATION)
	public String PROFILE_PICTURE_LOCATION;

	@Value("${module.employee.profile.picture.thumbnail.height:100}")
	public int PROFILE_PICTURE_THUMBNAIL_HEIGHT;

	@Value("${module.employee.profile.picture.thumbnail.weight:100}")
	public int PROFILE_PICTURE_THUMBNAIL_WEIGHT;

	@Override
	public Employee create() {
		return initDefaults(new Employee());
	}

	@Override
	public Employee initDefaults(Employee employee) {
		employee.setEnabled(true);
		employee.setAccountNonExpired(true);
		employee.setAccountNonLocked(true);
		employee.setCredentialsNonExpired(true);

		return employee;
	}

	@Transactional(readOnly = false)
	@Override
	public Employee save(Employee employee) {

		employee = entityEmployeeConverter.convert(employee);
		employee = employeeRepository.save(employee);
		employee = dtoEmployeeConverter.convert(employee);

		return employee;
	}

	@Transactional(readOnly = false)
	@Override
	public Employee saveProfile(Employee employee, MultipartFile picture) throws IOException {

		employee = entityEmployeeProfileConverter.convert(employee);
		employee = employeeRepository.save(employee);
		employee = dtoEmployeeProfileConverter.convert(employee);

		updatePrincipal(employee);

		saveProfilePicture(employee, picture);

		return employee;
	}

	private void saveProfilePicture(Employee employee, MultipartFile picture) throws IOException {
		if (picture != null && picture.isEmpty() == false) {

			File profilePictureFolder = new File(PROFILE_PICTURE_LOCATION + File.separator + employee.getUuid());
			FileUtils.forceMkdir(profilePictureFolder);

//			String mimetype = picture.getContentType();
//			String extension = mimetype.split("/")[1];

			File original = new File(
					PROFILE_PICTURE_LOCATION + File.separator + employee.getUuid() + File.separator + "original.png");
			original = new File(original.getAbsolutePath());
			picture.transferTo(original);

			File thumbnail = new File(
					PROFILE_PICTURE_LOCATION + File.separator + employee.getUuid() + File.separator + "thumbnail.png");
			BufferedImage img = ImageIO.read(original);
			BufferedImage thumbImg = Scalr.resize(img, Method.ULTRA_QUALITY, Mode.AUTOMATIC,
					PROFILE_PICTURE_THUMBNAIL_WEIGHT, PROFILE_PICTURE_THUMBNAIL_HEIGHT, Scalr.OP_ANTIALIAS);
			ImageIO.write(thumbImg, "png", thumbnail);
		}
	}

	@Override
	public Employee updatePrincipal(Employee employee) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Employee employeePrincipal = (Employee) auth.getPrincipal();
		employeePrincipal = dtoEmployeePrincipalConverter.convert(employee);

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(employeePrincipal,
				employeePrincipal.getPassword(), employeePrincipal.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);

		return employeePrincipal;
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(UUID uuid) {
		employeeRepository.deleteById(uuid);

		deleteEmployeeProfilePictureByUuid(uuid);
	}
	
	@Transactional(readOnly = false)
	@Override
	public void delete(String id) {
		Optional<Employee> employee = employeeRepository.findById(id);
		
		employeeRepository.deleteById(id);
		
		deleteEmployeeProfilePictureByUuid(employee.get().getUuid());
	}

	private void deleteEmployeeProfilePictureByUuid(UUID uuid) {
		File employeeProfilePictureFolder = new File(PROFILE_PICTURE_LOCATION + File.separator + uuid);
		try {
			if (employeeProfilePictureFolder.exists()) {
				FileUtils.deleteDirectory(employeeProfilePictureFolder);
			}
		} catch (IOException e) {
			logger.error(e.toString(), e);
		}
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteAll() {
		employeeRepository.deleteAll();

		deleteAllEmployeeProfilePicture();
	}

	private void deleteAllEmployeeProfilePicture() {
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
	public Optional<Employee> findEntityByUuid(UUID uuid) {
		return employeeRepository.findByUuid(uuid);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Employee> findEntityById(String id) {
		return employeeRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Employee findByUuid(UUID uuid) {
		Optional<Employee> employee = employeeRepository.findByUuid(uuid);

		if (employee.isPresent()) {
			return dtoEmployeeConverter.convert(employee.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Employee findById(String id) {
		Optional<Employee> employee = employeeRepository.findById(id);

		if (employee.isPresent()) {
			return dtoEmployeeConverter.convert(employee.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Employee> page(Employee employee, Pageable pageable) {
		Page<Employee> page = employeeRepository.findAll(EmployeeSpecification.findByCriteria(employee), pageable);
		List<Employee> content = dtoEmployeeConverter.convert(page.getContent());
		return new PageImpl<Employee>(content, page.getPageable(), page.getTotalElements());
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
	public Employee authenticate(String id, String password) {

		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(password)) {
			return null;
		}

		Optional<Employee> employeeEntity = employeeRepository.findById(id);

		if (employeeEntity.isPresent()) {
			if (PasswordUtils.isMatch(password, employeeEntity.get().getPassword()) == false) {
				return null;
			}
		} else {
			return null;
		}

		return getAuthenticated(employeeEntity.get());
	}

	private Employee getAuthenticated(Employee employeeEntity) {
		Employee employee = dtoEmployeeConverter.convert(employeeEntity);

		for (UserGroup userGroup : employee.getUserGroups()) {
			for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {

				if (Boolean.TRUE.equals(userAuthority.getEnabled())) {
					StringBuilder authority = new StringBuilder();
					authority.append(userAuthority.getUserPermission().getId());
					authority.append("_");
					authority.append(userAuthority.getUserRight().getId());

					employee.getAuthorities().add(new SimpleGrantedAuthority(authority.toString()));
				}

			}
		}

		return employee;
	}

	public EmployeeRepository getEmployeeRepository() {
		return employeeRepository;
	}

	public void setEmployeeRepository(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public EntityEmployeeConverter getEntityEmployeeConverter() {
		return entityEmployeeConverter;
	}

	public void setEntityEmployeeConverter(EntityEmployeeConverter entityEmployeeConverter) {
		this.entityEmployeeConverter = entityEmployeeConverter;
	}

	public DtoEmployeeConverter getDtoEmployeeConverter() {
		return dtoEmployeeConverter;
	}

	public void setDtoEmployeeConverter(DtoEmployeeConverter dtoEmployeeConverter) {
		this.dtoEmployeeConverter = dtoEmployeeConverter;
	}

}
