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
import com.beanframework.backoffice.ImexWebConstants;
import com.beanframework.backoffice.ImexWebConstants.ImexPreAuthorizeEnum;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.ImexDto;
import com.beanframework.core.facade.ImexFacade;

@PreAuthorize("isAuthenticated()")
@Controller
public class ImexController extends AbstractController {

	@Autowired
	private ImexFacade imexFacade;

	@Value(ImexWebConstants.Path.IMEX)
	private String PATH_IMEX;

	@Value(ImexWebConstants.View.LIST)
	private String VIEW_IMEX_LIST;

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = ImexWebConstants.Path.IMEX)
	public String list(@Valid @ModelAttribute(ImexWebConstants.ModelAttribute.IMEX_DTO) ImexDto imexDto, Model model) throws Exception {
		model.addAttribute("create", false);

		if (imexDto.getUuid() != null) {

			ImexDto existsDto = imexFacade.findOneByUuid(imexDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(ImexWebConstants.ModelAttribute.IMEX_DTO, existsDto);
			} else {
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_IMEX_LIST;
	}

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_CREATE)
	@GetMapping(value = ImexWebConstants.Path.IMEX, params = "create")
	public String createView(@Valid @ModelAttribute(ImexWebConstants.ModelAttribute.IMEX_DTO) ImexDto imexDto, Model model) throws Exception {

		imexDto = imexFacade.createDto();
		model.addAttribute(ImexWebConstants.ModelAttribute.IMEX_DTO, imexDto);
		model.addAttribute("create", true);

		return VIEW_IMEX_LIST;
	}

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = ImexWebConstants.Path.IMEX, params = "create")
	public RedirectView create(@Valid @ModelAttribute(ImexWebConstants.ModelAttribute.IMEX_DTO) ImexDto imexDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (imexDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				imexDto = imexFacade.create(imexDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(ImexDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(ImexDto.UUID, imexDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_IMEX);
		return redirectView;
	}

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = ImexWebConstants.Path.IMEX, params = "update")
	public RedirectView update(@Valid @ModelAttribute(ImexWebConstants.ModelAttribute.IMEX_DTO) ImexDto imexDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (imexDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				imexDto = imexFacade.update(imexDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(ImexDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(ImexDto.UUID, imexDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_IMEX);
		return redirectView;
	}

	@PreAuthorize(ImexPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = ImexWebConstants.Path.IMEX, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(ImexWebConstants.ModelAttribute.IMEX_DTO) ImexDto imexDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			imexFacade.delete(imexDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(ImexDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(ImexDto.UUID, imexDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_IMEX);
		return redirectView;

	}
}
