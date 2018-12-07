package com.beanframework.history.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.history.domain.History;
import com.beanframework.history.service.HistoryService;

@Component
public class DtoHistoryConverter implements Converter<History, History> {

	@Autowired
	private HistoryService historyService;

	@Override
	public History convert(History source) {
		return convert(source, historyService.create());
	}

	public List<History> convert(List<History> sources) {
		List<History> convertedList = new ArrayList<History>();
		for (History source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private History convert(History source, History prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		return prototype;
	}

}
