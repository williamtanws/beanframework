package com.beanframework.backoffice.api;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.RevisionType;
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
import com.beanframework.backoffice.data.CommentDataResponse;
import com.beanframework.comment.domain.Comment;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataResponse;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.core.data.CommentDto;
import com.beanframework.core.facade.CommentFacade;
import com.beanframework.core.facade.CommentFacade.CommentPreAuthorizeEnum;
import com.beanframework.user.domain.RevisionsEntity;

@RestController
public class CommentResource {

	@Autowired
	private CommentFacade commentFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_READ)
	@RequestMapping(CommentWebConstants.Path.Api.CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

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
	@RequestMapping(value = CommentWebConstants.Path.Api.PAGE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<CommentDataResponse> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.getSkipColumnIndexes().add(2);
		dataTableRequest.getSkipColumnIndexes().add(3);
		dataTableRequest.prepareDataTableRequest(request);
		
		Page<CommentDto> pagination = commentFacade.findPage(dataTableRequest);

		DataTableResponse<CommentDataResponse> dataTableResponse = new DataTableResponse<CommentDataResponse>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(commentFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (CommentDto dto : pagination.getContent()) {

			CommentDataResponse data = new CommentDataResponse();
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
				data.setLastUpdatedDate(new SimpleDateFormat("dd MMMM yyyy, hh:mma").format(lastUpdatedDate));
			
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@PreAuthorize(CommentPreAuthorizeEnum.HAS_READ)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = CommentWebConstants.Path.Api.HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataResponse> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		List<Object[]> history = commentFacade.findHistory(dataTableRequest);

		DataTableResponse<HistoryDataResponse> dataTableResponse = new DataTableResponse<HistoryDataResponse>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(commentFacade.countHistory(dataTableRequest));
		dataTableResponse.setRecordsFiltered(history.size());

		for (Object[] object : history) {

			CommentDto dto = (CommentDto) object[0];
			RevisionsEntity revisionEntity = (RevisionsEntity) object[1];
			RevisionType revisionType = (RevisionType) object[2];
			Set<String> propertiesChanged = (Set<String>) object[3];

			HistoryDataResponse data = new HistoryDataResponse();
			data.setEntity(dto);
			data.setRevisionId(String.valueOf(revisionEntity.getId()));
			data.setRevisionDate(new SimpleDateFormat("dd MMMM yyyy, hh:mma").format(revisionEntity.getRevisionDate()));
			data.setRevisionType(localeMessageService.getMessage("revision."+revisionType.name()));
			for (String property : propertiesChanged) {
				String localized = localeMessageService.getMessage("module.comment." + property);
				data.getPropertiesChanged().add(property + "=" + localized);
			}

			dataTableResponse.getData().add(data);

		}
		return dataTableResponse;
	}
}