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
import com.beanframework.backoffice.CompanyWebConstants;
import com.beanframework.backoffice.CompanyWebConstants.CompanyPreAuthorizeEnum;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CompanyDto;
import com.beanframework.core.facade.CompanyFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class CompanyController extends AbstractController {

	@Autowired
	private CompanyFacade companyFacade;

	@Value(CompanyWebConstants.Path.COMMENT)
	private String PATH_COMMENT;

	@Value(CompanyWebConstants.View.LIST)
	private String VIEW_COMMENT_LIST;

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = CompanyWebConstants.Path.COMMENT)
	public String list(@Valid @ModelAttribute(CompanyWebConstants.ModelAttribute.COMMENT_DTO) CompanyDto companyDto, Model model) throws Exception {
		model.addAttribute("create", false);

		if (companyDto.getUuid() != null) {

			CompanyDto existsDto = companyFacade.findOneByUuid(companyDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(CompanyWebConstants.ModelAttribute.COMMENT_DTO, existsDto);
			} else {
				companyDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_COMMENT_LIST;
	}

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = CompanyWebConstants.Path.COMMENT, params = "create")
	public String createView(@Valid @ModelAttribute(CompanyWebConstants.ModelAttribute.COMMENT_DTO) CompanyDto companyDto, Model model) throws Exception {

		companyDto = companyFacade.createDto();
		model.addAttribute(CompanyWebConstants.ModelAttribute.COMMENT_DTO, companyDto);
		model.addAttribute("create", true);

		return VIEW_COMMENT_LIST;
	}

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = CompanyWebConstants.Path.COMMENT, params = "create")
	public RedirectView create(@Valid @ModelAttribute(CompanyWebConstants.ModelAttribute.COMMENT_DTO) CompanyDto companyDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (companyDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				companyDto = companyFacade.create(companyDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CompanyDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CompanyDto.UUID, companyDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;
	}

	@PostMapping(value = CompanyWebConstants.Path.COMMENT, params = "update")
	public RedirectView update(@Valid @ModelAttribute(CompanyWebConstants.ModelAttribute.COMMENT_DTO) CompanyDto companyDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (companyDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				companyDto = companyFacade.update(companyDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CompanyDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CompanyDto.UUID, companyDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;
	}

	@PreAuthorize(CompanyPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = CompanyWebConstants.Path.COMMENT, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(CompanyWebConstants.ModelAttribute.COMMENT_DTO) CompanyDto companyDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			companyFacade.delete(companyDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(CompanyDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(CompanyDto.UUID, companyDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;

	}
}
