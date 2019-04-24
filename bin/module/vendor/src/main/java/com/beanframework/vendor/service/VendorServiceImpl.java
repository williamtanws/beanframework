package com.beanframework.vendor.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.service.ModelService;
import com.beanframework.user.service.AuditorService;
import com.beanframework.user.service.UserService;
import com.beanframework.vendor.domain.Vendor;
import com.beanframework.vendor.specification.VendorSpecification;

@Service
public class VendorServiceImpl implements VendorService {

	@Autowired
	private ModelService modelService;

	@Autowired
	private AuditorService auditorService;

	@Autowired
	private UserService userService;

	@Override
	public Vendor create() throws Exception {
		return modelService.create(Vendor.class);
	}

	@Override
	public Vendor findOneEntityByUuid(UUID uuid) throws Exception {
		return modelService.findOneEntityByUuid(uuid, Vendor.class);
	}

	@Override
	public Vendor findOneEntityByProperties(Map<String, Object> properties) throws Exception {

		return modelService.findOneEntityByProperties(properties, Vendor.class);
	}

	@Override
	public List<Vendor> findEntityBySorts(Map<String, Direction> sorts) throws Exception {
		return modelService.findEntityByPropertiesAndSorts(null, sorts, null, null, Vendor.class);
	}

	@Override
	public Vendor saveEntity(Vendor model) throws BusinessException {
		model = (Vendor) modelService.saveEntity(model, Vendor.class);
		auditorService.saveEntity(model);
		return model;
	}

	@Override
	public void deleteByUuid(UUID uuid) throws BusinessException {

		try {
			Vendor model = modelService.findOneEntityByUuid(uuid, Vendor.class);
			modelService.deleteByEntity(model, Vendor.class);

		} catch (Exception e) {
			throw new BusinessException(e.getMessage(), e);
		}
	}

	@Override
	public Page<Vendor> findEntityPage(DataTableRequest dataTableRequest) throws Exception {
		return modelService.findEntityPage(VendorSpecification.getSpecification(dataTableRequest), dataTableRequest.getPageable(), Vendor.class);
	}

	@Override
	public int count() throws Exception {
		return modelService.count(Vendor.class);
	}

	@Override
	public void saveProfilePicture(Vendor model, MultipartFile picture) throws IOException {
		userService.saveProfilePicture(model, picture);
	}

	@Override
	public void saveProfilePicture(Vendor model, InputStream inputStream) throws IOException {
		userService.saveProfilePicture(model, inputStream);
	}

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

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.findCountHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Vendor.class);
	}

	@Override
	public Vendor getCurrentUser() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {

			Vendor principal = (Vendor) auth.getPrincipal();
			return findOneEntityByUuid(principal.getUuid());
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
