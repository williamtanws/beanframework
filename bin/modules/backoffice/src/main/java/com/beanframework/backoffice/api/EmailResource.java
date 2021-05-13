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
import com.beanframework.backoffice.EmailWebConstants;
import com.beanframework.backoffice.EmailWebConstants.EmailPreAuthorizeEnum;
import com.beanframework.backoffice.api.data.EmailDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.data.EmailDto;
import com.beanframework.core.facade.EmailFacade;
import com.beanframework.email.domain.Email;

@RestController
public class EmailResource extends AbstractResource {
  @Autowired
  private EmailFacade emailFacade;

  @PreAuthorize(EmailPreAuthorizeEnum.HAS_READ)
  @RequestMapping(EmailWebConstants.Path.Api.EMAIL_CHECKID)
  public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams)
      throws Exception {

    String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put(Email.ID, id);

    EmailDto data = emailFacade.findOneProperties(properties);

    String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
    if (StringUtils.isNotBlank(uuidStr)) {
      UUID uuid = UUID.fromString(uuidStr);
      if (data != null && data.getUuid().equals(uuid)) {
        return true;
      }
    }

    return data != null ? false : true;
  }

  @PreAuthorize(EmailPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = EmailWebConstants.Path.Api.EMAIL, method = RequestMethod.GET,
      produces = "application/json")
  @ResponseBody
  public DataTableResponse<DataTableResponseData> page(HttpServletRequest request)
      throws Exception {

    DataTableRequest dataTableRequest = new DataTableRequest();
    dataTableRequest.prepareDataTableRequest(request);

    Page<EmailDto> pagination = emailFacade.findPage(dataTableRequest);

    DataTableResponse<DataTableResponseData> dataTableResponse =
        new DataTableResponse<DataTableResponseData>();
    dataTableResponse.setDraw(dataTableRequest.getDraw());
    dataTableResponse.setRecordsTotal(emailFacade.count());
    dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

    for (EmailDto dto : pagination.getContent()) {

      EmailDataTableResponseData data = new EmailDataTableResponseData();
      data.setUuid(dto.getUuid().toString());
      data.setId(dto.getId());
      data.setName(StringUtils.stripToEmpty(dto.getName()));
      dataTableResponse.getData().add(data);
    }
    return dataTableResponse;
  }

  @PreAuthorize(EmailPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = EmailWebConstants.Path.Api.EMAIL_HISTORY, method = RequestMethod.GET,
      produces = "application/json")
  @ResponseBody
  public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request)
      throws Exception {

    DataTableRequest dataTableRequest = new DataTableRequest();
    dataTableRequest.prepareDataTableRequest(request);
    dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

    return historyDataTableResponse(dataTableRequest, emailFacade.findHistory(dataTableRequest),
        emailFacade.countHistory(dataTableRequest));
  }
}
