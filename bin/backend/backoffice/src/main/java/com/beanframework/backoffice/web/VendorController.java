package com.beanframework.backoffice.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.beanframework.backoffice.VendorWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.VendorDto;
import com.beanframework.core.facade.VendorFacade;

@Controller
public class VendorController extends AbstractController {

	@Autowired
	private VendorFacade vendorFacade;

	@Value(VendorWebConstants.Path.VENDOR)
	private String PATH_VENDOR;

	@Value(VendorWebConstants.View.LIST)
	private String VIEW_VENDOR_LIST;

	@GetMapping(value = VendorWebConstants.Path.VENDOR)
	public String list(@ModelAttribute(VendorWebConstants.ModelAttribute.VENDOR_DTO) VendorDto vendorDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		model.addAttribute("create", false);

		if (vendorDto.getUuid() != null) {

			VendorDto existingVendor = vendorFacade.findOneByUuid(vendorDto.getUuid());

			if (existingVendor != null) {

				model.addAttribute(VendorWebConstants.ModelAttribute.VENDOR_DTO, existingVendor);
			} else {
				vendorDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_VENDOR_LIST;
	}

	@GetMapping(value = VendorWebConstants.Path.VENDOR, params = "create")
	public String createView(@ModelAttribute(VendorWebConstants.ModelAttribute.VENDOR_DTO) VendorDto vendorDto, Model model) throws Exception {

		vendorDto = vendorFacade.createDto();
		model.addAttribute(VendorWebConstants.ModelAttribute.VENDOR_DTO, vendorDto);
		model.addAttribute("create", true);

		return VIEW_VENDOR_LIST;
	}

	@PostMapping(value = VendorWebConstants.Path.VENDOR, params = "create")
	public RedirectView create(@ModelAttribute(VendorWebConstants.ModelAttribute.VENDOR_DTO) VendorDto vendorDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (vendorDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				vendorDto = vendorFacade.create(vendorDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(VendorDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(VendorDto.UUID, vendorDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_VENDOR);
		return redirectView;
	}

	@PostMapping(value = VendorWebConstants.Path.VENDOR, params = "update")
	public RedirectView update(@ModelAttribute(VendorWebConstants.ModelAttribute.VENDOR_DTO) VendorDto vendorDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) throws Exception {

		if (vendorDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				vendorDto = vendorFacade.update(vendorDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(VendorDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(VendorDto.UUID, vendorDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_VENDOR);
		return redirectView;
	}

	@PostMapping(value = VendorWebConstants.Path.VENDOR, params = "delete")
	public RedirectView delete(@ModelAttribute(VendorWebConstants.ModelAttribute.VENDOR_DTO) VendorDto vendorDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {
			vendorFacade.delete(vendorDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(VendorDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(VendorWebConstants.ModelAttribute.VENDOR_DTO, vendorDto);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_VENDOR);
		return redirectView;

	}
}
