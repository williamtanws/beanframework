package com.beanframework.media.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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

	@Override
	public Media findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, Media.class);
	}

	@Override
	public Media findOneEntityByProperties(Map<String, Object> properties) throws Exception {
		return modelService.findOneEntityByProperties(properties, Media.class);
	}

	@Override
	public List<Media> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Media.class);
	}

	@Override
	public Media saveEntity(Media model) throws BusinessException {
		return (Media) modelService.saveEntity(model, Media.class);
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Media model = modelService.findOneEntityByUuid(uuid, Media.class);

			File mediaFile = new File(MEDIA_LOCATION, model.getLocation());
			FileUtils.forceDelete(mediaFile);
			modelService.deleteByEntity(model, Media.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public <T> Page<Media> findEntityPage(DataTableRequest dataTableRequest, Specification<T> specification) throws Exception {
		return modelService.findEntityPage(specification, dataTableRequest.getPageable(), Media.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(Media.class);
	}

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		List<Object[]> histories = modelService.findHistories(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Media.class);
		return histories;
	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Media.class);
	}

	@Override
	public Media storeFile(Media media, MultipartFile file, String location) throws Exception {
		File mediaFolder = new File(MEDIA_LOCATION + File.separator + location);
		FileUtils.forceMkdir(mediaFolder);

		File original = new File(mediaFolder.getAbsolutePath(), media.getFileName());
		file.transferTo(original);

		media.setUrl(MEDIA_URL + "/" + media.getUuid());
		media.setLocation(location);

		media = (Media) modelService.saveEntity(media, Media.class);

		return media;
	}

	@Override
	public int countMediaByProperties(Map<String, Object> properties) throws Exception {
		return modelService.count(properties, Media.class);
	}
}
