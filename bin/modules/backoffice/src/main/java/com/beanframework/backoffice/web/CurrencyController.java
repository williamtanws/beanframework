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
import com.beanframework.backoffice.CurrencyWebConstants;
import com.beanframework.backoffice.CurrencyWebConstants.CurrencyPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.CurrencyDto;
import com.beanframework.core.facade.CurrencyFacade;


@Controller
public class CurrencyController extends AbstractController {

	@Autowired
	private CurrencyFacade currencyFacade;

	@Value(CurrencyWebConstants.Path.CURRENCY)
	private String PATH_CURRENCY;

	@Value(CurrencyWebConstants.Path.CURRENCY_FORM)
	private String PATH_CURRENCY_FORM;

	@Value(CurrencyWebConstants.View.CURRENCY)
	private String VIEW_CURRENCY;

	@Value(CurrencyWebConstants.View.CURRENCY_FORM)
	private String VIEW_CURRENCY_FORM;

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = CurrencyWebConstants.Path.CURRENCY)
	public String page(@Valid @ModelAttribute(CurrencyWebConstants.ModelAttribute.CURRENCY_DTO) CurrencyDto currencyDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		return VIEW_CURRENCY;
	}

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = CurrencyWebConstants.Path.CURRENCY_FORM)
	public String form(@Valid @ModelAttribute(CurrencyWebConstants.ModelAttribute.CURRENCY_DTO) CurrencyDto currencyDto, Model model) throws Exception {

		if (currencyDto.getUuid() != null) {
			currencyDto = currencyFacade.findOneByUuid(currencyDto.getUuid());
		} else {
			currencyDto = currencyFacade.createDto();
		}
		model.addAttribute(CurrencyWebConstants.ModelAttribute.CURRENCY_DTO, currencyDto);

		return VIEW_CURRENCY_FORM;
	}

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = CurrencyWebConstants.Path.CURRENCY_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(CurrencyWebConstants.ModelAttribute.CURRENCY_DTO) CurrencyDto currencyDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (currencyDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				currencyDto = currencyFacade.create(currencyDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CurrencyDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, currencyDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CURRENCY_FORM);
		return redirectView;
	}

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = CurrencyWebConstants.Path.CURRENCY_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(CurrencyWebConstants.ModelAttribute.CURRENCY_DTO) CurrencyDto currencyDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (currencyDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				currencyDto = currencyFacade.update(currencyDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CurrencyDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, currencyDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CURRENCY_FORM);
		return redirectView;
	}

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = CurrencyWebConstants.Path.CURRENCY_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(CurrencyWebConstants.ModelAttribute.CURRENCY_DTO) CurrencyDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				currencyFacade.delete(dto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
				redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_CURRENCY_FORM);
		return redirectView;

	}
}
