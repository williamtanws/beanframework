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
import com.beanframework.backoffice.EnumerationWebConstants;
import com.beanframework.backoffice.EnumerationWebConstants.EnumerationPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.core.facade.EnumerationFacade;


@Controller
public class EnumerationController extends AbstractController {

  @Autowired
  private EnumerationFacade enumerationFacade;

  @Value(EnumerationWebConstants.Path.ENUMERATION)
  private String PATH_ENUMERATION;

  @Value(EnumerationWebConstants.Path.ENUMERATION_FORM)
  private String PATH_ENUMERATION_FORM;

  @Value(EnumerationWebConstants.View.ENUMERATION)
  private String VIEW_ENUMERATION;

  @Value(EnumerationWebConstants.View.ENUMERATION_FORM)
  private String VIEW_ENUMERATION_FORM;

  @PreAuthorize(EnumerationPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = EnumerationWebConstants.Path.ENUMERATION)
  public String page(
      @Valid @ModelAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO) EnumerationDto enumerationDto,
      Model model, @RequestParam Map<String, Object> requestParams) {
    return VIEW_ENUMERATION;
  }

  @PreAuthorize(EnumerationPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = EnumerationWebConstants.Path.ENUMERATION_FORM)
  public String form(
      @Valid @ModelAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO) EnumerationDto enumerationDto,
      Model model) throws BusinessException {

    if (enumerationDto.getUuid() != null) {
      enumerationDto = enumerationFacade.findOneByUuid(enumerationDto.getUuid());
    } else {
      enumerationDto = enumerationFacade.createDto();
    }
    model.addAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO, enumerationDto);

    return VIEW_ENUMERATION_FORM;
  }

  @PreAuthorize(EnumerationPreAuthorizeEnum.HAS_CREATE)
  @PostMapping(value = EnumerationWebConstants.Path.ENUMERATION_FORM, params = "create")
  public RedirectView create(
      @Valid @ModelAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO) EnumerationDto enumerationDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (enumerationDto.getUuid() != null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Create new record doesn't required UUID.");
    } else {
      try {
        enumerationDto = enumerationFacade.save(enumerationDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(EnumerationDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, enumerationDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_ENUMERATION_FORM);
    return redirectView;
  }

  @PreAuthorize(EnumerationPreAuthorizeEnum.HAS_UPDATE)
  @PostMapping(value = EnumerationWebConstants.Path.ENUMERATION_FORM, params = "update")
  public RedirectView update(
      @Valid @ModelAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO) EnumerationDto enumerationDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (enumerationDto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Update record required existing UUID.");
    } else {
      try {
        enumerationDto = enumerationFacade.save(enumerationDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(EnumerationDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, enumerationDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_ENUMERATION_FORM);
    return redirectView;
  }

  @PreAuthorize(EnumerationPreAuthorizeEnum.HAS_DELETE)
  @PostMapping(value = EnumerationWebConstants.Path.ENUMERATION_FORM, params = "delete")
  public RedirectView delete(
      @Valid @ModelAttribute(EnumerationWebConstants.ModelAttribute.ENUMERATION_DTO) EnumerationDto dto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Delete record required existing UUID.");
    } else {
      try {
        enumerationFacade.delete(dto.getUuid());

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
      } catch (BusinessException e) {
        redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
        redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
      }
    }

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_ENUMERATION_FORM);
    return redirectView;

  }
}
