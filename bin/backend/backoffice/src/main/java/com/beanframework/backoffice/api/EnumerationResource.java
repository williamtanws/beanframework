package com.beanframework.backoffice.api;

import java.text.SimpleDateFormat;
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
import com.beanframework.backoffice.EnumerationWebConstants;
import com.beanframework.backoffice.data.EnumerationDataTableResponseData;
import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.core.data.EnumerationDto;
import com.beanframework.core.facade.EnumerationFacade;
import com.beanframework.core.facade.EnumerationFacade.EnumPreAuthorizeEnum;
import com.beanframework.enumuration.domain.Enumeration;
import com.beanframework.user.domain.RevisionsEntity;

@RestController
public class EnumerationResource {

	@Autowired
	private EnumerationFacade enumerationFacade;

	@Autowired
	private LocaleMessageService localeMessageService;

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_READ)
	@RequestMapping(EnumerationWebConstants.Path.Api.CHECKID)
	public boolean checkId(Model model, @RequestParam Map<String, Object> requestParams) throws Exception {

		String id = requestParams.get(BackofficeWebConstants.Param.ID).toString();

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(Enumeration.ID, id);

		EnumerationDto data = enumerationFacade.findOneProperties(properties);

		String uuidStr = (String) requestParams.get(BackofficeWebConstants.Param.UUID);
		if (StringUtils.isNotBlank(uuidStr)) {
			UUID uuid = UUID.fromString(uuidStr);
			if (data != null && data.getUuid().equals(uuid)) {
				return true;
			}
		}

		return data != null ? false : true;
	}

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_READ)
	@RequestMapping(value = EnumerationWebConstants.Path.Api.PAGE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<EnumerationDataTableResponseData> page(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.getSkipColumnIndexes().add(2);
		dataTableRequest.prepareDataTableRequest(request);
		
		Page<EnumerationDto> pagination = enumerationFacade.findPage(dataTableRequest);

		DataTableResponse<EnumerationDataTableResponseData> dataTableResponse = new DataTableResponse<EnumerationDataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(enumerationFacade.count());
		dataTableResponse.setRecordsFiltered((int) pagination.getTotalElements());

		for (EnumerationDto dto : pagination.getContent()) {

			EnumerationDataTableResponseData data = new EnumerationDataTableResponseData();
			data.setUuid(dto.getUuid().toString());
			data.setId(StringUtils.stripToEmpty(dto.getId()));
			data.setName(StringUtils.stripToEmpty(dto.getName()));
			data.setSort(dto.getSort());
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}

	@PreAuthorize(EnumPreAuthorizeEnum.HAS_READ)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = EnumerationWebConstants.Path.Api.HISTORY, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public DataTableResponse<HistoryDataTableResponseData> history(HttpServletRequest request) throws Exception {

		DataTableRequest dataTableRequest = new DataTableRequest();
		dataTableRequest.prepareDataTableRequest(request);
		dataTableRequest.setUniqueId((String) request.getParameter("uuid"));

		List<Object[]> history = enumerationFacade.findHistory(dataTableRequest);

		DataTableResponse<HistoryDataTableResponseData> dataTableResponse = new DataTableResponse<HistoryDataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(enumerationFacade.countHistory(dataTableRequest));
		dataTableResponse.setRecordsFiltered(history.size());

		for (Object[] object : history) {

			EnumerationDto dto = (EnumerationDto) object[0];
			RevisionsEntity revisionEntity = (RevisionsEntity) object[1];
			RevisionType revisionType = (RevisionType) object[2];
			Set<String> propertiesChanged = (Set<String>) object[3];

			HistoryDataTableResponseData data = new HistoryDataTableResponseData();
			data.setEntity(dto);
			data.setRevisionId(String.valueOf(revisionEntity.getId()));
			data.setRevisionDate(new SimpleDateFormat("dd MMMM yyyy, hh:mma").format(revisionEntity.getRevisionDate()));
			data.setRevisionType(localeMessageService.getMessage("revision."+revisionType.name()));
			for (String property : propertiesChanged) {
				String localized = localeMessageService.getMessage("module.enumeration." + property);
				data.getPropertiesChanged().add(property + "=" + localized);
			}
			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}
}