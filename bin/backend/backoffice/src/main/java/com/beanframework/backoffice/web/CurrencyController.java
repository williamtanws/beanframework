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
import com.beanframework.backoffice.CurrencyWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CurrencyDto;
import com.beanframework.core.facade.CurrencyFacade;
import com.beanframework.core.facade.CurrencyFacade.CurrencyPreAuthorizeEnum;

@Controller
public class CurrencyController extends AbstractController {

	@Autowired
	private CurrencyFacade currencyFacade;

	@Value(CurrencyWebConstants.Path.COMMENT)
	private String PATH_COMMENT;

	@Value(CurrencyWebConstants.View.LIST)
	private String VIEW_COMMENT_LIST;

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = CurrencyWebConstants.Path.COMMENT)
	public String list(@ModelAttribute(CurrencyWebConstants.ModelAttribute.COMMENT_DTO) CurrencyDto currencyDto, Model model) throws Exception {
		model.addAttribute("create", false);

		if (currencyDto.getUuid() != null) {

			CurrencyDto existsDto = currencyFacade.findOneByUuid(currencyDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(CurrencyWebConstants.ModelAttribute.COMMENT_DTO, existsDto);
			} else {
				currencyDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_COMMENT_LIST;
	}

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = CurrencyWebConstants.Path.COMMENT, params = "create")
	public String createView(@ModelAttribute(CurrencyWebConstants.ModelAttribute.COMMENT_DTO) CurrencyDto currencyDto, Model model) throws Exception {

		currencyDto = currencyFacade.createDto();
		model.addAttribute(CurrencyWebConstants.ModelAttribute.COMMENT_DTO, currencyDto);
		model.addAttribute("create", true);

		return VIEW_COMMENT_LIST;
	}

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = CurrencyWebConstants.Path.COMMENT, params = "create")
	public RedirectView create(@ModelAttribute(CurrencyWebConstants.ModelAttribute.COMMENT_DTO) CurrencyDto currencyDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (currencyDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				currencyDto = currencyFacade.create(currencyDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CurrencyDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CurrencyDto.UUID, currencyDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;
	}

	@PostMapping(value = CurrencyWebConstants.Path.COMMENT, params = "update")
	public RedirectView update(@ModelAttribute(CurrencyWebConstants.ModelAttribute.COMMENT_DTO) CurrencyDto currencyDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (currencyDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				currencyDto = currencyFacade.update(currencyDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CurrencyDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CurrencyDto.UUID, currencyDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;
	}

	@PreAuthorize(CurrencyPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = CurrencyWebConstants.Path.COMMENT, params = "delete")
	public RedirectView delete(@ModelAttribute(CurrencyWebConstants.ModelAttribute.COMMENT_DTO) CurrencyDto currencyDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			currencyFacade.delete(currencyDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(CurrencyDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(CurrencyDto.UUID, currencyDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;

	}
}
