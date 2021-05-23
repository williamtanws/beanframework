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
import com.beanframework.backoffice.LanguageWebConstants;
import com.beanframework.backoffice.LanguageWebConstants.LanguagePreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.LanguageDto;
import com.beanframework.core.facade.LanguageFacade;


@Controller
public class LanguageController extends AbstractController {

  @Autowired
  private LanguageFacade languageFacade;

  @Value(LanguageWebConstants.Path.LANGUAGE)
  private String PATH_LANGUAGE;

  @Value(LanguageWebConstants.Path.LANGUAGE_FORM)
  private String PATH_LANGUAGE_FORM;

  @Value(LanguageWebConstants.View.LANGUAGE)
  private String VIEW_LANGUAGE;

  @Value(LanguageWebConstants.View.LANGUAGE_FORM)
  private String VIEW_LANGUAGE_FORM;

  @PreAuthorize(LanguagePreAuthorizeEnum.HAS_READ)
  @GetMapping(value = LanguageWebConstants.Path.LANGUAGE)
  public String page(
      @Valid @ModelAttribute(LanguageWebConstants.ModelAttribute.LANGUAGE_DTO) LanguageDto languageDto,
      Model model, @RequestParam Map<String, Object> requestParams) {
    return VIEW_LANGUAGE;
  }

  @PreAuthorize(LanguagePreAuthorizeEnum.HAS_READ)
  @GetMapping(value = LanguageWebConstants.Path.LANGUAGE_FORM)
  public String form(
      @Valid @ModelAttribute(LanguageWebConstants.ModelAttribute.LANGUAGE_DTO) LanguageDto languageDto,
      Model model) throws BusinessException {

    if (languageDto.getUuid() != null) {
      languageDto = languageFacade.findOneByUuid(languageDto.getUuid());
    } else {
      languageDto = languageFacade.createDto();
    }
    model.addAttribute(LanguageWebConstants.ModelAttribute.LANGUAGE_DTO, languageDto);

    return VIEW_LANGUAGE_FORM;
  }

  @PreAuthorize(LanguagePreAuthorizeEnum.HAS_CREATE)
  @PostMapping(value = LanguageWebConstants.Path.LANGUAGE_FORM, params = "create")
  public RedirectView create(
      @Valid @ModelAttribute(LanguageWebConstants.ModelAttribute.LANGUAGE_DTO) LanguageDto languageDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (languageDto.getUuid() != null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Create new record doesn't required UUID.");
    } else {
      try {
        languageDto = languageFacade.save(languageDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(LanguageDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, languageDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_LANGUAGE_FORM);
    return redirectView;
  }

  @PreAuthorize(LanguagePreAuthorizeEnum.HAS_UPDATE)
  @PostMapping(value = LanguageWebConstants.Path.LANGUAGE_FORM, params = "update")
  public RedirectView update(
      @Valid @ModelAttribute(LanguageWebConstants.ModelAttribute.LANGUAGE_DTO) LanguageDto languageDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (languageDto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Update record required existing UUID.");
    } else {
      try {
        languageDto = languageFacade.save(languageDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(LanguageDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, languageDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_LANGUAGE_FORM);
    return redirectView;
  }

  @PreAuthorize(LanguagePreAuthorizeEnum.HAS_DELETE)
  @PostMapping(value = LanguageWebConstants.Path.LANGUAGE_FORM, params = "delete")
  public RedirectView delete(
      @Valid @ModelAttribute(LanguageWebConstants.ModelAttribute.LANGUAGE_DTO) LanguageDto dto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Delete record required existing UUID.");
    } else {
      try {
        languageFacade.delete(dto.getUuid());

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
      } catch (BusinessException e) {
        redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
        redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
      }
    }

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_LANGUAGE_FORM);
    return redirectView;

  }
}
