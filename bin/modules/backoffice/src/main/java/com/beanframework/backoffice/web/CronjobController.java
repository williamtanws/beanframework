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
import com.beanframework.backoffice.CronjobWebConstants;
import com.beanframework.backoffice.CronjobWebConstants.CronjobPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.core.facade.CronjobFacade;


@Controller
public class CronjobController extends AbstractController {

	@Autowired
	private CronjobFacade cronjobFacade;

	@Value(CronjobWebConstants.Path.CRONJOB)
	private String PATH_CRONJOB;

	@Value(CronjobWebConstants.Path.CRONJOB_FORM)
	private String PATH_CRONJOB_FORM;

	@Value(CronjobWebConstants.View.CRONJOB)
	private String VIEW_CRONJOB;

	@Value(CronjobWebConstants.View.CRONJOB_FORM)
	private String VIEW_CRONJOB_FORM;

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = CronjobWebConstants.Path.CRONJOB)
	public String page(@Valid @ModelAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO) CronjobDto cronjobDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		return VIEW_CRONJOB;
	}

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = CronjobWebConstants.Path.CRONJOB_FORM)
	public String form(@Valid @ModelAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO) CronjobDto cronjobDto, Model model) throws Exception {

		if (cronjobDto.getUuid() != null) {
			cronjobDto = cronjobFacade.findOneByUuid(cronjobDto.getUuid());
		} else {
			cronjobDto = cronjobFacade.createDto();
		}
		model.addAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO, cronjobDto);

		return VIEW_CRONJOB_FORM;
	}

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = CronjobWebConstants.Path.CRONJOB_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO) CronjobDto cronjobDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (cronjobDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				cronjobDto = cronjobFacade.create(cronjobDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CronjobDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, cronjobDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB_FORM);
		return redirectView;
	}

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = CronjobWebConstants.Path.CRONJOB_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO) CronjobDto cronjobDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (cronjobDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				cronjobDto = cronjobFacade.update(cronjobDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CronjobDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, cronjobDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB_FORM);
		return redirectView;
	}

	@PreAuthorize(CronjobPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = CronjobWebConstants.Path.CRONJOB_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(CronjobWebConstants.ModelAttribute.CRONJOB_DTO) CronjobDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				cronjobFacade.delete(dto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
				redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CRONJOB_FORM);
		return redirectView;

	}
}
