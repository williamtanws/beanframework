package com.beanframework.media.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.hibernate.Hibernate;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.media.MediaConstants;
import com.beanframework.media.domain.Media;

@Service
public class MediaServiceImpl implements MediaService {

	@Autowired
	private ModelService modelService;

	@Value(MediaConstants.MEDIA_LOCATION)
	public String MEDIA_LOCATION;

	@Value(MediaConstants.MEDIA_URL)
	public String MEDIA_URL;

	@Override
	public Media create() throws Exception {
		return modelService.create(Media.class);
	}

	@Cacheable(value = "MediaOne", key = "#uuid")
	@Override
	public Media findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, true, Media.class);
	}

	@Cacheable(value = "MediaOneProperties", key = "#properties")
	@Override
	public Media findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, true, Media.class);
	}

	@Cacheable(value = "MediasSorts", key = "'sorts:'+#sorts+',initialize:'+#initialize")
	@Override
	public List<Media> findEntityBySorts(Map<String, Direction> sorts, boolean initialize) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, initialize, Media.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "MediaOne", key = "#model.uuid", condition = "#model.uuid != null"), //
			@CacheEvict(value = "MediaOneProperties", allEntries = true), //
			@CacheEvict(value = "MediasSorts", allEntries = true), //
			@CacheEvict(value = "MediasPage", allEntries = true), //
			@CacheEvict(value = "MediasHistory", allEntries = true) }) //
	@Override
	public Media saveEntity(Media model) throws BusinessException {
		return (Media) modelService.saveEntity(model, Media.class);
	}

	@Caching(evict = { //
			@CacheEvict(value = "MediaOne", key = "#uuid"), //
			@CacheEvict(value = "MediaOneProperties", allEntries = true), //
			@CacheEvict(value = "MediasSorts", allEntries = true), //
			@CacheEvict(value = "MediasPage", allEntries = true), //
			@CacheEvict(value = "MediasHistory", allEntries = true) })
	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Media model = modelService.findOneEntityByUuid(uuid, true, Media.class);
			modelService.deleteByEntity(model, Media.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Cacheable(value = "MediasPage", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public <T> Page<Media> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), false, Media.class);
	}

	@Cacheable(value = "MediasPage", key = "'count'")
	@Override
	public int count() throws Exception {
		return modelService.count(Media.class);
	}

	@Cacheable(value = "MediasHistory", key = "'dataTableRequest:'+#dataTableRequest")
	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		List<Object[]> histories = modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Media.class);
		for (Object[] objects : histories) {
			Media media = (Media) objects[0];
			Hibernate.initialize(media.getLastModifiedBy());
			Hibernate.unproxy(media.getLastModifiedBy());
		}
		return histories;
	}

	@Cacheable(value = "MediasHistory", key = "'count, dataTableRequest:'+#dataTableRequest")
	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Media.class);
	}

	@Override
	public Media createByMultipartFile(MultipartFile file, String location) throws Exception {
		Media media = modelService.create(Media.class);
		media.setFileName(file.getOriginalFilename());
		media.setFileType(file.getContentType());
		media.setFileSize(file.getSize());
		media.setTitle(file.getName());
		media = (Media) modelService.saveEntity(media, Media.class);

		File mediaFolder = new File(MEDIA_LOCATION + File.separator + media.getUuid().toString());
		FileUtils.forceMkdir(mediaFolder);

		File original = new File(mediaFolder.getAbsolutePath(), file.getOriginalFilename());
		file.transferTo(original);

		media.setUrl(MEDIA_URL + "/" + media.getUuid());
		media.setLocation(MEDIA_LOCATION + "/" + location);
		media = (Media) modelService.saveEntity(media, Media.class);

		return media;
	}
}
