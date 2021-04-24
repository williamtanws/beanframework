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
import com.beanframework.backoffice.CountryWebConstants;
import com.beanframework.backoffice.CountryWebConstants.CountryPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.CountryDto;
import com.beanframework.core.facade.CountryFacade;


@Controller
public class CountryController extends AbstractController {

	@Autowired
	private CountryFacade countryFacade;

	@Value(CountryWebConstants.Path.COUNTRY)
	private String PATH_COUNTRY;

	@Value(CountryWebConstants.Path.COUNTRY_FORM)
	private String PATH_COUNTRY_FORM;

	@Value(CountryWebConstants.View.COUNTRY)
	private String VIEW_COUNTRY;

	@Value(CountryWebConstants.View.COUNTRY_FORM)
	private String VIEW_COUNTRY_FORM;

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = CountryWebConstants.Path.COUNTRY)
	public String page(@Valid @ModelAttribute(CountryWebConstants.ModelAttribute.COUNTRY_DTO) CountryDto countryDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		return VIEW_COUNTRY;
	}

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = CountryWebConstants.Path.COUNTRY_FORM)
	public String form(@Valid @ModelAttribute(CountryWebConstants.ModelAttribute.COUNTRY_DTO) CountryDto countryDto, Model model) throws Exception {

		if (countryDto.getUuid() != null) {
			countryDto = countryFacade.findOneByUuid(countryDto.getUuid());
		} else {
			countryDto = countryFacade.createDto();
		}
		model.addAttribute(CountryWebConstants.ModelAttribute.COUNTRY_DTO, countryDto);

		return VIEW_COUNTRY_FORM;
	}

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = CountryWebConstants.Path.COUNTRY_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(CountryWebConstants.ModelAttribute.COUNTRY_DTO) CountryDto countryDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (countryDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				countryDto = countryFacade.create(countryDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CountryDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, countryDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COUNTRY_FORM);
		return redirectView;
	}

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = CountryWebConstants.Path.COUNTRY_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(CountryWebConstants.ModelAttribute.COUNTRY_DTO) CountryDto countryDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (countryDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				countryDto = countryFacade.update(countryDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CountryDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, countryDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COUNTRY_FORM);
		return redirectView;
	}

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = CountryWebConstants.Path.COUNTRY_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(CountryWebConstants.ModelAttribute.COUNTRY_DTO) CountryDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				countryFacade.delete(dto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
				redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COUNTRY_FORM);
		return redirectView;

	}
}
