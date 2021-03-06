package com.beanframework.backoffice.api;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.beanframework.backoffice.DeploymentWebConstants;
import com.beanframework.backoffice.DeploymentWebConstants.DeploymentPreAuthorizeEnum;
import com.beanframework.backoffice.api.data.DeploymentDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.config.dto.DeploymentDto;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.facade.DeploymentFacade;

@RestController
public class DeploymentResource extends AbstractResource {

  @Autowired
  private DeploymentFacade deploymentFacade;

  @PreAuthorize(DeploymentPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = DeploymentWebConstants.Path.Api.DEPLOYMENT, method = RequestMethod.GET,
      produces = "application/json")
  @ResponseBody
  public DataTableResponse<DataTableResponseData> page(HttpServletRequest request)
      throws Exception {

    DataTableRequest dataTableRequest = new DataTableRequest();
    dataTableRequest.prepareDataTableRequest(request);

    Page<DeploymentDto> pagination = deploymentFacade.findPage(dataTableRequest);

    DataTableResponse<DataTableResponseData> dataTableResponse =
        new DataTableResponse<DataTableResponseData>();
    dataTableResponse.setDraw(dataTableRequest.getDraw());
    dataTableResponse.setRecordsTotal(deploymentFacade.count());
    dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

    for (DeploymentDto dto : pagination.getContent()) {
      DeploymentDataTableResponseData data = new DeploymentDataTableResponseData();
      data.setId(dto.getId());
      data.setName(dto.getName());
      dataTableResponse.getData().add(data);
    }
    return dataTableResponse;
  }
}
