package com.beanframework.backoffice.api;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.beanframework.backoffice.CommentWebConstants;
import com.beanframework.backoffice.CommentWebConstants.CommentPreAuthorizeEnum;
import com.beanframework.backoffice.api.data.CommentDataTableResponseData;
import com.beanframework.cms.domain.Comment;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.core.api.AbstractResource;
import com.beanframework.core.data.CommentDto;
import com.beanframework.core.facade.CommentFacade;

@RestController
public class CommentResource extends AbstractResource {

  @Autowired
  private CommentFacade commentFacade;

  private static final SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy, hh:mma");

  @PreAuthorize(CommentPreAuthorizeEnum.HAS_READ)
  @RequestMapping(CommentWebConstants.Path.Api.COMMENT_CHECKID)
  public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams)
      throws Exception {

    String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

    Map<String, Object> properties = new HashMap<String, Object>();
    properties.put(Comment.ID, id);

    CommentDto data = commentFacade.findOneProperties(properties);

    String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
    if (StringUtils.isNotBlank(uuidStr)) {
      UUID uuid = UUID.fromString(uuidStr);
      if (data != null && data.getUuid().equals(uuid)) {
        return true;
      }
    }

    return data != null ? false : true;
  }

  @PreAuthorize(CommentPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = CommentWebConstants.Path.Api.COMMENT, method = RequestMethod.GET,
      produces = "application/json")
  @ResponseBody
  public DataTableResponse<CommentDataTableResponseData> page(HttpServletRequest request)
      throws Exception {

    DataTableRequest dataTableRequest = new DataTableRequest();
    dataTableRequest.prepareDataTableRequest(request);

    Page<CommentDto> pagination = commentFacade.findPage(dataTableRequest);

    DataTableResponse<CommentDataTableResponseData> dataTableResponse =
        new DataTableResponse<CommentDataTableResponseData>();
    dataTableResponse.setDraw(dataTableRequest.getDraw());
    dataTableResponse.setRecordsTotal(commentFacade.count());
    dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

    for (CommentDto dto : pagination.getContent()) {

      CommentDataTableResponseData data = new CommentDataTableResponseData();
      data.setUuid(dto.getUuid().toString());
      data.setId(StringUtils.stripToEmpty(dto.getId()));
      data.setUser(dto.getUser());
      data.setHtml(StringUtils.substring(dto.getHtml(), 0, 100) + "...");
      data.setVisibled(dto.getVisibled());

      Date lastUpdatedDate;
      if (dto.getLastModifiedDate() == null) {
        lastUpdatedDate = dto.getCreatedDate();
      } else {
        lastUpdatedDate = dto.getLastModifiedDate();
      }

      if (lastUpdatedDate != null)
        data.setLastUpdatedDate(sdf.format(lastUpdatedDate));

      dataTableResponse.getData().add(data);
    }
    return dataTableResponse;
  }

  @PreAuthorize(CommentPreAuthorizeEnum.HAS_READ)
  @RequestMapping(value = CommentWebConstants.Path.Api.COMMENT_HISTORY, method = RequestMethod.GET,
      produces = "application/json")
  @ResponseBody
  public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request)
      throws Exception {

    DataTableRequest dataTableRequest = new DataTableRequest();
    dataTableRequest.prepareDataTableRequest(request);
    dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

    return historyDataTableResponse(dataTableRequest, commentFacade.findHistory(dataTableRequest),
        commentFacade.countHistory(dataTableRequest));
  }
}
