package com.beanframework.core.api;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;

import org.hibernate.envers.RevisionType;
import org.springframework.beans.factory.annotation.Autowired;

import com.beanframework.common.data.DataTableRequest;
import com.beanframework.common.data.DataTableResponse;
import com.beanframework.common.data.HistoryDataTableResponseData;
import com.beanframework.common.service.LocaleMessageService;
import com.beanframework.core.config.dto.RevisionsDto;

public class AbstractResource {

	@Autowired
	private LocaleMessageService localeMessageService;

	@SuppressWarnings("unchecked")
	public DataTableResponse<HistoryDataTableResponseData> historyDataTableResponse(DataTableRequest dataTableRequest, List<Object[]> history, int recordsTotal) throws Exception {

		DataTableResponse<HistoryDataTableResponseData> dataTableResponse = new DataTableResponse<HistoryDataTableResponseData>();
		dataTableResponse.setDraw(dataTableRequest.getDraw());
		dataTableResponse.setRecordsTotal(recordsTotal);
		dataTableResponse.setRecordsFiltered(recordsTotal);

		for (Object[] object : history) {

			RevisionsDto revisionDto = (RevisionsDto) object[1];
			RevisionType revisionType = (RevisionType) object[2];
			Set<String> propertiesChanged = (Set<String>) object[3];

			HistoryDataTableResponseData data = new HistoryDataTableResponseData();
			data.setEntity(object[0]);
			data.setRevisionId(String.valueOf(revisionDto.getId()));
			data.setRevisionDate(new SimpleDateFormat("dd MMM yy, HH:mm:ss").format(revisionDto.getRevisionDate()));
			data.setRevisionType(revisionType.name());
			data.setRevisionTypeLocale(localeMessageService.getMessage("revision." + revisionType.name()));
			data.setPropertiesChanged(propertiesChanged);

			dataTableResponse.getData().add(data);
		}
		return dataTableResponse;
	}
}
