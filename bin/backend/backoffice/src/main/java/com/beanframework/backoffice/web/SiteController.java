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
import com.beanframework.backoffice.SiteWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.SiteDto;
import com.beanframework.core.facade.SiteFacade;

@Controller
public class SiteController extends AbstractController {

	@Autowired
	private SiteFacade siteFacade;

	@Value(SiteWebConstants.Path.SITE)
	private String PATH_SITE;

	@Value(SiteWebConstants.View.LIST)
	private String VIEW_SITE_LIST;

	@ModelAttribute(SiteWebConstants.ModelAttribute.UPDATE)
	public SiteDto update(Model model) throws Exception {
		model.addAttribute("create", false);
		return new SiteDto();
	}

	@GetMapping(value = SiteWebConstants.Path.SITE)
	public String list(@ModelAttribute(SiteWebConstants.ModelAttribute.UPDATE) SiteDto updateDto, Model model) throws Exception {
		model.addAttribute("create", false);
		
		if (updateDto.getUuid() != null) {

			SiteDto existsDto = siteFacade.findOneByUuid(updateDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(SiteWebConstants.ModelAttribute.UPDATE, existsDto);
			} else {
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_SITE_LIST;
	}

	@GetMapping(value = SiteWebConstants.Path.SITE, params = "create")
	public String createView(Model model) throws Exception {
		model.addAttribute("create", true);
		return VIEW_SITE_LIST;
	}

	@PostMapping(value = SiteWebConstants.Path.SITE, params = "create")
	public RedirectView create(@ModelAttribute(SiteWebConstants.ModelAttribute.CREATE) SiteDto createDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (createDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				createDto = siteFacade.create(createDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(SiteDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(SiteDto.UUID, createDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_SITE);
		return redirectView;
	}

	@PostMapping(value = SiteWebConstants.Path.SITE, params = "update")
	public RedirectView update(@ModelAttribute(SiteWebConstants.ModelAttribute.UPDATE) SiteDto updateDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (updateDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				updateDto = siteFacade.update(updateDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(SiteDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(SiteDto.UUID, updateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_SITE);
		return redirectView;
	}

	@PostMapping(value = SiteWebConstants.Path.SITE, params = "delete")
	public RedirectView delete(@ModelAttribute(SiteWebConstants.ModelAttribute.UPDATE) SiteDto updateDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			siteFacade.delete(updateDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(SiteDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(SiteDto.UUID, updateDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_SITE);
		return redirectView;

	}
}
