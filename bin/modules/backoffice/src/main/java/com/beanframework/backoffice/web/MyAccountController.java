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
import com.beanframework.backoffice.MyAccountWebConstants;
import com.beanframework.backoffice.MyAccountWebConstants.MyAccountPreAuthorizeEnum;
import com.beanframework.common.data.GenericDto;
import com.beanframework.common.exception.BusinessException;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.data.MyAccountDto;
import com.beanframework.core.facade.MyAccountFacade;


@Controller
public class MyAccountController extends AbstractController {

  @Autowired
  private MyAccountFacade myaccountFacade;

  @Value(MyAccountWebConstants.Path.MYACCOUNT)
  private String PATH_MYACCOUNT;

  @Value(MyAccountWebConstants.View.MYACCOUNT_FORM)
  private String VIEW_MYACCOUNT_FORM;

  @PreAuthorize(MyAccountPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = MyAccountWebConstants.Path.MYACCOUNT)
  public String form(
      @Valid @ModelAttribute(MyAccountWebConstants.ModelAttribute.MYACCOUNT_DTO) MyAccountDto myaccountDto,
      Model model) throws Exception {

    myaccountDto = myaccountFacade.getCurrentUser();
    model.addAttribute(MyAccountWebConstants.ModelAttribute.MYACCOUNT_DTO, myaccountDto);

    return VIEW_MYACCOUNT_FORM;
  }

  @PreAuthorize(MyAccountPreAuthorizeEnum.HAS_UPDATE)
  @PostMapping(value = MyAccountWebConstants.Path.MYACCOUNT, params = "update")
  public RedirectView update(
      @Valid @ModelAttribute(MyAccountWebConstants.ModelAttribute.MYACCOUNT_DTO) MyAccountDto myaccountDto,
      Model model, BindingResult bindingResult, @RequestParam Map<String, Object> requestParams,
      RedirectAttributes redirectAttributes) {

    if (myaccountDto.getUuid() == null) {
      redirectAttributes.addFlashAttribute(BackofficeWebConstants.Model.ERROR,
          "Update record required existing UUID.");
    } else {
      try {
        myaccountDto = myaccountFacade.update(myaccountDto);

        addSuccessMessage(redirectAttributes, BackofficeWebConstants.Locale.SAVE_SUCCESS);
      } catch (BusinessException e) {
        addErrorMessage(MyAccountDto.class, e.getMessage(), bindingResult, redirectAttributes);
      }
    }

    redirectAttributes.addAttribute(GenericDto.UUID, myaccountDto.getUuid());

    RedirectView redirectView = new RedirectView();
    redirectView.setContextRelative(true);
    redirectView.setUrl(PATH_MYACCOUNT);
    return redirectView;
  }
}
