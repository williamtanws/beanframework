package com.beanframework.backoffice.web;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

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
import com.beanframework.backoffice.DynamicFieldWebConstants;
import com.beanframework.backoffice.data.DynamicFieldDto;
import com.beanframework.backoffice.data.DynamicFieldEnumDto;
import com.beanframework.backoffice.data.LanguageDto;
import com.beanframework.backoffice.facade.DynamicFieldFacade;
import com.beanframework.backoffice.facade.DynamicFieldFacade.DynamicFieldPreAuthorizeEnum;
import com.beanframework.backoffice.facade.LanguageFacade;
import com.beanframework.common.controller.AbstractController;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.common.utils.BooleanUtils;

@Controller
public class DynamicFieldController extends AbstractController {

	@Autowired
	private DynamicFieldFacade dynamicFieldFacade;

	@Autowired
	private LanguageFacade languageFacade;

	@Value(DynamicFieldWebConstants.Path.DYNAMICFIELD)
	private String PATH_DYNAMICFIELD;

	@Value(DynamicFieldWebConstants.View.LIST)
	private String VIEW_DYNAMICFIELD_LIST;

	@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.CREATE)
	public DynamicFieldDto create() throws Exception {
		return new DynamicFieldDto();
	}

	@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE)
	public DynamicFieldDto update() throws Exception {
		return new DynamicFieldDto();
	}

	@PreAuthorize(DynamicFieldPreAuthorizeEnum.READ)
	@GetMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD)
	public String list(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE) DynamicFieldDto updateDto, Model model) throws Exception {

		List<LanguageDto> languages = languageFacade.findAllDtoLanguages();
		model.addAttribute("languages", languages);

		if (updateDto.getUuid() != null) {

			DynamicFieldDto existsDto = dynamicFieldFacade.findOneByUuid(updateDto.getUuid());

			if (existsDto != null) {

				model.addAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE, existsDto);
			} else {
				updateDto.setUuid(null);
				addErrorMessage(model, BackofficeWebConstants.Locale.RECORD_UUID_NOT_FOUND);
			}
		}

		return VIEW_DYNAMICFIELD_LIST;
	}

	@PostMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD, params = "create")
	public RedirectView create(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.CREATE) DynamicFieldDto createDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (createDto.getUuid() != null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Create new record doesn't need UUID.");
		} else {

			try {
				createDto = dynamicFieldFacade.create(createDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldDto.UUID, createDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELD);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD, params = "update")
	public RedirectView update(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE) DynamicFieldDto updateDto, Model model, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) throws Exception {

		if (updateDto.getUuid() == null) {
			redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR, "Update record needed existing UUID.");
		} else {

			// DynamicFieldEnum
			if (updateDto.getTableEnums() != null) {
				List<DynamicFieldEnumDto> enums = dynamicFieldFacade.findOneByUuid(updateDto.getUuid()).getEnums();

				for (int i = 0; i < updateDto.getTableEnums().length; i++) {

					boolean remove = true;
					if (updateDto.getTableSelectedEnums() != null && updateDto.getTableSelectedEnums().length > i && BooleanUtils.parseBoolean(updateDto.getTableSelectedEnums()[i])) {
						remove = false;
					}

					if (remove) {
						for (Iterator<DynamicFieldEnumDto> childsIterator = enums.listIterator(); childsIterator.hasNext();) {
							if (childsIterator.next().getUuid().equals(UUID.fromString(updateDto.getTableEnums()[i]))) {
								childsIterator.remove();
							}
						}
					} else {
						boolean add = true;
						for (Iterator<DynamicFieldEnumDto> childsIterator = enums.listIterator(); childsIterator.hasNext();) {
							if (childsIterator.next().getUuid().equals(UUID.fromString(updateDto.getTableEnums()[i]))) {
								add = false;
							}
						}

						if (add) {
							DynamicFieldEnumDto child = new DynamicFieldEnumDto();
							child.setUuid(UUID.fromString(updateDto.getTableEnums()[i]));
							enums.add(child);
						}
					}
				}
				updateDto.setEnums(enums);
			}

			try {
				updateDto = dynamicFieldFacade.update(updateDto);

				addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
			} catch (BusinessException e) {
				addErrorMessage(DynamicFieldDto.class, e.getMessage(), bindingResult, redirectAttributes);
			}
		}

		redirectAttributes.addAttribute(DynamicFieldDto.UUID, updateDto.getUuid());

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELD);
		return redirectView;
	}

	@PostMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD, params = "delete")
	public RedirectView delete(@ModelAttribute(DynamicFieldWebConstants.ModelAttribute.UPDATE) DynamicFieldDto updateDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		try {
			dynamicFieldFacade.delete(updateDto.getUuid());

			addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
		} catch (BusinessException e) {
			addErrorMessage(DynamicFieldDto.class, e.getMessage(), bindingResult, redirectAttributes);
			redirectAttributes.addAttribute(DynamicFieldDto.UUID, updateDto.getUuid());
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl(PATH_DYNAMICFIELD);
		return redirectView;

	}
}
