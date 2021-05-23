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
import com.beanframework.backoffice.SiteWebConstants;
import com.beanframework.backoffice.SiteWebConstants.SitePreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.SiteDto;
import com.beanframework.core.facade.SiteFacade;


@Controller
public class SiteController extends AbstractController {

  @Autowired
  private SiteFacade siteFacade;

  @Value(SiteWebConstants.Path.SITE)
  private String PATH_SITE;

  @Value(SiteWebConstants.Path.SITE_FORM)
  private String PATH_SITE_FORM;

  @Value(SiteWebConstants.View.SITE)
  private String VIEW_SITE;

  @Value(SiteWebConstants.View.SITE_FORM)
  private String VIEW_SITE_FORM;

  @PreAuthorize(SitePreAuthorizeEnum.HAS_READ)
  @GetMapping(value = SiteWebConstants.Path.SITE)
  public String page(
      @Valid @ModelAttribute(SiteWebConstants.ModelAttribute.SITE_DTO) SiteDto siteDto, Model model,
      @RequestParam Map<String, Object> requestParams) {
    return VIEW_SITE;
  }

  @PreAuthorize(SitePreAuthorizeEnum.HAS_READ)
  @GetMapping(value = SiteWebConstants.Path.SITE_FORM)
  public String form(
      @Valid @ModelAttribute(SiteWebConstants.ModelAttribute.SITE_DTO) SiteDto siteDto, Model model)
      throws BusinessException {

    if (siteDto.getUuid() != null) {
      siteDto = siteFacade.findOneByUuid(siteDto.getUuid());
    } else {
      siteDto = siteFacade.createDto();
    }
    model.addAttribute(SiteWebConstants.ModelAttribute.SITE_DTO, siteDto);

    return VIEW_SITE_FORM;
  }

  @PreAuthorize(SitePreAuthorizeEnum.HAS_CREATE)
  @PostMapping(value = SiteWebConstants.Path.SITE_FORM, params = "create")
  public RedirectView create(
      @Valid @ModelAttribute(SiteWebConstants.ModelAttribute.SITE_DTO) SiteDto siteDto, Model model,
      BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (siteDto.getUuid() != null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Create new record doesn't required UUID.");
    } else {
      try {
        siteDto = siteFacade.save(siteDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(SiteDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, siteDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_SITE_FORM);
    return redirectView;
  }

  @PreAuthorize(SitePreAuthorizeEnum.HAS_UPDATE)
  @PostMapping(value = SiteWebConstants.Path.SITE_FORM, params = "update")
  public RedirectView update(
      @Valid @ModelAttribute(SiteWebConstants.ModelAttribute.SITE_DTO) SiteDto siteDto, Model model,
      BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (siteDto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Update record required existing UUID.");
    } else {
      try {
        siteDto = siteFacade.save(siteDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(SiteDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, siteDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_SITE_FORM);
    return redirectView;
  }

  @PreAuthorize(SitePreAuthorizeEnum.HAS_DELETE)
  @PostMapping(value = SiteWebConstants.Path.SITE_FORM, params = "delete")
  public RedirectView delete(
      @Valid @ModelAttribute(SiteWebConstants.ModelAttribute.SITE_DTO) SiteDto dto, Model model,
      BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Delete record required existing UUID.");
    } else {
      try {
        siteFacade.delete(dto.getUuid());

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
      } catch (BusinessException e) {
        redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
        redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
      }
    }

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_SITE_FORM);
    return redirectView;

  }
}
