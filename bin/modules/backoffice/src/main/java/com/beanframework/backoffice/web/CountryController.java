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
import com.beanframework.backoffice.CountryWebConstants;
import com.beanframework.backoffice.CountryWebConstants.CountryPreAuthorizeEnum;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CountryDto;
import com.beanframework.core.facade.CountryFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class CountryController extends AbstractController {

	@Autowired
	private CountryFacade countryFacade;

	@Value(CountryWebConstants.Path.COMMENT)
	private String PATH_COMMENT;

	@Value(CountryWebConstants.View.LIST)
	private String VIEW_COMMENT_LIST;

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = CountryWebConstants.Path.COMMENT)
	public String list(@Valid @ModelAttribute(CountryWebConstants.ModelAttribute.COMMENT_DTO) CountryDto countryDto, Model model) throws Exception {
		model.addAttribute("create", false);

		if (countryDto.getUuid() != null) {

			CountryDto existsDto = countryFacade.findOneByUuid(countryDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(CountryWebConstants.ModelAttribute.COMMENT_DTO, existsDto);
			} else {
				countryDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_COMMENT_LIST;
	}

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = CountryWebConstants.Path.COMMENT, params = "create")
	public String createView(@Valid @ModelAttribute(CountryWebConstants.ModelAttribute.COMMENT_DTO) CountryDto countryDto, Model model) throws Exception {

		countryDto = countryFacade.createDto();
		model.addAttribute(CountryWebConstants.ModelAttribute.COMMENT_DTO, countryDto);
		model.addAttribute("create", true);

		return VIEW_COMMENT_LIST;
	}

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = CountryWebConstants.Path.COMMENT, params = "create")
	public RedirectView create(@Valid @ModelAttribute(CountryWebConstants.ModelAttribute.COMMENT_DTO) CountryDto countryDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (countryDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				countryDto = countryFacade.create(countryDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CountryDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CountryDto.UUID, countryDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;
	}

	@PostMapping(value = CountryWebConstants.Path.COMMENT, params = "update")
	public RedirectView update(@Valid @ModelAttribute(CountryWebConstants.ModelAttribute.COMMENT_DTO) CountryDto countryDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (countryDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				countryDto = countryFacade.update(countryDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CountryDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CountryDto.UUID, countryDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;
	}

	@PreAuthorize(CountryPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = CountryWebConstants.Path.COMMENT, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(CountryWebConstants.ModelAttribute.COMMENT_DTO) CountryDto countryDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			countryFacade.delete(countryDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(CountryDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(CountryDto.UUID, countryDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;

	}
}
