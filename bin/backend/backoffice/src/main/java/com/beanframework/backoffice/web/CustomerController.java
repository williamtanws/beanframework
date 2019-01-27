package com.beanframework.backoffice.web;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.CustomerWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.core.data.UserGroupDto;
import com.beanframework.core.facade.CustomerFacade;
import com.beanframework.core.facade.CustomerFacade.CustomerPreAuthorizeEnum;

@Controller
public class CustomerController extends AbstractController {

	@Autowired
	private CustomerFacade customerFacade;

	@Value(CustomerWebConstants.Path.CUSTOMER)
	private String PATH_CUSTOMER;

	@Value(CustomerWebConstants.View.LIST)
	private String VIEW_CUSTOMER_LIST;

	@ModelAttribute(CustomerWebConstants.ModelAttribute.CREATE)
	public CustomerDto create() throws Exception {
		return new CustomerDto();
	}

	@ModelAttribute(CustomerWebConstants.ModelAttribute.UPDATE)
	public CustomerDto update() throws Exception {
		return new CustomerDto();
	}

	@PreAuthorize(CustomerPreAuthorizeEnum.READ)
	@GetMapping(value = CustomerWebConstants.Path.CUSTOMER)
	public String list(@ModelAttribute(CustomerWebConstants.ModelAttribute.UPDATE) CustomerDto updateDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		if (updateDto.getUuid() != null) {

			CustomerDto existingCustomer = customerFacade.findOneByUuid(updateDto.getUuid());

			if (existingCustomer != null) {

				model.addAttribute(CustomerWebConstants.ModelAttribute.UPDATE, existingCustomer);
			} else {
				updateDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_CUSTOMER_LIST;
	}

	@PostMapping(value = CustomerWebConstants.Path.CUSTOMER, params = "create")
	public RedirectView create(@ModelAttribute(CustomerWebConstants.ModelAttribute.CREATE) CustomerDto customerCreate, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (customerCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				customerCreate = customerFacade.create(customerCreate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CustomerDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CustomerDto.UUID, customerCreate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER);
		return redirectView;
	}

	@PostMapping(value = CustomerWebConstants.Path.CUSTOMER, params = "update")
	public RedirectView update(@ModelAttribute(CustomerWebConstants.ModelAttribute.UPDATE) CustomerDto updateDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (updateDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			// UserGroup
			if (updateDto.getTableUserGroups() != null) {
				List<UserGroupDto> userGroups = customerFacade.findOneByUuid(updateDto.getUuid()).getUserGroups();

				for (int i = 0; i < updateDto.getTableUserGroups().length; i++) {

					boolean remove = true;
					if (updateDto.getTableSelectedUserGroups() != null && updateDto.getTableSelectedUserGroups().length > i && BooleanUtils.parseBoolean(updateDto.getTableSelectedUserGroups()[i])) {
						remove = false;
					}

					if (remove) {
						for (Iterator<UserGroupDto> userGroupsIterator = userGroups.listIterator(); userGroupsIterator.hasNext();) {
							if (userGroupsIterator.next().getUuid().equals(UUID.fromString(updateDto.getTableUserGroups()[i]))) {
								userGroupsIterator.remove();
							}
						}
					} else {
						boolean add = true;
						for (Iterator<UserGroupDto> userGroupsIterator = userGroups.listIterator(); userGroupsIterator.hasNext();) {
							if (userGroupsIterator.next().getUuid().equals(UUID.fromString(updateDto.getTableUserGroups()[i]))) {
								add = false;
							}
						}

						if (add) {
							UserGroupDto userGroup = new UserGroupDto();
							userGroup.setUuid(UUID.fromString(updateDto.getTableUserGroups()[i]));
							userGroups.add(userGroup);
						}
					}
				}
				updateDto.setUserGroups(userGroups);
			}

			try {
				updateDto = customerFacade.update(updateDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CustomerDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CustomerDto.UUID, updateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER);
		return redirectView;
	}

	@PostMapping(value = CustomerWebConstants.Path.CUSTOMER, params = "delete")
	public RedirectView delete(@ModelAttribute(CustomerWebConstants.ModelAttribute.UPDATE) CustomerDto updateDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {
			customerFacade.delete(updateDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(CustomerDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(CustomerWebConstants.ModelAttribute.UPDATE, updateDto);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER);
		return redirectView;

	}
}
