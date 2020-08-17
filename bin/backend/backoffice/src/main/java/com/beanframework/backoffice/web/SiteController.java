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

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.SiteWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.SiteDto;
import com.beanframework.core.facade.SiteFacade;
import com.beanframework.core.facade.SiteFacade.SitePreAuthorizeEnum;

@Controller
public class SiteController extends AbstractController {

	@Autowired
	private SiteFacade siteFacade;

	@Value(SiteWebConstants.Path.SITE)
	private String PATH_SITE;

	@Value(SiteWebConstants.View.LIST)
	private String VIEW_SITE_LIST;

	@PreAuthorize(SitePreAuthorizeEnum.HAS_READ)
	@GetMapping(value = SiteWebConstants.Path.SITE)
	public String list(@Valid @ModelAttribute(SiteWebConstants.ModelAttribute.SITE_DTO) SiteDto siteDto, Model model) throws Exception {
		model.addAttribute("create", false);

		if (siteDto.getUuid() != null) {

			SiteDto existsDto = siteFacade.findOneByUuid(siteDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(SiteWebConstants.ModelAttribute.SITE_DTO, existsDto);
			} else {
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_SITE_LIST;
	}

	@PreAuthorize(SitePreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = SiteWebConstants.Path.SITE, params = "create")
	public String createView(@Valid @ModelAttribute(SiteWebConstants.ModelAttribute.SITE_DTO) SiteDto siteDto, Model model) throws Exception {

		siteDto = siteFacade.createDto();
		model.addAttribute(SiteWebConstants.ModelAttribute.SITE_DTO, siteDto);
		model.addAttribute("create", true);

		return VIEW_SITE_LIST;
	}

	@PreAuthorize(SitePreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = SiteWebConstants.Path.SITE, params = "create")
	public RedirectView create(@Valid @ModelAttribute(SiteWebConstants.ModelAttribute.SITE_DTO) SiteDto siteDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (siteDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				siteDto = siteFacade.create(siteDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(SiteDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(SiteDto.UUID, siteDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_SITE);
		return redirectView;
	}

	@PreAuthorize(SitePreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = SiteWebConstants.Path.SITE, params = "update")
	public RedirectView update(@Valid @ModelAttribute(SiteWebConstants.ModelAttribute.SITE_DTO) SiteDto siteDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (siteDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				siteDto = siteFacade.update(siteDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(SiteDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(SiteDto.UUID, siteDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_SITE);
		return redirectView;
	}

	@PreAuthorize(SitePreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = SiteWebConstants.Path.SITE, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(SiteWebConstants.ModelAttribute.SITE_DTO) SiteDto siteDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			siteFacade.delete(siteDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(SiteDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(SiteDto.UUID, siteDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_SITE);
		return redirectView;

	}
}
