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
import com.beanframework.backoffice.CommentWebConstants;
import com.beanframework.backoffice.CommentWebConstants.CommentPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.CommentDto;
import com.beanframework.core.facade.CommentFacade;


@Controller
public class CommentController extends AbstractController {

	@Autowired
	private CommentFacade commentFacade;

	@Value(CommentWebConstants.Path.COMMENT)
	private String PATH_COMMENT;

	@Value(CommentWebConstants.Path.COMMENT_FORM)
	private String PATH_COMMENT_FORM;

	@Value(CommentWebConstants.View.COMMENT)
	private String VIEW_COMMENT;

	@Value(CommentWebConstants.View.COMMENT_FORM)
	private String VIEW_COMMENT_FORM;

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = CommentWebConstants.Path.COMMENT)
	public String page(@Valid @ModelAttribute(CommentWebConstants.ModelAttribute.COMMENT_DTO) CommentDto commentDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		return VIEW_COMMENT;
	}

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = CommentWebConstants.Path.COMMENT_FORM)
	public String form(@Valid @ModelAttribute(CommentWebConstants.ModelAttribute.COMMENT_DTO) CommentDto commentDto, Model model) throws Exception {

		if (commentDto.getUuid() != null) {
			commentDto = commentFacade.findOneByUuid(commentDto.getUuid());
		} else {
			commentDto = commentFacade.createDto();
		}
		model.addAttribute(CommentWebConstants.ModelAttribute.COMMENT_DTO, commentDto);

		return VIEW_COMMENT_FORM;
	}

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = CommentWebConstants.Path.COMMENT_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(CommentWebConstants.ModelAttribute.COMMENT_DTO) CommentDto commentDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (commentDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				commentDto = commentFacade.create(commentDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CommentDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, commentDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT_FORM);
		return redirectView;
	}

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = CommentWebConstants.Path.COMMENT_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(CommentWebConstants.ModelAttribute.COMMENT_DTO) CommentDto commentDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (commentDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				commentDto = commentFacade.update(commentDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CommentDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, commentDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT_FORM);
		return redirectView;
	}

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = CommentWebConstants.Path.COMMENT_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(CommentWebConstants.ModelAttribute.COMMENT_DTO) CommentDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				commentFacade.delete(dto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
				redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT_FORM);
		return redirectView;

	}
}
