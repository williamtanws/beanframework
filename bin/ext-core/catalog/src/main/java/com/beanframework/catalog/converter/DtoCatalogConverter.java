package com.beanframework.catalog.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.beanframework.catalog.domain.Catalog;
import com.beanframework.catalog.service.CatalogService;

@Component
public class DtoCatalogConverter implements Converter<Catalog, Catalog> {

	@Autowired
	private CatalogService catalogService;

	@Override
	public Catalog convert(Catalog source) {
		return convert(source, catalogService.create());
	}

	public List<Catalog> convert(List<Catalog> sources) {
		List<Catalog> convertedList = new ArrayList<Catalog>();
		for (Catalog source : sources) {
			convertedList.add(convert(source));
		}
		return convertedList;
	}

	private Catalog convert(Catalog source, Catalog prototype) {

		prototype.setUuid(source.getUuid());
		prototype.setId(source.getId());
		prototype.setName(source.getName());
		prototype.setSort(source.getSort());
		prototype.setActive(source.getActive());
		prototype.setCreatedBy(source.getCreatedBy());
		prototype.setCreatedDate(source.getCreatedDate());
		prototype.setLastModifiedBy(source.getLastModifiedBy());
		prototype.setLastModifiedDate(source.getLastModifiedDate());

		return prototype;
	}

}
