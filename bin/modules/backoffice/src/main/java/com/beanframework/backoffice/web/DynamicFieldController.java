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
import com.beanframework.backoffice.DynamicFieldWebConstants;
import com.beanframework.backoffice.DynamicFieldWebConstants.DynamicFieldPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.DynamicFieldDto;
import com.beanframework.core.facade.DynamicFieldFacade;


@Controller
public class DynamicFieldController extends AbstractController {

  @Autowired
  private DynamicFieldFacade dynamicFieldFacade;

  @Value(DynamicFieldWebConstants.Path.DYNAMICFIELD)
  private String PATH_DYNAMICFIELD;

  @Value(DynamicFieldWebConstants.Path.DYNAMICFIELD_FORM)
  private String PATH_DYNAMICFIELD_FORM;

  @Value(DynamicFieldWebConstants.View.DYNAMICFIELD)
  private String VIEW_DYNAMICFIELD;

  @Value(DynamicFieldWebConstants.View.DYNAMICFIELD_FORM)
  private String VIEW_DYNAMICFIELD_FORM;

  @PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD)
  public String page(
      @Valid @ModelAttribute(DynamicFieldWebConstants.ModelAttribute.DYNAMICFIELD_DTO) DynamicFieldDto dynamicFieldDto,
      Model model, @RequestParam Map<String, Object> requestParams) {
    return VIEW_DYNAMICFIELD;
  }

  @PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD_FORM)
  public String form(
      @Valid @ModelAttribute(DynamicFieldWebConstants.ModelAttribute.DYNAMICFIELD_DTO) DynamicFieldDto dynamicFieldDto,
      Model model) throws BusinessException {

    if (dynamicFieldDto.getUuid() != null) {
      dynamicFieldDto = dynamicFieldFacade.findOneByUuid(dynamicFieldDto.getUuid());
    } else {
      dynamicFieldDto = dynamicFieldFacade.createDto();
    }
    model.addAttribute(DynamicFieldWebConstants.ModelAttribute.DYNAMICFIELD_DTO, dynamicFieldDto);

    return VIEW_DYNAMICFIELD_FORM;
  }

  @PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_CREATE)
  @PostMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD_FORM, params = "create")
  public RedirectView create(
      @Valid @ModelAttribute(DynamicFieldWebConstants.ModelAttribute.DYNAMICFIELD_DTO) DynamicFieldDto dynamicFieldDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dynamicFieldDto.getUuid() != null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Create new record doesn't required UUID.");
    } else {
      try {
        dynamicFieldDto = dynamicFieldFacade.save(dynamicFieldDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(DynamicFieldDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, dynamicFieldDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_DYNAMICFIELD_FORM);
    return redirectView;
  }

  @PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_UPDATE)
  @PostMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD_FORM, params = "update")
  public RedirectView update(
      @Valid @ModelAttribute(DynamicFieldWebConstants.ModelAttribute.DYNAMICFIELD_DTO) DynamicFieldDto dynamicFieldDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dynamicFieldDto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Update record required existing UUID.");
    } else {
      try {
        dynamicFieldDto = dynamicFieldFacade.save(dynamicFieldDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(DynamicFieldDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, dynamicFieldDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_DYNAMICFIELD_FORM);
    return redirectView;
  }

  @PreAuthorize(DynamicFieldPreAuthorizeEnum.HAS_DELETE)
  @PostMapping(value = DynamicFieldWebConstants.Path.DYNAMICFIELD_FORM, params = "delete")
  public RedirectView delete(
      @Valid @ModelAttribute(DynamicFieldWebConstants.ModelAttribute.DYNAMICFIELD_DTO) DynamicFieldDto dto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Delete record required existing UUID.");
    } else {
      try {
        dynamicFieldFacade.delete(dto.getUuid());

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
      } catch (BusinessException e) {
        redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
        redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
      }
    }

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_DYNAMICFIELD_FORM);
    return redirectView;

  }
}
