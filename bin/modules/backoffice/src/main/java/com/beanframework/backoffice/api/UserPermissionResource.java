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
import com.beanframework.backoffice.UserPermissionWebConstants;
import com.beanframework.backoffice.UserPermissionWebConstants.UserPermissionPreAuthorizeEnum;
import com.beanframework.backoffice.api.data.UserPermissionDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.data.UserPermissionDto;
import com.beanframework.core.facade.UserPermissionFacade;
import com.beanframework.user.domain.UserPermission;

@RestController
public class UserPermissionResource extends AbstractResource {

  @Autowired
  private UserPermissionFacade userPermissionFacade;

  @PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_READ)
  @RequestMapping(UserPermissionWebConstants.Path.Api.PERMISSION_CHECKID)
  public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams)
      throws Exception {

    String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put(UserPermission.ID, id);

    UserPermissionDto data = userPermissionFacade.findOneProperties(properties);

    String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
    if (StringUtils.isNotBlank(uuidStr)) {
      UUID uuid = UUID.fromString(uuidStr);
      if (data != null && data.getUuid().equals(uuid)) {
        return true;
      }
    }

    return data != null ? false : true;
  }

  @PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = UserPermissionWebConstants.Path.Api.PERMISSION,
      method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public DataTableResponse<DataTableResponseData> page(HttpServletRequest request)
      throws Exception {

    DataTableRequest dataTableRequest = new DataTableRequest();
    dataTableRequest.prepareDataTableRequest(request);

    Page<UserPermissionDto> pagination = userPermissionFacade.findPage(dataTableRequest);

    DataTableResponse<DataTableResponseData> dataTableResponse =
        new DataTableResponse<DataTableResponseData>();
    dataTableResponse.setDraw(dataTableRequest.getDraw());
    dataTableResponse.setRecordsTotal(userPermissionFacade.count());
    dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

    for (UserPermissionDto dto : pagination.getContent()) {

      UserPermissionDataTableResponseData data = new UserPermissionDataTableResponseData();
      data.setUuid(dto.getUuid().toString());
      data.setId(StringUtils.stripToEmpty(dto.getId()));
      data.setName(StringUtils.stripToEmpty(dto.getName()));
      data.setSort(dto.getSort());
      dataTableResponse.getData().add(data);
    }
    return dataTableResponse;
  }

  @PreAuthorize(UserPermissionPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = UserPermissionWebConstants.Path.Api.PERMISSION_HISTORY,
      method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request)
      throws Exception {

    DataTableRequest dataTableRequest = new DataTableRequest();
    dataTableRequest.prepareDataTableRequest(request);
    dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

    return historyDataTableResponse(dataTableRequest,
        userPermissionFacade.findHistory(dataTableRequest),
        userPermissionFacade.countHistory(dataTableRequest));
  }
}
