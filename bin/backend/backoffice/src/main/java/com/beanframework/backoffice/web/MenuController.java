package com.beanframework.backoffice.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
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

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebMenuConstants;
import com.beanframework.common.controller.AbstractCommonController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuFacade;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.UserGroupFacade;

@Controller
public class MenuController extends AbstractCommonController {

	@Autowired
	private MenuFacade menuFacade;

	@Autowired
	private UserGroupFacade userGroupFacade;

	@Value(WebMenuConstants.Path.MENU)
	private String PATH_MENU;

	@Value(WebMenuConstants.View.LIST)
	private String VIEW_MENU_LIST;

	@Value(WebMenuConstants.LIST_SIZE)
	private int MODULE_MENU_LIST_SIZE;

	@ModelAttribute(WebMenuConstants.ModelAttribute.CREATE)
	public Menu populateMenuCreate(HttpServletRequest request) throws Exception {
		return menuFacade.create();
	}

	@ModelAttribute(WebMenuConstants.ModelAttribute.UPDATE)
	public Menu populateMenuForm(HttpServletRequest request) throws Exception {
		return menuFacade.create();
	}

	@GetMapping(value = WebMenuConstants.Path.MENU)
	public String list(@ModelAttribute(WebMenuConstants.ModelAttribute.UPDATE) Menu menuUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) throws Exception {

		if (menuUpdate.getUuid() != null) {
			
			Menu existingMenu = menuFacade.findOneDtoByUuid(menuUpdate.getUuid());
			if (existingMenu != null) {

				Map<String, Sort.Direction> sorts = new HashMap<String, Sort.Direction>();
				sorts.put(UserGroup.CREATED_DATE, Sort.Direction.DESC);

				List<UserGroup> userGroups = userGroupFacade.findDtoBySorts(sorts);

				for (int i = 0; i < userGroups.size(); i++) {
					for (UserGroup userGroup : existingMenu.getUserGroups()) {
						if (userGroups.get(i).getUuid().equals(userGroup.getUuid())) {
							userGroups.get(i).setSelected("true");
						}
					}
				}
				existingMenu.setUserGroups(userGroups);

				model.addAttribute(WebMenuConstants.ModelAttribute.UPDATE, existingMenu);
			} else {
				menuUpdate.setUuid(null);
				addErrorMessage(model, WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		model.addAttribute("menuSelectedUuid", requestParams.get("menuSelectedUuid"));

		return VIEW_MENU_LIST;
	}

	@PostMapping(value = WebMenuConstants.Path.MENU, params = "create")
	public RedirectView create(@ModelAttribute(WebMenuConstants.ModelAttribute.CREATE) Menu menuCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (menuCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {

			List<UserGroup> userGroups = new ArrayList<UserGroup>();
			for (UserGroup userGroup : menuCreate.getUserGroups()) {
				if (BooleanUtils.parseBoolean(userGroup.getSelected())) {
					userGroups.add(userGroup);
				}
			}
			menuCreate.setUserGroups(userGroups);

			try {
				menuCreate = menuFacade.updateDto(menuCreate);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Menu.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Menu.UUID, menuCreate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;
	}

	@PostMapping(value = WebMenuConstants.Path.MENU, params = "update")
	public RedirectView update(@ModelAttribute(WebMenuConstants.ModelAttribute.UPDATE) Menu menuUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (menuUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {

			List<UserGroup> userGroups = new ArrayList<UserGroup>();
			for (UserGroup userGroup : menuUpdate.getUserGroups()) {
				if (BooleanUtils.parseBoolean(userGroup.getSelected())) {
					userGroups.add(userGroup);
				}
			}
			menuUpdate.setUserGroups(userGroups);

			try {
				menuUpdate = menuFacade.updateDto(menuUpdate);

				addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(Menu.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(Menu.UUID, menuUpdate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;
	}

	@PostMapping(value = WebMenuConstants.Path.MENU, params = "delete")
	public RedirectView delete(@ModelAttribute(WebMenuConstants.ModelAttribute.UPDATE) Menu menuUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			menuFacade.delete(menuUpdate.getUuid());

			addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(Menu.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(WebMenuConstants.ModelAttribute.UPDATE, menuUpdate);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;

	}
	
	@PostMapping(value = WebMenuConstants.Path.MENU, params = "move")
	public RedirectView move(Model model, @RequestParam Map<String, Object> requestParams,
			final RedirectAttributes redirectAttributes) {

		String fromUuid = (String) requestParams.get("fromUuid");
		String toUuid = (String) requestParams.get("toUuid");
		String toIndex = (String) requestParams.get("toIndex");

		MapBindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(), Menu.class.getName());

		try {
			menuFacade.changePosition(UUID.fromString(fromUuid),
					StringUtils.isNotEmpty(toUuid) ? UUID.fromString(toUuid) : null, Integer.valueOf(toIndex));

			addSuccessMessage(redirectAttributes, WebBackofficeConstants.Locale.SAVE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(Menu.class, e.getMessage(), bindingResult, redirectAttributes);
		}

		redirectAttributes.addAttribute("menuSelectedUuid", fromUuid);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;
	}
}
