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
import com.beanframework.backoffice.LogentryWebConstants;
import com.beanframework.backoffice.LogentryWebConstants.LogentryPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.LogentryDto;
import com.beanframework.core.facade.LogentryFacade;


@Controller
public class LogentryController extends AbstractController {

  @Autowired
  private LogentryFacade logentryFacade;

  @Value(LogentryWebConstants.Path.LOGENTRY)
  private String PATH_LOGENTRY;

  @Value(LogentryWebConstants.Path.LOGENTRY_FORM)
  private String PATH_LOGENTRY_FORM;

  @Value(LogentryWebConstants.View.LOGENTRY)
  private String VIEW_LOGENTRY;

  @Value(LogentryWebConstants.View.LOGENTRY_FORM)
  private String VIEW_LOGENTRY_FORM;

  @PreAuthorize(LogentryPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = LogentryWebConstants.Path.LOGENTRY)
  public String page(
      @Valid @ModelAttribute(LogentryWebConstants.ModelAttribute.LOGENTRY_DTO) LogentryDto logentryDto,
      Model model, @RequestParam Map<String, Object> requestParams) {
    return VIEW_LOGENTRY;
  }

  @PreAuthorize(LogentryPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = LogentryWebConstants.Path.LOGENTRY_FORM)
  public String form(
      @Valid @ModelAttribute(LogentryWebConstants.ModelAttribute.LOGENTRY_DTO) LogentryDto logentryDto,
      Model model) throws BusinessException {

    if (logentryDto.getUuid() != null) {
      logentryDto = logentryFacade.findOneByUuid(logentryDto.getUuid());
    } else {
      logentryDto = logentryFacade.createDto();
    }
    model.addAttribute(LogentryWebConstants.ModelAttribute.LOGENTRY_DTO, logentryDto);

    return VIEW_LOGENTRY_FORM;
  }

  @PreAuthorize(LogentryPreAuthorizeEnum.HAS_CREATE)
  @PostMapping(value = LogentryWebConstants.Path.LOGENTRY_FORM, params = "create")
  public RedirectView create(
      @Valid @ModelAttribute(LogentryWebConstants.ModelAttribute.LOGENTRY_DTO) LogentryDto logentryDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (logentryDto.getUuid() != null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Create new record doesn't required UUID.");
    } else {
      try {
        logentryDto = logentryFacade.save(logentryDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(LogentryDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, logentryDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_LOGENTRY_FORM);
    return redirectView;
  }

  @PreAuthorize(LogentryPreAuthorizeEnum.HAS_UPDATE)
  @PostMapping(value = LogentryWebConstants.Path.LOGENTRY_FORM, params = "update")
  public RedirectView update(
      @Valid @ModelAttribute(LogentryWebConstants.ModelAttribute.LOGENTRY_DTO) LogentryDto logentryDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (logentryDto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Update record required existing UUID.");
    } else {
      try {
        logentryDto = logentryFacade.save(logentryDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(LogentryDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, logentryDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_LOGENTRY_FORM);
    return redirectView;
  }

  @PreAuthorize(LogentryPreAuthorizeEnum.HAS_DELETE)
  @PostMapping(value = LogentryWebConstants.Path.LOGENTRY_FORM, params = "delete")
  public RedirectView delete(
      @Valid @ModelAttribute(LogentryWebConstants.ModelAttribute.LOGENTRY_DTO) LogentryDto dto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Delete record required existing UUID.");
    } else {
      try {
        logentryFacade.delete(dto.getUuid());

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
      } catch (BusinessException e) {
        redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
        redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
      }
    }

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_LOGENTRY_FORM);
    return redirectView;

  }
}
