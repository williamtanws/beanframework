package com.beanframework.backoffice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.MediaWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.MediaDto;
import com.beanframework.core.facade.MediaFacade;

@Controller
public class MediaController extends AbstractController {

	@Autowired
	private MediaFacade mediaFacade;

	@Value(MediaWebConstants.Path.MEDIA)
	private String PATH_MEDIA;

	@Value(MediaWebConstants.View.LIST)
	private String VIEW_MEDIA_LIST;

	@ModelAttribute(MediaWebConstants.ModelAttribute.UPDATE)
	public MediaDto update(Model model) throws Exception {
		model.addAttribute("create", false);
		return new MediaDto();
	}

	@GetMapping(value = MediaWebConstants.Path.MEDIA)
	public String list(@ModelAttribute(MediaWebConstants.ModelAttribute.UPDATE) MediaDto updateDto, Model model) throws Exception {
		model.addAttribute("create", false);
		
		if (updateDto.getUuid() != null) {

			MediaDto existsDto = mediaFacade.findOneByUuid(updateDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(MediaWebConstants.ModelAttribute.UPDATE, existsDto);
			} else {
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_MEDIA_LIST;
	}

	@GetMapping(value = MediaWebConstants.Path.MEDIA, params = "create")
	public String createView(Model model) throws Exception {
		model.addAttribute("create", true);
		return VIEW_MEDIA_LIST;
	}

	@PostMapping(value = MediaWebConstants.Path.MEDIA, params = "create")
	public RedirectView create(@ModelAttribute(MediaWebConstants.ModelAttribute.CREATE) MediaDto createDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (createDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				createDto = mediaFacade.create(createDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(MediaDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(MediaDto.UUID, createDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MEDIA);
		return redirectView;
	}

	@PostMapping(value = MediaWebConstants.Path.MEDIA, params = "update")
	public RedirectView update(@ModelAttribute(MediaWebConstants.ModelAttribute.UPDATE) MediaDto updateDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (updateDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				updateDto = mediaFacade.update(updateDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(MediaDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(MediaDto.UUID, updateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MEDIA);
		return redirectView;
	}

	@PostMapping(value = MediaWebConstants.Path.MEDIA, params = "delete")
	public RedirectView delete(@ModelAttribute(MediaWebConstants.ModelAttribute.UPDATE) MediaDto updateDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			mediaFacade.delete(updateDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(MediaDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(MediaDto.UUID, updateDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MEDIA);
		return redirectView;

	}
}
