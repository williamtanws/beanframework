package com.beanframework.backoffice.web;

import java.util.List;
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

import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.DynamicFieldEnumWebConstants;
import com.beanframework.backoffice.data.DynamicFieldEnumDto;
import com.beanframework.backoffice.data.LanguageDto;
import com.beanframework.backoffice.facade.DynamicFieldEnumFacade;
import com.beanframework.backoffice.facade.LanguageFacade;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;

@Controller
public class DynamicFieldEnumController extends AbstractController {

	@Autowired
	private DynamicFieldEnumFacade dynamicFieldEnumFacade;

	@Autowired
	private LanguageFacade languageFacade;

	@Value(DynamicFieldEnumWebConstants.Path.DYNAMICFIELDENUM)
	private String PATH_DYNAMICFieldENUM;

	@Value(DynamicFieldEnumWebConstants.View.LIST)
	private String VIEW_DYNAMICFieldENUM_LIST;

	@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.CREATE)
	public DynamicFieldEnumDto create() throws Exception {
		return new DynamicFieldEnumDto();
	}

	@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.UPDATE)
	public DynamicFieldEnumDto update() throws Exception {
		return new DynamicFieldEnumDto();
	}

	@GetMapping(value = DynamicFieldEnumWebConstants.Path.DYNAMICFIELDENUM)
	public String list(@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.UPDATE) DynamicFieldEnumDto updateDto, Model model) throws Exception {

		List<LanguageDto> languages = languageFacade.findAllDtoLanguages();
		model.addAttribute("languages", languages);

		if (updateDto.getUuid() != null) {

			DynamicFieldEnumDto existsDto = dynamicFieldEnumFacade.findOneByUuid(updateDto.getUuid());

//			List<Object[]> revisions = dynamicFieldEnumFacade.findHistoryByUuid(updateDto.getUuid(), null, null);
//			model.addAttribute(BackofficeWebConstants.Model.REVISIONS, revisions);

			if (existsDto != null) {
				model.addAttribute(DynamicFieldEnumWebConstants.ModelAttribute.UPDATE, existsDto);
			} else {
				updateDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_DYNAMICFieldENUM_LIST;
	}

	@PostMapping(value = DynamicFieldEnumWebConstants.Path.DYNAMICFIELDENUM, params = "create")
	public RedirectView create(@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.CREATE) DynamicFieldEnumDto createDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (createDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				createDto = dynamicFieldEnumFacade.create(createDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldEnumDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldEnumDto.UUID, createDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFieldENUM);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldEnumWebConstants.Path.DYNAMICFIELDENUM, params = "update")
	public RedirectView update(@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.UPDATE) DynamicFieldEnumDto updateDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (updateDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			try {
				updateDto = dynamicFieldEnumFacade.update(updateDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldEnumDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldEnumDto.UUID, updateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFieldENUM);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldEnumWebConstants.Path.DYNAMICFIELDENUM, params = "delete")
	public RedirectView delete(@ModelAttribute(DynamicFieldEnumWebConstants.ModelAttribute.UPDATE) DynamicFieldEnumDto updateDto, Model model, BindingResult bindingResult,
			@RequestParam Map<String, Object> requestParams, RedirectAttributes redirectAttributes) {

		try {
			dynamicFieldEnumFacade.delete(updateDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(DynamicFieldEnumDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(DynamicFieldEnumDto.UUID, updateDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFieldENUM);
		return redirectView;

	}
}
