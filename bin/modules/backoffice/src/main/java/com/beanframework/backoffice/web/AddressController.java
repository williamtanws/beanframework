package com.beanframework.backoffice.web;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.AddressWebConstants;
import com.beanframework.backoffice.AddressWebConstants.AddressPreAuthorizeEnum;
import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.AddressDto;
import com.beanframework.core.facade.AddressFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class AddressController extends AbstractController {

	@Autowired
	private AddressFacade addressFacade;

	@Value(AddressWebConstants.Path.PATH)
	private String PATH_COMMENT;

	@Value(AddressWebConstants.View.LIST)
	private String VIEW_COMMENT_LIST;

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = AddressWebConstants.Path.PATH)
	public String list(@Valid @ModelAttribute(AddressWebConstants.ModelAttribute.DTO) AddressDto addressDto, Model model) throws Exception {
		model.addAttribute("create", false);

		if (addressDto.getUuid() != null) {

			AddressDto existsDto = addressFacade.findOneByUuid(addressDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(AddressWebConstants.ModelAttribute.DTO, existsDto);
			} else {
				addressDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_COMMENT_LIST;
	}

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = AddressWebConstants.Path.PATH, params = "create")
	public String createView(@Valid @ModelAttribute(AddressWebConstants.ModelAttribute.DTO) AddressDto addressDto, Model model) throws Exception {

		addressDto = addressFacade.createDto();
		model.addAttribute(AddressWebConstants.ModelAttribute.DTO, addressDto);
		model.addAttribute("create", true);

		return VIEW_COMMENT_LIST;
	}

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = AddressWebConstants.Path.PATH, params = "create")
	public RedirectView create(@Valid @ModelAttribute(AddressWebConstants.ModelAttribute.DTO) AddressDto addressDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {

		if (addressDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				addressDto = addressFacade.create(addressDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(AddressDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(AddressDto.UUID, addressDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;
	}

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = AddressWebConstants.Path.PATH, params = "update")
	public RedirectView update(@Valid @ModelAttribute(AddressWebConstants.ModelAttribute.DTO) AddressDto addressDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {

		if (addressDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				addressDto = addressFacade.update(addressDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(AddressDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(AddressDto.UUID, addressDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;
	}

	@PreAuthorize(AddressPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = AddressWebConstants.Path.PATH, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(AddressWebConstants.ModelAttribute.DTO) AddressDto addressDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			addressFacade.delete(addressDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(AddressDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(AddressDto.UUID, addressDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;

	}
}
