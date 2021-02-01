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
import com.beanframework.backoffice.UserRightWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.UserRightDto;
import com.beanframework.core.facade.UserRightFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class UserRightController extends AbstractController {

	@Autowired
	private UserRightFacade userrightFacade;
	
	@Value(UserRightWebConstants.Path.USERRIGHT_PAGE)
	private String PATH_USERRIGHT_PAGE;
	
	@Value(UserRightWebConstants.Path.USERRIGHT_FORM)
	private String PATH_USERRIGHT_FORM;

	@Value(UserRightWebConstants.View.PAGE)
	private String VIEW_USERRIGHT_PAGE;

	@Value(UserRightWebConstants.View.FORM)
	private String VIEW_USERRIGHT_FORM;

	@GetMapping(value = UserRightWebConstants.Path.USERRIGHT_PAGE)
	public String list(@Valid @ModelAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO) UserRightDto userrightDto, Model model, @RequestParam Map<String, Object> requestParams)
			throws Exception {
		return VIEW_USERRIGHT_PAGE;
	}

	@GetMapping(value = UserRightWebConstants.Path.USERRIGHT_FORM)
	public String createView(@Valid @ModelAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO) UserRightDto userrightDto, Model model) throws Exception {

		if (userrightDto.getUuid() != null) {
			userrightDto = userrightFacade.findOneByUuid(userrightDto.getUuid());
		} else {
			userrightDto = userrightFacade.createDto();
		}
		model.addAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO, userrightDto);

		return VIEW_USERRIGHT_FORM;
	}

	@PostMapping(value = UserRightWebConstants.Path.USERRIGHT_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO) UserRightDto userrightDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (userrightDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				userrightDto = userrightFacade.create(userrightDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserRightDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserRightDto.UUID, userrightDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT_FORM);
		return redirectView;
	}

	@PostMapping(value = UserRightWebConstants.Path.USERRIGHT_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO) UserRightDto userrightDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (userrightDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				userrightDto = userrightFacade.update(userrightDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserRightDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserRightDto.UUID, userrightDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT_FORM);
		return redirectView;
	}

	@PostMapping(value = UserRightWebConstants.Path.USERRIGHT_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO) UserRightDto userrightDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (userrightDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				userrightFacade.delete(userrightDto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserRightDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT_FORM);
		return redirectView;

	}
}
