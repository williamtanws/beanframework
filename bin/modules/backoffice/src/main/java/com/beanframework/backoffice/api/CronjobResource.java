package com.beanframework.backoffice.api;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
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
import com.beanframework.backoffice.CronjobWebConstants;
import com.beanframework.backoffice.CronjobWebConstants.CronjobPreAuthorizeEnum;
import com.beanframework.backoffice.api.data.CronjobDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.CronjobDto;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.facade.CronjobFacade;
import com.beanframework.cronjob.domain.Cronjob;

@RestController
public class CronjobResource extends AbstractResource {

  @Autowired
  private CronjobFacade cronjobFacade;

  @PreAuthorize(CronjobPreAuthorizeEnum.HAS_READ)
  @RequestMapping(CronjobWebConstants.Path.Api.CRONJOB_CHECKID)
  public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams)
      throws Exception {

    String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put(Cronjob.ID, id);

    CronjobDto data = cronjobFacade.findOneProperties(properties);

    String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
    if (StringUtils.isNotBlank(uuidStr)) {
      UUID uuid = UUID.fromString(uuidStr);
      if (data != null && data.getUuid().equals(uuid)) {
        return true;
      }
    }

    return data != null ? false : true;
  }

  @PreAuthorize(CronjobPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = CronjobWebConstants.Path.Api.CRONJOB, method = RequestMethod.GET,
      produces = "application/json")
  @ResponseBody
  public DataTableResponse<DataTableResponseData> page(HttpServletRequest request)
      throws Exception {

    DataTableRequest dataTableRequest = new DataTableRequest();
    dataTableRequest.prepareDataTableRequest(request);

    Page<CronjobDto> pagination = cronjobFacade.findPage(dataTableRequest);

    DataTableResponse<DataTableResponseData> dataTableResponse =
        new DataTableResponse<DataTableResponseData>();
    dataTableResponse.setDraw(dataTableRequest.getDraw());
    dataTableResponse.setRecordsTotal(cronjobFacade.count());
    dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

    for (CronjobDto dto : pagination.getContent()) {

      CronjobDataTableResponseData data = new CronjobDataTableResponseData();
      data.setUuid(dto.getUuid().toString());
      data.setId(StringUtils.stripToEmpty(dto.getId()));
      data.setJobGroup(StringUtils.stripToEmpty(dto.getJobGroup()));
      data.setName(StringUtils.stripToEmpty(dto.getName()));
      data.setStatus(dto.getStatus() == null ? "" : dto.getStatus().toString());
      dataTableResponse.getData().add(data);
    }
    return dataTableResponse;
  }

  @PreAuthorize(CronjobPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = CronjobWebConstants.Path.Api.CRONJOB_HISTORY, method = RequestMethod.GET,
      produces = "application/json")
  @ResponseBody
  public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request)
      throws Exception {

    DataTableRequest dataTableRequest = new DataTableRequest();
    dataTableRequest.prepareDataTableRequest(request);
    dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

    return historyDataTableResponse(dataTableRequest, cronjobFacade.findHistory(dataTableRequest),
        cronjobFacade.countHistory(dataTableRequest));
  }

  @PreAuthorize(CronjobPreAuthorizeEnum.HAS_READ)
  @RequestMapping(CronjobWebConstants.Path.Api.CRONJOB_CHECKJOBGROUPNAME)
  public boolean checkName(Model model, @RequestParam Map<String, Object> requestParams)
      throws Exception {

    String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
    String jobGroup = (String) requestParams.get("jobGroup");
    String name = (String) requestParams.get("name");

    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put(Cronjob.JOB_GROUP, jobGroup);
    properties.put(Cronjob.NAME, name);

    CronjobDto cronjob = cronjobFacade.findOneProperties(properties);

    if (StringUtils.isNotBlank(uuidStr) && cronjob != null
        && cronjob.getUuid().equals(UUID.fromString(uuidStr))) {
      return true;
    } else if (cronjob == null) {
      return true;
    } else {
      return false;
    }
  }

  @PreAuthorize(CronjobPreAuthorizeEnum.HAS_READ)
  @RequestMapping(CronjobWebConstants.Path.Api.CRONJOB_CHECKJOBCLASS)
  public boolean checkJobClass(Model model, @RequestParam Map<String, Object> requestParams) {

    String jobClass = (String) requestParams.get("jobClass");

    try {
      Class.forName(jobClass).newInstance();
    } catch (Exception e) {
      return false;
    }

    return true;
  }

  @PreAuthorize(CronjobPreAuthorizeEnum.HAS_READ)
  @RequestMapping(CronjobWebConstants.Path.Api.CRONJOB_CHECKCRONEXPRESSION)
  public boolean checkConExpression(Model model, @RequestParam Map<String, Object> requestParams) {

    String conExpression = (String) requestParams.get("conExpression");

    if (StringUtils.isNotBlank(conExpression)) {
      boolean isValid = CronExpression.isValidExpression(conExpression);
      return isValid;
    } else {
      return true;
    }
  }
}
