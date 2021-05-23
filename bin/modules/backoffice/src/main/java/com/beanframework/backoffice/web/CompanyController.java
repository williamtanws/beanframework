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
import com.beanframework.backoffice.CompanyWebConstants;
import com.beanframework.backoffice.CompanyWebConstants.CompanyPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.CompanyDto;
import com.beanframework.core.facade.CompanyFacade;


@Controller
public class CompanyController extends AbstractController {

  @Autowired
  private CompanyFacade companyFacade;

  @Value(CompanyWebConstants.Path.COMPANY)
  private String PATH_COMPANY;

  @Value(CompanyWebConstants.Path.COMPANY_FORM)
  private String PATH_COMPANY_FORM;

  @Value(CompanyWebConstants.View.COMPANY)
  private String VIEW_COMPANY;

  @Value(CompanyWebConstants.View.COMPANY_FORM)
  private String VIEW_COMPANY_FORM;

  @PreAuthorize(CompanyPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = CompanyWebConstants.Path.COMPANY)
  public String page(
      @Valid @ModelAttribute(CompanyWebConstants.ModelAttribute.COMPANY_DTO) CompanyDto companyDto,
      Model model, @RequestParam Map<String, Object> requestParams) {
    return VIEW_COMPANY;
  }

  @PreAuthorize(CompanyPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = CompanyWebConstants.Path.COMPANY_FORM)
  public String form(
      @Valid @ModelAttribute(CompanyWebConstants.ModelAttribute.COMPANY_DTO) CompanyDto companyDto,
      Model model) throws BusinessException {

    if (companyDto.getUuid() != null) {
      companyDto = companyFacade.findOneByUuid(companyDto.getUuid());
    } else {
      companyDto = companyFacade.createDto();
    }
    model.addAttribute(CompanyWebConstants.ModelAttribute.COMPANY_DTO, companyDto);

    return VIEW_COMPANY_FORM;
  }

  @PreAuthorize(CompanyPreAuthorizeEnum.HAS_CREATE)
  @PostMapping(value = CompanyWebConstants.Path.COMPANY_FORM, params = "create")
  public RedirectView create(
      @Valid @ModelAttribute(CompanyWebConstants.ModelAttribute.COMPANY_DTO) CompanyDto companyDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (companyDto.getUuid() != null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Create new record doesn't required UUID.");
    } else {
      try {
        companyDto = companyFacade.save(companyDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(CompanyDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, companyDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_COMPANY_FORM);
    return redirectView;
  }

  @PreAuthorize(CompanyPreAuthorizeEnum.HAS_UPDATE)
  @PostMapping(value = CompanyWebConstants.Path.COMPANY_FORM, params = "update")
  public RedirectView update(
      @Valid @ModelAttribute(CompanyWebConstants.ModelAttribute.COMPANY_DTO) CompanyDto companyDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (companyDto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Update record required existing UUID.");
    } else {
      try {
        companyDto = companyFacade.save(companyDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(CompanyDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, companyDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_COMPANY_FORM);
    return redirectView;
  }

  @PreAuthorize(CompanyPreAuthorizeEnum.HAS_DELETE)
  @PostMapping(value = CompanyWebConstants.Path.COMPANY_FORM, params = "delete")
  public RedirectView delete(
      @Valid @ModelAttribute(CompanyWebConstants.ModelAttribute.COMPANY_DTO) CompanyDto dto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (dto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Delete record required existing UUID.");
    } else {
      try {
        companyFacade.delete(dto.getUuid());

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.DELETE_SUCCESS);
      } catch (BusinessException e) {
        redirectAttributes.addFlashAttribute(ERROR, e.getMessage());
        redirectAttributes.addAttribute(GenericDto.UUID, dto.getUuid());
      }
    }

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_COMPANY_FORM);
    return redirectView;

  }
}
