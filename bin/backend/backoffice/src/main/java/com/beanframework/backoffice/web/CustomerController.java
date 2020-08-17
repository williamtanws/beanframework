package com.beanframework.backoffice.web;

import java.util.Map;

import javax.validation.Valid;

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
import com.beanframework.core.data.CustomerDto;
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

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = CustomerWebConstants.Path.CUSTOMER)
	public String list(@Valid @ModelAttribute(CustomerWebConstants.ModelAttribute.CUSTOMER_DTO) CustomerDto customerDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		model.addAttribute("create", false);

		if (customerDto.getUuid() != null) {

			CustomerDto existingCustomer = customerFacade.findOneByUuid(customerDto.getUuid());

			if (existingCustomer != null) {

				model.addAttribute(CustomerWebConstants.ModelAttribute.CUSTOMER_DTO, existingCustomer);
			} else {
				customerDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_CUSTOMER_LIST;
	}

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = CustomerWebConstants.Path.CUSTOMER, params = "create")
	public String createView(@Valid @ModelAttribute(CustomerWebConstants.ModelAttribute.CUSTOMER_DTO) CustomerDto customerDto, Model model) throws Exception {

		customerDto = customerFacade.createDto();
		model.addAttribute(CustomerWebConstants.ModelAttribute.CUSTOMER_DTO, customerDto);
		model.addAttribute("create", true);

		return VIEW_CUSTOMER_LIST;
	}

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = CustomerWebConstants.Path.CUSTOMER, params = "create")
	public RedirectView create(@Valid @ModelAttribute(CustomerWebConstants.ModelAttribute.CUSTOMER_DTO) CustomerDto customerDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (customerDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				customerDto = customerFacade.create(customerDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CustomerDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CustomerDto.UUID, customerDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER);
		return redirectView;
	}

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = CustomerWebConstants.Path.CUSTOMER, params = "update")
	public RedirectView update(@Valid @ModelAttribute(CustomerWebConstants.ModelAttribute.CUSTOMER_DTO) CustomerDto customerDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (customerDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				customerDto = customerFacade.update(customerDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CustomerDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CustomerDto.UUID, customerDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER);
		return redirectView;
	}

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = CustomerWebConstants.Path.CUSTOMER, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(CustomerWebConstants.ModelAttribute.CUSTOMER_DTO) CustomerDto customerDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {
			customerFacade.delete(customerDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(CustomerDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(CustomerWebConstants.ModelAttribute.CUSTOMER_DTO, customerDto);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER);
		return redirectView;

	}
}
