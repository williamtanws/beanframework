package com.beanframework.backoffice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.CommentWebConstants;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.data.CommentDto;
import com.beanframework.core.facade.CommentFacade;

@Controller
public class CommentController extends AbstractController {

	@Autowired
	private CommentFacade commentFacade;

	@Value(CommentWebConstants.Path.COMMENT)
	private String PATH_COMMENT;

	@Value(CommentWebConstants.View.LIST)
	private String VIEW_COMMENT_LIST;

	@GetMapping(value = CommentWebConstants.Path.COMMENT)
	public String list(@ModelAttribute(CommentWebConstants.ModelAttribute.COMMENT_DTO) CommentDto commentDto, Model model) throws Exception {
		model.addAttribute("create", false);

		if (commentDto.getUuid() != null) {

			CommentDto existsDto = commentFacade.findOneByUuid(commentDto.getUuid());

			if (existsDto != null) {
				model.addAttribute(CommentWebConstants.ModelAttribute.COMMENT_DTO, existsDto);
			} else {
				commentDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_COMMENT_LIST;
	}

	@GetMapping(value = CommentWebConstants.Path.COMMENT, params = "create")
	public String createView(@ModelAttribute(CommentWebConstants.ModelAttribute.COMMENT_DTO) CommentDto commentDto, Model model) throws Exception {

		commentDto = commentFacade.createDto();
		model.addAttribute(CommentWebConstants.ModelAttribute.COMMENT_DTO, commentDto);
		model.addAttribute("create", true);

		return VIEW_COMMENT_LIST;
	}

	@PostMapping(value = CommentWebConstants.Path.COMMENT, params = "create")
	public RedirectView create(@ModelAttribute(CommentWebConstants.ModelAttribute.COMMENT_DTO) CommentDto commentDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (commentDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				commentDto = commentFacade.create(commentDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CommentDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CommentDto.UUID, commentDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;
	}

	@PostMapping(value = CommentWebConstants.Path.COMMENT, params = "update")
	public RedirectView update(@ModelAttribute(CommentWebConstants.ModelAttribute.COMMENT_DTO) CommentDto commentDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes)
			throws Exception {

		if (commentDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				commentDto = commentFacade.update(commentDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(CommentDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(CommentDto.UUID, commentDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;
	}

	@PostMapping(value = CommentWebConstants.Path.COMMENT, params = "delete")
	public RedirectView delete(@ModelAttribute(CommentWebConstants.ModelAttribute.COMMENT_DTO) CommentDto commentDto, Model model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			commentFacade.delete(commentDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(CommentDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(CommentDto.UUID, commentDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_COMMENT);
		return redirectView;

	}
}
