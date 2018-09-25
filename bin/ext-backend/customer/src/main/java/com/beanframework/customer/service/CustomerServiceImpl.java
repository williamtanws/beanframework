package com.beanframework.customer.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beanframework.customer.converter.DtoCustomerConverter;
import com.beanframework.customer.converter.EntityCustomerConverter;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.domain.CustomerSpecification;
import com.beanframework.customer.repository.CustomerRepository;
import com.beanframework.user.domain.UserAuthority;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.utils.PasswordUtils;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private EntityCustomerConverter entityCustomerConverter;

	@Autowired
	private DtoCustomerConverter dtoCustomerConverter;

	@Override
	public Customer create() {
		return initDefaults(new Customer());
	}

	@Override
	public Customer initDefaults(Customer customer) {
		customer.setEnabled(true);
		customer.setAccountNonExpired(true);
		customer.setAccountNonLocked(true);
		customer.setCredentialsNonExpired(true);
		
		return customer;
	}

	@Transactional(readOnly = false)
	@Override
	public Customer save(Customer customer) {
		customer = entityCustomerConverter.convert(customer);
		customer = customerRepository.save(customer);
		customer = dtoCustomerConverter.convert(customer);

		return customer;
	}

	@Transactional(readOnly = false)
	@Override
	public void delete(UUID uuid) {
		customerRepository.deleteById(uuid);
	}
	
	@Transactional(readOnly = false)
	@Override
	public void deleteById(String id) {
		customerRepository.deleteById(id);
	}

	@Transactional(readOnly = false)
	@Override
	public void deleteAll() {
		customerRepository.deleteAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Customer> findEntityByUuid(UUID uuid) {
		return customerRepository.findByUuid(uuid);
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Customer> findEntityById(String id) {
		return customerRepository.findById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Customer findByUuid(UUID uuid) {
		Optional<Customer> customer = customerRepository.findByUuid(uuid);

		if (customer.isPresent()) {
			return dtoCustomerConverter.convert(customer.get());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Customer findById(String id) {
		Optional<Customer> customer = customerRepository.findById(id);

		if (customer.isPresent()) {
			return dtoCustomerConverter.convert(customer.get());
		} else {
			return null;
		}
	}
	
	@Transactional(readOnly = true)
	@Override
	public boolean existsById(String id) {
		return customerRepository.existsById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Customer> page(Customer customer, Pageable pageable) {
		Page<Customer> page = customerRepository.findAll(CustomerSpecification.findByCriteria(customer), pageable);
		List<Customer> content = dtoCustomerConverter.convert(page.getContent());
		return new PageImpl<Customer>(content, page.getPageable(), page.getTotalElements());
	}

	@Transactional(readOnly = false)
	@Override
	public Customer authenticate(String id, String password) {

		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(password)) {
			return null;
		}

		Customer customer = findById(id);

		if (customer != null) {
			if (PasswordUtils.isMatch(password, customer.getPassword()) == false) {
				return null;
			}
		} else {
			return null;
		}

		for (UserGroup userGroup : customer.getUserGroups()) {
			for (UserAuthority userAuthority : userGroup.getUserAuthorities()) {

				if (Boolean.TRUE.equals(userAuthority.getEnabled())) {
					StringBuilder authority = new StringBuilder();
					authority.append(userAuthority.getUserPermission().getId());
					authority.append("_");
					authority.append(userAuthority.getUserRight().getId());

					customer.getAuthorities().add(new SimpleGrantedAuthority(authority.toString()));
				}

			}
		}

		return customer;
	}

	public CustomerRepository getCustomerRepository() {
		return customerRepository;
	}

	public void setCustomerRepository(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public EntityCustomerConverter getEntityCustomerConverter() {
		return entityCustomerConverter;
	}

	public void setEntityCustomerConverter(EntityCustomerConverter entityCustomerConverter) {
		this.entityCustomerConverter = entityCustomerConverter;
	}

	public DtoCustomerConverter getDtoCustomerConverter() {
		return dtoCustomerConverter;
	}

	public void setDtoCustomerConverter(DtoCustomerConverter dtoCustomerConverter) {
		this.dtoCustomerConverter = dtoCustomerConverter;
	}

}
