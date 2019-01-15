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
import com.beanframework.backoffice.data.MenuDto;
import com.beanframework.backoffice.data.UserGroupDto;
import com.beanframework.backoffice.facade.MenuFacade;
import com.beanframework.backoffice.facade.UserGroupFacade;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.BooleanUtils;

@Controller
public class MenuController extends AbstractController {

	@Autowired
	private MenuFacade menuFacade;

	@Autowired
	private UserGroupFacade userGroupFacade;

	@Value(MenuWebConstants.Path.MENU)
	private String PATH_MENU;

	@Value(MenuWebConstants.View.LIST)
	private String VIEW_MENU_LIST;

	@Value(MenuWebConstants.LIST_SIZE)
	private int MODULE_MENU_LIST_SIZE;

	@ModelAttribute(MenuWebConstants.ModelAttribute.CREATE)
	public MenuDto populateMenuCreate(HttpServletRequest request) throws Exception {
		return new MenuDto();
	}

	@ModelAttribute(MenuWebConstants.ModelAttribute.UPDATE)
	public MenuDto populateMenuForm(HttpServletRequest request) throws Exception {
		return new MenuDto();
	}

	@GetMapping(value = MenuWebConstants.Path.MENU)
	public String list(@ModelAttribute(MenuWebConstants.ModelAttribute.UPDATE) MenuDto menuUpdate, Model model,
			@RequestParam Map<String, Object> requestParams) throws Exception {

		if (menuUpdate.getUuid() != null) {
			
			MenuDto existingMenu = menuFacade.findOneByUuid(menuUpdate.getUuid());
			if (existingMenu != null) {

				List<UserGroupDto> userGroups = userGroupFacade.findAllDtoUserGroups();

				for (int i = 0; i < userGroups.size(); i++) {
					for (UserGroupDto userGroup : existingMenu.getUserGroups()) {
						if (userGroups.get(i).getUuid().equals(userGroup.getUuid())) {
							userGroups.get(i).setSelected("true");
						}
					}
				}
				existingMenu.setUserGroups(userGroups);
				
				List<Object[]> revisions = menuFacade.findHistoryByUuid(menuUpdate.getUuid(), null, null);
				model.addAttribute(BackofficeWebConstants.Model.REVISIONS, revisions);
				
				List<Object[]> fieldRevisions = menuFacade.findFieldHistoryByUuid(menuUpdate.getUuid(), null, null);
				model.addAttribute(BackofficeWebConstants.Model.FIELD_REVISIONS, fieldRevisions);

				model.addAttribute(MenuWebConstants.ModelAttribute.UPDATE, existingMenu);
			} else {
				menuUpdate.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		model.addAttribute("menuSelectedUuid", requestParams.get("menuSelectedUuid"));

		return VIEW_MENU_LIST;
	}

	@PostMapping(value = MenuWebConstants.Path.MENU, params = "create")
	public RedirectView create(@ModelAttribute(MenuWebConstants.ModelAttribute.CREATE) MenuDto menuCreate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (menuCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
					"Create new record doesn't need UUID.");
		} else {

			List<UserGroupDto> userGroups = new ArrayList<UserGroupDto>();
			for (UserGroupDto userGroup : menuCreate.getUserGroups()) {
				if (BooleanUtils.parseBoolean(userGroup.getSelected())) {
					userGroups.add(userGroup);
				}
			}
			menuCreate.setUserGroups(userGroups);

			try {
				menuCreate = menuFacade.update(menuCreate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(MenuDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(MenuDto.UUID, menuCreate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;
	}

	@PostMapping(value = MenuWebConstants.Path.MENU, params = "update")
	public RedirectView update(@ModelAttribute(MenuWebConstants.ModelAttribute.UPDATE) MenuDto menuUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (menuUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
					"Update record needed existing UUID.");
		} else {

			List<UserGroupDto> userGroups = new ArrayList<UserGroupDto>();
			for (UserGroupDto userGroup : menuUpdate.getUserGroups()) {
				if (BooleanUtils.parseBoolean(userGroup.getSelected())) {
					userGroups.add(userGroup);
				}
			}
			menuUpdate.setUserGroups(userGroups);

			try {
				menuUpdate = menuFacade.update(menuUpdate);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(MenuDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(MenuDto.UUID, menuUpdate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;
	}

	@PostMapping(value = MenuWebConstants.Path.MENU, params = "delete")
	public RedirectView delete(@ModelAttribute(MenuWebConstants.ModelAttribute.UPDATE) MenuDto menuUpdate, Model model,
			BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			menuFacade.delete(menuUpdate.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(MenuDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(MenuWebConstants.ModelAttribute.UPDATE, menuUpdate);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_MENU);
		return redirectView;

	}
	
	@PostMapping(value = MenuWebConstants.Path.MENU, params = "move")
	public RedirectView move(Model model, @RequestParam Map<String, Object> requestParams,
			final RedirectAttributes redirectAttributes) {

		String fromUuid = (String) requestParams.get("fromUuid");
		String toUuid = (String) requestParams.get("toUuid");
		String toIndex = (String) requestParams.get("toIndex");

		MapBindingResult bindingResult = new MapBindingResult(new HashMap<String, Object>(), MenuDto.class.getName());

		try {
			menuFacade.changePosition(UUID.fromString(fromUuid),
					StringUtils.isNotBlank(toUuid) ? UUID.fromString(toUuid) : null, Integer.valueOf(toIndex));

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
