package com.beanframework.backoffice.web;

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

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.DynamicFieldWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.facade.DynamicFieldFacade;
import com.beanframework.core.facade.DynamicFieldFacade.DynamicFieldPreAuthorizeEnum;

@Controller
public class DynamicFieldController extends AbstractController {

	@Autowired
	private DynamicFieldFacade dynamicFieldFacade;

	@Value(DynamicFieldWebConstants.Path.DYNAMICFIELD)
	private String PATH_DYNAMICFIELD;

	@Value(DynamicFieldWebConstants.View.LIST)
	private String VIEW_DYNAMICFIELD_LIST;

	@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE)
	public DynamicFieldDto update(Model model) throws Exception {
		model.addAttribute("create", false);
		return new DynamicFieldDto();
	}

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.READ)
	@GetMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD)
	public String list(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE) DynamicFieldDto updateDto, Model model) throws Exception {

		if (updateDto.getUuid() != null) {

			DynamicFieldDto existsDto = dynamicFieldFacade.findOneByUuid(updateDto.getUuid());

			if (existsDto != null) {

				model.addAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE, existsDto);
			} else {
				updateDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_DYNAMICFIELD_LIST;
	}
	
	@GetMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD, params = "create")
	public String createView(Model model) throws Exception {
		model.addAttribute("create", true);
		return VIEW_DYNAMICFIELD_LIST;
	}

	@PostMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD, params = "create")
	public RedirectView create(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.CREATE) DynamicFieldDto createDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (createDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				createDto = dynamicFieldFacade.create(createDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldDto.UUID, createDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELD);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD, params = "update")
	public RedirectView update(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE) DynamicFieldDto updateDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (updateDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				updateDto = dynamicFieldFacade.update(updateDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldDto.UUID, updateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELD);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD, params = "delete")
	public RedirectView delete(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE) DynamicFieldDto updateDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			dynamicFieldFacade.delete(updateDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(DynamicFieldDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(DynamicFieldDto.UUID, updateDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELD);
		return redirectView;

	}
}
