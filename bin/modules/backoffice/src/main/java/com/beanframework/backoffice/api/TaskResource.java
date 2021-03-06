package com.beanframework.backoffice.api;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.beanframework.backoffice.TaskWebConstants;
import com.beanframework.backoffice.TaskWebConstants.TaskPreAuthorizeEnum;
import com.beanframework.backoffice.api.data.TaskDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.config.dto.TaskDto;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.facade.TaskFacade;

@RestController
public class TaskResource extends AbstractResource {

  @Autowired
  private TaskFacade taskFacade;

  @PreAuthorize(TaskPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = TaskWebConstants.Path.Api.TASK, method = RequestMethod.GET,
      produces = "application/json")
  @ResponseBody
  public DataTableResponse<DataTableResponseData> page(HttpServletRequest request)
      throws Exception {

    DataTableRequest dataTableRequest = new DataTableRequest();
    dataTableRequest.prepareDataTableRequest(request);

    Page<TaskDto> pagination = taskFacade.findPage(dataTableRequest);

    DataTableResponse<DataTableResponseData> dataTableResponse =
        new DataTableResponse<DataTableResponseData>();
    dataTableResponse.setDraw(dataTableRequest.getDraw());
    dataTableResponse.setRecordsTotal(taskFacade.count());
    dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

    for (TaskDto dto : pagination.getContent()) {
      TaskDataTableResponseData data = new TaskDataTableResponseData();
      data.setId(dto.getId());
      data.setName(dto.getName());
      dataTableResponse.getData().add(data);
    }
    return dataTableResponse;
  }
}
