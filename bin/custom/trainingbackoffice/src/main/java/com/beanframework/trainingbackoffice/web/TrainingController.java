package com.beanframework.trainingbackoffice.web;

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
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.trainingbackoffice.TrainingWebConstants;
import com.beanframework.trainingbackoffice.TrainingWebConstants.TrainingPreAuthorizeEnum;
import com.beanframework.trainingcore.data.TrainingDto;
import com.beanframework.trainingcore.facade.TrainingFacade;


@Controller
public class TrainingController extends AbstractController {

	@Autowired
	private TrainingFacade trainingFacade;

	@Value(TrainingWebConstants.Path.TRAINING)
	private String PATH_TRAINING;

	@Value(TrainingWebConstants.Path.TRAINING_FORM)
	private String PATH_TRAINING_FORM;

	@Value(TrainingWebConstants.View.TRAINING)
	private String VIEW_TRAINING;

	@Value(TrainingWebConstants.View.TRAINING_FORM)
	private String VIEW_TRAINING_FORM;

	@PreAuthorize(TrainingPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = TrainingWebConstants.Path.TRAINING)
	public String page(@Valid @ModelAttribute(TrainingWebConstants.ModelAttribute.TRAINING_DTO) TrainingDto trainingDto, Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
		return VIEW_TRAINING;
	}

	@PreAuthorize(TrainingPreAuthorizeEnum.HAS_READ)
	@GetMapping(value = TrainingWebConstants.Path.TRAINING_FORM)
	public String form(@Valid @ModelAttribute(TrainingWebConstants.ModelAttribute.TRAINING_DTO) TrainingDto trainingDto, Model model) throws Exception {

		if (trainingDto.getUuid() != null) {
			trainingDto = trainingFacade.findOneByUuid(trainingDto.getUuid());
		} else {
			trainingDto = trainingFacade.createDto();
		}
		model.addAttribute(TrainingWebConstants.ModelAttribute.TRAINING_DTO, trainingDto);

		return VIEW_TRAINING_FORM;
	}

	@PreAuthorize(TrainingPreAuthorizeEnum.HAS_CREATE)
	@PostMapping(value = TrainingWebConstants.Path.TRAINING_FORM, params = "create")
	public RedirectView create(@Valid @ModelAttribute(TrainingWebConstants.ModelAttribute.TRAINING_DTO) TrainingDto trainingDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (trainingDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't required UUID.");
		} else {
			try {
				trainingDto = trainingFacade.create(trainingDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(TrainingDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, trainingDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_TRAINING_FORM);
		return redirectView;
	}

	@PreAuthorize(TrainingPreAuthorizeEnum.HAS_UPDATE)
	@PostMapping(value = TrainingWebConstants.Path.TRAINING_FORM, params = "update")
	public RedirectView update(@Valid @ModelAttribute(TrainingWebConstants.ModelAttribute.TRAINING_DTO) TrainingDto trainingDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (trainingDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record required existing UUID.");
		} else {
			try {
				trainingDto = trainingFacade.update(trainingDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(TrainingDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(GenericDto.UUID, trainingDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_TRAINING_FORM);
		return redirectView;
	}

	@PreAuthorize(TrainingPreAuthorizeEnum.HAS_DELETE)
	@PostMapping(value = TrainingWebConstants.Path.TRAINING_FORM, params = "delete")
	public RedirectView delete(@Valid @ModelAttribute(TrainingWebConstants.ModelAttribute.TRAINING_DTO) TrainingDto dto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		if (dto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Delete record required existing UUID.");
		} else {
			try {
				trainingFacade.delete(dto.getUuid());

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
			} catch (BusinessException e) {
				redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
				redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
			}
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_TRAINING_FORM);
		return redirectView;

	}
}
