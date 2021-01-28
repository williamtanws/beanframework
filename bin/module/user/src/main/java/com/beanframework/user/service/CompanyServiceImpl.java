package com.beanframework.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.beanframework.common.service.ModelService;
import com.beanframework.internationalization.domain.Country;
import com.beanframework.user.domain.Address;
import com.beanframework.user.domain.Company;
import com.beanframework.user.domain.User;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private ModelService modelService;

	@Override
	public void removeContactPersonRel(User model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Company.CONTACT_PERSON, model.getUuid());
		List<Company> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Company.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getContactPerson() != null)
					if (entities.get(i).getContactPerson().equals(model.getUuid())) {
						entities.get(i).setContactPerson(null);
						modelService.saveEntityQuietly(entities.get(i), Company.class);
					}
			}
	}

	@Override
	public void removeResponsibleCompanyRel(Company model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Company.RESPONSIBLE_COMPANY, model.getUuid());
		List<Company> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Company.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getResponsibleCompany() != null)
					if (entities.get(i).getResponsibleCompany().equals(model.getUuid())) {
						entities.get(i).setResponsibleCompany(null);
						modelService.saveEntityQuietly(entities.get(i), Company.class);
					}
			}
	}

	@Override
	public void removeCountryRel(Country model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Company.COUNTRY, model.getUuid());
		List<Company> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Company.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getCountry() != null)
					if (entities.get(i).getCountry().equals(model.getUuid())) {
						entities.get(i).setCountry(null);
						modelService.saveEntityQuietly(entities.get(i), Company.class);
					}
			}
	}

	@Override
	public void removeAddressesRel(Address model) throws Exception {
		Specification<Company> specification = new Specification<Company>() {
			private static final long serialVersionUID = 1L;

			public String toString() {
				return model.getUuid().toString();
			}

			@Override
			public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();

				predicates.add(cb.or(root.join(Company.ADDRESSES, JoinType.LEFT).in(model.getUuid())));

				if (predicates.isEmpty()) {
					return cb.and(predicates.toArray(new Predicate[predicates.size()]));
				} else {
					return cb.or(predicates.toArray(new Predicate[predicates.size()]));
				}

			}
		};

		List<Company> entities = modelService.findBySpecificationBySort(specification, Company.class);

		for (int i = 0; i < entities.size(); i++) {

			boolean removed = false;
			for (int j = 0; j < entities.get(i).getAddresses().size(); j++) {
				entities.get(i).getAddresses().remove(model.getUuid());
			}

			if (removed)
				modelService.saveEntity(entities.get(i));
		}
	}

	@Override
	public void removeShippingAddressRel(Address model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Company.SHIPPING_ADDRESS, model.getUuid());
		List<Company> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Company.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getShippingAddress() != null)
					if (entities.get(i).getShippingAddress().equals(model.getUuid())) {
						entities.get(i).setShippingAddress(null);
						modelService.saveEntityQuietly(entities.get(i), Company.class);
					}
			}
	}

	@Override
	public void removeUnloadingAddressRel(Address model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Company.UNLOADING_ADDRESS, model.getUuid());
		List<Company> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Company.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getUnloadingAddress() != null)
					if (entities.get(i).getUnloadingAddress().equals(model.getUuid())) {
						entities.get(i).setUnloadingAddress(null);
						modelService.saveEntityQuietly(entities.get(i), Company.class);
					}
			}
	}

	@Override
	public void removeBillingAddressRel(Address model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Company.BILLING_ADDRESS, model.getUuid());
		List<Company> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Company.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getUnloadingAddress() != null)
					if (entities.get(i).getUnloadingAddress().equals(model.getUuid())) {
						entities.get(i).setUnloadingAddress(null);
						modelService.saveEntityQuietly(entities.get(i), Company.class);
					}
			}
	}

	@Override
	public void removeContactAddressRel(Address model) throws Exception {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Company.CONTACT_ADDRESS, model.getUuid());
		List<Company> entities = modelService.findByPropertiesBySortByResult(properties, null, null, null, Company.class);

		if (entities != null)
			for (int i = 0; i < entities.size(); i++) {

				if (entities.get(i).getUnloadingAddress() != null)
					if (entities.get(i).getUnloadingAddress().equals(model.getUuid())) {
						entities.get(i).setUnloadingAddress(null);
						modelService.saveEntityQuietly(entities.get(i), Company.class);
					}
			}
	}
}
