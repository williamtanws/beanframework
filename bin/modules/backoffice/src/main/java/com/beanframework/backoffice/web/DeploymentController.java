package com.beanframework.backoffice.web;

import java.util.Map;
import javax.validation.Valid;
import org.flowable.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import com.beanframework.backoffice.DeploymentWebConstants;
import com.beanframework.backoffice.DeploymentWebConstants.DeploymentPreAuthorizeEnum;
import com.beanframework.core.controller.AbstractController;
import com.beanframework.core.facade.DeploymentFacade;

@Controller
public class DeploymentController extends AbstractController {

  @Autowired
  private DeploymentFacade deploymentFacade;

  @Value(DeploymentWebConstants.View.DEPLOYMENT)
  private String VIEW_DEPLOYMENT;

  @Value(DeploymentWebConstants.View.DEPLOYMENT_FORM)
  private String VIEW_DEPLOYMENT_FORM;

  @PreAuthorize(DeploymentPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = DeploymentWebConstants.Path.DEPLOYMENT)
  public String page(
      @Valid @ModelAttribute(DeploymentWebConstants.ModelAttribute.DEPLOYMENT) Deployment deployment,
      Model model, @RequestParam Map<String, Object> requestParams) throws Exception {
    return VIEW_DEPLOYMENT;
  }

  @PreAuthorize(DeploymentPreAuthorizeEnum.HAS_READ)
  @GetMapping(value = DeploymentWebConstants.Path.DEPLOYMENT_FORM)
  public String form(
      @Valid @ModelAttribute(DeploymentWebConstants.ModelAttribute.DEPLOYMENT) Deployment deployment,
      Model model) throws Exception {

    deployment = deploymentFacade.findOneById(deployment.getId());
    model.addAttribute(DeploymentWebConstants.ModelAttribute.DEPLOYMENT, deployment);

    return VIEW_DEPLOYMENT_FORM;
  }
}
