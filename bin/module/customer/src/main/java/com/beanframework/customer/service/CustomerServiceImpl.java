package com.beanframework.customer.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.context.FetchContext;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.customer.CustomerConstants;
import com.beanframework.customer.domain.Customer;
import com.beanframework.media.MediaConstants;
import com.beanframework.user.service.AuditorService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private AuditorService auditorService;

	@Autowired
	private FetchContext fetchContext;

	@Value(MediaConstants.MEDIA_LOCATION)
	public String MEDIA_LOCATION;

	@Value(CustomerConstants.CUSTOMER_MEDIA_LOCATION)
	public String PROFILE_PICTURE_LOCATION;

	@Value(CustomerConstants.CUSTOMER_PROFILE_PICTURE_THUMBNAIL_WIDTH)
	public int CUSTOMER_PROFILE_PICTURE_THUMBNAIL_WIDTH;

	@Value(CustomerConstants.CUSTOMER_PROFILE_PICTURE_THUMBNAIL_HEIGHT)
	public int CUSTOMER_PROFILE_PICTURE_THUMBNAIL_HEIGHT;

	@Override
	public Customer create() throws Exception {
		return modelService.create(Customer.class);
	}

	@Override
	public Customer findOneEntityByUuid(UUID uuid) throws Exception {
		fetchContext.clearFetchProperties(Customer.class);

		fetchContext.addFetchProperty(Customer.class, Customer.USER_GROUPS);
		fetchContext.addFetchProperty(Customer.class, Customer.FIELDS);
		return modelService.findOneEntityByUuid(uuid, Customer.class);
	}

	@Override
	public Customer findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		fetchContext.clearFetchProperties(Customer.class);

		fetchContext.addFetchProperty(Customer.class, Customer.USER_GROUPS);
		fetchContext.addFetchProperty(Customer.class, Customer.FIELDS);
		return modelService.findOneEntityByProperties(properties, Customer.class);
	}

	@Override
	public List<Customer> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Customer.class);
	}

	@Override
	public Customer saveEntity(Customer model) throws BusinessException {
		model = (Customer) modelService.saveEntity(model, Customer.class);
		auditorService.saveEntity(model);
		return model;
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Customer model = modelService.findOneEntityByUuid(uuid, Customer.class);
			modelService.deleteByEntity(model, Customer.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public <T> Page<Customer> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), Customer.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(Customer.class);
	}

	@Override
	public void saveProfilePicture(Customer model, MultipartFile picture) throws IOException {
		if (picture != null && picture.isEmpty() == false) {

			File profilePictureFolder = new File(MEDIA_LOCATION, PROFILE_PICTURE_LOCATION + File.separator + model.getUuid());
			FileUtils.forceMkdir(profilePictureFolder);

			File original = new File(MEDIA_LOCATION, PROFILE_PICTURE_LOCATION + File.separator + model.getUuid() + File.separator + "original.png");
			original = new File(original.getAbsolutePath());
			picture.transferTo(original);

			File thumbnail = new File(MEDIA_LOCATION, PROFILE_PICTURE_LOCATION + File.separator + model.getUuid() + File.separator + "thumbnail.png");
			BufferedImage img = ImageIO.read(original);
			BufferedImage thumbImg = Scalr.resize(img, Method.ULTRA_QUALITY, Mode.AUTOMATIC, CUSTOMER_PROFILE_PICTURE_THUMBNAIL_WIDTH, CUSTOMER_PROFILE_PICTURE_THUMBNAIL_HEIGHT, Scalr.OP_ANTIALIAS);
			ImageIO.write(thumbImg, "png", thumbnail);
		}
	}

	@Override
	public void saveProfilePicture(Customer customer, InputStream inputStream) throws IOException {

		File profilePictureFolder = new File(MEDIA_LOCATION, PROFILE_PICTURE_LOCATION + File.separator + customer.getUuid());
		FileUtils.forceMkdir(profilePictureFolder);

		File original = new File(MEDIA_LOCATION, PROFILE_PICTURE_LOCATION + File.separator + customer.getUuid() + File.separator + "original.png");
		original = new File(original.getAbsolutePath());
		FileUtils.copyInputStreamToFile(inputStream, original);

		File thumbnail = new File(MEDIA_LOCATION, PROFILE_PICTURE_LOCATION + File.separator + customer.getUuid() + File.separator + "thumbnail.png");
		BufferedImage img = ImageIO.read(original);
		BufferedImage thumbImg = Scalr.resize(img, Method.ULTRA_QUALITY, Mode.AUTOMATIC, CUSTOMER_PROFILE_PICTURE_THUMBNAIL_WIDTH, CUSTOMER_PROFILE_PICTURE_THUMBNAIL_HEIGHT, Scalr.OP_ANTIALIAS);
		ImageIO.write(thumbImg, "png", thumbnail);

	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Customer.class);

	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Customer.class);
	}

	@Override
	public Customer getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {

			Customer user = (Customer) auth.getPrincipal();
			return user;
		} else {
			return null;
		}
	}

	@Override
	public Customer updatePrincipal(Customer model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Customer principal = (Customer) auth.getPrincipal();
		principal.setId(model.getId());
		principal.setName(model.getName());
		principal.setPassword(model.getPassword());

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), auth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);

		return principal;
	}
}
