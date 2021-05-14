package com.beanframework.backoffice.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.beanframework.backoffice.BackofficeWebConstants;
import com.beanframework.backoffice.CustomerWebConstants;
import com.beanframework.backoffice.CustomerWebConstants.CustomerPreAuthorizeEnum;
import com.beanframework.backoffice.api.data.CustomerDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.CustomerDto;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.facade.CustomerFacade;
import com.beanframework.user.domain.Customer;

@RestController
public class CustomerResource extends AbstractResource {
  @Autowired
  private CustomerFacade customerFacade;

  @PreAuthorize(CustomerPreAuthorizeEnum.HAS_READ)
  @RequestMapping(CustomerWebConstants.Path.Api.CUSTOMER_CHECKID)
  public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams)
      throws Exception {

    String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put(Customer.ID, id);

    CustomerDto data = customerFacade.findOneProperties(properties);

    String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
    if (StringUtils.isNotBlank(uuidStr)) {
      UUID uuid = UUID.fromString(uuidStr);
      if (data != null && data.getUuid().equals(uuid)) {
        return true;
      }
    }

    return data != null ? false : true;
  }

  @PreAuthorize(CustomerPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = CustomerWebConstants.Path.Api.CUSTOMER, method = RequestMethod.GET,
      produces = "application/json")
  @ResponseBody
  public DataTableResponse<DataTableResponseData> page(HttpServletRequest request)
      throws Exception {

    DataTableRequest dataTableRequest = new DataTableRequest();
    dataTableRequest.prepareDataTableRequest(request);

    Page<CustomerDto> pagination = customerFacade.findPage(dataTableRequest);

    DataTableResponse<DataTableResponseData> dataTableResponse =
        new DataTableResponse<DataTableResponseData>();
    dataTableResponse.setDraw(dataTableRequest.getDraw());
    dataTableResponse.setRecordsTotal(customerFacade.count());
    dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

    for (CustomerDto dto : pagination.getContent()) {

      CustomerDataTableResponseData data = new CustomerDataTableResponseData();
      data.setUuid(dto.getUuid().toString());
      data.setId(StringUtils.stripToEmpty(dto.getId()));
      data.setName(StringUtils.stripToEmpty(dto.getName()));
      dataTableResponse.getData().add(data);
    }
    return dataTableResponse;
  }

  @PreAuthorize(CustomerPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = CustomerWebConstants.Path.Api.CUSTOMER_HISTORY,
      method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request)
      throws Exception {

    DataTableRequest dataTableRequest = new DataTableRequest();
    dataTableRequest.prepareDataTableRequest(request);
    dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

    return historyDataTableResponse(dataTableRequest, customerFacade.findHistory(dataTableRequest),
        customerFacade.countHistory(dataTableRequest));
  }
}
