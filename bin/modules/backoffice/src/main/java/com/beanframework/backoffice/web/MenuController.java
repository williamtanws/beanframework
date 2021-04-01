package com.beanframework.backoffice.web;

import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
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
import com.beanframework.backoffice.MenuWebConstants;
import com.beanframework.backoffice.MenuWebConstants.MenuPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.facade.MenuFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class MenuController extends AbstractController {

	@Autowired
	private MenuFacade menuFacade;

	@Value(MenuWebConstants.Path.MENU)
	private String PATH_MENU;

	@Value(MenuWebConstants.Path.MENU_FORM)
	private String PATH_MENU_FORM;

	@Value(MenuWebConstants.View.MENU)
	private String VIEW_MENU;

	@Value(MenuWebConstants.View.MENU_FORM)
	private String VIEW_MENU_FORM;

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = MenuWebConstants.Path.MENU)
	public String page(@Valid @ModelAttribute(MenuWebConstants.ModelAttribute.MENU_DTO) MenuDto dynamicFieldDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		return VIEW_MENU;
	}

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = MenuWebConstants.Path.MENU_FORM)
	public String form(@Valid @ModelAttribute(MenuWebConstants.ModelAttribute.MENU_DTO) MenuDto dynamicFieldDto, Model model) throws Exception {

		if (dynamicFieldDto.getUuid() != null) {
			dynamicFieldDto = menuFacade.findOneByUuid(dynamicFieldDto.getUuid());
		} else {
			dynamicFieldDto = menuFacade.createDto();
		}
		model.addAttribute(MenuWebConstants.ModelAttribute.MENU_DTO, dynamicFieldDto);

		return VIEW_MENU_FORM;
	}

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = MenuWebConstants.Path.MENU_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(MenuWebConstants.ModelAttribute.MENU_DTO) MenuDto dynamicFieldDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (dynamicFieldDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				dynamicFieldDto = menuFacade.create(dynamicFieldDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(MenuDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, dynamicFieldDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU_FORM);
		return redirectView;
	}

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = MenuWebConstants.Path.MENU_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(MenuWebConstants.ModelAttribute.MENU_DTO) MenuDto dynamicFieldDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (dynamicFieldDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				dynamicFieldDto = menuFacade.update(dynamicFieldDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(MenuDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, dynamicFieldDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU_FORM);
		return redirectView;
	}

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = MenuWebConstants.Path.MENU_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(MenuWebConstants.ModelAttribute.MENU_DTO) MenuDto dto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				menuFacade.delete(dto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
				redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU_FORM);
		return redirectView;
	}

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = MenuWebConstants.Path.MENU, params = "move")
	public RedirectView move(Model model, @RequestParam Map<String, Object> requestParams, final RedirectAttributes redirectAttributes) {

		String fromUuid = (String) requestParams.get("fromUuid");
		String toUuid = (String) requestParams.get("toUuid");
		String toIndex = (String) requestParams.get("toIndex");

		try {
			menuFacade.changePosition(UUID.fromString(fromUuid), StringUtils.isNotBlank(toUuid) ? UUID.fromString(toUuid) : null, Integer.valueOf(toIndex));

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
		} catch (BusinessException e) {
			redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
			redirectAttributes.addAttribute(GenericDto.UUID, fromUuid);
		}

		redirectAttributes.addAttribute("menuSelectedUuid", fromUuid);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;
	}
}
