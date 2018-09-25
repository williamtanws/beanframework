package com.beanframework.history.converter;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.history.domain.History;
import com.beanframework.history.service.HistoryService;

@Component
public class EntityHistoryConverter implements Converter<History, History> {

	@Autowired
	private HistoryService historyService;

	@Override
	public History convert(History source) {

		Optional<History> prototype = null;
		if (source.getUuid() != null) {
			prototype = historyService.findEntityByUuid(source.getUuid());
			if (prototype.isPresent() == false) {
				prototype = Optional.of(historyService.create());
			}
		}
		else {
			prototype = Optional.of(historyService.create());
		}

		return convert(source, prototype.get());
	}

	private History convert(History source, History prototype) {

		prototype.setId(source.getId());
		prototype.setLastModifiedDate(new Date());

		return prototype;
	}

}
