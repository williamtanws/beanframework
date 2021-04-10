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
import com.beanframework.backoffice.ImexWebConstants;
import com.beanframework.backoffice.ImexWebConstants.ImexPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.ImexDto;
import com.beanframework.core.facade.ImexFacade;


@Controller
public class ImexController extends AbstractController {

	@Autowired
	private ImexFacade imexFacade;

	@Value(ImexWebConstants.Path.IMEX)
	private String PATH_IMEX;

	@Value(ImexWebConstants.Path.IMEX_FORM)
	private String PATH_IMEX_FORM;

	@Value(ImexWebConstants.View.IMEX)
	private String VIEW_IMEX;

	@Value(ImexWebConstants.View.IMEX_FORM)
	private String VIEW_IMEX_FORM;

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = ImexWebConstants.Path.IMEX)
	public String page(@Valid @ModelAttribute(ImexWebConstants.ModelAttribute.IMEX_DTO) ImexDto imexDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		return VIEW_IMEX;
	}

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = ImexWebConstants.Path.IMEX_FORM)
	public String form(@Valid @ModelAttribute(ImexWebConstants.ModelAttribute.IMEX_DTO) ImexDto imexDto, Model model) throws Exception {

		if (imexDto.getUuid() != null) {
			imexDto = imexFacade.findOneByUuid(imexDto.getUuid());
		} else {
			imexDto = imexFacade.createDto();
		}
		model.addAttribute(ImexWebConstants.ModelAttribute.IMEX_DTO, imexDto);

		return VIEW_IMEX_FORM;
	}

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = ImexWebConstants.Path.IMEX_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(ImexWebConstants.ModelAttribute.IMEX_DTO) ImexDto imexDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (imexDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				imexDto = imexFacade.create(imexDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(ImexDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, imexDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_IMEX_FORM);
		return redirectView;
	}

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = ImexWebConstants.Path.IMEX_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(ImexWebConstants.ModelAttribute.IMEX_DTO) ImexDto imexDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (imexDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				imexDto = imexFacade.update(imexDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(ImexDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, imexDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_IMEX_FORM);
		return redirectView;
	}

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = ImexWebConstants.Path.IMEX_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(ImexWebConstants.ModelAttribute.IMEX_DTO) ImexDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				imexFacade.delete(dto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
				redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_IMEX_FORM);
		return redirectView;

	}
}
