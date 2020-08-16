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
import com.beanframework.backoffice.RegionWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.RegionDto;
import com.beanframework.core.facade.RegionFacade;
import com.beanframework.core.facade.RegionFacade.RegionPreAuthorizeEnum;

@Controller
public class RegionController extends AbstractController {

	@Autowired
	private RegionFacade regionFacade;

	@Value(RegionWebConstants.Path.COMMENT)
	private String PATH_COMMENT;

	@Value(RegionWebConstants.View.LIST)
	private String VIEW_COMMENT_LIST;

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = RegionWebConstants.Path.COMMENT)
	public String list(@ModelAttribute(RegionWebConstants.ModelAttribute.COMMENT_DTO) RegionDto regionDto, Model model) throws Exception {
		model.addAttribute("create", false);

		if (regionDto.getUuid() != null) {

			RegionDto existsDto = regionFacade.findOneByUuid(regionDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(RegionWebConstants.ModelAttribute.COMMENT_DTO, existsDto);
			} else {
				regionDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_COMMENT_LIST;
	}

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = RegionWebConstants.Path.COMMENT, params = "create")
	public String createView(@ModelAttribute(RegionWebConstants.ModelAttribute.COMMENT_DTO) RegionDto regionDto, Model model) throws Exception {

		regionDto = regionFacade.createDto();
		model.addAttribute(RegionWebConstants.ModelAttribute.COMMENT_DTO, regionDto);
		model.addAttribute("create", true);

		return VIEW_COMMENT_LIST;
	}

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = RegionWebConstants.Path.COMMENT, params = "create")
	public RedirectView create(@Valid @ModelAttribute(RegionWebConstants.ModelAttribute.COMMENT_DTO) RegionDto regionDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (regionDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				regionDto = regionFacade.create(regionDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(RegionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(RegionDto.UUID, regionDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;
	}

	@PostMapping(value = RegionWebConstants.Path.COMMENT, params = "update")
	public RedirectView update(@ModelAttribute(RegionWebConstants.ModelAttribute.COMMENT_DTO) RegionDto regionDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {

		if (regionDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				regionDto = regionFacade.update(regionDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(RegionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(RegionDto.UUID, regionDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;
	}

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = RegionWebConstants.Path.COMMENT, params = "delete")
	public RedirectView delete(@ModelAttribute(RegionWebConstants.ModelAttribute.COMMENT_DTO) RegionDto regionDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			regionFacade.delete(regionDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(RegionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(RegionDto.UUID, regionDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;

	}
}
