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
import com.beanframework.backoffice.AddressWebConstants;
import com.beanframework.backoffice.AddressWebConstants.AddressPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.AddressDto;
import com.beanframework.core.facade.AddressFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class AddressController extends AbstractController {

	@Autowired
	private AddressFacade addressFacade;

	@Value(AddressWebConstants.Path.ADDRESS)
	private String PATH_ADDRESS;

	@Value(AddressWebConstants.Path.ADDRESS_FORM)
	private String PATH_ADDRESS_FORM;

	@Value(AddressWebConstants.View.ADDRESS)
	private String VIEW_ADDRESS;

	@Value(AddressWebConstants.View.ADDRESS_FORM)
	private String VIEW_ADDRESS_FORM;

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = AddressWebConstants.Path.ADDRESS)
	public String page(@Valid @ModelAttribute(AddressWebConstants.ModelAttribute.ADDRESS_DTO) AddressDto addressDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		return VIEW_ADDRESS;
	}

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = AddressWebConstants.Path.ADDRESS_FORM)
	public String form(@Valid @ModelAttribute(AddressWebConstants.ModelAttribute.ADDRESS_DTO) AddressDto addressDto, Model model) throws Exception {

		if (addressDto.getUuid() != null) {
			addressDto = addressFacade.findOneByUuid(addressDto.getUuid());
		} else {
			addressDto = addressFacade.createDto();
		}
		model.addAttribute(AddressWebConstants.ModelAttribute.ADDRESS_DTO, addressDto);

		return VIEW_ADDRESS_FORM;
	}

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = AddressWebConstants.Path.ADDRESS_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(AddressWebConstants.ModelAttribute.ADDRESS_DTO) AddressDto addressDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (addressDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				addressDto = addressFacade.create(addressDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(AddressDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, addressDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ADDRESS_FORM);
		return redirectView;
	}

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = AddressWebConstants.Path.ADDRESS_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(AddressWebConstants.ModelAttribute.ADDRESS_DTO) AddressDto addressDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (addressDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				addressDto = addressFacade.update(addressDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(AddressDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, addressDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ADDRESS_FORM);
		return redirectView;
	}

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = AddressWebConstants.Path.ADDRESS_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(AddressWebConstants.ModelAttribute.ADDRESS_DTO) AddressDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				addressFacade.delete(dto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
				redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ADDRESS_FORM);
		return redirectView;

	}
}
