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
import com.beanframework.backoffice.CustomerWebConstants.CustomerPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.core.facade.CustomerFacade;


@Controller
public class CustomerController extends AbstractController {

	@Autowired
	private CustomerFacade employeeFacade;

	@Value(CustomerWebConstants.Path.CUSTOMER)
	private String PATH_CUSTOMER;

	@Value(CustomerWebConstants.Path.CUSTOMER_FORM)
	private String PATH_CUSTOMER_FORM;

	@Value(CustomerWebConstants.View.CUSTOMER)
	private String VIEW_CUSTOMER;

	@Value(CustomerWebConstants.View.CUSTOMER_FORM)
	private String VIEW_CUSTOMER_FORM;

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = CustomerWebConstants.Path.CUSTOMER)
	public String page(@Valid @ModelAttribute(CustomerWebConstants.ModelAttribute.CUSTOMER_DTO) CustomerDto employeeDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		return VIEW_CUSTOMER;
	}

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = CustomerWebConstants.Path.CUSTOMER_FORM)
	public String form(@Valid @ModelAttribute(CustomerWebConstants.ModelAttribute.CUSTOMER_DTO) CustomerDto employeeDto, Model model) throws Exception {

		if (employeeDto.getUuid() != null) {
			employeeDto = employeeFacade.findOneByUuid(employeeDto.getUuid());
		} else {
			employeeDto = employeeFacade.createDto();
		}
		model.addAttribute(CustomerWebConstants.ModelAttribute.CUSTOMER_DTO, employeeDto);

		return VIEW_CUSTOMER_FORM;
	}

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = CustomerWebConstants.Path.CUSTOMER_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(CustomerWebConstants.ModelAttribute.CUSTOMER_DTO) CustomerDto employeeDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (employeeDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				employeeDto = employeeFacade.create(employeeDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CustomerDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, employeeDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER_FORM);
		return redirectView;
	}

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = CustomerWebConstants.Path.CUSTOMER_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(CustomerWebConstants.ModelAttribute.CUSTOMER_DTO) CustomerDto employeeDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (employeeDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				employeeDto = employeeFacade.update(employeeDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CustomerDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, employeeDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER_FORM);
		return redirectView;
	}

	@PreAuthorize(CustomerPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = CustomerWebConstants.Path.CUSTOMER_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(CustomerWebConstants.ModelAttribute.CUSTOMER_DTO) CustomerDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				employeeFacade.delete(dto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
				redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CUSTOMER_FORM);
		return redirectView;

	}
}
