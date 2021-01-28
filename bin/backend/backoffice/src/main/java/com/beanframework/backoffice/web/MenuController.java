package com.beanframework.backoffice.web;

import java.util.HashMap;
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
import org.springframework.validation.MapBindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.MenuWebConstants;
import com.beanframework.backoffice.MenuWebConstants.MenuPreAuthorizeEnum;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.MenuDto;
import com.beanframework.core.facade.MenuFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class MenuController extends AbstractController {

	@Autowired
	private MenuFacade menuFacade;

	@Value(MenuWebConstants.Path.MENU)
	private String PATH_MENU;

	@Value(MenuWebConstants.View.LIST)
	private String VIEW_MENU_LIST;

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = MenuWebConstants.Path.MENU)
	public String list(@Valid @ModelAttribute(MenuWebConstants.ModelAttribute.MENU_DTO) MenuDto menuDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		model.addAttribute("create", false);

		if (menuDto.getUuid() != null) {

			MenuDto existingMenu = menuFacade.findOneByUuid(menuDto.getUuid());
			if (existingMenu != null) {

				model.addAttribute(MenuWebConstants.ModelAttribute.MENU_DTO, existingMenu);
			} else {
				menuDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		model.addAttribute("menuSelectedUuid", requestParams.get("menuSelectedUuid"));

		return VIEW_MENU_LIST;
	}

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = MenuWebConstants.Path.MENU, params = "create")
	public String createView(@Valid @ModelAttribute(MenuWebConstants.ModelAttribute.MENU_DTO) MenuDto menuDto, Model model) throws Exception {

		menuDto = menuFacade.createDto();
		model.addAttribute(MenuWebConstants.ModelAttribute.MENU_DTO, menuDto);
		model.addAttribute("create", true);

		return VIEW_MENU_LIST;
	}

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = MenuWebConstants.Path.MENU, params = "create")
	public RedirectView create(@Valid @ModelAttribute(MenuWebConstants.ModelAttribute.MENU_DTO) MenuDto menuDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (menuDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				menuDto = menuFacade.update(menuDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(MenuDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(MenuDto.UUID, menuDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;
	}

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = MenuWebConstants.Path.MENU, params = "update")
	public RedirectView update(@Valid @ModelAttribute(MenuWebConstants.ModelAttribute.MENU_DTO) MenuDto menuDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) throws Exception {

		if (menuDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				menuDto = menuFacade.update(menuDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(MenuDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(MenuDto.UUID, menuDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;
	}

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = MenuWebConstants.Path.MENU, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(MenuWebConstants.ModelAttribute.MENU_DTO) MenuDto menuDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			menuFacade.delete(menuDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(MenuDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(MenuWebConstants.ModelAttribute.MENU_DTO, menuDto);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;

	}

	@PreAuthorize(MenuPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = MenuWebConstants.Path.MENU, params = "move")
	public RedirectView move(Model model, @RequestParam Map<String, Object> requestParams, final RedirectAttributes redirectAttributes) {

		String fromUuid = (String) requestParams.get("fromUuid");
		String toUuid = (String) requestParams.get("toUuid");
		String toIndex = (String) requestParams.get("toIndex");

		MapBindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(), MenuDto.class.getName());

		try {
			menuFacade.changePosition(UUID.fromString(fromUuid), StringUtils.isNotBlank(toUuid) ? UUID.fromString(toUuid) : null, Integer.valueOf(toIndex));

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(MenuDto.class, e.getMessage(), bindingResult, redirectAttributes);
		}

		redirectAttributes.addAttribute("menuSelectedUuid", fromUuid);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;
	}
}
