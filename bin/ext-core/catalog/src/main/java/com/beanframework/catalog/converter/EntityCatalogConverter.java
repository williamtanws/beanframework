package com.beanframework.catalog.converter;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.catalog.domain.Catalog;
import com.beanframework.catalog.service.CatalogService;

@Component
public class EntityCatalogConverter implements Converter<Catalog, Catalog> {

	@Autowired
	private CatalogService catalogService;

	@Override
	public Catalog convert(Catalog source) {

		Optional<Catalog> prototype = null;
		if (source.getUuid() != null) {
			prototype = catalogService.findEntityByUuid(source.getUuid());
			if (prototype.isPresent() == false) {
				prototype = Optional.of(catalogService.create());
			}
		}
		else {
			prototype = Optional.of(catalogService.create());
		}

		return convert(source, prototype.get());
	}

	private Catalog convert(Catalog source, Catalog prototype) {

		prototype.setId(source.getId());
		prototype.setName(source.getName());
		prototype.setSort(source.getSort());
		prototype.setActive(source.getActive());
		prototype.setLastModifiedDate(new Date());

		return prototype;
	}

}
