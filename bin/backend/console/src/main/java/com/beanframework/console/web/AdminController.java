package com.beanframework.console.web;

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

import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.console.AdminWebConstants;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.core.data.AdminDto;
import com.beanframework.core.facade.AdminFacade;

@Controller
public class AdminController extends AbstractController {

	@Autowired
	private AdminFacade adminFacade;

	@Value(AdminWebConstants.Path.ADMIN)
	private String PATH_ADMIN;

	@Value(AdminWebConstants.View.LIST)
	private String VIEW_ADMIN_LIST;

	@ModelAttribute(AdminWebConstants.ModelAttribute.UPDATE)
	public AdminDto update(Model model) throws Exception {
		model.addAttribute("create", false);
		return new AdminDto();
	}

	@GetMapping(value = AdminWebConstants.Path.ADMIN)
	public String list(@ModelAttribute(AdminWebConstants.ModelAttribute.UPDATE) AdminDto adminUpdate, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		model.addAttribute("create", false);
		
		if (adminUpdate.getUuid() != null) {
			AdminDto existingAdmin = adminFacade.findOneByUuid(adminUpdate.getUuid());

			if (existingAdmin != null) {

				model.addAttribute(AdminWebConstants.ModelAttribute.UPDATE, existingAdmin);
			} else {
				adminUpdate.setUuid(null);
				addErrorMessage(model, ConsoleWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_ADMIN_LIST;
	}
	
	@GetMapping(value = AdminWebConstants.Path.ADMIN, params = "create")
	public String createView(Model model) throws Exception {
		model.addAttribute("create", true);
		return VIEW_ADMIN_LIST;
	}

	@PostMapping(value = AdminWebConstants.Path.ADMIN, params = "create")
	public RedirectView create(@ModelAttribute(AdminWebConstants.ModelAttribute.CREATE) AdminDto adminCreate, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (adminCreate.getUuid() != null) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {
			try {
				adminCreate = adminFacade.create(adminCreate);

				addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(AdminDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(AdminDto.UUID, adminCreate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ADMIN);
		return redirectView;
	}

	@PostMapping(value = AdminWebConstants.Path.ADMIN, params = "update")
	public RedirectView update(@ModelAttribute(AdminWebConstants.ModelAttribute.UPDATE) AdminDto adminUpdate, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (adminUpdate.getUuid() == null) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				adminUpdate = adminFacade.update(adminUpdate);

				addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(AdminDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(AdminDto.UUID, adminUpdate.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ADMIN);
		return redirectView;
	}

	@PostMapping(value = AdminWebConstants.Path.ADMIN, params = "delete")
	public RedirectView delete(@ModelAttribute(AdminWebConstants.ModelAttribute.UPDATE) AdminDto adminUpdate, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			adminFacade.delete(adminUpdate.getUuid());

			addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(AdminDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(AdminWebConstants.ModelAttribute.UPDATE, adminUpdate);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ADMIN);
		return redirectView;

	}
}
