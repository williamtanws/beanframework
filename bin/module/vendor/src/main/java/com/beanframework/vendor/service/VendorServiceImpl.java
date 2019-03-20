package com.beanframework.vendor.service;

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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.media.MediaConstants;
import com.beanframework.user.service.AuditorService;
import com.beanframework.vendor.VendorConstants;
import com.beanframework.vendor.domain.Vendor;

@Service
public class VendorServiceImpl implements VendorService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private AuditorService auditorService;
	
	@Value(MediaConstants.MEDIA_LOCATION)
	public String MEDIA_LOCATION;
	
	@Value(VendorConstants.VENDOR_MEDIA_LOCATION)
	public String PROFILE_PICTURE_LOCATION;

	@Value(VendorConstants.VENDOR_PROFILE_PICTURE_THUMBNAIL_WIDTH)
	public int VENDOR_PROFILE_PICTURE_THUMBNAIL_WIDTH;

	@Value(VendorConstants.VENDOR_PROFILE_PICTURE_THUMBNAIL_HEIGHT)
	public int VENDOR_PROFILE_PICTURE_THUMBNAIL_HEIGHT;

	@Override
	public Vendor create() throws Exception {
		return modelService.create(Vendor.class);
	}

	@Cacheable(value = "VendorOne", key = "#uuid")
	@Override
	public Vendor findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, true, Vendor.class);
	}

	@Cacheable(value = "VendorOneProperties", key = "#properties")
	@Override
	public Vendor findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true, Vendor.class);
	}

	@Cacheable(value = "VendorsSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<Vendor> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, Vendor.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "VendorOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "VendorOneProperties", allEntries = true), //
			@CacheEvict(value = "VendorsSorts", allEntries = true), //
			@CacheEvict(value = "VendorsPage", allEntries = true), //
			@CacheEvict(value = "VendorsHistory", allEntries = true) }) //
	@Override
	public Vendor saveEntity(Vendor model) throws BusinessException {
		model = (Vendor) modelService.saveEntity(model, Vendor.class);
		auditorService.saveEntity(model);
		return model;
	}

	@Caching(evict = { //
			@CacheEvict(value = "VendorOne", key = "#uuid"), //
			@CacheEvict(value = "VendorOneProperties", allEntries = true), //
			@CacheEvict(value = "VendorsSorts", allEntries = true), //
			@CacheEvict(value = "VendorsPage", allEntries = true), //
			@CacheEvict(value = "VendorsHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Vendor model = modelService.findOneEntityByUuid(uuid, true, Vendor.class);
			modelService.deleteByEntity(model, Vendor.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "VendorsPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<Vendor> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), false, Vendor.class);
	}

	@Cacheable(value = "VendorsPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(Vendor.class);
	}
	
	@Override
	public void saveProfilePicture(Vendor model, MultipartFile picture) throws IOException {
		if (picture != null && picture.isEmpty() == false) {

			File profilePictureFolder = new File(MEDIA_LOCATION, PROFILE_PICTURE_LOCATION + File.separator + model.getUuid());
			FileUtils.forceMkdir(profilePictureFolder);

			File original = new File(MEDIA_LOCATION, PROFILE_PICTURE_LOCATION + File.separator + model.getUuid() + File.separator + "original.png");
			original = new File(original.getAbsolutePath());
			picture.transferTo(original);

			File thumbnail = new File(MEDIA_LOCATION, PROFILE_PICTURE_LOCATION + File.separator + model.getUuid() + File.separator + "thumbnail.png");
			BufferedImage img = ImageIO.read(original);
			BufferedImage thumbImg = Scalr.resize(img, Method.ULTRA_QUALITY, Mode.AUTOMATIC, VENDOR_PROFILE_PICTURE_THUMBNAIL_WIDTH, VENDOR_PROFILE_PICTURE_THUMBNAIL_HEIGHT, Scalr.OP_ANTIALIAS);
			ImageIO.write(thumbImg, "png", thumbnail);
		}
	}

	@Override
	public void saveProfilePicture(Vendor model, InputStream inputStream) throws IOException {

		File profilePictureFolder = new File(MEDIA_LOCATION, PROFILE_PICTURE_LOCATION + File.separator + model.getUuid());
		FileUtils.forceMkdir(profilePictureFolder);

		File original = new File(MEDIA_LOCATION, PROFILE_PICTURE_LOCATION + File.separator + model.getUuid() + File.separator + "original.png");
		original = new File(original.getAbsolutePath());
		FileUtils.copyInputStreamToFile(inputStream, original);

		File thumbnail = new File(MEDIA_LOCATION, PROFILE_PICTURE_LOCATION + File.separator + model.getUuid() + File.separator + "thumbnail.png");
		BufferedImage img = ImageIO.read(original);
		BufferedImage thumbImg = Scalr.resize(img, Method.ULTRA_QUALITY, Mode.AUTOMATIC, VENDOR_PROFILE_PICTURE_THUMBNAIL_WIDTH, VENDOR_PROFILE_PICTURE_THUMBNAIL_HEIGHT, Scalr.OP_ANTIALIAS);
		ImageIO.write(thumbImg, "png", thumbnail);

	}

	@Cacheable(value = "VendorsHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		return modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Vendor.class);

	}

	@Cacheable(value = "VendorsHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Vendor.class);
	}
	
	@Override
	public Vendor getCurrentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {

			Vendor user = (Vendor) auth.getPrincipal();
			return user;
		} else {
			return null;
		}
	}

	@Override
	public Vendor updatePrincipal(Vendor model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Vendor principal = (Vendor) auth.getPrincipal();
		principal.setId(model.getId());
		principal.setName(model.getName());
		principal.setPassword(model.getPassword());

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), auth.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(token);

		return principal;
	}
}
