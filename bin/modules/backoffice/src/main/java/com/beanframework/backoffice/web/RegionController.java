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
import com.beanframework.backoffice.RegionWebConstants;
import com.beanframework.backoffice.RegionWebConstants.RegionPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.RegionDto;
import com.beanframework.core.facade.RegionFacade;


@Controller
public class RegionController extends AbstractController {

	@Autowired
	private RegionFacade languageFacade;

	@Value(RegionWebConstants.Path.REGION)
	private String PATH_REGION;

	@Value(RegionWebConstants.Path.REGION_FORM)
	private String PATH_REGION_FORM;

	@Value(RegionWebConstants.View.REGION)
	private String VIEW_REGION;

	@Value(RegionWebConstants.View.REGION_FORM)
	private String VIEW_REGION_FORM;

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = RegionWebConstants.Path.REGION)
	public String page(@Valid @ModelAttribute(RegionWebConstants.ModelAttribute.REGION_DTO) RegionDto languageDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		return VIEW_REGION;
	}

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = RegionWebConstants.Path.REGION_FORM)
	public String form(@Valid @ModelAttribute(RegionWebConstants.ModelAttribute.REGION_DTO) RegionDto languageDto, Model model) throws Exception {

		if (languageDto.getUuid() != null) {
			languageDto = languageFacade.findOneByUuid(languageDto.getUuid());
		} else {
			languageDto = languageFacade.createDto();
		}
		model.addAttribute(RegionWebConstants.ModelAttribute.REGION_DTO, languageDto);

		return VIEW_REGION_FORM;
	}

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = RegionWebConstants.Path.REGION_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(RegionWebConstants.ModelAttribute.REGION_DTO) RegionDto languageDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (languageDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				languageDto = languageFacade.create(languageDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(RegionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, languageDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_REGION_FORM);
		return redirectView;
	}

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = RegionWebConstants.Path.REGION_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(RegionWebConstants.ModelAttribute.REGION_DTO) RegionDto languageDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (languageDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				languageDto = languageFacade.update(languageDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(RegionDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, languageDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_REGION_FORM);
		return redirectView;
	}

	@PreAuthorize(RegionPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = RegionWebConstants.Path.REGION_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(RegionWebConstants.ModelAttribute.REGION_DTO) RegionDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				languageFacade.delete(dto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
				redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_REGION_FORM);
		return redirectView;

	}
}
