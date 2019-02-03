package com.beanframework.backoffice.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Controller
public class UserRightController extends AbstractController {

	@Autowired
	private UserRightFacade userRightFacade;

	@Value(UserRightWebConstants.Path.USERRIGHT)
	private String PATH_USERRIGHT;

	@Value(UserRightWebConstants.View.LIST)
	private String VIEW_USERRIGHT_LIST;

	@GetMapping(value = UserRightWebConstants.Path.USERRIGHT)
	public String list(@ModelAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO) UserRightDto userRightDto, Model model) throws Exception {
		model.addAttribute("create", false);
		
		if (userRightDto.getUuid() != null) {

			UserRightDto existsDto = userRightFacade.findOneByUuid(userRightDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO, existsDto);
			} else {
				userRightDto.setUuid(null);
				model.addAttribute(BackofficeWebConstants.Model.ERROR, localeMessageService.getMessage(BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND));
			}
		}

		return VIEW_USERRIGHT_LIST;
	}
	
	@GetMapping(value = UserRightWebConstants.Path.USERRIGHT, params = "create")
	public String createView(@ModelAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO) UserRightDto userRightDto, Model model) throws Exception {
		
		userRightDto = userRightFacade.createDto();
		model.addAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO, userRightDto);
		model.addAttribute("create", true);
		
		return VIEW_USERRIGHT_LIST;
	}

	@PostMapping(value = UserRightWebConstants.Path.USERRIGHT, params = "create")
	public RedirectView create(@ModelAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO) UserRightDto userRightDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		if (userRightDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				userRightDto = userRightFacade.create(userRightDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserRightDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserRightDto.UUID, userRightDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT);
		return redirectView;
	}

	@PostMapping(value = UserRightWebConstants.Path.USERRIGHT, params = "update")
	public RedirectView update(@ModelAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO) UserRightDto userRightDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (userRightDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				userRightDto = userRightFacade.update(userRightDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(UserRightDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(UserRightDto.UUID, userRightDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT);
		return redirectView;
	}

	@PostMapping(value = UserRightWebConstants.Path.USERRIGHT, params = "delete")
	public RedirectView delete(@ModelAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO) UserRightDto userRightDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {

			userRightFacade.delete(userRightDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(UserRightDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(UserRightWebConstants.ModelAttribute.USERRIGHT_DTO, userRightDto);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_USERRIGHT);
		return redirectView;

	}
}
