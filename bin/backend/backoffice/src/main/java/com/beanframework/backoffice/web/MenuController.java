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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.WebBackofficeConstants;
import com.beanframework.backoffice.WebMenuConstants;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.common.utils.BooleanUtils;
import com.beanframework.menu.domain.Menu;
import com.beanframework.menu.service.MenuFacade;
import com.beanframework.user.domain.UserGroup;
import com.beanframework.user.service.UserGroupFacade;

@Controller
public class MenuController {

	@Autowired
	private MenuFacade menuFacade;

	@Autowired
	private LocaleMessageService localeMessageService;
	
	@Autowired
	private UserGroupFacade userGroupFacade;

	@Value(WebMenuConstants.Path.MENU)
	private String PATH_MENU;

	@Value(WebMenuConstants.View.LIST)
	private String VIEW_MENU_LIST;

	@Value(WebMenuConstants.LIST_SIZE)
	private int MODULE_MENU_LIST_SIZE;

	@ModelAttribute(WebMenuConstants.ModelAttribute.CREATE)
	public Menu populateMenuCreate(HttpServletRequest request) {
		return menuFacade.create();
	}

	@ModelAttribute(WebMenuConstants.ModelAttribute.UPDATE)
	public Menu populateMenuForm(HttpServletRequest request) {
		return menuFacade.create();
	}

	@PreAuthorize(WebMenuConstants.PreAuthorize.READ)
	@GetMapping(value = WebMenuConstants.Path.MENU)
	public String list(@ModelAttribute(WebMenuConstants.ModelAttribute.UPDATE) Menu menuUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) {

		model.addAttribute("menus", menuFacade.findMenuTree());

		if (menuUpdate.getUuid() != null) {
			Menu existingMenu = menuFacade.findByUuid(menuUpdate.getUuid());
			if (existingMenu != null) {
				
				List<UserGroup> userGroups = userGroupFacade.findByOrderByCreatedDate();
				for (int i = 0; i < userGroups.size(); i++) {
					for (UserGroup userGroup : existingMenu.getUserGroups()) {
						if(userGroups.get(i).getUuid().equals(userGroup.getUuid())) {
							userGroups.get(i).setSelected("true");
						}
					}
				}
				existingMenu.setUserGroups(userGroups);
				
				model.addAttribute(WebMenuConstants.ModelAttribute.UPDATE, existingMenu);
			} else {
				menuUpdate.setUuid(null);
				model.addAttribute(WebBackofficeConstants.Model.ERROR,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.RECORD_UUID_NOT_FOUND));
			}
		}
		
		model.addAttribute("menuSelectedUuid", requestParams.get("menuSelectedUuid"));

		return VIEW_MENU_LIST;
	}

	@PreAuthorize(WebMenuConstants.PreAuthorize.CREATE)
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
				if(BooleanUtils.parseBoolean(userGroup.getSelected())) {
					userGroups.add(userGroup);
				}
			}
			menuCreate.setUserGroups(userGroups);
			
			menuCreate = menuFacade.save(menuCreate, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
			}
		}

		redirectAttributes.addAttribute(Menu.UUID, menuCreate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;
	}
	
	@PreAuthorize(WebMenuConstants.PreAuthorize.UPDATE)
	@PostMapping(value = WebMenuConstants.Path.MENU, params = "move")
	public RedirectView move(Model model, @RequestParam Map<String, Object> requestParams,
			final RedirectAttributes redirectAttributes) throws Exception {

		String fromUuid = (String) requestParams.get("fromUuid");
		String toUuid = (String) requestParams.get("toUuid");
		String toIndex = (String) requestParams.get("toIndex");

		MapBindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(), Menu.class.getName());

		menuFacade.changePosition(UUID.fromString(fromUuid),
				StringUtils.isNotEmpty(toUuid) ? UUID.fromString(toUuid) : null, Integer.valueOf(toIndex),
				bindingResult);
		if (bindingResult.hasErrors()) {

			StringBuilder errorMessage = new StringBuilder();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				if (errorMessage.length() != 0) {
					errorMessage.append("<br>");
				}
				errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
			}

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());

		} else {

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
					localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
		}

		redirectAttributes.addAttribute("menuSelectedUuid", fromUuid);

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;
	}

	@PreAuthorize(WebMenuConstants.PreAuthorize.UPDATE)
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
				if(BooleanUtils.parseBoolean(userGroup.getSelected())) {
					userGroups.add(userGroup);
				}
			}
			menuUpdate.setUserGroups(userGroups);
			
			menuUpdate = menuFacade.save(menuUpdate, bindingResult);
			if (bindingResult.hasErrors()) {

				StringBuilder errorMessage = new StringBuilder();
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (errorMessage.length() != 0) {
						errorMessage.append("<br>");
					}
					errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
				}

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());

			} else {

				redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
						localeMessageService.getMessage(WebBackofficeConstants.Locale.SAVE_SUCCESS));
			}
		}

		redirectAttributes.addAttribute(Menu.UUID, menuUpdate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;
	}

	@PreAuthorize(WebMenuConstants.PreAuthorize.DELETE)
	@PostMapping(value = WebMenuConstants.Path.MENU, params = "delete")
	public RedirectView delete(@ModelAttribute(WebMenuConstants.ModelAttribute.UPDATE) Menu menuUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		menuFacade.delete(menuUpdate.getUuid(), bindingResult);

		if (bindingResult.hasErrors()) {

			StringBuilder errorMessage = new StringBuilder();
			List<ObjectError> errors = bindingResult.getAllErrors();
			for (ObjectError error : errors) {
				if (errorMessage.length() != 0) {
					errorMessage.append("<br>");
				}
				errorMessage.append(error.getObjectName() + ": " + error.getDefaultMessage());
			}

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.ERROR, errorMessage.toString());
			redirectAttributes.addFlashAttribute(WebMenuConstants.ModelAttribute.UPDATE, menuUpdate);
		} else {

			redirectAttributes.addFlashAttribute(WebBackofficeConstants.Model.SUCCESS,
					localeMessageService.getMessage(WebBackofficeConstants.Locale.DELETE_SUCCESS));
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;

	}
}
