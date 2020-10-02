package com.beanframework.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.criteria.AuditCriterion;
import org.hibernate.envers.query.order.AuditOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.service.ModelService;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.internationalization.domain.Region;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.Company;
import com.beanframework.user.domain.User;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private ModelService modelService;

	@Override
	public List<Object[]> findHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(dataTableRequest.getAuditCriterion());

		List<AuditOrder> auditOrders = new ArrayList<AuditOrder>();
		if (dataTableRequest.getAuditOrder() != null)
			auditOrders.add(dataTableRequest.getAuditOrder());

		List<Object[]> histories = modelService.findHistory(false, auditCriterions, auditOrders, dataTableRequest.getStart(), dataTableRequest.getLength(), Address.class);
		return histories;
	}

	@Override
	public int findCountHistory(DataTableRequest dataTableRequest) throws Exception {

		List<AuditCriterion> auditCriterions = new ArrayList<AuditCriterion>();
		if (dataTableRequest.getAuditCriterion() != null)
			auditCriterions.add(AuditEntity.id().eq(UUID.fromString(dataTableRequest.getUniqueId())));

		return modelService.countHistory(false, auditCriterions, null, dataTableRequest.getStart(), dataTableRequest.getLength(), Address.class);
	}

	@Override
	public void removeOwnerRel(User model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Address.OWNER, model.getUuid());
		List<Address> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Address.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getOwner() != null)
					if (entities.get(i).getOwner().equals(model.getUuid())) {
						entities.get(i).setOwner(null);
						modelService.saveEntityQuietly(entities.get(i), Address.class);
					}
			}
	}

	@Override
	public void removeCountryRel(Country model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Address.COUNTRY, model.getUuid());
		List<Address> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Address.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getCountry() != null)
					if (entities.get(i).getCountry().equals(model.getUuid())) {
						entities.get(i).setCountry(null);
						modelService.saveEntityQuietly(entities.get(i), Address.class);
					}
			}
	}

	@Override
	public void removeRegionRel(Region model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Address.COUNTRY, model.getUuid());
		List<Region> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Region.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getCountry() != null)
					if (entities.get(i).getCountry().equals(model.getUuid())) {
						entities.get(i).setCountry(null);
						modelService.saveEntityQuietly(entities.get(i), Region.class);
					}
			}
	}

	@Override
	public void removeShippingAddressRel(Address model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Company.CONTACT_ADDRESS, model.getUuid());
		List<Address> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Address.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getShippingAddress() != null)
					if (entities.get(i).getShippingAddress().equals(model.getUuid())) {
						entities.get(i).setShippingAddress(null);
						modelService.saveEntityQuietly(entities.get(i), Address.class);
					}
			}
	}

	@Override
	public void removeBillingAddressRel(Address model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Company.CONTACT_ADDRESS, model.getUuid());
		List<Address> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Address.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getBillingAddress() != null)
					if (entities.get(i).getBillingAddress().equals(model.getUuid())) {
						entities.get(i).setBillingAddress(null);
						modelService.saveEntityQuietly(entities.get(i), Address.class);
					}
			}

	}

	@Override
	public void removeContactAddressRel(Address model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Company.CONTACT_ADDRESS, model.getUuid());
		List<Address> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Address.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getContactAddress() != null)
					if (entities.get(i).getContactAddress().equals(model.getUuid())) {
						entities.get(i).setContactAddress(null);
						modelService.saveEntityQuietly(entities.get(i), Address.class);
					}
			}

	}

	@Override
	public void removeDefaultPaymentAddressRel(Address model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Company.CONTACT_ADDRESS, model.getUuid());
		List<Address> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Address.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getDefaultPaymentAddress() != null)
					if (entities.get(i).getDefaultPaymentAddress().equals(model.getUuid())) {
						entities.get(i).setDefaultPaymentAddress(null);
						modelService.saveEntityQuietly(entities.get(i), Address.class);
					}
			}

	}

	@Override
	public void removeDefaultShipmentAddressRel(Address model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Company.CONTACT_ADDRESS, model.getUuid());
		List<Address> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Address.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getDefaultShipmentAddress() != null)
					if (entities.get(i).getDefaultShipmentAddress().equals(model.getUuid())) {
						entities.get(i).setDefaultShipmentAddress(null);
						modelService.saveEntityQuietly(entities.get(i), Address.class);
					}
			}

	}
}
