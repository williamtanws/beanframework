package com.beanframework.console.web;

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

import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.console.AdminWebConstants;
import com.beanframework.console.ConsoleWebConstants;
import com.beanframework.core.data.AdminDto;
import com.beanframework.core.facade.AdminFacade;
import com.beanframework.core.facade.AdminFacade.AdminPreAuthorizeEnum;

@Controller
public class AdminController extends AbstractController {

	@Autowired
	private AdminFacade adminFacade;

	@Value(AdminWebConstants.Path.ADMIN)
	private String PATH_ADMIN;

	@Value(AdminWebConstants.View.LIST)
	private String VIEW_ADMIN_LIST;

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = AdminWebConstants.Path.ADMIN)
	public String list(@Valid @ModelAttribute(AdminWebConstants.ModelAttribute.ADMIN_DTO) AdminDto adminDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		model.addAttribute("create", false);

		if (adminDto.getUuid() != null) {
			AdminDto existingAdmin = adminFacade.findOneByUuid(adminDto.getUuid());

			if (existingAdmin != null) {

				model.addAttribute(AdminWebConstants.ModelAttribute.ADMIN_DTO, existingAdmin);
			} else {
				adminDto.setUuid(null);
				addErrorMessage(model, ConsoleWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_ADMIN_LIST;
	}

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = AdminWebConstants.Path.ADMIN, params = "create")
	public String createView(@Valid @ModelAttribute(AdminWebConstants.ModelAttribute.ADMIN_DTO) AdminDto adminDto, Model model) throws Exception {

		adminDto = adminFacade.createDto();
		model.addAttribute(AdminWebConstants.ModelAttribute.ADMIN_DTO, adminDto);
		model.addAttribute("create", true);

		return VIEW_ADMIN_LIST;
	}

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = AdminWebConstants.Path.ADMIN, params = "create")
	public RedirectView create(@Valid @ModelAttribute(AdminWebConstants.ModelAttribute.ADMIN_DTO) AdminDto adminDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (adminDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {
			try {
				adminDto = adminFacade.create(adminDto);

				addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(AdminDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(AdminDto.UUID, adminDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ADMIN);
		return redirectView;
	}

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = AdminWebConstants.Path.ADMIN, params = "update")
	public RedirectView update(@Valid @ModelAttribute(AdminWebConstants.ModelAttribute.ADMIN_DTO) AdminDto adminDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		if (adminDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(ConsoleWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {
			try {
				adminDto = adminFacade.update(adminDto);

				addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(AdminDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(AdminDto.UUID, adminDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ADMIN);
		return redirectView;
	}

	@PreAuthorize(AdminPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = AdminWebConstants.Path.ADMIN, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(AdminWebConstants.ModelAttribute.ADMIN_DTO) AdminDto adminDto, Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
			RedirectAttributes redirectAttributes) {

		try {
			adminFacade.delete(adminDto.getUuid());

			addSuccessMessage(redirectAttributes, ConsoleWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(AdminDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addFlashAttribute(AdminWebConstants.ModelAttribute.ADMIN_DTO, adminDto);
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_ADMIN);
		return redirectView;

	}
}
