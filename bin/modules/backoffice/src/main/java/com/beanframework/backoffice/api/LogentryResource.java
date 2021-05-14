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
import com.beanframework.backoffice.LogentryWebConstants;
import com.beanframework.backoffice.LogentryWebConstants.LogentryPreAuthorizeEnum;
import com.beanframework.backoffice.api.data.LogentryDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.LogentryDto;
import com.beanframework.core.facade.LogentryFacade;
import com.beanframework.logentry.domain.Logentry;

@RestController
public class LogentryResource extends AbstractResource {

  @Autowired
  private LogentryFacade logentryFacade;

  @PreAuthorize(LogentryPreAuthorizeEnum.HAS_READ)
  @RequestMapping(LogentryWebConstants.Path.Api.LOGENTRY_CHECKID)
  public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams)
      throws Exception {

    String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put(Logentry.ID, id);

    LogentryDto data = logentryFacade.findOneProperties(properties);

    String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
    if (StringUtils.isNotBlank(uuidStr)) {
      UUID uuid = UUID.fromString(uuidStr);
      if (data != null && data.getUuid().equals(uuid)) {
        return true;
      }
    }

    return data != null ? false : true;
  }

  @PreAuthorize(LogentryPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = LogentryWebConstants.Path.Api.LOGENTRY, method = RequestMethod.GET,
      produces = "application/json")
  @ResponseBody
  public DataTableResponse<LogentryDataTableResponseData> page(HttpServletRequest request)
      throws Exception {

    DataTableRequest dataTableRequest = new DataTableRequest();
    dataTableRequest.prepareDataTableRequest(request);

    Page<LogentryDto> pagination = logentryFacade.findPage(dataTableRequest);

    DataTableResponse<LogentryDataTableResponseData> dataTableResponse =
        new DataTableResponse<LogentryDataTableResponseData>();
    dataTableResponse.setDraw(dataTableRequest.getDraw());
    dataTableResponse.setRecordsTotal(logentryFacade.count());
    dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

    for (LogentryDto dto : pagination.getContent()) {

      LogentryDataTableResponseData data = new LogentryDataTableResponseData();
      data.setUuid(dto.getUuid().toString());
      data.setType(dto.getType());
      data.setMessage(StringUtils.stripToEmpty(dto.getMessage()));
      dataTableResponse.getData().add(data);
    }
    return dataTableResponse;
  }

  @PreAuthorize(LogentryPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = LogentryWebConstants.Path.Api.LOGENTRY_HISTORY,
      method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request)
      throws Exception {

    DataTableRequest dataTableRequest = new DataTableRequest();
    dataTableRequest.prepareDataTableRequest(request);
    dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

    return historyDataTableResponse(dataTableRequest, logentryFacade.findHistory(dataTableRequest),
        logentryFacade.countHistory(dataTableRequest));
  }
}
