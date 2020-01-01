package com.beanframework.backoffice.api;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.RevisionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.beanframework.backoffice.AuditorWebConstants;
import com.beanframework.backoffice.data.AuditorDataTableResponseData;
import com.beanframework.common.data.AuditorDto;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.core.data.DataTableResponseData;
import com.beanframework.core.facade.AuditorFacade;
import com.beanframework.core.facade.AuditorFacade.PreAuthorizeEnum;
import com.beanframework.user.domain.RevisionsEntity;

@RestController
public class AuditorResource {

	@Autowired
	private AuditorFacade auditorFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@PreAuthorize(PreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = AuditorWebConstants.Path.Api.PAGE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<DataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		
		Page<AuditorDto> pagination = auditorFacade.findPage(dataTableRequest);

		DataTableResponse<DataTableResponseData> dataTableResponse = new DataTableResponse<DataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(auditorFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (AuditorDto dto : pagination.getContent()) {

			AuditorDataTableResponseData data = new AuditorDataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setName(StringUtils.stripToEmpty(dto.getName()));
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@PreAuthorize(PreAuthorizeEnum.HAS_READ)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = AuditorWebConstants.Path.Api.HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		List<Object[]> history = auditorFacade.findHistory(dataTableRequest);

		DataTableResponse<HistoryDataTableResponseData> dataTableResponse = new DataTableResponse<HistoryDataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(auditorFacade.countHistory(dataTableRequest));
		dataTableResponse.setRecordsFiltered(history.size());

		for (Object[] object : history) {

			AuditorDto dto = (AuditorDto) object[0];
			RevisionsEntity revisionEntity = (RevisionsEntity) object[1];
			RevisionType revisionType = (RevisionType) object[2];
			Set<String> propertiesChanged = (Set<String>) object[3];

			HistoryDataTableResponseData data = new HistoryDataTableResponseData();
			data.setEntity(dto);
			data.setRevisionId(String.valueOf(revisionEntity.getId()));
			data.setRevisionDate(new SimpleDateFormat("dd MMMM yyyy, hh:mma").format(revisionEntity.getRevisionDate()));
			data.setRevisionType(localeMessageService.getMessage("revision."+revisionType.name()));
			for (String property : propertiesChanged) {
				String localized = localeMessageService.getMessage("module.auditor." + property);
				data.getPropertiesChanged().add(property + "=" + localized);
			}

			dataTableResponse.getData().add(data);

		}
		return dataTableResponse;
	}
}