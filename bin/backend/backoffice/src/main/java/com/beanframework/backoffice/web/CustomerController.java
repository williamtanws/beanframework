package com.beanframework.backoffice.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.CustomerWebConstants;
import com.beanframework.backoffice.data.CustomerDto;
import com.beanframework.backoffice.data.CustomerSearch;
import com.beanframework.backoffice.data.UserGroupDto;
import com.beanframework.backoffice.facade.CustomerFacade;
import com.beanframework.backoffice.facade.UserGroupFacade;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.common.utils.ParamUtils;

@Controller
public class CustomerController extends AbstractController {

	@Autowired
	private CustomerFacade customerFacade;

	@Autowired
	private UserGroupFacade userGroupFacade;

	@Value(CustomerWebConstants.Path.CUSTOMER)
	private String PATH_CUSTOMER;

	@Value(CustomerWebConstants.View.LIST)
	private String VIEW_CUSTOMER_LIST;

	@Value(CustomerWebConstants.LIST_SIZE)
	private int MODULE_CUSTOMER_LIST_SIZE;

	private Page<CustomerDto> getPagination(CustomerSearch customerSearch, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_CUSTOMER_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String[] properties = StringUtils.isBlank(propertiesStr) ? null : propertiesStr.split(BackofficeWebConstants.Pagination.PROPERTIES_SPLIT);

		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));
		Direction direction = StringUtils.isBlank(directionStr) ? Direction.ASC : Direction.fromString(directionStr);

		if (properties == null) {
			properties = new String[1];
			properties[0] = CustomerDto.CREATED_DATE;
			direction = Sort.Direction.DESC;
		}

		Page<CustomerDto> pagination = customerFacade.findPage(customerSearch, PageRequest.of(page <= 0 ? 0 : page - 1, size <= 0 ? 1 : size, direction, properties));

		model.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		model.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);

		return pagination;
	}

	private RedirectAttributes setPaginationRedirectAttributes(RedirectAttributes redirectAttributes, @RequestParam Map<String, Object> requestParams, CustomerSearch customerSearch) {

		customerSearch.setSearchAll((String) requestParams.get("customerSearch.searchAll"));
		customerSearch.setId((String) requestParams.get("customerSearch.id"));

		int page = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.PAGE));
		page = page <= 0 ? 1 : page;
		int size = ParamUtils.parseInt(requestParams.get(BackofficeWebConstants.Pagination.SIZE));
		size = size <= 0 ? MODULE_CUSTOMER_LIST_SIZE : size;

		String propertiesStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.PROPERTIES));
		String directionStr = ParamUtils.parseString(requestParams.get(BackofficeWebConstants.Pagination.DIRECTION));

		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PAGE, page);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.SIZE, size);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.PROPERTIES, propertiesStr);
		redirectAttributes.addAttribute(BackofficeWebConstants.Pagination.DIRECTION, directionStr);
		redirectAttributes.addAttribute("searchAll", customerSearch.getSearchAll());
		redirectAttributes.addAttribute("id", customerSearch.getId());

		return redirectAttributes;
	}

	@ModelAttribute(CustomerWebConstants.ModelAttribute.CREATE)
	public CustomerDto populateCustomerCreate(HttpServletRequest request) throws Exception {
		return new CustomerDto();
	}

	@ModelAttribute(CustomerWebConstants.ModelAttribute.UPDATE)
	public CustomerDto populateCustomerForm(HttpServletRequest request) throws Exception {
		return new CustomerDto();
	}

	@ModelAttribute(CustomerWebConstants.ModelAttribute.SEARCH)
	public CustomerSearch populateCustomerSearch(HttpServletRequest request) {
		return new CustomerSearch();
	}

	@GetMapping(value = CustomerWebConstants.Path.CUSTOMER)
	public String list(@ModelAttribute(CustomerWebConstants.ModelAttribute.SEARCH) CustomerSearch customerSearch,
			@ModelAttribute(CustomerWebConstants.ModelAttribute.UPDATE) CustomerDto customerUpdate, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		model.addAttribute(BackofficeWebConstants.PAGINATION, getPagination(customerSearch, model, requestParams));

		if (customerUpdate.getUuid() != null) {

			CustomerDto existingCustomer = customerFacade.findOneByUuid(customerUpdate.getUuid());

			if (existingCustomer != null) {

				List<UserGroupDto> userGroups = userGroupFacade.findAllDtoUserGroups();

				for (int i = 0; i < userGroups.size(); i++) {
					for (UserGroupDto userGroup : existingCustomer.getUserGroups()) {
						if (userGroups.get(i).getUuid().equals(userGroup.getUuid())) {
							userGroups.get(i).setSelected("true");
						}
					}
				}
				existingCustomer.setUserGroups(userGroups);

				List<Object[]> revisions = customerFacade.findHistoryByUuid(customerUpdate.getUuid(), null, null);
				model.addAttribute(BackofficeWebConstants.Model.REVISIONS, revisions);

				List<Object[]> fieldRevisions = customerFacade.findFieldHistoryByUuid(customerUpdate.getUuid(), null, null);
				model.addAttribute(BackofficeWebConstants.Model.FIELD_REVISIONS, fieldRevisions);

				model.addAttribute(CustomerWebConstants.ModelAttribute.UPDATE, existingCustomer);
			} else {
				customerUpdate.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_CUSTOMER_LIST;
	}

	@PostMapping(value = CustomerWebConstants.Path.CUSTOMER, params = "create")
	public RedirectView create(@ModelAttribute(CustomerWebConstants.ModelAttribute.SEARCH) CustomerSearch customerSearch,
			@ModelAttribute(CustomerWebConstants.ModelAttribute.CREATE) CustomerDto customerCreate, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (customerCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			List<UserGroupDto> userGroups = new ArrayList<UserGroupDto>();
			for (UserGroupDto userGroup : customerCreate.getUserGroups()) {
				if (BooleanUtils.parseBoolean(userGroup.getSelected())) {
					userGroups.add(userGroup);
				}
			}
			customerCreate.setUserGroups(userGroups);

			try {
				customerCreate = customerFacade.create(customerCreate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CustomerDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CustomerDto.UUID, customerCreate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, customerSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER);
		return redirectView;
	}

	@PostMapping(value = CustomerWebConstants.Path.CUSTOMER, params = "update")
	public RedirectView update(@ModelAttribute(CustomerWebConstants.ModelAttribute.SEARCH) CustomerSearch customerSearch,
			@ModelAttribute(CustomerWebConstants.ModelAttribute.UPDATE) CustomerDto customerUpdate, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (customerUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			List<UserGroupDto> userGroups = new ArrayList<UserGroupDto>();
			for (UserGroupDto userGroup : customerUpdate.getUserGroups()) {
				if (BooleanUtils.parseBoolean(userGroup.getSelected())) {
					userGroups.add(userGroup);
				}
			}
			customerUpdate.setUserGroups(userGroups);

			try {
				customerUpdate = customerFacade.update(customerUpdate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CustomerDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CustomerDto.UUID, customerUpdate.getUuid());
		setPaginationRedirectAttributes(redirectAttributes, requestParams, customerSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER);
		return redirectView;
	}

	@PostMapping(value = CustomerWebConstants.Path.CUSTOMER, params = "delete")
	public RedirectView delete(@ModelAttribute(CustomerWebConstants.ModelAttribute.SEARCH) CustomerSearch customerSearch,
			@ModelAttribute(CustomerWebConstants.ModelAttribute.UPDATE) CustomerDto customerUpdate, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			customerFacade.delete(customerUpdate.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(CustomerDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(CustomerWebConstants.ModelAttribute.UPDATE, customerUpdate);
		}

		setPaginationRedirectAttributes(redirectAttributes, requestParams, customerSearch);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER);
		return redirectView;

	}
}
