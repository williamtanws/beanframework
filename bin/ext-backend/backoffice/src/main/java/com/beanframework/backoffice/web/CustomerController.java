package com.beanframework.backoffice.web;

import java.util.ArrayList;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebCustomerConstants;
import com.beanframework.backoffice.domain.CustomerSearch;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.common.utils.ParamUtils;
import com.beanframework.customer.domain.Customer;
import com.beanframework.customer.service.CustomerFacade;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.UserGroupFacade;

@Controller
public class CustomerController {

	@Autowired
	private CustomerFacade customerFacade;
	
	@Autowired
	private UserGroupFacade userGroupFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

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
		return customerFacade.create();
	}

	@ModelAttribute(WebCustomerConstants.ModelAttribute.UPDATE)
	public Customer populateCustomerForm(HttpServletRequest request) {
		return customerFacade.create();
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
			Customer existingCustomer = customerFacade.findByUuid(customerUpdate.getUuid());
			if (existingCustomer != null) {
				
				List<UserGroup> userGroups = userGroupFacade.findByOrderByCreatedDate();
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
				model.addAttribute(WebBackofficeConstants.Model.ERROR,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND));
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
			
			customerCreate = customerFacade.save(customerCreate, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
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
			
			customerUpdate = customerFacade.save(customerUpdate, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
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

		customerFacade.delete(customerUpdate.getUuid(), bindingResult);

		if (bindingResult.hasErrors()) {

			StringBuilder errorMessage = new StringBuilder();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				if (errorMessage.length() != 0) {
					errorMessage.append("<br>");
				}
				errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
			}

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());
			redirectAttributes.addFlashAttribute(WebCustomerConstants.ModelAttribute.UPDATE, customerUpdate);
		} else {

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
					localeMessageService.getMessage(WebBackofficeConstants.Locale.DELETE_SUCCESS));
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, customerSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER);
		return redirectView;

	}
}
