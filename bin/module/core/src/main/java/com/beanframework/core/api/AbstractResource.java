package com.beanframework.core.api;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.RevisionType;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.core.data.EmployeeDto;
import com.beanframework.user.domain.RevisionsEntity;

public class AbstractResource {

	@Autowired
	private LocaleMessageService localeMessageService;

	public DataTableResponse<HistoryDataTableResponseData> historyDataTableResponse(DataTableRequest dataTableRequest, List<Object[]> history, int recordsTotal, String localizedProperty) throws Exception {

		DataTableResponse<HistoryDataTableResponseData> dataTableResponse = new DataTableResponse<HistoryDataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(recordsTotal);
		dataTableResponse.setRecordsFiltered(history.size());

		for (Object[] object : history) {

			EmployeeDto dto = (EmployeeDto) object[0];
			dto.setPassword("(Changed)");
			RevisionsEntity revisionEntity = (RevisionsEntity) object[1];
			RevisionType revisionType = (RevisionType) object[2];
			@SuppressWarnings("unchecked")
			Set<String> propertiesChanged = (Set<String>) object[3];

			HistoryDataTableResponseData data = new HistoryDataTableResponseData();
			data.setEntity(dto);
			data.setRevisionId(String.valueOf(revisionEntity.getId()));
			data.setRevisionDate(new SimpleDateFormat("dd MMMM yyyy, hh:mma").format(revisionEntity.getRevisionDate()));
			data.setRevisionType(localeMessageService.getMessage("revision." + revisionType.name()));
			for (String property : propertiesChanged) {

				String localized = null;
				if (StringUtils.isNotBlank(localizedProperty)) {
					localized = localeMessageService.getMessage(localizedProperty + "." + property);
				}
				if (StringUtils.isBlank(localized)) {
					localized = localeMessageService.getMessage("module.backoffice." + property);
				}
				if (StringUtils.isBlank(localized)) {
					localized = localeMessageService.getMessage(property);
				}

				data.getPropertiesChanged().add(property + "=" + localized);
			}

			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}
}
