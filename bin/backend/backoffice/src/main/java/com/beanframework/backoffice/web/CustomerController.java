package com.beanframework.backoffice.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebCustomerConstants;
import com.beanframework.backoffice.domain.CustomerSearch;
import com.beanframework.common.controller.AbstractCommonController;
import com.beanframework.common.service.ModelService;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.service.CustomerFacade;
import com.beanframework.user.domain.UserGroup;

@Controller
public class CustomerController extends AbstractCommonController {
	
	@Autowired
	private ModelService modelService;

	@Autowired
	private CustomerFacade customerFacade;
	
	@Value(WebCustomerConstants.Path.CUSTOMER)
	private String PATH_CUSTOMER;

	@Value(WebCustomerConstants.View.LIST)
	private String VIEW_CUSTOMER_LIST;

	@Value(WebCustomerConstants.LIST_SIZE)
	private int MODULE_CUSTOMER_LIST_SIZE;

	private Page<Customer> getPagination(Model model, @RequestParam Map<String, Object> requestParams) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_CUSTOMER_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isEmpty(propertiesStr) ? null
				: propertiesStr.split(WebBackofficeConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isEmpty(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		CustomerSearch customerSearch = (CustomerSearch) model.asMap().get(WebCustomerConstants.ModelAttribute.SEARCH);

		Customer customer = new Customer();
		customer.setId(customerSearch.getIdSearch());

		if (properties == null) {
			properties = new String[1];
			properties[0] = Customer.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<Customer> pagination = customerFacade.page(customer, page, size, direction, properties);

		model.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes,
			@RequestParam Map<String, Object> requestParams, CustomerSearch customerSearch) {
		int page = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(WebBackofficeConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_CUSTOMER_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(WebBackofficeConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(WebBackofficeConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute(CustomerSearch.ID_SEARCH, customerSearch.getIdSearch());
		redirectAttributes.addFlashAttribute(WebCustomerConstants.ModelAttribute.SEARCH, customerSearch);

		return redirectAttributes;
	}

	@ModelAttribute(WebCustomerConstants.ModelAttribute.CREATE)
	public Customer populateCustomerCreate(HttpServletRequest request) {
		return modelService.create(Customer.class);
	}

	@ModelAttribute(WebCustomerConstants.ModelAttribute.UPDATE)
	public Customer populateCustomerForm(HttpServletRequest request) {
		return modelService.create(Customer.class);
	}

	@ModelAttribute(WebCustomerConstants.ModelAttribute.SEARCH)
	public CustomerSearch populateCustomerSearch(HttpServletRequest request) {
		return new CustomerSearch();
	}

	@PreAuthorize(WebCustomerConstants.PreAuthorize.READ)
	@GetMapping(value = WebCustomerConstants.Path.CUSTOMER)
	public String list(@ModelAttribute(WebCustomerConstants.ModelAttribute.SEARCH) CustomerSearch customerSearch,
			@ModelAttribute(WebCustomerConstants.ModelAttribute.UPDATE) Customer customerUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) {

		model.addAttribute(WebBackofficeConstants.PAGINATION, getPagination(model, requestParams));

		if (customerUpdate.getUuid() != null) {
			Map<String, Object> properties = new HashMap<String, Object>();
			properties.put(Customer.UUID, customerUpdate.getUuid());
			Customer existingCustomer= modelService.findOneEntityByProperties(properties, Customer.class);
			
			if (existingCustomer != null) {
				
				Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
				sorts.put(UserGroup.CREATED_DATE, Sort.Direction.DESC);
				
				List<UserGroup> userGroups = modelService.findBySorts(sorts, UserGroup.class);
				
				for (int i = 0; i < userGroups.size(); i++) {
					for (UserGroup userGroup : existingCustomer.getUserGroups()) {
						if(userGroups.get(i).getUuid().equals(userGroup.getUuid())) {
							userGroups.get(i).setSelected("true");
						}
					}
				}
				existingCustomer.setUserGroups(userGroups);

				model.addAttribute(WebCustomerConstants.ModelAttribute.UPDATE, existingCustomer);
			} else {
				customerUpdate.setUuid(null);
				addErrorMessage(model, WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}
		
		return VIEW_CUSTOMER_LIST;
	}

	@PreAuthorize(WebCustomerConstants.PreAuthorize.CREATE)
	@PostMapping(value = WebCustomerConstants.Path.CUSTOMER, params="create")
	public RedirectView create(@ModelAttribute(WebCustomerConstants.ModelAttribute.SEARCH) CustomerSearch customerSearch,
			@ModelAttribute(WebCustomerConstants.ModelAttribute.CREATE) Customer customerCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (customerCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {
			
			List<UserGroup> userGroups = new ArrayList<UserGroup>();
			for (UserGroup userGroup : customerCreate.getUserGroups()) {
				if(BooleanUtils.parseBoolean(userGroup.getSelected())) {
					userGroups.add(userGroup);
				}
			}
			customerCreate.setUserGroups(userGroups);
			
			try {
				modelService.save(customerCreate);
				
				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (Exception e) {
				addErrorMessage(Customer.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Customer.UUID, customerCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, customerSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER);
		return redirectView;
	}

	@PreAuthorize(WebCustomerConstants.PreAuthorize.UPDATE)
	@PostMapping(value = WebCustomerConstants.Path.CUSTOMER, params="update")
	public RedirectView update(@ModelAttribute(WebCustomerConstants.ModelAttribute.SEARCH) CustomerSearch customerSearch,
			@ModelAttribute(WebCustomerConstants.ModelAttribute.UPDATE) Customer customerUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (customerUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			
			List<UserGroup> userGroups = new ArrayList<UserGroup>();
			for (UserGroup userGroup : customerUpdate.getUserGroups()) {
				if(BooleanUtils.parseBoolean(userGroup.getSelected())) {
					userGroups.add(userGroup);
				}
			}
			customerUpdate.setUserGroups(userGroups);
			
			try {
				modelService.save(customerUpdate);
				
				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (Exception e) {
				addErrorMessage(Customer.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Customer.UUID, customerUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, customerSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER);
		return redirectView;
	}

	@PreAuthorize(WebCustomerConstants.PreAuthorize.DELETE)
	@PostMapping(value = WebCustomerConstants.Path.CUSTOMER, params="delete")
	public RedirectView delete(@ModelAttribute(WebCustomerConstants.ModelAttribute.SEARCH) CustomerSearch customerSearch,
			@ModelAttribute(WebCustomerConstants.ModelAttribute.UPDATE) Customer customerUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			modelService.remove(customerUpdate.getUuid());

			addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.DELETE_SUCCESS);
		} catch (Exception e) {
			addErrorMessage(Customer.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(WebCustomerConstants.ModelAttribute.UPDATE, customerUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, customerSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER);
		return redirectView;

	}
}
